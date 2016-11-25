package cz.borec.demo;

import java.math.BigDecimal;

public enum DPH {
	DPH_21,
	DPH_15,
	DPH_10;

	@Override
	public String toString() {
		String s = null;
		switch(this) {
		case DPH_21:
			s = "21 %";
			break;
		case DPH_15:
			s = "15 %";
			break;
		case DPH_10:
			s = "10 %";
			break;
		}
		return s;
	}

	public BigDecimal getValue() {
		switch(this) {
		case DPH_21:
			return BigDecimal.valueOf(0.21);
		case DPH_15:
			return BigDecimal.valueOf(0.15);
		case DPH_10:
			return BigDecimal.valueOf(0.10);
		}
		return null;
	}
	
	
}
