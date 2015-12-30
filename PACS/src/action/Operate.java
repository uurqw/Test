package action;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import pojo.User;

public class Operate {
	//在Hibernate中，所有的操作都是通过Session完成
    //此Session不同于JSP的Session
	private Session session = null;
	 //在构造方法之中实例化session对象
	public Operate(){
		// 找到Hibernate配置
		Configuration config = new Configuration().configure();
		//从配置中取出SessionFactory
		SessionFactory factory = config.buildSessionFactory();
		//从SessionFactory中取出一个Session
		session = factory.openSession();
	}
	//所有操作都是由session进行的
    //向数据库中增加数据
	public void insert(User user){
		//开始事务
		Transaction tran = this.session.beginTransaction();
		//执行语句
		this.session.save(user);
		//提交事务
		tran.commit();
	}
}
