/*package cz.borec.demo.gui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.gui.ProductSearchPaneOrders.ButtonCell;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.gui.controls.SubCategoryButton;
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

public class ProductSearchPaneSalesProducts extends AbstractPaneBase {


	public ProductSearchPaneSalesProducts(Controller controller, List<CategoryDTO> list) {
		super(controller);
		setCategories(list);
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Produkty prodejn\u00ED");
		hbox.getChildren().add(arrow);
	}

	public class ButtonCell extends TableCell<SalesProductEntity, Boolean> {
		final LiveButton cellButton = new LiveButton("Upravit");

		public ButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					// do something when button clicked
					// ...
					SalesProductEntity o = (SalesProductEntity) getTableRow().getItem();

					controller.salesProductDetailPane(o);
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

	protected TableCell<SalesProductEntity, Boolean> getButtonCell() {

		return new ButtonCell();
	}

	protected void createButtons(HBox hbox) {
		LiveButton buttonDone = new LiveButton("Hotovo");
		buttonDone.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				controller.mainMenu();
			}
		});

		LiveButton buttonAdd = new LiveButton("P\u0159idat");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (category == null) {
					AlertHelper.showInfoDialog("Nen\u00ED zvolena kategorie.", "Zvolte kategorii !");
				} else {
					SalesProductEntity o = new SalesProductEntity();
					o.setCategory(category);
					controller.salesProductDetailPane(o);
				}
			}
		});
		hbox.getChildren().add(buttonAdd);

		LiveButton buttonDelete = new LiveButton("Smazat");
		buttonDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				SalesProductEntity o = (SalesProductEntity) table.getSelectionModel().getSelectedItem();
				if(o == null) {
					AlertHelper.showInfoDialog("Zvolte produkt !", "");
					return;
				}
				boolean b = AlertHelper.showConfirmDialog("Opravdu smazat produkt ?", o.getName());
				if (b) {
					o.setDeleted(true);
					controller.getModel().updateSalesProduct(o);
					refresh();
				}
			}
		});
		hbox.getChildren().add(buttonDelete);
		hbox.getChildren().add(buttonDone);
	}

	// -------- base class :

	//List<CategoryDTO> categories;
	BlueText title = new BlueText("Kategorie", 16);
	private TableView<SalesProductEntity> table;
	protected int count = 0;
	protected OrderDTO orderDTO;
	//protected CategoryDTO category;
	protected TableColumn<SalesProductEntity, String> nameCol;
	protected TableColumn<SalesProductEntity, BigDecimal> priceCol;
	protected TableColumn col_action;
	//protected BlueText categoryText;
	protected TableColumn<SalesProductEntity, Boolean> offerCol;
	//private List<CategoryDTO> selectedSubCategories;

	
	 * @Override protected void fillHorizontalBox(HBox hbox) { Button buttonMenu
	 * = createMenuButton(); hbox.getChildren().add(buttonMenu); BlueText arrow
	 * = new BlueText("\u2192"); hbox.getChildren().add(arrow); BlueText label =
	 * new BlueText("Produkty"); hbox.getChildren().add(label); }
	 
	@Override
	protected void fillVBox(javafx.scene.layout.GridPane vbox) {
		// title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		title = new BlueText("Kategorie", 16);
		vbox.getChildren().add(title);
		if (categories != null) {
			SubCategoryButton options[] = createHyperlinks(categories);
			fillWithHyperlinks(options);
		}
		
		 * Hyperlink options[] = new Hyperlink[] { new Hyperlink("Sales"), new
		 * Hyperlink("Marketing"), new Hyperlink("Distribution"), new
		 * Hyperlink("Costs")};
		 * 
		 * fillWithHyperlinks(options);
		 
	}

	@Override
	public Pane addGridPane() {
		{
			BorderPane borderPane = new BorderPane();
			HBox hbox = new HBox();
			hbox.setPadding(new Insets(8, 10, 5, 0));
			hbox.setSpacing(10);
			
			 * hbox.getChildren().add(new BlueText("Po\u010Det kus\u016F: ",
			 * 15)); textCount = new BlueText("0", 18);
			 * hbox.getChildren().add(this.textCount);
			 
			hbox.getChildren().add(new BlueText("Kategorie: ", 15));
			categoryText = new BlueText(" -- ", 15);
			hbox.getChildren().add(categoryText);
			hbox.setAlignment(Pos.CENTER_LEFT);
			borderPane.setTop(hbox);
			borderPane.setCenter(createMainPane());
			return borderPane;

			
			 * GridPane grid = new GridPane(); grid.setHgap(10);
			 * grid.setVgap(10); grid.setPadding(new Insets(0, 10, 0, 10));
			 * 
			 * // Category in column 2, row 1 Text category = new
			 * Text("Sales:"); category.setFont(Font.font("Arial",
			 * FontWeight.BOLD, 20)); grid.add(category, 1, 0);
			 * 
			 * // Title in column 3, row 1 Text chartTitle = new Text(
			 * "Current Year"); chartTitle.setFont(Font.font("Arial",
			 * FontWeight.BOLD, 20)); grid.add(chartTitle, 2, 0);
			 * 
			 * // Subtitle in columns 2-3, row 2 Text chartSubtitle = new Text(
			 * "Goods and Services"); grid.add(chartSubtitle, 1, 1, 2, 1);
			 * 
			 * // House icon in column 1, rows 1-2
			 * 
			 * ImageView imageHouse = new ImageView( new
			 * Image(LayoutSample.class
			 * .getResourceAsStream("graphics/house.png")));
			 * grid.add(imageHouse, 0, 0, 1, 2);
			 * 
			 * // Left label in column 1 (bottom), row 3 Text goodsPercent = new
			 * Text("Goods\n80%"); GridPane.setValignment(goodsPercent,
			 * VPos.BOTTOM); grid.add(goodsPercent, 0, 2);
			 * 
			 * // Chart in columns 2-3, row 3
			 * 
			 * ImageView imageChart = new ImageView( new
			 * Image(LayoutSample.class
			 * .getResourceAsStream("graphics/piechart.png")));
			 * grid.add(imageChart, 1, 2, 2, 1);
			 * 
			 * // Right label in column 4 (top), row 3 Text servicesPercent =
			 * new Text("Services\n20%");
			 * GridPane.setValignment(servicesPercent, VPos.TOP);
			 * grid.add(servicesPercent, 3, 2);
			 * 
			 * return grid;
			  }
	}

	public void setCategories(List<CategoryDTO> allCategories) {
		categories = allCategories;
		reloadVBox();
	}

	private void reloadVBox() {

		javafx.scene.layout.GridPane vbox = getVBox();
		vbox.getChildren().clear();
		fillVBox(vbox);
	}

	
	 * protected abstract TableCell<ProductDTO, Boolean> getButtonCell();
	 

	protected Node createMainContent() {

		BorderPane pane = new BorderPane();
		table = new TableView<SalesProductEntity>();
		table.setPlaceholder(new javafx.scene.control.Label(""));
		nameCol = new TableColumn<SalesProductEntity, String>("N\u00E1zev");
		nameCol.setCellValueFactory(new PropertyValueFactory("name"));
		nameCol.setPrefWidth(250);
		nameCol.setStyle("-fx-alignment: CENTER-LEFT;");

		priceCol = new TableColumn<SalesProductEntity, BigDecimal>("Cena (k\u010D)");
		priceCol.setCellValueFactory(new PropertyValueFactory("price"));

		offerCol = new TableColumn<SalesProductEntity, Boolean>("Zobrazit");
		offerCol.setCellValueFactory(new PropertyValueFactory("offer"));

		col_action = new TableColumn("Akce");
		col_action.setSortable(false);
        col_action.setPrefWidth(150);
        col_action.setStyle( "-fx-alignment: CENTER;");

		col_action.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<SalesProductEntity, Boolean>, javafx.beans.value.ObservableValue<Boolean>>() {

					@Override
					public javafx.beans.value.ObservableValue<Boolean> call(
							TableColumn.CellDataFeatures<SalesProductEntity, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		col_action.setCellFactory(
				new Callback<TableColumn<SalesProductEntity, Boolean>, TableCell<SalesProductEntity, Boolean>>() {

					@Override
					public TableCell<SalesProductEntity, Boolean> call(TableColumn<SalesProductEntity, Boolean> p) {
						return getButtonCell();
					}

				});

		setColumns(table.getColumns());
		table.setBackground(Settings.getBackground());
		pane.setCenter(table);
		return pane;
	}


	public void refresh() {
		if (category != null) {
			table.getColumns().clear();
			setColumns(table.getColumns());
			List<SalesProductEntity> list = controller.getModel().getSalesProductsByCategoryId(category.getId(),
					isOrder());
			ObservableList data = FXCollections.observableList(list);
			table.setItems(data);
		}
	}

	protected void setColumns(ObservableList<TableColumn<SalesProductEntity, ?>> columns) {
		columns.setAll(nameCol, priceCol, offerCol, col_action);
	}

	protected boolean isOrder() {
		return false;
	}

}
*/