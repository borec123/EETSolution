package cz.borec.demo.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import cz.borec.demo.Context;

public class DatabaseConnectionTester {
	public static boolean testConnection() {
		ApplicationContext applicationContext = Context.getApplicationContext();

		DriverManagerDataSource dataSorce = (DriverManagerDataSource) applicationContext
				.getBean("dataSource");
		
		try {
			Connection connection = dataSorce.getConnection();
			return connection != null;
		} catch (SQLException e) {

			//e.printStackTrace();
			return false;
		}
		
		finally {
			System.out.println("----------------> finally block");
		}

	}
}
