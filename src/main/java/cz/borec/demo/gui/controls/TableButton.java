package cz.borec.demo.gui.controls;

import java.net.URL;

public class TableButton extends LiveButton {

	public TableButton(String name) {
		super(name);
		
		URL url = getClass().getClassLoader().getResource("images/wood3.jpg");
		String image = url.toString();
		
		setStyle("-fx-background-image: url('" + image + "'); "
				+ " -fx-background-position: center center; "
				+ " -fx-background-repeat: stretch;"
				+ " -fx-font-size: 13px; "
				+ " -fx-text-fill: black;");
		
	}

/*	@Override
	protected void set() {
		
		super.set();
		setBackground(new Background(
				new BackgroundFill(Color.BURLYWOOD, new CornerRadii(5.0), Insets.EMPTY)));
	}*/
	
	protected String getCCSId() {
		
		return "table";
	}


}
