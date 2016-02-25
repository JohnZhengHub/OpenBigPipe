package start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bigpipe.action.LoginAction;

public class Test {
	public static void main(String[] args){
		try{
			ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
			LoginAction loginAction = (LoginAction)cxt.getBean("loginAction");
			loginAction.login();
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
}
