package cz.borec.demo.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class NumberFormatterTest {

	@Test
	public void test() {
		NumberFormatter numberFormatter = new NumberFormatter();
		
		BigDecimal a = BigDecimal.valueOf(5555555.0);
		BigDecimal b = BigDecimal.valueOf(5555555.1);
		BigDecimal c = BigDecimal.valueOf(5555555.11);
		BigDecimal d = BigDecimal.valueOf(5555555.111);
		BigDecimal e = BigDecimal.valueOf(Math.PI * 10000);

		System.out.println(numberFormatter.format(a));
		System.out.println(numberFormatter.format(b));
		System.out.println(numberFormatter.format(c));
		System.out.println(numberFormatter.format(d));
		System.out.println(numberFormatter.format(e));
	
	
	}

}
