package cz.borec.demo.gui.controls;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Settings {

	public static Background getBackground() {
		return new Background(new BackgroundFill(Color.LIGHTSKYBLUE, new CornerRadii(5.0), Insets.EMPTY));
	}
}
