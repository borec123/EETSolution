package cz.borec.demo.gui.controls;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BlueText extends Text {

	public BlueText() {
		super();
		init();
	}

	private void init() {
		init(18);
	}

	private void init(int fontSize) {
	    setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
	    setFill(Color.WHITE);
	    setStroke(Color.web("#6777A0")); //7080A0")); 
	}

	public BlueText(String arg0) {
		super(arg0);
		init();
	}

	public BlueText(String arg0, int fontSize) {
		super(arg0);
		init(fontSize);
	}

}
