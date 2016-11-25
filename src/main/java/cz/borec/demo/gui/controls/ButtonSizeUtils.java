package cz.borec.demo.gui.controls;

import cz.borec.demo.Constants;

public class ButtonSizeUtils {

	public static double getTouchButtonSize() {
		if (AppPropertiesProxy.getDisplaySize() == 2) {
			return Constants.BUTTON_SIZE + 10;
		} else if (AppPropertiesProxy.getDisplaySize() == 3) {
			return Constants.BUTTON_SIZE + 25;
		} else {
			return Constants.BUTTON_SIZE;
		}

	}

	public static double getLiveButtonHeight() {
		if (AppPropertiesProxy.getDisplaySize() == 2) {
			return 45;
		} else if (AppPropertiesProxy.getDisplaySize() == 3) {
			return 55;
		} else {
			return 0;
		}

	}

	public static double getLiveButtonWidth() {
		if (AppPropertiesProxy.getDisplaySize() == 2) {
			return 170;
		} else if (AppPropertiesProxy.getDisplaySize() == 3) {
			return 200;
		} else {
			return 140;
		}

	}

	public static double getRoomButtonHeight() {
		if (AppPropertiesProxy.getDisplaySize() == 2) {
			return Constants.TOUCH_BUTTON_HEIGHT + 5;
		} else if (AppPropertiesProxy.getDisplaySize() == 3) {
			return Constants.TOUCH_BUTTON_HEIGHT + 10;
		} else {
			return Constants.TOUCH_BUTTON_HEIGHT;
		}

	}

	public static double getRoomButtonWidth() {
		if (AppPropertiesProxy.getDisplaySize() == 2) {
			return Constants.TOUCH_BUTTON_WIDTH + 20;
		} else if (AppPropertiesProxy.getDisplaySize() == 3) {
			return Constants.TOUCH_BUTTON_WIDTH + 40;
		} else {
			return Constants.TOUCH_BUTTON_WIDTH;
		}

	}

}
