package cz.borec.demo;

public enum PrinterWidth {
	mm58,
	mm76,
	mm80;
	
	
	@Override
	public String toString() {
		String s = null;
		switch(this) {
		case mm58:
			s = "58 mm";
			break;
		case mm76:
			s = "76 mm";
			break;
		case mm80:
			s = "80 mm";
			break;
		}
		return s;
	}
	

}
