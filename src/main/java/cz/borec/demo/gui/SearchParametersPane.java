package cz.borec.demo.gui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

import cz.borec.demo.core.entity.UnitEntity;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class SearchParametersPane extends AbstractPaneBase {

	private static final Integer[] HOURS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	javafx.scene.control.DatePicker d =  new javafx.scene.control.DatePicker();
	private DatePicker dateTo = new javafx.scene.control.DatePicker();;
	private ComboBox comboBoxHours;
	private ComboBox comboBoxHours2;
    private LocalDate localDateFrom;
	private LocalDate localDateTo;
	
	public SearchParametersPane(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Reporty zadani od - do.");
		hbox.getChildren().add(arrow);
	}

	@Override
	protected void fillVBox(javafx.scene.layout.GridPane vbox) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pane addGridPane() {
		BorderPane borderPane = new BorderPane();

		borderPane.setCenter(createMainPane());
		
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(8, 10, 8, 10));
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		borderPane.setBottom(hbox);
		
		
		return borderPane;
	}
	
	@SuppressWarnings("restriction")
	protected Node createMainContent() {

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(30, 0, 0, 70));
		
		javafx.scene.control.Label label1 = new Label("Od:");
		d = new javafx.scene.control.DatePicker();
		
		  d.valueProperty().addListener(new ChangeListener<LocalDate>() {
				@Override
		        public void changed(
		                ObservableValue<? extends LocalDate> observableValue,
		                LocalDate oldValue, LocalDate newValue) {
		        	localDateFrom =  newValue;
		        }
		    });
		
		dateTo = new javafx.scene.control.DatePicker();
		dateTo.valueProperty().addListener(new ChangeListener<LocalDate>() {

				@Override
		        public void changed(
		                ObservableValue<? extends LocalDate> observableValue,
		                LocalDate oldValue, LocalDate newValue) {
		        	localDateTo =  newValue;
		        }
		    });

		ObservableList<Integer> unitList = 
			    FXCollections.observableArrayList(
			    		HOURS
			        );

		comboBoxHours = new ComboBox (unitList);
		comboBoxHours2 = new ComboBox (unitList);
		//comboBoxHours.setItems(FXCollections.observableArrayList(HOURS));
		grid.add(label1, 0, 0);
		grid.add(d, 1, 0);
		grid.add(comboBoxHours, 2, 0);
		label1 = new Label(":  00");
		grid.add(label1, 3, 0);

		setDate();
		
		
		
		label1 = new Label("Do:");
		grid.add(label1, 0, 1);
		
/*		label1 = new Label("Ted (" + new Date().toLocaleString() + ")");
		grid.add(label1, 1, 1, 3, 1);
*/		
/*		RadioButton radioButton = new javafx.scene.control.RadioButton();
		//radioButton.setPadding(new Insets(10, 10, 10, 10));
		radioButton.setText("Ted");
		grid.add(radioButton, 1, 1);
		
		
		radioButton = new javafx.scene.control.RadioButton();
		//radioButton.setPadding(new Insets(10, 10, 10, 10));
		radioButton.setText("Jindy");
		grid.add(radioButton, 2, 1);
	*/	
		
		//comboBoxHours.setItems(FXCollections.observableArrayList(HOURS));
		grid.add(dateTo, 1, 1);
		grid.add(comboBoxHours2, 2, 1);
		label1 = new Label(":  00");
		grid.add(label1, 3, 1);
		
	
		return grid;
	}

	private void setDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		LocalDate l = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		this.dateTo.setValue(l);
		int h = cal.get(Calendar.HOUR_OF_DAY);
		if(h < 24) {
			h++;
		}
		comboBoxHours2.setValue(h);
		
		if(hour >= 0 && hour < 5) {
			cal.add(Calendar.HOUR_OF_DAY, -24);
		}
		l = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		d.setValue(l);

		comboBoxHours.setValue(5);
	}

	
	protected void createButtons(HBox hbox) {
		LiveButton buttonDone = new LiveButton("OK");
		buttonDone.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.set(localDateFrom.getYear(), localDateFrom.getMonthValue() - 1, localDateFrom.getDayOfMonth(), 
						(Integer)comboBoxHours.getValue(), 0, 0);
				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(new Date());
				cal2.set(localDateTo.getYear(), localDateTo.getMonthValue() - 1, localDateTo.getDayOfMonth(), 
						(Integer)comboBoxHours2.getValue(), 0, 0);
				
				controller.salesHistoryPane(cal.getTime(), cal2.getTime());
			}
	
		});

		hbox.getChildren().add(buttonDone);

	}

	public void refresh() {
		setDate();
	}
	
	
}
