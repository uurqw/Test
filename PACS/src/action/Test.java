package action;
import pojo.User;

public class Test {
	public static void main(String[] args){
		User doctor = new User(001,"123","ҽ��");
		
		Operate oper = new Operate();
		
		oper.insert(doctor);
		
	}
}
