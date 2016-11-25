package cz.borec.demo.gui;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cz.borec.demo.AppProperties;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.entity.OrderEntity;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.ButtonSizeUtils;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;

public class MainPane extends AbstractPaneBase {

	private Button buttonRooms = new Button();
	private Button buttonProducts = null;
	private Button buttonStore = new Button();
	private LiveButton buttonAllerts;
	private String original;
	private String error;

	public MainPane(Controller c) {
		super(c);
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		BlueText label = new BlueText("Menu");
		hbox.getChildren().add(label);
	}

	@Override
	protected void fillVBox(GridPane vbox) {
		/*
		 * Button buttonRooms = new Button(); Button buttonProducts = new
		 * Button(); Button buttonStore = new Button();
		 * buttonRooms.setText("M\u00EDstnosti");
		 * vbox.getChildren().add(buttonRooms);
		 * buttonStore.setText("P\u0159\u00EDjem na sklad");
		 * vbox.getChildren().add(buttonStore);
		 * buttonProducts.setText("Produkty");
		 * vbox.getChildren().add(buttonProducts);
		 * buttonProducts.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) {
		 * controller.productSearchPane();
		 * 
		 * } });
		 */}

	@Override
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.setHgap(50);
		grid.setVgap(30);
		grid.setPadding(new Insets(30, 0, 0, 70));

		LiveButton buttonRooms = createButtonRooms();
		LiveButton buttonStore = new LiveButton();
		LiveButton buttonReports = new LiveButton();
		buttonAllerts = new LiveButton();

		buttonRooms.setPrefWidth(ButtonSizeUtils.getLiveButtonWidth());
		buttonStore.setPrefWidth(ButtonSizeUtils.getLiveButtonWidth());
		buttonReports.setPrefWidth(ButtonSizeUtils.getLiveButtonWidth());
		buttonAllerts.setPrefWidth(ButtonSizeUtils.getLiveButtonWidth());

		grid.add(buttonAllerts, 1, 0);
		buttonAllerts.setText("Alerty");
		grid.add(buttonRooms, 0, 0);
		buttonStore.setText("P\u0159\u00EDjem na sklad");
		grid.add(buttonStore, 0, 1);
		buttonReports.setText("Reporty");
		grid.add(buttonReports, 1, 1);

		if (AppProperties.getProperties().isSuperUser()) {

			LiveButton buttonCategories = new LiveButton();
			LiveButton buttonSettings = new LiveButton();
			buttonCategories.setPrefWidth(ButtonSizeUtils.getLiveButtonWidth());
			buttonSettings.setPrefWidth(ButtonSizeUtils.getLiveButtonWidth());
			grid.add(buttonCategories, 0, 4);
			buttonCategories.setText("Kategorie");
			grid.add(buttonSettings, 1, 4);
			buttonSettings.setText("Nastaven\u00ED");

			buttonSettings.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					controller.settingsPane();

				}
			});

			LiveButton buttonSalesProducts = new LiveButton();
			buttonSalesProducts.setPrefWidth(ButtonSizeUtils.getLiveButtonWidth());
			buttonSalesProducts.setText("Produkty prodejn\u00ED");
			grid.add(buttonSalesProducts, 0, 3);
			buttonSalesProducts.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					controller.productSalesSearchPane();
				}
			});
			buttonProducts = new LiveButton();
			buttonProducts.setText("Skladov\u00E9 produkty");
			buttonProducts.setPrefWidth(ButtonSizeUtils.getLiveButtonWidth());
			grid.add(buttonProducts, 1, 3);
			buttonProducts.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					controller.productSearchPane(ProductSearchMode.PRODUCTS);
				}
			});
			buttonCategories.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					controller.categoryPane();

				}
			});
		}

		buttonStore.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.productSearchPane(ProductSearchMode.STORE);
			}
		});

		buttonReports.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// controller.salesHistoryPane();
				controller.searchParametersPane();

			}
		});

		buttonAllerts.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String errorMessage = "";
				if(!StringUtils.isEmpty(error)) {
					errorMessage += error + "\n";
				}
				List<OrderDTO> orders = controller.getModel().findNotSentOrders();
				if (!orders.isEmpty()) {
					errorMessage += "V syst\u00E9mu je " + orders.size() + " neodeslan\u00FDch objedn\u00E1vek.";
				}

				orders = controller.getModel().findNotStornoedOrders();
				if (!orders.isEmpty()) {
					errorMessage += "V syst\u00E9mu je " + orders.size() + " nestornovan\u00FDch objedn\u00E1vek.";
				}

				List<ProductDTO> products = controller.getModel().getInsufficientProducts();
				for (ProductDTO productDTO : products) {
					errorMessage += "\nProdukt '" + productDTO.getName()
							+ "' m\u00E1 n\u00EDzk\u00E9 mno\u017Estv\u00ED na sklad\u011B: ("
							+ productDTO.getUnitAmount() + ").";
				}

				if ("".equals(errorMessage)) {
					AlertHelper.showInfoDialog("Alerty", "V\u0161e OK.");
				} else {
					AlertHelper.showDialog("Alerty", errorMessage, AlertType.ERROR);
				}

			}
		});

		return grid;
	}

	public void setError(int i) {
		if (i > 0) {
			if (i == 1) {
				error = "Zkontrolujte internetov\u00E9 p\u0159ipojen\u00ED.";
				buttonAllerts.setTooltip(new Tooltip(error));
			}
			else if (i == 2) {
				error = "Zkontrolujte internetov\u00E9 p\u0159ipojen\u00ED.";
				buttonAllerts
						.setTooltip(new Tooltip(error));
			}
			else {
				error = "Nazn\u00E1m\u00E1 chyba.";
				buttonAllerts.setTooltip(new Tooltip(error));
			}
			// original = buttonAllerts.getStylesheets().get(0);
			// buttonAllerts.getStylesheets().remove(0);
			buttonAllerts.getStylesheets().add("css/red.css");
			this.buttonAllerts.setBackground(new Background(
					new BackgroundFill(/* Color.LIGHTSKYBLUE */ Color.RED, new CornerRadii(0.0), Insets.EMPTY)));

		} else {
			error = "";
			buttonAllerts.setTooltip(null);
			buttonAllerts.getStylesheets().remove("css/red.css");
			this.buttonAllerts.setBackground(null);
			// buttonAllerts.getStylesheets().add(original);
		}
	}

}
