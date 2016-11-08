package cz.borec.demo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter {

	private DecimalFormatSymbols symbols;
	private DecimalFormat formatter;
	private static NumberFormatter instance = null;
	
	public String formatInternal(BigDecimal number) {
		
		
    	String s = formatter.format(number.setScale(2, BigDecimal.ROUND_UP));
    	if(!s.contains(".")) {
    		s += ".00";
    	}
    	if(s.indexOf('.') == s.length() - 2) {
    		s += '0';
    	}
		return s;
	}

	public NumberFormatter() {
		super();
		formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		formatter.setDecimalFormatSymbols(symbols);
	}

	public static String format(BigDecimal number) {
		
		return getInstance().formatInternal(number);
	}

	private static NumberFormatter getInstance() {
		if(instance == null) {
			instance = new NumberFormatter();
		}
		return instance;
	}
}
