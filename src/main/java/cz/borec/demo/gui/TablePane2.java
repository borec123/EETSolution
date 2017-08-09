package cz.borec.demo.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import cz.borec.demo.AppProperties;
import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.core.entity.OrderState;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.ButtonSizeUtils;
import cz.borec.demo.gui.controls.CategoryButton;
import cz.borec.demo.gui.controls.DiscountPane;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.ProductButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.gui.controls.SubCategoryButton;
import cz.borec.demo.gui.print.Printer;
import cz.borec.demo.gui.utils.GridPaneFiller;
import cz.borec.demo.util.StringUtils;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Callback;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;

public class TablePane2 extends AbstractPaneBase2 {

	private static final String LABEL_STR = "Objedn\u00E1vka";
	protected static final long SECOND = 1000;
	private TableDTO tableDTO;
	private OrderDTO orderDTO;
	private TableView<OrderItemDTO> table;
	private TableColumn<OrderItemDTO, String> lastNameCol;
	private TableColumn<OrderItemDTO, Integer> amount;
	private TableColumn<OrderItemDTO, Integer> price;
	private BlueText label_order ;
	private TableColumn col_action;
	private FIClientOpenEET fIClient;
	protected DiscountPane discountPane = new DiscountPane();
	private List<CategoryDTO> categories;
	private HBox topButtons;
	private GridPane g;
	private Map<Long, List<LiveButton>> productButtonMap = new HashMap<Long, List<LiveButton>>();
	private Map<Long, List<LiveButton>> categoryButtonMap = new HashMap<Long, List<LiveButton>>();

	public void resetCategories() {
		topButtons.getChildren().clear();
		productButtonMap = new HashMap<Long, List<LiveButton>>();
		categoryButtonMap = new HashMap<Long, List<LiveButton>>();
		categories = controller.getModel().getAllCategories(true);
	}

	public TablePane2(final Controller controller, List<CategoryDTO> list)
			throws JAXBException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		super(controller);
		this.categories = list;
		orderDTO = new OrderDTO();
		fIClient = FIClientOpenEET.getInstance();
		fIClient.setController(controller);
		if (!Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_IS_MULTINODED))
				|| Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_IS_SERVER))) {
			new Thread(new Runnable() {

				private static final long HOUR = 3600000;

				@Override
				public void run() {
					try {
						Thread.sleep(HOUR / 120);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					while (true) {
						try {
							sendNotSentOrders();
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						}
						try {
							sendNotStornoedOrders();
							Thread.sleep(HOUR);
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}
				}

				private void sendNotSentOrders() {
					List<OrderDTO> orders = controller.getModel().findNotSentOrders();

					for (OrderDTO orderDTO : orders) {
						sendOrder(orderDTO, false);
					}

				}

				private void sendNotStornoedOrders() {
					List<OrderDTO> orders = controller.getModel().findNotStornoedOrders();

					for (OrderDTO orderDTO : orders) {
						sendOrder(orderDTO, true);
					}

				}

				private void sendOrder(OrderDTO orderDTO, boolean storno) {
					if ((storno ? orderDTO.getFIKStorno() : orderDTO.getFIK()) == null) {
						try {
							fIClient.callFIPublic(orderDTO, storno);
							controller.updateOrderWithoutCheck(orderDTO);
							Thread.sleep(SECOND);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}

			}).start();
		}

	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		LiveButton buttonOrders = new LiveButton("Objedn\u00E1vky");
		buttonOrders.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.orderQueuePane();

			}

		});
		hbox.getChildren().add(buttonOrders);
		arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		//arrow = new BlueText(this.orderDTO.getId() == null ? "Nov\u00E1 objedn\u00E1vka" : orderDTO.getId().toString());
		label_order = new BlueText(LABEL_STR, 17);
		arrow = new BlueText("Detail objedn\u00E1vky", 17);
		hbox.getChildren().add(arrow);
	}

	@Override
	protected void fillVBox(VBox vbox) {
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
		/*
		 * HBox hbox = new HBox(); hbox.setPadding(new Insets(8, 10, 5, 0));
		 * hbox.setSpacing(10);
		 */


		/*
		 * hbox.getChildren().add(label_order);
		 * hbox.setAlignment(Pos.CENTER_LEFT); borderPane.setTop(hbox);
		 */

		Node main = createMainContent();
		borderPane.setLeft(main);
		/*
		 * double a = getBoundsInParent().getWidth() -
		 * main.getBoundsInParent().getWidth(); double l = getLayoutX();
		 */borderPane.setCenter(createRightPane());
		borderPane.setBottom(createButtonPane());
		return borderPane;
	}

	private Node createRightPane() {
		BorderPane b = new BorderPane();
		topButtons = new HBox();
		topButtons.setPadding(new Insets(5, 5, 5, 5));
		topButtons.setSpacing(5);
		g = new GridPane();
		g.setPadding(new Insets(5, 5, 5, 5));
		g.setHgap(7);
		g.setVgap(7);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(g);

		b.setTop(topButtons);
		b.setCenter(scrollPane);

		// b.add(new LiveButton("Radegast 12\u00B0\n 0,5l"), 0, 0);

		return b;
	}

	public void setTable(TableDTO tableDTO) {
		this.tableDTO = tableDTO;

		orderDTO = tableDTO.getOrderDTO();
		if (orderDTO == null) {
			orderDTO = new OrderDTO();
			orderDTO.setFullName(tableDTO.getName());
			orderDTO.setTable(tableDTO);
			tableDTO.setOrderDTO(orderDTO);
		}

		refresh();

	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	protected Node createMainContent() {

		BorderPane pane = new BorderPane();
		HBox h = new HBox();
		// h.setSpacing(5);
		h.setPadding(new Insets(2, 0, 3, 0));
		h.getChildren().add(label_order);
		pane.setTop(h);
		table = new TableView<OrderItemDTO>();
		table.setBackground(Settings.getBackground());
		table.setPlaceholder(new javafx.scene.control.Label(""));
		
		lastNameCol = new TableColumn<OrderItemDTO, String>("N\u00E1zev polo\u017Eky");
		lastNameCol.setCellValueFactory(new PropertyValueFactory("productName"));
		lastNameCol.setPrefWidth(160);

		amount = new TableColumn<OrderItemDTO, Integer>("Po\u010Det");
		amount.setCellValueFactory(new PropertyValueFactory("amount"));
		amount.setPrefWidth(50);

		price = new TableColumn<OrderItemDTO, Integer>("Cena (k\u010D)");
		price.setCellValueFactory(new PropertyValueFactory("priceTotal"));

		col_action = new TableColumn("Akce");
		col_action.setSortable(false);
		col_action.setPrefWidth(100);

		col_action.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<OrderItemDTO, Boolean>, javafx.beans.value.ObservableValue<Boolean>>() {

					@Override
					public javafx.beans.value.ObservableValue<Boolean> call(
							TableColumn.CellDataFeatures<OrderItemDTO, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		col_action.setCellFactory(new Callback<TableColumn<OrderItemDTO, Boolean>, TableCell<OrderItemDTO, Boolean>>() {

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

		LiveButton buttonAdd = new LiveButton("Posunout stav");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (validateCount(orderDTO)) {
					if(orderDTO.getState() == OrderState.PREPARING) {
						orderDTO.setState(OrderState.SHIFT);
						//--- odepise ze skladu - decreases store:
						controller.getModel().completeOrder(orderDTO);
					}
					else 
						if(orderDTO.getState() == OrderState.SHIFT) {
							orderDTO.setState(OrderState.HAND_OVER);
							orderDTO.setDateOfHandOver(new Date());
							controller.getModel().updateOrder(orderDTO);
						}
					refreshLabel();
				}
			}
		});
		hbox.getChildren().add(buttonAdd);
		LiveButton buttonStorno = new LiveButton("Storno");
		buttonStorno.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (validateCount(orderDTO)) {
					orderDTO.setState(OrderState.STORNO);
					controller.getModel().updateOrder(orderDTO);
					refreshLabel();
				}
			}
		});
		hbox.getChildren().add(buttonStorno);

		LiveButton buttonRename = new LiveButton("P\u0159ejmenovat");
		LiveButton buttonUnPayed = new LiveButton("Nezaplacen\u00E1");
		LiveButton buttonSplit = new LiveButton("Rozd\u011Blit");

		buttonUnPayed.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (validateCount(orderDTO)) {
					boolean b = AlertHelper.showConfirmDialog("Opravdu nezaplacen\u00E1 objedn\u00E1vka ?", "");
					// System.out.println(b);
					if (b) {
						orderDTO.setPayed(false);
						orderDTO.setDate(new Date());
						if (orderDTO.getFIK() != null) {
							// --- Storno ! Must be true:
							boolean result = fIClient.callFIPublic(orderDTO, true);
							controller.updateOrderWithoutCheck(orderDTO);
							if (!result) {
								AlertHelper.showInfoDialog(
										"Chyba b\u011Bhem stornov\u00E1n\u00ED objedn\u00E1vky na finan\u010Dn\u00ED spr\u00E1v\u011B.",
										"");
							}
						}
						controller.completeOrder(orderDTO);
						newOrder();
					}
				}
			}
		});

		buttonRename.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (validateOrder(orderDTO)) {
					discountPane.setAmount(orderDTO.getDiscount());
					discountPane.setFullName(orderDTO.getFullName());

					AlertHelper.showDiscountDialog(discountPane,
							"Zadejte jm\u00E9no objedn\u00E1vaj\u00EDc\u00EDho a slevu.");
					if (discountPane.isOK()) {
						orderDTO.setDiscount(discountPane.getAmount());
						orderDTO.setFullName(discountPane.getFullName());
						controller.getModel().updateOrder(orderDTO);
						refreshLabel();
					}
				}
			}
		});

		buttonSplit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (validateCount(orderDTO)) {
					System.out.println("Count is OK...");
					if (validateOrder(orderDTO)) {
						System.out.println("Order FIK is OK...");
						controller.partialPaymentPane(orderDTO);
					}
				}
			}
		});

		hbox.getChildren().add(buttonRename);
		hbox.getChildren().add(buttonUnPayed);
		hbox.getChildren().add(buttonSplit);
	}

	private boolean validateOrder(OrderDTO orderDTO) {
		boolean b = orderDTO.getFIK() == null;
		if (!b) {
			AlertHelper.showInfoDialog("Objedn\u00E1vka u\u017E byla odesl\u00E1na na finan\u010Dn\u00ED spr\u00E1vu.",
					"\u00DA\u010Dtenku lze stornovat v sekci 'Historie'.");
			return b;
		}
		b = orderDTO.getState() == OrderState.PREPARING;
		if (!b) {
			AlertHelper.showInfoDialog("Objedn\u00E1vka u\u017E byla expedov\u00E1na.",
					"\u00DA\u010Dtenku lze stornovat v sekci 'Historie'.");
		}
		return b;
	}

	protected void createButtons(HBox hbox) {
		/*
		 * LiveButton buttonAdd = new LiveButton("Odeslat");
		 * buttonAdd.setTooltip(new Tooltip(
		 * "Pouze odeslat na finan\u010Dn\u00ED spr\u00E1vu bez tisku."));
		 */ // buttonAdd.setId("subCategory");
		LiveButton buttonPrint = new LiveButton("Tisknout");
		LiveButton buttonComplete = new LiveButton("Ukon\u010Dit objedn\u00E1vku");
		/*
		 * buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) {
		 * 
		 * if (validateCount(orderDTO)) { send(); }
		 * 
		 * }
		 * 
		 * });
		 */ buttonPrint.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (validateCount(orderDTO)) {
					print();
				}
			}
		});
		buttonComplete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (validatePrint(orderDTO)) {
					controller.completeOrder(orderDTO);
					newOrder();
				}
			}
		});

		// hbox.getChildren().add(buttonAdd);
		hbox.getChildren().add(buttonPrint);
		hbox.getChildren().add(buttonComplete);

	}

	protected boolean validatePrint(OrderDTO orderDTO2) {
		if (orderDTO.getDate() == null) {
			AlertHelper.showInfoDialog("Objedn\u00E1vka je\u0161t\u011B nebyla vytisknuta ani odesl\u00E1na.",
					"Vytiskn\u011Bte nebo ode\u0161lete a potom ukon\u010Dit.");
			return false;
		} else {

			return true;
		}
	}

	protected void newOrder() {
		orderDTO = new OrderDTO();
		//tableDTO.setOrderDTO(orderDTO);
		//orderDTO.setTable(tableDTO);
		orderDTO.setFullName(Controller.DEFAULT_ORDER_NAME);
		refresh();
	}

	protected boolean validateCount(OrderDTO orderDTO2) {
		if (orderDTO.getItems().size() < 1) {
			AlertHelper.showInfoDialog("Objedn\u00E1vka nem\u00E1 \u017E\u00E1dnou polo\u017Eku.",
					"P\u0159idejte aspo\u0148 jednu polo\u017Eku.");
			return false;
		}
		return true;
	}

	protected void print() {

		send();

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

	protected void send() {
		orderDTO.setDate(new Date());
		if (orderDTO.getFIK() == null) {
			fIClient.callFIPublic(orderDTO, false);
			controller.updateOrderWithoutCheck(orderDTO);
		}
	}

	public void refresh() {
		if (topButtons.getChildren().size() == 0) {
			for (final CategoryDTO categoryDTO : categories) {
				LiveButton b = new CategoryButton(categoryDTO.getName());
				b.setPrefSize(ButtonSizeUtils.getRoomButtonWidth(), ButtonSizeUtils.getLiveButtonHeight());
				b.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						reloadSubCategories(categoryDTO);
					}
				});
				topButtons.getChildren().add(b);
			}

		}
		// refresh
		refreshTable(false);
	}

	void refreshTable(boolean reloadOrderFromDB) {
		if (reloadOrderFromDB) {
			tableDTO = controller.getModel().getTableById(tableDTO.getId());
			orderDTO = tableDTO.getOrderDTO();
		}
		/*
		 * if (reloadOrderFromDB && orderDTO.getId() != null) { orderDTO =
		 * controller.getModel().getOrderById(orderDTO.getId());
		 * orderDTO.setTable(tableDTO); }
		 */
		if (orderDTO == null) {
			newOrder();
		}

		table.getColumns().clear();
		table.getColumns().addAll(lastNameCol, amount, price, col_action);

		ObservableList data = FXCollections.observableArrayList(orderDTO.getItemMap().values());

		table.setItems(data);
		refreshLabel();
	}

	private void refreshLabel() {
		label_order
				.setText((orderDTO.getId() != null ? orderDTO.getId() : "") + "\t'" + orderDTO.getFullName() + "' "
						+ "stav: " + orderDTO.getState().toString() + "\t"
						+ ((orderDTO.getSum()
								.doubleValue() != 0)
										? orderDTO.getSumFormatted()
												+ (orderDTO.getDiscount() != null && orderDTO.getDiscount()
														.doubleValue() > BigDecimal.ZERO.doubleValue()
																? " (-" + orderDTO.getDiscount() + ")" : "")
								+ " k\u010D" : ""));
	}

	protected void reloadSubCategories(CategoryDTO categoryDTO) {
		List<LiveButton> buttonList = null;
		List<LiveButton> buttonList2 = null;
		if (categoryDTO.getChildCategories().size() > 0) {
			buttonList = categoryButtonMap.get(categoryDTO.getId());
			if (buttonList == null) {
				buttonList = new ArrayList<LiveButton>();
				for (final CategoryDTO category : categoryDTO.getChildCategories()) {
					LiveButton b = new SubCategoryButton(StringUtils.splitIntoLines(category.getName()));
					// b.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
					b.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							reloadSubCategories(category);
						}
					});
					buttonList.add(b);
				}
				categoryButtonMap.put(categoryDTO.getId(), buttonList);
			}
			// fillProductButtons(buttonList);

		}
		// else {
		buttonList2 = productButtonMap.get(categoryDTO.getId());
		if (buttonList2 == null) {
			List<SalesProductEntity> prods = controller.getModel().getSalesProductsByCategoryId(categoryDTO.getId(),
					true);
			buttonList2 = new ArrayList<LiveButton>();
			for (final SalesProductEntity category : prods) {
				LiveButton b = new ProductButton(StringUtils.splitIntoLines(category.getName()));
				// b.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
				b.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (validateOrder(orderDTO)) {
							orderDTO.addItem(category);
							controller.saveOrUpdateOrder(orderDTO);
							refresh();
						}
					}
				});
				buttonList2.add(b);
			}
			productButtonMap.put(categoryDTO.getId(), buttonList2);
		}

		// }

		List<LiveButton> completeButtonList = new ArrayList<LiveButton>();
		if (buttonList != null)
			completeButtonList.addAll(buttonList);
		if (buttonList2 != null)
			completeButtonList.addAll(buttonList2);

		Node hovno = (Node) g.getParent().getParent().getParent();
		double g_Width = ((ScrollPane) hovno).getWidth();
		GridPaneFiller.fillButtons(g, completeButtonList, g_Width);
		
		
		//fillProductButtons(completeButtonList);
	}

/*	private void fillProductButtons(List<LiveButton> buttonList) {
		int i = 0;
		int j = 0;
		// Node hovno = (Node)g.getParent().getParent().getParent().getParent();
		Node hovno = (Node) g.getParent().getParent().getParent();
		double g_Width = ((ScrollPane) hovno).getWidth();
		double width = 0.0;
		g.getChildren().clear();
		for (LiveButton liveButton : buttonList) {
			g.add(liveButton, i++, j);
			double BUTTON_SIZE = ButtonSizeUtils.getTouchButtonSize();
			width += BUTTON_SIZE + g.getHgap();
			if (width > g_Width - BUTTON_SIZE) {
				width = 0.0;
				i = 0;
				j++;
			}
			// else {
			// }
		}

	}
*/
	public class ButtonCell extends TableCell<OrderItemDTO, Boolean> {
		final LiveButton cellButton = new LiveButton("Upravit");

		public ButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				private AmountPane amountPane = new AmountPane();

				@Override
				public void handle(ActionEvent t) {
					if (validateOrder(orderDTO)) {
						OrderItemDTO o = (OrderItemDTO) getTableRow().getItem();
						System.out.println(o.getAmount());
						// amountPane.setAmountObject(o);
						amountPane.setAmount(o.getAmount());
						AlertHelper.showAmountDialog(amountPane, "Mno\u017Estv\u00ED");
						if (amountPane.isOK()) {
							o.setAmount(amountPane.getAmount());
							o.calculateVat();
							controller.getModel().saveOrderItem(o, true);
						} else if (amountPane.isDELETE()) {
							if (o.getAmount() == 0 || AlertHelper.showConfirmDialog("Opravdu smazat polozku ze seznamu ?", "")) {
								OrderDTO order = o.getOrder();
								order.getItems().remove(o);
								controller.getModel().deleteOrderItem(o);
								refresh();
							}
							amountPane.setDELETE(false);
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

	public TableDTO getTable() {
		return this.tableDTO;
	}

}
