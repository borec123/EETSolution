package cz.borec.demo.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DatabaseStarterTest {

	@Test
	public void test() throws IOException, InterruptedException {
		int a = PostgreSQLDatabaseStarter.startDatabase();
		System.out.println(a);
		assertEquals(0, a);
	}


	@Test
	public void testH2Database() throws IOException, InterruptedException {
		int a = H2DatabaseStarter.startDatabase();
		System.out.println(a);
		assertEquals(0, a);
	}

}
