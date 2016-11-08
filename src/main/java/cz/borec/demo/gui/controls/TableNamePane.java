package cz.borec.demo.gui.controls;

import java.math.BigDecimal;

import cz.borec.demo.core.dto.TableType;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.util.Callback;

public class TableNamePane extends GridPane {

//	/private static final String[] type_s = { "Standard", "Velk\u00FD", "\u017Didle", "Horizont\u00E1ln\u00ED", "Vertik\u00E1ln\u00ED"};
	private static final ObservableList types = FXCollections.observableArrayList(TableType.values());
	private boolean OK = false;
	private ComboBox comboBoxType;

	private TextField textField;

	private String fullName;
	protected TableType type;


	public TableNamePane() {
		super();
		textField = new TextField();
		LiveButton b2 = new LiveButton("OK");
		LiveButton b4 = new LiveButton("Zru\u0161it");
		b2.setPrefWidth(100);
		b2.setPrefHeight(100);
		textField.setPrefWidth(50);
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(20);
		setVgap(20);
		add(new Label("N\u00E1zev: "), 0, 0);
		add(textField, 1, 0);
		add(new Label("Typ: "), 0, 1);
		comboBoxType = new ComboBox(types);
		comboBoxType.valueProperty().addListener(new ChangeListener<TableType>() {

			@Override
			public void changed(ObservableValue<? extends TableType> observable, TableType oldValue, TableType newValue) {
				type = newValue;
				
			}
		});
		add(comboBoxType, 1, 1);
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

		/*textField.textProperty().addListener(new InvalidationListener() {
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
		});*/
		
		comboBoxType = new ComboBox (types);

	}
	
	public TableType getType() {

		return type;
	}

	public void setType(TableType type) {
		this.comboBoxType.getSelectionModel().select(type);
		this.comboBoxType.setValue(type);
	}



	public void setName(String name) {
		
		textField.setText(name);
	}

	public boolean isOK() {
		return OK;
	}
	
	protected void closeWindow() {
		OK = true;
		this.getScene().getWindow().hide();

	}

	public String getName() {
		return textField.getText();
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
		
	}

}
