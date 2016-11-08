package cz.borec.demo.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.gui.TablePane.ButtonCell;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.gui.print.Printer;
import cz.borec.demo.ws.FIClient;
import cz.borec.demo.ws.FIClientOpenEET;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TableHistoryPane extends AbstractPaneBase {

	private BlueText label;
	private TableDTO tableDTO;
	private BlueText label_order;
	private TableView<OrderDTO> table;
	private TableColumn<OrderDTO, Date> date;
	private TableColumn<OrderDTO, BigDecimal> sum;
	private TableColumn col_action;
	private List<OrderDTO> orderHistory;
	private FIClientOpenEET fIClient;
	private TableColumn<OrderDTO, Long> id;
	private TableColumn<OrderDTO, String> state;
	private TableColumn col_action2;

	public TableHistoryPane(Controller controller) throws JAXBException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		super(controller);
		fIClient = FIClientOpenEET.getInstance();
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		LiveButton buttonRooms = createButtonRooms();
		hbox.getChildren().add(buttonRooms);
		arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		label = new BlueText("St\u016Fl: ");
		hbox.getChildren().add(label);
		arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Historie");
		hbox.getChildren().add(arrow);
	}

	@Override
	protected void fillVBox(javafx.scene.layout.GridPane vbox) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pane addGridPane() {
		BorderPane borderPane = new BorderPane();
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(8, 10, 5, 0));
		hbox.setSpacing(10);
		label_order = new BlueText("Hovno. " , 15);
		hbox.getChildren().add(label_order);
		hbox.setAlignment(Pos.CENTER_LEFT);
		borderPane.setTop(hbox);
		borderPane.setCenter(createMainPane());
		return borderPane;
	}

	public void setTable(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
		label.setText(tableDTO.getRoomDTO().getName() + " \u2192 St\u016Fl: "
				+ tableDTO.getName());
		label_order.setText(String.format("Historie objedn\u00E1vek na stole \u010D. %s za posledn\u00EDch " + Constants.HISTORY + " hodin", tableDTO.getName()));
		
		orderHistory = controller.getModel().getOrderHistoryOfTable(tableDTO);
		
		refresh();

	}

	protected Node createMainContent() {

		BorderPane pane = new BorderPane();
		table = new TableView<OrderDTO>();
		table.setBackground(Settings.getBackground());
		date = new TableColumn<OrderDTO, Date>(
				"Datum a \u010Das");
		date.setCellValueFactory(new PropertyValueFactory("date"));
		date.setPrefWidth(200);
		date.setStyle( "-fx-alignment: CENTER-LEFT;");

		id = new TableColumn<OrderDTO, Long>(
				"\u010C\u00EDslo \u00FA\u010Dtenky");
		id.setCellValueFactory(new PropertyValueFactory("id"));
		id.setPrefWidth(110);

		sum = new TableColumn<OrderDTO, BigDecimal>("Cena (k\u010D)");
		sum.setCellValueFactory(new PropertyValueFactory("sumFormatted"));

		state = new TableColumn<OrderDTO, String>(
				"Stav");
		state.setCellValueFactory(new PropertyValueFactory("state"));
		state.setPrefWidth(260);
		state.setStyle( "-fx-alignment: CENTER-LEFT;");

		col_action = new TableColumn("Akce");
        col_action.setSortable(false);
        col_action.setPrefWidth(150);
        col_action.setStyle( "-fx-alignment: CENTER;");
         
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OrderDTO, Boolean>, 
                javafx.beans.value.ObservableValue<Boolean>>() {
 
            @Override
            public javafx.beans.value.ObservableValue<Boolean> call(TableColumn.CellDataFeatures<OrderDTO, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
 
        col_action.setCellFactory(
                new Callback<TableColumn<OrderDTO, Boolean>, TableCell<OrderDTO, Boolean>>() {
 
            @Override
            public TableCell<OrderDTO, Boolean> call(TableColumn<OrderDTO, Boolean> p) {
                return new ButtonCell();
            }
         
        });
        
		col_action2 = new TableColumn("Akce");
		col_action2.setSortable(false);
		col_action2.setPrefWidth(100);
         
		col_action2.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OrderDTO, Boolean>, 
                javafx.beans.value.ObservableValue<Boolean>>() {
 
            @Override
            public javafx.beans.value.ObservableValue<Boolean> call(TableColumn.CellDataFeatures<OrderDTO, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
 
		col_action2.setCellFactory(
                new Callback<TableColumn<OrderDTO, Boolean>, TableCell<OrderDTO, Boolean>>() {
 
            @Override
            public TableCell<OrderDTO, Boolean> call(TableColumn<OrderDTO, Boolean> p) {
                return new ButtonCell2();
            }
         
        });
        


		table.getColumns().setAll(id, date, sum, state, col_action, col_action2);
		pane.setCenter(table);
		return pane;
	}

	public class ButtonCell extends TableCell<OrderDTO, Boolean> {
		final LiveButton cellButton = new LiveButton("Tisknout");

		public ButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				private AmountPane amountPane = new AmountPane();

				@Override
				public void handle(ActionEvent t) {
					OrderDTO o = (OrderDTO) getTableRow().getItem();
					o.setDate(new Date());
					if(o.isPayed()) {
						if(o.getFIK() == null) {
							fIClient.callFIPublic(o, false);
							controller.updateOrderWithoutCheck(o);
						}
					}
					
					Printer.print(o);
				}
			});

		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}
	}
	
	public class ButtonCell2 extends TableCell<OrderDTO, Boolean> {
		final LiveButton cellButton = new LiveButton("Stornovat");

		public ButtonCell2() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					OrderDTO o = (OrderDTO) getTableRow().getItem();
					if(!o.isPayed()) {
						if(!(o.getFIK() == null && !o.isStorno())) {
							AlertHelper.showInfoDialog("Objedn\u00E1vka nen\u00ED zaplacena.", "A nen\u00ED ve stavu hodn\u00E9ho stornov\u00E1n\u00ED.");
							return;
						}
					}
					if(o.getFIK() == null) {
						AlertHelper.showInfoDialog("Objedn\u00E1vka nen\u00ED odesl\u00E1na na finan\u010Dn\u00ED spr\u00E1vu..", "");
						return;
					}
					if(o.isStorno()) {
						AlertHelper.showInfoDialog("Objedn\u00E1vka je ji\u017E stornov\u00E1na..", "");
						return;
					} 
					if(AlertHelper.showConfirmDialog("Stornovat objedn\u00E1vku ?", "")) {
						//--- Storno ! Must be true:
						boolean result = fIClient.callFIPublic(o, true);
						controller.updateOrderWithoutCheck(o);
						if(!result) {
							AlertHelper.showInfoDialog("Chyba b\u011Bhem stornov\u00E1n\u00ED objedn\u00E1vky na finan\u010Dn\u00ED spr\u00E1v\u011B.", "");
						}
						refresh();
					}

				}
			});
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}
	}

	protected void refresh() {
		table.getColumns().clear();
		table.getColumns().addAll(id, date, sum, state, col_action, col_action2);

		ObservableList data = FXCollections.observableArrayList(orderHistory);

		table.setItems(data);
	}

	protected void createButtons(HBox hbox) {
		LiveButton buttonAdd = new LiveButton("Zp\u011Bt");

		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				controller.tablePane();

			}

		});

		hbox.getChildren().add(buttonAdd);
	}
}
