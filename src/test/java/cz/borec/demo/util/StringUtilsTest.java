package cz.borec.demo.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void test() {
		String s = StringUtils.splitIntoLines("Odrudové víno bílé");
		assertEquals("Odrudové\nvíno\nbílé", s);
		s = StringUtils.splitIntoLines("Odrudové víno bílé ");
		assertEquals("Odrudové\nvíno\nbílé", s);
	}

}
