package cz.borec.demo.ws;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringExtractor {
	
	public static String extractFIK(String reponse) {
		String pattern = "fik=\"([^\"]+)\"";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(reponse);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	public static String extractChyba(String reponse) {
		String pattern = "Chyba.*>([^<]+)</";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(reponse);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

}
