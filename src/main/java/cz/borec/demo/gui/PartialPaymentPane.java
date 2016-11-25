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

import org.apache.commons.lang.SerializationUtils;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.DiscountPane;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.gui.print.Printer;
import cz.borec.demo.util.StringUtils;
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
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class PartialPaymentPane extends AbstractPaneBase2 {

	private static final String LABEL_STR = "Aktu\u00E1ln\u00ED objedn\u00E1vka";
	protected static final long SECOND = 1000;
	private static final double BUTTON_SIZE = 100;
	private static final String PARTIAL_LABEL_STR = "\u010C\u00E1ste\u010Dn\u00E1 \u00FA\u010Dtenka";
	private TableDTO tableDTO;
	private BlueText label;
	private OrderDTO orderDTO;
	private TableView<OrderItemDTO> table;
	private TableColumn<OrderItemDTO, String> lastNameCol;
	private TableColumn<OrderItemDTO, Integer> amount;
	private TableColumn<OrderItemDTO, Integer> price;
	private BlueText label_order;
	private TableColumn col_action;
	private FIClientOpenEET fIClient;
	protected DiscountPane discountPane = new DiscountPane();
	private List<CategoryDTO> categories;
	private GridPane g;
	private Map<Long, List<LiveButton>> productButtonMap = new HashMap<Long, List<LiveButton>>();
	private Map<Long, List<LiveButton>> categoryButtonMap = new HashMap<Long, List<LiveButton>>();
	private BlueText label_partial_order;
	private OrderDTO orderPartialDTO;
	private OrderDTO orderDecreasedDTO;
	private TableView<OrderItemDTO> tablePartial;
	private TableColumn<OrderItemDTO, String> lastNameColPartial;
	private TableColumn<OrderItemDTO, Integer> amountPartial;
	private TableColumn<OrderItemDTO, Integer> pricePartial;
	private TableColumn col_actionPartial;

	public PartialPaymentPane(final Controller controller, List<CategoryDTO> list)
			throws JAXBException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		super(controller);
		this.categories = list;
		orderDTO = new OrderDTO();
		orderPartialDTO = new OrderDTO();
		fIClient = FIClientOpenEET.getInstance();
		fIClient.setController(controller);
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

		label_order = new BlueText(LABEL_STR, 15);
		label_partial_order = new BlueText(PARTIAL_LABEL_STR, 15);

		/*
		 * hbox.getChildren().add(label_order);
		 * hbox.setAlignment(Pos.CENTER_LEFT); borderPane.setTop(hbox);
		 */

		Node main = createMainContent();
		borderPane.setLeft(main);
		/*
		 * double a = getBoundsInParent().getWidth() -
		 * main.getBoundsInParent().getWidth(); double l = getLayoutX();
		 */ borderPane.setCenter(createRightPane());
		borderPane.setBottom(createButtonPane());
		return borderPane;
	}

	private Node createRightPane() {
		BorderPane pane = new BorderPane();
		pane.setTop(label_partial_order);
		tablePartial = new TableView<OrderItemDTO>();
		tablePartial.setBackground(Settings.getBackground());
		lastNameColPartial = new TableColumn<OrderItemDTO, String>("N\u00E1zev polo\u017Eky");
		lastNameColPartial.setCellValueFactory(new PropertyValueFactory("productName"));
		lastNameColPartial.setPrefWidth(170);

		amountPartial = new TableColumn<OrderItemDTO, Integer>("Po\u010Det");
		amountPartial.setCellValueFactory(new PropertyValueFactory("amount"));

		pricePartial = new TableColumn<OrderItemDTO, Integer>("Cena (k\u010D)");
		pricePartial.setCellValueFactory(new PropertyValueFactory("priceTotal"));

		col_actionPartial = new TableColumn("P\u0159esun");
		col_actionPartial.setSortable(false);
		col_actionPartial.setPrefWidth(90);

		col_actionPartial.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<OrderItemDTO, Boolean>, javafx.beans.value.ObservableValue<Boolean>>() {

					@Override
					public javafx.beans.value.ObservableValue<Boolean> call(
							TableColumn.CellDataFeatures<OrderItemDTO, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		col_actionPartial.setCellFactory(new Callback<TableColumn<OrderItemDTO, Boolean>, TableCell<OrderItemDTO, Boolean>>() {

			@Override
			public TableCell<OrderItemDTO, Boolean> call(TableColumn<OrderItemDTO, Boolean> p) {
				return new ButtonCell2();
			}

		});

		tablePartial.getColumns().setAll(lastNameColPartial, amountPartial, pricePartial, col_actionPartial);
		pane.setCenter(tablePartial);
		return pane;
	}

	public void setTable(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
		label.setText(tableDTO.getRoomDTO().getName() + " \u2192 St\u016Fl: " + tableDTO.getName());

		orderDTO = tableDTO.getOrderDTO();
		if (orderDTO == null) {
			orderDTO = new OrderDTO();
			orderDTO.setFullName(tableDTO.getName());
			orderDTO.setTable(tableDTO);
			tableDTO.setOrderDTO(orderDTO);
		}
		
		orderPartialDTO = new OrderDTO();
		orderPartialDTO.setTable(tableDTO);
		orderPartialDTO.setFullName(orderDTO.getFullName() + " (rozd\u011Blen\u00E1)");
		orderDecreasedDTO = (OrderDTO) SerializationUtils.clone(orderDTO);

		refresh();

	}

	protected Node createMainContent() {

		BorderPane pane = new BorderPane();
		pane.setTop(label_order);
		table = new TableView<OrderItemDTO>();
		table.setBackground(Settings.getBackground());
		table.setPlaceholder(new javafx.scene.control.Label(""));
		lastNameCol = new TableColumn<OrderItemDTO, String>("N\u00E1zev polo\u017Eky");
		lastNameCol.setCellValueFactory(new PropertyValueFactory("productName"));
		lastNameCol.setPrefWidth(170);

		amount = new TableColumn<OrderItemDTO, Integer>("Po\u010Det");
		amount.setCellValueFactory(new PropertyValueFactory("amount"));

		price = new TableColumn<OrderItemDTO, Integer>("Cena (k\u010D)");
		price.setCellValueFactory(new PropertyValueFactory("priceTotal"));

		col_action = new TableColumn("P\u0159esun");
		col_action.setSortable(false);
		col_action.setPrefWidth(90);

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

		LiveButton buttonBack = new LiveButton("Zp\u011Bt");
		buttonBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				orderPartialDTO = new OrderDTO();
				controller.tablePane(orderDTO);
			}
		});


		hbox.getChildren().add(buttonBack);
	}

	private boolean validateOrder(OrderDTO orderDTO) {
		return orderDTO.getFIK() == null;
	}

	protected void createButtons(HBox hbox) {
		LiveButton buttonPrint = new LiveButton("Tisknout");
		LiveButton buttonComplete = new LiveButton("Ukon\u010Dit objedn\u00E1vku");
		buttonPrint.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (validateCount(orderPartialDTO)) {
					controller.getModel().saveOrderItems(orderDecreasedDTO);
					controller.getModel().deleteOrderItems(orderDecreasedDTO);
					
					orderDTO.setItemMap(orderDecreasedDTO.getItemMap());
					//controller.getModel().saveOrderItems(orderDTO);
					//controller.getModel().createOrder(orderPartialDTO);
					
					
					controller.saveOrUpdateOrder(orderPartialDTO);
					print();
				}
			}
		});
		buttonComplete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (validatePrint(orderPartialDTO)) {
					//controller.completeOrder(orderPartialDTO);
					controller.getModel().completeOrder(orderPartialDTO);
					newOrder();
				}
			}
		});

		hbox.getChildren().add(buttonPrint);
		hbox.getChildren().add(buttonComplete);

	}

	protected boolean validatePrint(OrderDTO orderDTO2) {
		if (orderPartialDTO.getDate() == null) {
			AlertHelper.showInfoDialog("Objedn\u00E1vka je\u0161t\u011B nebyla vytisknuta.",
					"Vytiskn\u011Bte a potom ukon\u010Dit.");
			return false;
		} else {

			return true;
		}
	}

	protected void newOrder() {
		orderPartialDTO = new OrderDTO();
		orderPartialDTO.setTable(tableDTO);
		
		orderPartialDTO.setFullName(tableDTO.getName() + " (rozd\u011Blen\u00E1)");
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

		orderPartialDTO.setDate(new Date());
		if (orderPartialDTO.getFIK() == null) {
			fIClient.callFIPublic(orderPartialDTO, false);
			controller.updateOrderWithoutCheck(orderPartialDTO);
		}

		Printer.print(orderPartialDTO);

	}

	public void refresh() {

		table.getColumns().clear();
		table.getColumns().addAll(lastNameCol, amount, price, col_action);
		ObservableList data = FXCollections.observableArrayList(orderDecreasedDTO.getItemMap().values());
		table.setItems(data);
		
		tablePartial.getColumns().clear();
		tablePartial.getColumns().addAll(lastNameColPartial, amountPartial, pricePartial, col_actionPartial);
		ObservableList data2 = FXCollections.observableArrayList(orderPartialDTO.getItemMap().values());
		tablePartial.setItems(data2);
		
		refreshLabel();
	}

	private void refreshLabel() {
		label_order
		.setText(LABEL_STR + "\t\t'" + orderDecreasedDTO.getFullName() + "'\t\t"
				+ ((orderDecreasedDTO.getSum()
						.doubleValue() != 0)
								? orderDecreasedDTO.getSumFormatted()
										+ (orderDecreasedDTO.getDiscount() != null && orderDecreasedDTO.getDiscount()
												.doubleValue() > BigDecimal.ZERO.doubleValue()
														? " (-" + orderDecreasedDTO.getDiscount() + ")" : "")
						+ " k\u010D" : ""));
		label_partial_order
		.setText(PARTIAL_LABEL_STR + "\t\t'" + orderPartialDTO.getFullName() + "'\t\t"
				+ ((orderPartialDTO.getSum()
						.doubleValue() != 0)
								? orderPartialDTO.getSumFormatted()
										+ (orderPartialDTO.getDiscount() != null && orderPartialDTO.getDiscount()
												.doubleValue() > BigDecimal.ZERO.doubleValue()
														? " (-" + orderPartialDTO.getDiscount() + ")" : "")
						+ " k\u010D" : ""));
	}

	protected void reloadSubCategories(CategoryDTO categoryDTO) {
		if (categoryDTO.getChildCategories().size() > 0) {
			List<LiveButton> buttonList = categoryButtonMap.get(categoryDTO.getId());
			if (buttonList == null) {
				buttonList = new ArrayList<LiveButton>();
				for (final CategoryDTO category : categoryDTO.getChildCategories()) {
					LiveButton b = new LiveButton(StringUtils.splitIntoLines(category.getName()));
					b.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
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
			fillProductButtons(buttonList);
			
		} else {
			List<LiveButton> buttonList = productButtonMap.get(categoryDTO.getId());
			if (buttonList == null) {
				List<SalesProductEntity> prods = controller.getModel().getSalesProductsByCategoryId(categoryDTO.getId(),
						true);
				buttonList = new ArrayList<LiveButton>();
				for (final SalesProductEntity category : prods) {
					LiveButton b = new LiveButton(StringUtils.splitIntoLines(category.getName()));
					b.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
					b.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							if (!validateOrder(orderDTO)) {
								AlertHelper.showInfoDialog(
										"Objedn\u00E1vka u\u017E byla odesl\u00E1na na finan\u010Dn\u00ED spr\u00E1vu.",
										"\u00DA\u010Dtenku lze stornovat v sekci 'Historie'.");
							} else {
								orderDTO.addItem(category);
								controller.saveOrUpdateOrder(orderDTO);
								refresh();
							}
						}
					});
					buttonList.add(b);
				}
				productButtonMap.put(categoryDTO.getId(), buttonList);
			}
			fillProductButtons(buttonList);
		}

	}

	private void fillProductButtons(List<LiveButton> buttonList) {
		int i = 0;
		int j = 0;
		double g_Width = g.getWidth();
		double width = 0.0;
		g.getChildren().clear();
		for (LiveButton liveButton : buttonList) {
			width += BUTTON_SIZE + g.getHgap();
			if (width > g_Width) {
				width = 0.0;
				i = 0;
				j++;
			} else {
				g.add(liveButton, i++, j);
			}
		}

	}

	public class ButtonCell extends TableCell<OrderItemDTO, Boolean> {
		final LiveButton cellButton = new LiveButton(">>");

		public ButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				private AmountPane amountPane = new AmountPane();

				@Override
				public void handle(ActionEvent t) {
					OrderItemDTO o = (OrderItemDTO) getTableRow().getItem();
					Integer am = o.getAmount();
					if(am > 0) {
						orderPartialDTO.addItem(o.getProduct());
						o.setAmount(am - 1);
						if(o.getAmount() == 0) {
							o.getOrder().getDeletedItems().add(o);
							o.getOrder().getItems().remove(o);
						}
						else {
							o.calculateVat();
						}

/*						controller.getModel().saveOrderItem(o, true);
						controller.getModel().saveOrderItems(orderPartialDTO);
*/					}
					refresh();
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
	
	public class ButtonCell2 extends TableCell<OrderItemDTO, Boolean> {
		final LiveButton cellButton = new LiveButton("<<");

		public ButtonCell2() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				private AmountPane amountPane = new AmountPane();

				@Override
				public void handle(ActionEvent t) {
					OrderItemDTO o = (OrderItemDTO) getTableRow().getItem();
					Integer am = o.getAmount();
					if(am > 0) {
						orderDecreasedDTO.addItem(o.getProduct());
						o.setAmount(am - 1);
						if(o.getAmount() == 0) {
							o.getOrder().getItems().remove(o);
						}
						else {
							o.calculateVat();
						}
/*						controller.getModel().saveOrderItem(o, true);
						controller.getModel().saveOrderItems(orderPartialDTO);
*/					}
					refresh();
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

/*	public void setOrder(OrderDTO orderDTO2) {
		this.orderDTO = orderDTO2;
	}
*/
}
