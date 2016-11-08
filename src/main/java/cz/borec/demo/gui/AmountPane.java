package cz.borec.demo.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import cz.borec.demo.gui.controls.LiveButton;

public class AmountPane extends GridPane {

	private boolean OK = false;

	Integer amount;
	//private AmountObject amountObject;
	private TextField textField;

	public AmountPane() {
		super();
		textField = new TextField();
		LiveButton b1 = new LiveButton("+");
		LiveButton b2 = new LiveButton("OK");
		LiveButton b3 = new LiveButton("-");
		LiveButton b4 = new LiveButton("Zru\u0161it");
		b1.setPrefWidth(100);
		b2.setPrefWidth(100);
		b2.setPrefHeight(100);
		b3.setPrefWidth(100);
		b3.setDefaultButton(true);
		textField.setPrefWidth(50);
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(20);
		setVgap(20);
		add(textField, 0, 0, 1, 3);
		add(b1, 1, 0);
		add(b2, 1, 1);
		add(b3, 1, 2);
		add(b4, 2, 1);
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//assert amountObject != null : "amountObject cannot be null.";
				amount++;
				//amountObject.setAmount(amount);
				textField.setText(Integer.toString(amount));
			}
		});
		b3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//assert amountObject != null : "amountObject cannot be null.";
				if (amount > 1) {
					amount--;
					//amountObject.setAmount(amount);
				}
				textField.setText(Integer.toString(amount));
			}
		});
		b2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				closeWindow();

			}
		});
		
		b4.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				OK = false;
				getScene().getWindow().hide();
			}
		});

		textField.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				String value = ((StringProperty) observable).get();
				try {
					amount = Integer.parseInt(value);
					//amountObject.setAmount(amount);
				} catch (Exception e) {
					textField.textProperty().setValue(Integer.toString(amount));
				}
			}
		});
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
		
		textField.setText(Integer.toString(amount));
	}

	/*	public void setAmountObject(AmountObject o) {
		amountObject = o;
		this.amount = o.getAmount();
		textField.setText(Integer.toString(amount));

	}
*/
	public boolean isOK() {
		return OK;
	}
	
	protected void closeWindow() {
		OK = true;
		this.getScene().getWindow().hide();

	}

	public Integer getAmount() {
		return amount;
	}

}
