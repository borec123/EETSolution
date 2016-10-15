package cz.borec.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Context {
	private static ApplicationContext applicationContext = null;
	
	public static ApplicationContext getApplicationContext() {
		if(applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext(
					"spring/application.xml");
		}
		return applicationContext;
	}

}
