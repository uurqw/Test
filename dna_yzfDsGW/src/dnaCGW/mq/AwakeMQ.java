package dnaCGW.mq;


import dnaCGW.common.ConfigUtil;
import dnaCGW.common.StringUtil;
import dnaCGW.common.ToolKit;
import dnaCGW.mq.MqService;

/** 
 * @author liufeng E-mail:liu.feng@payeco.com 
 * @version ����ʱ�䣺2015��5��14�� ����9:55:24 
 * ��˵�� 
 */

public class AwakeMQ extends Thread {
	private MqService ms=null;	
	public  AwakeMQ(MqService ms) {
		this.ms=ms;		
	}
	public void run(){
		try {
			Thread.sleep(Long.parseLong(ConfigUtil.getValue("stopMQTime"))*60*1000);
		} catch (Exception e) {
			ToolKit.writeLog(this.getClass().getName(), StringUtil.toString(e));
		}
		ms.setWorking(true);
	}

}
