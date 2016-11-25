package cz.borec.demo.gui.controls;

import cz.borec.demo.Constants;

public class SubCategoryButton extends LiveButton {

	public SubCategoryButton(String arg0) {
		super(arg0);
		setPrefSize(ButtonSizeUtils.getTouchButtonSize(), ButtonSizeUtils.getTouchButtonSize());
	}

	public SubCategoryButton() {
		super();
	}

	protected String getCCSId() {

		return "subCategory";
	}

}
