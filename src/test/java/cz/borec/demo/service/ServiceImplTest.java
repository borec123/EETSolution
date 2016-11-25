package cz.borec.demo.service;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class ServiceImplTest {

	@Test
	public void test() {
		Locale[] l = Locale.getAvailableLocales();
		for (Locale locale : l) {
			System.out.println(locale);
		}
		
		DateFormat formatData = new SimpleDateFormat("d.MM.yyyy H:mm");

		
		System.out.println(formatData.format(new Date()));
		System.out.println(DateFormat.getDateInstance(DateFormat.LONG, new Locale("cs")).format(new Date()));
		
		DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("cs_CZ"));
		
		
		
	}

}
