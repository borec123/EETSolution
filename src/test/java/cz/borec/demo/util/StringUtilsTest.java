package cz.borec.demo.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void test() {
		String s = StringUtils.splitIntoLines("Odrudov� v�no b�l�");
		assertEquals("Odrudov�\nv�no\nb�l�", s);
		s = StringUtils.splitIntoLines("Odrudov� v�no b�l� ");
		assertEquals("Odrudov�\nv�no\nb�l�", s);
	}

}
