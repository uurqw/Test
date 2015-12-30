package dnaCGW.mq;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import dnaCGW.common.*;

public class MqService {
	private static Logger logger = Logger.getLogger("dnaCGW.mq.MqService");
	private String MQ_MGR = "";
	private String MQ_request = "";
	private String MQ_response = "";
	private int TIME_OUT = 1000 * 60;
	private Hashtable<Object, Object> properties = new Hashtable<Object, Object>();
	private Queue<MQQueueManager> mqMgrList = new ConcurrentLinkedQueue<MQQueueManager>(); // �̰߳�ȫ�ģ�������
	private Semaphore semaphore;
	private static MqService instanceA;
	private static MqService instanceB;
	private int msgCount = 0;
	private boolean Working = true;
	private int MGRFailTime = 0;
	private Object MGRFailTimeLock = new Object();
	private Object MGRFailTimeLock2 = new Object();
	private Object msgCountLock = new Object();
	private int poolSize = Integer.parseInt(ConfigUtil.getValue("mqManagerPoolSize"));
	private MqService() {
		
	}

	public static MqService getInstance(String MQName) { // MQName:MQA,MQB
		if (MQName.equals("MQA")) {
			if (instanceA == null) {
				instanceA = new MqService(ConfigUtil.getValue("MQA"));
			}
			return instanceA;
		} else {
			if (instanceB == null) {
				instanceB = new MqService(ConfigUtil.getValue("MQB"));
			}
			return instanceB;
		}
	}

	public void close() {
		int n;
		int i;
		n = mqMgrList.size();
		for (i = 0; i < n; i++) {
			try {
				mqMgrList.poll().disconnect();
				ToolKit.writeLog(this.getClass().getName(), "�Ͽ���" + i + "��MQ����");
			} catch (MQException e) {
				ToolKit.writeLog(this.getClass().getName(), "�Ͽ�MQ�����쳣��", e);
			}
		}
	}

	private MqService(String mqinterface) {
		logger.info("mq������Ϣ��" + mqinterface);
		String[] infos = mqinterface.split("\\|");
		properties.put("hostname", infos[0]);
		properties.put("port", Integer.valueOf(infos[1]));
		properties.put("channel", infos[2]);
		properties.put("userID", infos[3]);
		properties.put(MQConstants.PASSWORD_PROPERTY, infos[4]);
		MQ_MGR = infos[5];
		MQ_request = infos[6];
		MQ_response = infos[7];
		TIME_OUT = Integer.parseInt(infos[8]);
		semaphore = new Semaphore(poolSize);
		MQQueueManager qMgr = null;
		for (int i = 0; i < poolSize; i++) {
			try {
				qMgr = new MQQueueManager(MQ_MGR, properties);
			} catch (MQException e) {
				ToolKit.writeLog(this.getClass().getName(), "����Mq�������쳣��", e);
			}
			if (qMgr == null) {
				logger.info("����qMgr�쳣");
			} else {
				mqMgrList.offer(qMgr);
				logger.info("������" + i + "��qMgr!");
			}
		}
	}

	/**
	 * �����������
	 */
	public void put(String msgId, String msg) {
		put(msgId, msg, MQ_response, TIME_OUT);
	}

	/*
	 * ���key����Ӧ�������ȡ��Ϣ timeout ��λ ����
	 */
	public String getValue(String key, int timeout) {
		String msg = null;
		try {
			msg = getValue(key, MQ_response, timeout);
		} catch (Exception e) {
			logger.error(StringUtil.toString(e));
		}
		return msg;
	}

	/*
	 * expiryTime ��λ ms
	 */

	public boolean put(String msgID, String msg, String QueueName, int expiryTime) {
		if (!Working) {
			logger.warn("MQ���й�����:" + properties.get("hostname") + "," + MQ_MGR
					+ ",������");
			return false;
		}
		MQQueueManager qMgr = null;
		MQQueue replyQueue = null;
		if (expiryTime < 30000) {
			expiryTime = 80000;
		}
		qMgr = getQManager();
		if (qMgr == null) {
			logger.warn("qMgr is null,key:" + msgID + ",write to mq failed!");
			semaphore.release();
			return false;
		}

		try {
			int openOptions = 8208;
			replyQueue = qMgr.accessQueue(QueueName, openOptions);
		} catch (Exception e) {
			semaphore.release();
			logger.error(StringUtil.toString(e));
			try {
				qMgr.disconnect();
			} catch (MQException e1) {
				logger.error(StringUtil.toString(e1));
			}
			addMGRFailTime();
			if (getMGRFailTime() > poolSize) {
				// setWorking(false);
				suspendMQ(this);
			}
			return false;
		}

		if (replyQueue == null) {
			logger.warn("replyQueue is null,key:" + msgID
					+ ",write to mq failed!");
			semaphore.release();
			return false;
		}

		MQMessage outMsg = new MQMessage();
		outMsg.format = "MQSTR   ";
		outMsg.messageType = MQConstants.MQMT_REPLY;
		outMsg.encoding = 273;
		outMsg.characterSet = 1381;
		outMsg.expiry = expiryTime / 100;
		logger.info("MQ��Ϣ��ʱʱ�䣺" + outMsg.expiry / 10 + "s");
		try {
			if (msgID != null && !msgID.equals("")) {
				outMsg.correlationId = msgID.getBytes("UTF-8");
				outMsg.messageId = msgID.getBytes("UTF-8");
			}
		} catch (Exception e) {
			logger.error(StringUtil.toString(e));
			semaphore.release();
			return false;
		}

		minusMGRFailTime();

		try {
			outMsg.writeString(msg);
			MQPutMessageOptions pmo = new MQPutMessageOptions();
			pmo.options = pmo.options + 2;
			pmo.options = pmo.options + MQConstants.MQPMO_SYNCPOINT;
			replyQueue.put(outMsg, pmo);
			qMgr.commit();

			logger.info("��Ϣ����ɹ�:" + msgID + ";" + properties.get("hostname"));

		} catch (Exception e) {
			logger.error(StringUtil.toString(e));
		} finally {
			try {
				if (replyQueue != null)
					replyQueue.close();
				mqMgrList.offer(qMgr);
				semaphore.release();
				// qMgr.disconnect();
			} catch (Exception e) {
				logger.error(StringUtil.toString(e));
			}
		}
		return true;
	}

	/*
	 * timeOut ��λ������
	 */
	public String getValue(String key, String ResQueueName, int timeOut) {
		if (!Working) {
			logger.warn("MQ���й�����:" + properties.get("hostname") + "," + MQ_MGR
					+ ",������");
			return "fail";
		}
		logger.info("���Դ���Ӧ���л�ȡ��Ӧ���ģ�" + ResQueueName);
		MQQueue rcvQueue = null;
		MQQueueManager qMgr = null;
		qMgr = getQManager();
		if (qMgr == null) {
			logger.warn("��ȡqMgrʧ�ܣ�");
			semaphore.release();
			return "fail";
		}

		try {
			int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
					| MQConstants.MQOO_OUTPUT | MQConstants.MQOO_INQUIRE;
			rcvQueue = qMgr.accessQueue(ResQueueName, openOptions);
		} catch (Exception e) {
			semaphore.release();
			logger.error(StringUtil.toString(e));
			try {
				qMgr.disconnect();
			} catch (MQException e1) {
				logger.error(StringUtil.toString(e1));
			}
			addMGRFailTime();
			if (getMGRFailTime() > poolSize) {
				// setWorking(false);
				suspendMQ(this);
			}
			return "fail";
		}
		minusMGRFailTime();

		MQMessage inMsg = new MQMessage();
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = gmo.options + 2;
		gmo.options = gmo.options + 1;
		gmo.options = gmo.options + 8192;
		gmo.waitInterval = timeOut; // ����
		inMsg.format = "MQSTR   ";
		inMsg.encoding = 273;
		inMsg.characterSet = 1381;

		try {
			if (key != null && !key.equals("")) {
				inMsg.messageId = key.getBytes("UTF-8");
			}
			rcvQueue.get(inMsg, gmo);
			qMgr.commit();
			byte iii[] = new byte[inMsg.getMessageLength()];
			inMsg.readFully(iii);
			String str = new String(iii, "UTF-8");
			return str;

		} catch (Exception e) {
			if (!StringUtil.toString(e).contains("2033"))
				logger.error(StringUtil.toString(e));
		} finally {
			try {
				if (rcvQueue != null){
					rcvQueue.close();
				}
				mqMgrList.offer(qMgr);
				semaphore.release();
				// qMgr.disconnect();
			} catch (MQException e) {
				logger.error(StringUtil.toString(e));
			}
		}
		return "";
	}

	/**
	 * �ӳ�ʼ������������л�ȡ��������
	 */
	public String[] getValue() {
		return getValue(MQ_request);
	}

	/**
	 * ��������л�ȡ
	 * 
	 * @return
	 */
	public String[] getValue(String reqQueue) {
		String[] res = new String[2];
		res[0] = "";
		res[1] = "";
		if (!Working) {
			logger.warn("MQ���й�����:" + properties.get("hostname") + "," + MQ_MGR + ",������;���У�" + reqQueue + "���ɷ��ʡ�");
			return res;
		}
		MQQueue rcvQueue = null;
		MQQueueManager qMgr = null;
		String xmlString = null;
		String key = null;

		qMgr = getQManager();
		if (qMgr == null) {
			logger.warn("qMgr is null,get msg from mq failed!");
			semaphore.release();
			return res;
		}
		int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
				| MQConstants.MQOO_OUTPUT | MQConstants.MQOO_INQUIRE;
		try {
			rcvQueue = qMgr.accessQueue(reqQueue, openOptions);
		} catch (Exception e) {
			semaphore.release();
			logger.error(StringUtil.toString(e));
			try {
				qMgr.disconnect();
			} catch (MQException e1) {
				logger.error(StringUtil.toString(e1));
			}
			addMGRFailTime();
			if (getMGRFailTime() > poolSize) {
				// setWorking(false);
				suspendMQ(this);
			}
			return res;
		}

		if (rcvQueue == null) {
			logger.warn("replyQueue is null,get reqMsg from mq failed!");
			semaphore.release();
			return res;
		}

		minusMGRFailTime();
		try {
			MQMessage inMsg = new MQMessage();
			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = gmo.options + 2;
			gmo.options = gmo.options + 1;
			gmo.options = gmo.options + 8192;
			gmo.waitInterval = 5 * 1000;

			inMsg.format = "MQSTR   ";

			inMsg.encoding = 273;
			inMsg.characterSet = 1381;

			int curdepth = rcvQueue.getCurrentDepth();
			logger.info("The depth of the MQ:" + this.getProperties().get("hostname") + " ; queue : " + reqQueue + ": " + curdepth);
			if (curdepth > 0) {
				rcvQueue.get(inMsg, gmo);
				byte iii[] = new byte[inMsg.getMessageLength()];
				inMsg.readFully(iii);
				key = new String(inMsg.messageId, "UTF-8");
				xmlString = new String(iii, "GB18030");
			}
			qMgr.commit();
			res[0] = key;
			res[1] = xmlString;
		} catch (Exception e) {
			logger.error(StringUtil.toString(e));
		} finally {
			try {
				if (rcvQueue != null){
					rcvQueue.close();
				}
				mqMgrList.offer(qMgr);
				semaphore.release();
				// qMgr.disconnect();
			} catch (MQException e) {
				logger.error(StringUtil.toString(e));
			}
		}

		return res;
	}

	private MQQueueManager getQManager() {
		long a = System.currentTimeMillis();
		semaphore.acquire();
		long b = System.currentTimeMillis();
		logger.debug("�ȴ��ź�����ʱ��" + (b - a));
		MQQueueManager qMgr = null;
		qMgr = mqMgrList.poll();
		if (qMgr == null || !qMgr.isOpen() || !qMgr.isConnected()) {
			logger.info("qMgr is null or not open,creating a new mqManager and add to the qMgr pool");
			try {
				if (qMgr != null) {
					qMgr.disconnect();
					qMgr = null;
				}
				qMgr = new MQQueueManager(MQ_MGR, properties);
			} catch (MQException e) {
				ToolKit.writeLog(this.getClass().getName(), "����Mq�������쳣��", e);
				addMGRFailTime();
				if (getMGRFailTime() > poolSize) {
					// setWorking(false);
					suspendMQ(this);
				}
			}

		}
		return qMgr;
	}

	public boolean isWorking() {
		return Working;
	}

	public void setWorking(boolean working) {
		logger.info(this.getProperties().get("hostname") + "mq ����״̬����Ϊ��"
				+ working);
		Working = working;
	}

	public void suspendMQ(MqService ms) {
		setWorking(false);
		AwakeMQ a = new AwakeMQ(ms);
		a.start();
	}

	public void addMGRFailTime() {
		synchronized (MGRFailTimeLock) {
			logger.info("����mq������ʧ��,���У�" + MQ_request + "����ʧ�ܣ�����ʧ�ܴ����ۼ�1");
			MGRFailTime += 1;
		}
	}

	public void minusMGRFailTime() {
		synchronized (MGRFailTimeLock2) {
			logger.debug("����mq�������ɹ������У�" + MQ_request + "���ʳɹ�������ʧ�ܴ����ȥ1");
			if (MGRFailTime > 0) {
				MGRFailTime -= 1;
			}
		}
	}

	public int getMGRFailTime() {
		return MGRFailTime;
	}

	public void setMGRFailTime(int mGRFailTime) {
		MGRFailTime = mGRFailTime;
	}

	public void addMsgCount() {
		synchronized (msgCountLock) {
			msgCount++;
			/*
			 * if(msgCount>100){ msgCount=0; }
			 */
		}
	}

	public int getMsgCount() {
		synchronized (msgCountLock) {
			return msgCount;
		}
	}

	public void setMsgCount(int msgCount) {
		synchronized (msgCountLock) {
			this.msgCount = msgCount;
		}
	}

	public Hashtable<Object, Object> getProperties() {
		return properties;
	}

	public void setProperties(Hashtable<Object, Object> properties) {
		this.properties = properties;
	}

}
