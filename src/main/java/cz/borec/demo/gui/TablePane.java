package cz.borec.demo.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.DiscountPane;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.gui.print.Printer;
import cz.borec.demo.ws.FIClient;
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

public class TablePane extends AbstractPaneBase {

	private static final String LABEL_STR = "Aktu\u00E1ln\u00ED objedn\u00E1vka";
	protected static final long SECOND = 1000;
	private TableDTO tableDTO;
	private BlueText label;
	private OrderDTO orderDTO;
	private TableView<OrderItemDTO> table;
	private TableColumn<OrderItemDTO, String> lastNameCol;
	private TableColumn<OrderItemDTO, Integer> amount;
	private TableColumn<OrderItemDTO, Integer> price;
	private BlueText label_order;
	private TableColumn col_action;
	private FIClient fIClient;
	protected DiscountPane discountPane = new DiscountPane();

	public TablePane(final Controller controller) throws JAXBException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		super(controller);
		orderDTO = new OrderDTO();
		fIClient = FIClient.getInstance();
		new Thread(new Runnable() {

			private static final long HOUR = 3600000;
			@Override
			public void run() {
				try {
					Thread.sleep(HOUR / 120);
					while (true) {
						sendNotSentOrders();
						Thread.sleep(HOUR);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			private void sendNotSentOrders() {
				List<OrderDTO> orders = controller.getModel().findNotSentOrders();

				for (OrderDTO orderDTO : orders) {
					sendOrder(orderDTO);
				}

			}

			private void sendOrder(OrderDTO orderDTO) {
				if (orderDTO.getFIK() == null) {
					try {
						fIClient.callFIPublic(orderDTO, false);
						controller.updateOrderWithoutCheck(orderDTO);
						Thread.sleep(SECOND);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		}).start();

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
	}

	@Override
	protected void fillVBox(javafx.scene.layout.GridPane vbox) {
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

	public void setTable(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
		label.setText(tableDTO.getRoomDTO().getName() + " \u2192 St\u016Fl: "
				+ tableDTO.getName());

		orderDTO = tableDTO.getOrderDTO();
		if (orderDTO == null) {
			orderDTO = new OrderDTO();
			orderDTO.setFullName(tableDTO.getName());
			orderDTO.setTable(tableDTO);
			tableDTO.setOrderDTO(orderDTO);
		}

		refresh();

	}

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

		price = new TableColumn<OrderItemDTO, Integer>("Cena (k\u010D)");
		price.setCellValueFactory(new PropertyValueFactory("priceTotal"));

		col_action = new TableColumn("Akce");
		col_action.setSortable(false);
		col_action.setPrefWidth(150);

		col_action.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<OrderItemDTO, Boolean>, javafx.beans.value.ObservableValue<Boolean>>() {

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

				});

		table.getColumns().setAll(lastNameCol, amount, price, col_action);
		pane.setCenter(table);
		return pane;
	}

	protected void createLeftButtons(HBox hbox) {

		LiveButton buttonHistory = new LiveButton("Historie");
		LiveButton buttonMove = new LiveButton("P\u0159esunout");
		LiveButton buttonRename = new LiveButton("P\u0159ejmenovat");
		LiveButton buttonUnPayed = new LiveButton("Nezaplacen\u00E1");
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
		
		buttonUnPayed.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(validateCount(orderDTO)) {
					boolean b = AlertHelper.showConfirmDialog("Opravdu nezaplacen\u00E1 objedn\u00E1vka ?", "");
					//System.out.println(b);
					if(b) {
						orderDTO.setPayed(false);
						orderDTO.setDate(new Date());
						controller.completeOrder(orderDTO);
						newOrder() ;
					}
				}
			}
		});
		
		buttonRename.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				discountPane.setAmount(orderDTO.getDiscount());
				discountPane.setFullName(orderDTO.getFullName());

				AlertHelper.showDiscountDialog(discountPane, "Zadejte jm\u00E9no objedn\u00E1vaj\u00EDc\u00EDho a slevu.");
				if (discountPane.isOK()) {
					orderDTO.setDiscount(discountPane.getAmount());
					orderDTO.setFullName(discountPane.getFullName());
					controller.getModel().updateOrder(orderDTO);
				}
			}
		});

		hbox.getChildren().add(buttonHistory);
		hbox.getChildren().add(buttonMove);
		hbox.getChildren().add(buttonRename);
		hbox.getChildren().add(buttonUnPayed);
	}

	private boolean validateOrder(OrderDTO orderDTO) {
		return orderDTO.getFIK() == null;
	}
	protected void createButtons(HBox hbox) {
		LiveButton buttonAdd = new LiveButton("P\u0159idat produkt");
		LiveButton buttonPrint = new LiveButton("Tisknout");
		LiveButton buttonComplete = new LiveButton(
				"Ukon\u010Dit objedn\u00E1vku");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(!validateOrder(orderDTO)) {
					AlertHelper.showInfoDialog("Objedn\u00E1vka u\u017E byla odesl\u00E1na na finan\u010Dn\u00ED spr\u00E1vu.", "\u00DA\u010Dtenku lze stornovat v sekci 'Historie'.");
				} else {
					controller.productSearchPane(orderDTO);
				}
			}

		});
		buttonPrint.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				if(validateCount(orderDTO)) {
					print();
				}
			}
		});
		buttonComplete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(validatePrint(orderDTO)) {
					controller.completeOrder(orderDTO);
					newOrder();
				}
			}
		});

		hbox.getChildren().add(buttonAdd);
		hbox.getChildren().add(buttonPrint);
		hbox.getChildren().add(buttonComplete);

	}

	protected boolean validatePrint(OrderDTO orderDTO2) {
		if (orderDTO.getDate() == null) {
			AlertHelper
					.showInfoDialog(
							"Objedn\u00E1vka je\u0161t\u011B nebyla vytisknuta.",
							"Vytiskn\u011Bte a potom ukon\u010Dit.");
			return false;
		} else {

			return true;
		}
	}

	protected void newOrder() {
		orderDTO = new OrderDTO();
		tableDTO.setOrderDTO(orderDTO);
		orderDTO.setTable(tableDTO);
		orderDTO.setFullName(tableDTO.getName());
		refresh();
	}

	protected boolean validateCount(OrderDTO orderDTO2) {
		if (orderDTO.getItems().size() < 1) {
			AlertHelper
					.showInfoDialog(
							"Objedn\u00E1vka nem\u00E1 \u017E\u00E1dnou polo\u017Eku.",
							"P\u0159idejte aspo\u0148 jednu polo\u017Eku.");
			return false;
		} 
		return true;
	}

	protected void print() {

		orderDTO.setDate(new Date());
		if (orderDTO.getFIK() == null) {
			fIClient.callFIPublic(orderDTO, false);
			controller.updateOrderWithoutCheck(orderDTO);
		}

		Printer.print(orderDTO);

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
		table.getColumns().addAll(lastNameCol, amount, price, col_action);

		ObservableList data = FXCollections.observableArrayList(orderDTO
				.getItemMap().values());

		table.setItems(data);
		label_order.setText(LABEL_STR
				+ "\t\t'"
				+ orderDTO.getFullName()
				+ "'\t\t"
				+ ((orderDTO.getSum().doubleValue() != 0) ? orderDTO.getSumFormatted()
						+ " k\u010D" : ""));
	}

	public class ButtonCell extends TableCell<OrderItemDTO, Boolean> {
		final LiveButton cellButton = new LiveButton("Upravit");

		public ButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				private AmountPane amountPane = new AmountPane();

				@Override
				public void handle(ActionEvent t) {
					if(!validateOrder(orderDTO)) {
						AlertHelper.showInfoDialog("Objedn\u00E1vka u\u017E byla odesl\u00E1na na finan\u010Dn\u00ED spr\u00E1vu.", "\u00DA\u010Dtenku lze stornovat v sekci 'Historie'.");
					} else {
						OrderItemDTO o = (OrderItemDTO) getTableRow().getItem();
						System.out.println(o.getAmount());
						// amountPane.setAmountObject(o);
						amountPane.setAmount(o.getAmount());
						AlertHelper.showAmountDialog(amountPane, "Mno\u017Estv\u00ED");
						if (amountPane.isOK()) {
							o.setAmount(amountPane.getAmount());
							o.calculateVat();
							controller.getModel().saveOrderItem(o, true);
						}
						refresh();
					}
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
	}

}
