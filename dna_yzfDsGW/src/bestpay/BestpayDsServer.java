package bestpay;

import org.apache.log4j.Logger;

import dnaCGW.common.ConfigUtil;
import dnaCGW.common.StringUtil;
import dnaCGW.common.Strings;
import dnaCGW.common.ToolKit;
import dnaCGW.mq.*;

public class BestpayDsServer {
	private boolean work = true;
	private static BestpayDsServer proxy = new BestpayDsServer();
	private Logger logger = Logger.getLogger(BestpayDsServer.class.getName());
	private long suspendTime = 0;
	private int sendFailNum = 0;
	private Object sendFailNumLock = new Object();
	private Object suspendTimeLock = new Object();
	
	/*
	  public static void main(String[] args){
		  new BestpayDsServer().run();
	  }
	*/
	
	public void run(){
		MqService ms1 = MqService.getInstance("MQA");
		MqService ms2 = MqService.getInstance("MQB");
		long sleep = 200;
		long sleepTime = 400;
		boolean isWorking = true;
		String processName = "bestpay.BestpayDsServer";
		while(work){
			isWorking = Boolean.parseBoolean(ConfigUtil.getValue("isworking"));
			try{
				sleepTime = Long.parseLong(ConfigUtil.getValue("sleepTime"));
				ToolKit.writeLog(processName, "sleepTime=" + sleepTime);
			}catch(Exception e){
				logger.error(StringUtil.toString(e));
			}
			if(!isWorking){
				logger.info("配置文件config.ini已经设置停止工作,网关不再读mq");
				try{
					logger.info("睡眠五分钟");
					Thread.sleep(5 * 60 * 1000);
				}catch(InterruptedException e){
					logger.error(StringUtil.toString(e));
				}
			}
			try{
				String[] req = ms1.getValue();
				if(!Strings.isNullOrEmpty(req[1])){
					sleep = sleepTime;
					Handler handler = new Handler(req,proxy,ms1);
					handler.start();
				}else if(sleep<5 * 1000){
					sleep += 200;
				}
			}catch(Exception e){
				logger.error(StringUtil.toString(e));
			}
			try {
				logger.info("suspendServerTime:" + getSuspendTime() + " ms");
				Thread.sleep(sleep + getSuspendTime());
			} catch (InterruptedException e) {
				logger.error(StringUtil.toString(e));
			}
			sleep = sleepTime;
			try{
				String[] req = ms2.getValue();
				if (!StringUtil.isNullOrEmpty(req[1])) {
					sleep = sleepTime;
					Handler handler = new Handler(req, proxy, ms2);
					handler.start();
				} else if (sleep < 5 * 1000) {
					sleep += 200;
				}
			}catch(Exception e){
				logger.error(StringUtil.toString(e));
			}
			try {
				logger.info("suspendServerTime:" + getSuspendTime() + " ms");
				Thread.sleep(sleep + getSuspendTime());
			} catch (InterruptedException e) {
				logger.error(StringUtil.toString(e));
			}
		}
	}
	
	public long getSuspendTime() {
		synchronized (suspendTimeLock) {
			return suspendTime;
		}
	}

	public void setSuspendTime(long suspendTime) {
		synchronized (suspendTimeLock) {
			this.suspendTime = suspendTime;
		}
	}

	public int getSendFailNum() {
		synchronized (sendFailNumLock) {
			return sendFailNum;
		}
	}

	public void setSendFailNum(int sendFailNum) {
		synchronized (sendFailNumLock) {
			this.sendFailNum = sendFailNum;
		}
	}

	public void addSendFailNum() {
		synchronized (sendFailNumLock) {
			logger.info("发送或接送银联返回失败，连续失败次数增加1,当前连续失败次数：" + sendFailNum);
			sendFailNum += 1;
		}
	}

	public void minusSendFailNum() {
		synchronized (sendFailNumLock) {

			setSendFailNum(0);
			setSuspendTime(0);
			logger.info("发送或接送银联返回成功，连续失败次数重置为0,睡眠时间重置为0;当前连续失败次数："
					+ sendFailNum);
		}
	}
}
