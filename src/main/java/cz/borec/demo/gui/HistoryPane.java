package cz.borec.demo.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;

import cz.borec.demo.DPH;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.SummarizedOrderDTO;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.gui.print.SummarizedPrinter;
import cz.borec.demo.ws.FIClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

public class HistoryPane extends AbstractPaneBase {

	private static DateFormat formatData = new SimpleDateFormat("d.MM.yyyy H:mm");
	
	private static final String LABEL_STR = "P\u0159ehled tr\u017Eeb";
	private BlueText label;
	private SummarizedOrderDTO orderDTO;
	private TableView<OrderItemDTO> table;
	private TableColumn<OrderItemDTO, String> lastNameCol;
	private TableColumn<OrderItemDTO, Integer> amount;
	private TableColumn<OrderItemDTO, Integer> price;
	private BlueText label_order;
/*	private TableColumn col_action;
*/	//private FIClient fIClient;
private Date dateFrom;
private Date dateTo;

private TableColumn<OrderItemDTO, Integer> vat;

private TableColumn<OrderItemDTO, DPH> vat2;

	public HistoryPane(Controller controller) throws JAXBException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		super(controller);
		orderDTO = new SummarizedOrderDTO();
		//fIClient = FIClient.getInstance();
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		label = new BlueText(LABEL_STR);
		hbox.getChildren().add(label);
	}

	@Override
	protected void fillVBox(GridPane vbox) {
		/*
		 * LiveButton buttonAdd = new LiveButton("P\u0159idat"); LiveButton
		 * buttonPrint = new LiveButton("Tisknout"); LiveButton buttonComplete =
		 * new LiveButton("Ukon\u010Dit"); buttonAdd.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) {
		 * controller.productSearchPane(ProductSearchMode.ORDERS); } });
		 * vbox.getChildren().add(buttonAdd);
		 * vbox.getChildren().add(buttonPrint);
		 * vbox.getChildren().add(buttonComplete);
		 */}

	@Override
	public Pane addGridPane() {
		BorderPane borderPane = new BorderPane();
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(8, 10, 5, 0));
		hbox.setSpacing(10);
		label_order = new BlueText(LABEL_STR, 15);
		hbox.getChildren().add(label_order);
		hbox.setAlignment(Pos.CENTER_LEFT);
		borderPane.setTop(hbox);
		borderPane.setCenter(createMainPane());
		return borderPane;
	}

/*	public void setTable(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
		label.setText(tableDTO.getRoomDTO().getName() + " \u2192 St\u016Fl: "
				+ tableDTO.getName());

		orderDTO = tableDTO.getOrderDTO();
		if (orderDTO == null) {
			orderDTO = new OrderDTO();
			orderDTO.setTable(tableDTO);
			tableDTO.setOrderDTO(orderDTO);
		}

		refresh();

	}*/

	protected Node createMainContent() {

		BorderPane pane = new BorderPane();
		table = new TableView<OrderItemDTO>();
		table.setBackground(Settings.getBackground());
		table.setPlaceholder(new javafx.scene.control.Label(""));
		lastNameCol = new TableColumn<OrderItemDTO, String>(
				"N\u00E1zev polo\u017Eky");
		lastNameCol
				.setCellValueFactory(new PropertyValueFactory("productName"));
		lastNameCol.setPrefWidth(250);

		amount = new TableColumn<OrderItemDTO, Integer>("Po\u010Det");
		amount.setCellValueFactory(new PropertyValueFactory("amount"));
		amount.setPrefWidth(100);

		price = new TableColumn<OrderItemDTO, Integer>("Cena (k\u010D)");
		price.setCellValueFactory(new PropertyValueFactory("price"));
		price.setPrefWidth(150);
		
		vat = new TableColumn<OrderItemDTO, Integer>("DPH (k\u010D)");
		vat.setCellValueFactory(new PropertyValueFactory("vatValue"));
		vat.setPrefWidth(130);
		
		vat2 = new TableColumn<OrderItemDTO, DPH>("DPH (%)");
		vat2.setCellValueFactory(new PropertyValueFactory("kokot"));
		vat2.setPrefWidth(50);
		
/*		col_action = new TableColumn("Akce");
        col_action.setSortable(false);
        col_action.setPrefWidth(150);
         
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OrderItemDTO, Boolean>, 
                javafx.beans.value.ObservableValue<Boolean>>() {
 
            @Override
            public javafx.beans.value.ObservableValue<Boolean> call(TableColumn.CellDataFeatures<OrderItemDTO, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
 
        col_action.setCellFactory(
                new Callback<TableColumn<OrderItemDTO, Boolean>, TableCell<OrderItemDTO, Boolean>>() {
 
            @Override
            public TableCell<OrderItemDTO, Boolean> call(TableColumn<OrderItemDTO, Boolean> p) {
                return new ButtonCell();
            }
         
        });*/
        


		table.getColumns().setAll(lastNameCol, amount, vat2, vat, price/*, col_action*/);
		pane.setCenter(table);
		return pane;
	}

/*	protected void createLeftButtons(HBox hbox) {
		
		LiveButton buttonHistory = new LiveButton("Historie");
		LiveButton buttonMove = new LiveButton("P\u0159esunout");
		buttonMove.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controller.roomsPane(orderDTO);
			}
		});

		
		buttonHistory.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.tableHistoryPane(tableDTO);
				
			}
		});


		
		hbox.getChildren().add(buttonHistory);
		hbox.getChildren().add(buttonMove);
	}*/

	protected void createButtons(HBox hbox) {
		LiveButton buttonPrint = new LiveButton("Tisknout");
		LiveButton buttonComplete = new LiveButton(
				"Zp\u011Bt");
		buttonPrint.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (orderDTO.getItems().size() < 1) {
					AlertHelper
							.showInfoDialog(
									"Objedn\u00E1vka nem\u00E1 \u017E\u00E1dnou polo\u017Eku.",
									"P\u0159idejte aspo\u0148 jednu polo\u017Eku.");
				} else {
					print();
				}
			}
		});
		buttonComplete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controller.searchParametersPane();
			}
		});

		
		hbox.getChildren().add(buttonPrint);
		hbox.getChildren().add(buttonComplete);
		
	}

	protected void print() {

		//orderDTO.setDate(new Date());

		
		SummarizedPrinter.print(orderDTO);

		/*
		 * Collection<OrderItemDTO> items = orderDTO.getItems(); int itemCount =
		 * items.size(); GridPane root = new GridPane(); root.setHgap(30);
		 * root.setVgap(itemCount * 2); // root.sets.setPadding(new Insets(30,
		 * 0, 0, 70)); root.setBackground(new Background(new
		 * BackgroundFill(Color.WHITE, new CornerRadii(5.0), Insets.EMPTY)));
		 * root.setAlignment(Pos.TOP_CENTER); // root.setGridLinesVisible(true);
		 * 
		 * Image image = new Image("images/printlogo.png"); ImageView pic = new
		 * ImageView(); pic.setFitWidth(130); pic.setFitHeight(130);
		 * 
		 * pic.setImage(image);
		 * 
		 * root.add(pic, 0, 0, 3, 3);
		 * 
		 * Label l1 = new Label("Po\u010Det"); l1.setPrefWidth(50); root.add(l1,
		 * 0, 3); Label l2 = new Label("Cena"); l2.setAlignment(Pos.CENTER);
		 * l2.setPrefWidth(100); root.add(l2, 1, 3); Label l3 = new
		 * Label("Celkem"); l3.setPrefWidth(100); root.add(l3, 2, 3);
		 * 
		 * root.add(new Label(
		 * "-----------------------------------------------------------"), 0, 4,
		 * 3, 1); int index = 5; for (OrderItemDTO orderItemDTO : items) {
		 * root.add(new Label(orderItemDTO.getProductName()), 0, index, 3, 1);
		 * root.add(new Label(orderItemDTO.getAmount().toString() + " ks"), 0,
		 * index + 1); root.add( new
		 * Label(orderItemDTO.getProduct().getPrice().toString()), 1, index +
		 * 1); root.add(new Label(orderItemDTO.getPrice().toString()), 2, index
		 * + 1); index += 2; }
		 * 
		 * BlueText t = new BlueText("D\u011Bkujeme za n\u00E1v\u0161t\u011Bvu",
		 * 5);
		 * 
		 * root.add(t, 0, index, 3, 1);
		 * 
		 * Stage stage = new Stage(); stage.setTitle("My New Stage Title");
		 * stage.setScene(new Scene(root)); stage.show();
		 * 
		 * javafx.print.PrinterJob job = javafx.print.PrinterJob
		 * .createPrinterJob(); if (job != null) { boolean success =
		 * job.printPage(root); if (success) { job.endJob(); } }
		 */
	}



	public void refresh() {
		// TODO: remove. This is workaround for bug of javafx of tableview
		// refresh
		table.getColumns().clear();
		table.getColumns().addAll(lastNameCol, amount, vat2, vat, price/*, col_action*/);

		ObservableList data = FXCollections.observableArrayList(orderDTO
				.getItemMap().values());

		table.setItems(data);
		label_order.setText(LABEL_STR
				+ "\t\t\t"
				+ ((orderDTO.getSum().doubleValue() != 0) ? orderDTO.getSumFormatted()
						 + " k\u010D" : ""));
	}

	/*	
	 * 	public class ButtonCell extends TableCell<OrderItemDTO, Boolean> {
		final LiveButton cellButton = new LiveButton("Upravit");

	public ButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				private AmountPane amountPane = new AmountPane();

				@Override
				public void handle(ActionEvent t) {
					// do something when button clicked
					// ...
					OrderItemDTO o = (OrderItemDTO) getTableRow().getItem();
					System.out.println(o.getAmount());
					//amountPane.setAmountObject(o);
					amountPane.setAmount(o.getAmount());
					AlertHelper.showAmountDialog(amountPane , "Množství");
					if(amountPane.isOK()) {
						o.setAmount(amountPane.getAmount());
						controller.getModel().saveOrderItem(o, true);
					}
					refresh();
				}
			});

			cellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					System.out.println("kokot");
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
	}*/

	public void loadData(Date dateFrom, Date dateTo, SummarizedOrderDTO orderDTO) {
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		orderDTO.setDate(dateFrom);
		orderDTO.setDateTo(dateTo);
		this.orderDTO = orderDTO;
		label.setText(LABEL_STR + " od: " + formatData.format(this.dateFrom) + " do: " + formatData.format(this.dateTo));
	}

}
