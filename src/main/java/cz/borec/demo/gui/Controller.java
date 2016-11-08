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

import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.xml.bind.JAXBException;

import org.springframework.context.ApplicationContext;

import cz.borec.demo.AppProperties;
import cz.borec.demo.Context;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.dto.SummarizedOrderDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.gui.notifier.Notifier;
import cz.borec.demo.service.ServiceInterface;

public class Controller {

	private final Scene scene;

	private ServiceInterface model;

	private ProductSearchPaneSalesProductsOrders productSearchPaneSalesProductsOrders;
	private ProductSearchPaneProducts productSearchPaneProducts;
	private ProductSearchPaneStore productSearchPaneStore;
	private RoomsPane roomsPane;
	private SwitchTableRoomsPane switchTableRoomsPane;
	private MainPane mainPane;
	private TablePane2 tablePane;
	private TableHistoryPane tableHistoryPane;
	private ProductDetailPane productDetailPane;
	private SalesProductDetailPane salesProductDetailPane;

	private OrderDTO order;

	private ProductSearchPaneSalesProducts productSalesSearchPane;

	private ProductSearchPaneForSalesProducts productSearchPaneForSalesProducts;

	private SalesProductEnterAmountPane salesProductEnterAmountPane;

	private StoreIncomePane storeIncomePane;

	private HistoryPane historyPane;

	private SearchParametersPane searchParametersPane;

	private PartialPaymentPane partialPaymentPane;

	private Notifier notifier = new Notifier();

	public Controller(final Scene scene) throws JAXBException, InvalidKeyException, UnrecoverableKeyException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		ApplicationContext applicationContext = Context.getApplicationContext();

		model = (ServiceInterface) applicationContext.getBean("ServiceImpl");
		this.scene = scene;
		productSearchPaneSalesProductsOrders = new ProductSearchPaneSalesProductsOrders(this, model.getAllCategories());
		productSearchPaneProducts = new ProductSearchPaneProducts(this, model.getAllCategories());
		productSearchPaneStore = new ProductSearchPaneStore(this, model.getAllCategories());
		mainPane = new MainPane(this);
		roomsPane = new RoomsPane(this, model.getAllRooms());
		tablePane = new TablePane2(this, model.getAllCategories());
		switchTableRoomsPane = new SwitchTableRoomsPane(this, model.getAllRooms());
		tableHistoryPane = new TableHistoryPane(this);
		productDetailPane = new ProductDetailPane(this, model.getAllUnits());
		productSalesSearchPane = new ProductSearchPaneSalesProducts(this, model.getAllCategories());
		salesProductDetailPane = new SalesProductDetailPane(this);
		productSearchPaneForSalesProducts = new ProductSearchPaneForSalesProducts(this, model.getAllCategories());
		salesProductEnterAmountPane = new SalesProductEnterAmountPane(this);
		storeIncomePane = new StoreIncomePane(this);
		historyPane = new HistoryPane(this);
		searchParametersPane = new SearchParametersPane(this);
		partialPaymentPane = new PartialPaymentPane(this, model.getAllCategories());
		// javafx.application.Platform.runLater(new Runnable() {
		/*
		 * new Thread(new Runnable() { private static final long TEN_SECONDS =
		 * 10000;
		 * 
		 * @Override public void run() { while (true) { try {
		 * Thread.sleep(TEN_SECONDS); Parent a = scene.getRoot(); if (a
		 * instanceof RoomsPane) { RoomsPane roomsPane = (RoomsPane) a;
		 * roomsPane.refresh(); } } catch (Exception e) { e.printStackTrace();
		 * throw new RuntimeException(e); } } } }).start();
		 */

		if (AppProperties.getProperties().isMultiNoded()) {
			javafx.concurrent.Task task = new javafx.concurrent.Task<Void>() {
				private static final long TEN_SECONDS = 10000;

				@Override
				public Void call() {

					while (true) {
						try {
							Thread.sleep(TEN_SECONDS);
							final Parent a = scene.getRoot();
							if (a instanceof RoomsPane) {
								javafx.application.Platform.runLater(new Runnable() {
									@Override
									public void run() {
										final RoomsPane roomsPane = (RoomsPane) a;
										if (!roomsPane.editMode) {
											roomsPane.refresh();
										}
									}
								});
							} else if (a instanceof TablePane2) {
								javafx.application.Platform.runLater(new Runnable() {
									@Override
									public void run() {
										final TablePane2 tablePane = (TablePane2) a;
										tablePane.refreshTable(true);
									}
								});
							}
						} catch (Exception e) {
							e.printStackTrace();
							// throw new RuntimeException(e);
						}
					}

				}
			};
			new Thread(task).start();
		}
	}

	public void mainMenu() {
		scene.setRoot(mainPane);
	}

	public void roomsPane() {
		// roomsPane.setRooms(model.getAllRooms());
		scene.setRoot(roomsPane);
		roomsPane.refresh();
	}

	public void productSearchPane(ProductSearchMode mode) {
		switch (mode) {
		case PRODUCTS:
			// productSearchPaneProducts.setCategories(model.getAllCategories());
			productSearchPaneProducts.refresh();
			scene.setRoot(productSearchPaneProducts);
			break;

		case ORDERS:
			// productSearchPaneOrders.setCategories(model.getAllCategories());
			scene.setRoot(productSearchPaneSalesProductsOrders);
			break;

		case STORE:
			// productSearchPaneStore.setCategories(model.getAllCategories());
			productSearchPaneStore.refresh();
			scene.setRoot(productSearchPaneStore);
			break;

		case SALES_PRODUCTS:
			// productSearchPaneStore.setCategories(model.getAllCategories());
			// TODO
			scene.setRoot(productSearchPaneForSalesProducts);
			break;

		default:
			break;
		}

	}

	public void productSearchPane(OrderDTO orderDTO) {
		productSearchPaneSalesProductsOrders.setOrder(orderDTO);
		productSearchPaneSalesProductsOrders.setCategories(model.getAllCategories());
		scene.setRoot(productSearchPaneSalesProductsOrders);
	}

	public void tablePane(TableDTO tableDTO) {
		tablePane.setTable(tableDTO);
		scene.setRoot(tablePane);
	}

	public void tablePane() {
		tablePane.refresh();
		scene.setRoot(tablePane);
	}

	public void tableHistoryPane(TableDTO tableDTO) {
		tableHistoryPane.setTable(tableDTO);
		scene.setRoot(tableHistoryPane);
	}

	public ServiceInterface getModel() {
		return model;

	}

	public void completeOrder(OrderDTO orderDTO) {
		// TODO: encapsulate particular operations to service transaction !!!
		orderDTO.getTableDTO().setOrderDTO(null);
		saveTableOrder(orderDTO.getTableDTO());
		model.completeOrder(orderDTO);
	}

	public void updateOrderWithoutCheck(OrderDTO orderDTO) {
		model.updateOrderInternal(orderDTO);
	}

	public void saveOrUpdateOrder(OrderDTO orderDTO) {
		// TODO: encapsulate particular operations to service transaction !!!
		if (orderDTO.getId() == null) {
			model.createOrder(orderDTO);
			saveTableOrder(orderDTO.getTableDTO());
		} else {
			model.saveOrderItems(orderDTO);
		}
	}

	private void saveTableOrder(TableDTO tableDTO) {
		model.saveTable(tableDTO);

	}

	public void roomsPane(OrderDTO orderDTO) {
		order = orderDTO;
		scene.setRoot(switchTableRoomsPane);
		switchTableRoomsPane.refresh();
	}

	public void switchTable(TableDTO tableDTO) {
		TableDTO t = order.getTableDTO();
		t.setOrderDTO(null);
		saveTableOrder(t);

		tableDTO.setOrderDTO(order);
		order.setTable(tableDTO);
		tablePane(tableDTO);
		saveTableOrder(order.getTableDTO());
	}

	public void productDetailPane(ProductDTO o) {
		productDetailPane.setProduct(o);
		scene.setRoot(productDetailPane);
	}

	public void productSalesSearchPane() {
		productSalesSearchPane.refresh();
		scene.setRoot(productSalesSearchPane);

	}

	public void salesProductDetailPane(SalesProductEntity o) {
		if (o != null) {
			salesProductDetailPane.setProduct(o);
			model.loadStoreProducts(o);
			salesProductDetailPane.refresh();
		}
		scene.setRoot(salesProductDetailPane);
	}

	public void salesProductEnterAmountPane(ProductDTO o) {
		salesProductEnterAmountPane.setStoreProduct(o);
		scene.setRoot(salesProductEnterAmountPane);
	}

	public void salesProductDetailPane(ProductDTO product, BigDecimal amount) {
		salesProductDetailPane.addStoreProduct(product, amount);
		scene.setRoot(salesProductDetailPane);
	}

	public void storeIncomePane(ProductDTO o) {
		storeIncomePane.setStoreProduct(o);
		scene.setRoot(storeIncomePane);
	}

	public void performStoreIncome(ProductDTO product, BigDecimal amount, BigDecimal price, String company) {

		model.performStoreIncome(product, amount, price, company);

		productSearchPane(ProductSearchMode.STORE);
	}

	public void salesHistoryPane(Date from, Date to) {

		SummarizedOrderDTO summarizedOrder = model.getSalesHistory(from, to);

		historyPane.loadData(from, to, summarizedOrder);
		historyPane.refresh();
		scene.setRoot(historyPane);
	}

	public void searchParametersPane() {
		searchParametersPane.refresh();
		scene.setRoot(searchParametersPane);

	}

	public void partialPaymentPane(OrderDTO orderDTO) {
		partialPaymentPane.setTable(tablePane.getTable());
		scene.setRoot(partialPaymentPane);

	}

	public void tablePane(OrderDTO orderDTO) {
		TableDTO t = tablePane.getTable();
		
		t.setOrderDTO(orderDTO);
		orderDTO.setTable(t);
		tablePane.setTable(t);
		scene.setRoot(tablePane);
		//tablePane();
	}

}
