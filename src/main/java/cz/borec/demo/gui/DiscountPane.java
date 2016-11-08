package cz.borec.demo.gui;

import java.math.BigDecimal;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import cz.borec.demo.gui.controls.LiveButton;

public class DiscountPane extends GridPane {

	private boolean OK = false;

	BigDecimal amount;
	//private AmountObject amountObject;
	private TextField textField;

	private String fullName;

	public DiscountPane() {
		super();
		textField = new TextField();
		LiveButton b2 = new LiveButton("OK");
		LiveButton b4 = new LiveButton("Zrušit");
		b2.setPrefWidth(100);
		b2.setPrefHeight(100);
		textField.setPrefWidth(50);
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(20);
		setVgap(20);
		add(textField, 0, 0, 1, 3);
		add(b2, 1, 1);
		add(b4, 2, 1);
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
					amount = new BigDecimal(Double.parseDouble(value));
					//amountObject.setAmount(amount);
				} catch (Exception e) {
					textField.textProperty().setValue(amount.toString());
				}
			}
		});
	}

	public void setAmount(BigDecimal bigDecimal) {
		this.amount = bigDecimal;
		
		textField.setText(bigDecimal.toString());
	}

	public boolean isOK() {
		return OK;
	}
	
	protected void closeWindow() {
		OK = true;
		this.getScene().getWindow().hide();

	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
		
	}

}
