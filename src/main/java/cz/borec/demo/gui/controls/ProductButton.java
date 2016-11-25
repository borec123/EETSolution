package cz.borec.demo.gui.controls;

public class ProductButton extends LiveButton {
	public ProductButton(String arg0) {
		super(arg0);
		setPrefSize(ButtonSizeUtils.getTouchButtonSize(), ButtonSizeUtils.getTouchButtonSize());
	}

	public ProductButton() {
		super();
	}
		protected String getCCSId() {
		
		return "product";
	}


}
