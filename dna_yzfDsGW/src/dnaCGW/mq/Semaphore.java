package dnaCGW.mq;
/** 
 * @author liufeng E-mail:liu.feng@payeco.com 
 * @version ����ʱ�䣺2014��11��28�� ����11:04:16 
 * ��˵�� 
 */

public class Semaphore {
	 private int count;
	    public Semaphore(int count){
	        this.count=count;
	    }
	    synchronized public void acquire() {//throws InterruptedException{
	        while (count==0){
	            try {
	                wait();
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	        count --;
	    }
	    synchronized public void release(){
	        count ++;
	        notify();
	    }

}
