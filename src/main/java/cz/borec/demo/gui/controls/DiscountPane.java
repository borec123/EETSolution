package cz.borec.demo.gui.controls;

import java.math.BigDecimal;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DiscountPane extends GridPane {

	private boolean OK = false;

	BigDecimal amount;
	//private AmountObject amountObject;
	private TextField textFieldDiscount;

	private TextField textField;

	public DiscountPane() {
		super();
		textField = new TextField();
		textFieldDiscount = new TextField();
		LiveButton b2 = new LiveButton("OK");
		LiveButton b4 = new LiveButton("Zru\u0161it");
		b2.setPrefWidth(100);
		b2.setPrefHeight(100);
		textFieldDiscount.setPrefWidth(50);
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(20);
		setVgap(20);
		add(new Label("N\u00E1zev \u00FA\u010Dtu: "), 0, 0);
		add(textField, 1, 0);
		add(new Label("Sleva: "), 0, 1);
		add(textFieldDiscount, 1, 1);
		add(b2, 2, 0, 1, 2);
		add(b4, 3, 1);
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

		textFieldDiscount.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				String value = ((StringProperty) observable).get();
				try {
					amount = new BigDecimal(Double.parseDouble(value));
					//amountObject.setAmount(amount);
				} catch (Exception e) {
					textFieldDiscount.textProperty().setValue(amount.toString());
				}
			}
		});
	}

	public void setAmount(BigDecimal bigDecimal) {
		this.amount = bigDecimal;
		
		textFieldDiscount.setText(bigDecimal.toString());
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
		textField.setText(fullName);
	}

	public String getFullName() {
		return textField.getText();
	}

}
