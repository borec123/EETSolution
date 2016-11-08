package cz.borec.demo.util;

import org.junit.Test;

public class DatabaseConnectionTesterTest {

	@Test
	public void test() {
		boolean b = DatabaseConnectionTester.testConnection();
		System.out.println(b);
	}

}
