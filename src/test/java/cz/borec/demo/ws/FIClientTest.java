package cz.borec.demo.ws;

import static org.junit.Assert.*;

import org.junit.Test;

public class FIClientTest {

	@Test
	public void test() {
		String s = "2016-07-03T14:39:18.276+02:00";
		s = s.replaceFirst("\\.\\d+", "");
		assertEquals(s, "2016-07-03T14:39:18+02:00");
	}

}
