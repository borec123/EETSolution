package cz.borec.demo.core.entity;

import cz.borec.demo.Constants;

public enum OrderState {
	PREPARING,
	SHIFT,
	HAND_OVER,
	STORNO;

	@Override
	public String toString() {
		switch (this) {
		case PREPARING:
			return Constants.LABEL_PREPARING;
		case SHIFT:
			return Constants.LABEL_SHIFT;
		case HAND_OVER:
			return Constants.LABEL_HAND_OVER;
		case STORNO:
			return Constants.LABEL_STORNO;
		default:
			return super.toString();
		}
	}
	
	
}
