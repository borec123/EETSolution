package cz.borec.demo.gui.controls;

public class CategoryButton extends SubCategoryButton {
	public CategoryButton(String string) {
		super(string);
	}

	protected String getCCSId() {
		
		return "category";
	}


}
