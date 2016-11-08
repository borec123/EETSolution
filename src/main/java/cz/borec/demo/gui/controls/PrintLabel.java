package cz.borec.demo.gui.controls;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PrintLabel extends Label {

	public PrintLabel() {
		super();
		init(5, false);
	}

	public PrintLabel(String arg0) {
		super(arg0);
		init(8, false);
	}

	public PrintLabel(String arg0, boolean bold) {
		super(arg0);
		init(8, bold);
	}

	public PrintLabel(int fontSize, boolean bold) {
		super();
		init(fontSize, bold);
	}

	private void init(int fontSize, boolean bold) {
	    setFont(Font.font(getFont().getName(), bold ? FontWeight.BOLD : FontWeight.NORMAL, fontSize));
	    // doesn't work: setAlignment(Pos.CENTER);
	}
}
