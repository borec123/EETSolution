package cz.borec.demo.util;

public class StringUtils {

	public static String splitIntoLines(String name) {
		name = name.trim();
		return name.replaceAll(" ", "\n");
	/*	for (int i = name.length() - 1; i >= 0; i--) {
			char c = name.charAt(i);
			if(c == ' ') {
				return name.substring(0, i ) + '\n' + name.substring(i + 1);
			}
		}
		return name;*/
	}

	public static String splitIntoLines2(String name) {
		name = splitIntoLines(name);
		return name.replaceFirst("\n", " ");

	}

}
