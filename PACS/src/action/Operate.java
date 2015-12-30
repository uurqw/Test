package action;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import pojo.User;

public class Operate {
	//��Hibernate�У����еĲ�������ͨ��Session���
    //��Session��ͬ��JSP��Session
	private Session session = null;
	 //�ڹ��췽��֮��ʵ����session����
	public Operate(){
		// �ҵ�Hibernate����
		Configuration config = new Configuration().configure();
		//��������ȡ��SessionFactory
		SessionFactory factory = config.buildSessionFactory();
		//��SessionFactory��ȡ��һ��Session
		session = factory.openSession();
	}
	//���в���������session���е�
    //�����ݿ�����������
	public void insert(User user){
		//��ʼ����
		Transaction tran = this.session.beginTransaction();
		//ִ�����
		this.session.save(user);
		//�ύ����
		tran.commit();
	}
}
