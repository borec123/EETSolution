package cz.borec.demo.gui.controls;

import cz.borec.demo.AppProperties;
import cz.borec.demo.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LiveButton extends Button {

	public LiveButton() {
		super();
		set();
	}

	protected void set() {
		// setTextFill(Color.WHITE);

		setFont(Font.font(getFont().getName(), FontWeight.NORMAL, 14));
		setId(getCCSId());

		/*
		 * setBackground(new Background( new BackgroundFill(Color.LIGHTBLUE, new
		 * CornerRadii(5.0), Insets.EMPTY)));
		 */
		// setPrefSize(100, 20);
		final DropShadow shadow = new DropShadow();
		Color c;
		if (AppProperties.getProperties().getLookId() == 2) {
			c = Color.LIGHTGRAY;
		} else {
			c = Color.BLACK;
		}
		shadow.setColor(c);
		setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(shadow);

			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(null);

			}
		});

		/*
		 * setPrefWidth(Constants.TOUCH_BUTTON_WIDTH);
		 * setPrefHeight(Constants.TOUCH_BUTTON_HEIGHT);
		 */

		if (AppPropertiesProxy.getDisplaySize() > 1) {
			setPrefHeight(ButtonSizeUtils.getLiveButtonHeight());
		}
	}

	protected String getCCSId() {

		return "kokot";
	}

	public LiveButton(String arg0) {
		super(arg0);
		set();
	}

}
