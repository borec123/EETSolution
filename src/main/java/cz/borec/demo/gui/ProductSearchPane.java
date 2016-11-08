package cz.borec.demo.gui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.gui.controls.SubCategoryButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

public abstract class ProductSearchPane extends AbstractPaneBase {

	//private BlueText categoryText;
	//private Map<Long, SubCategoryButton[]> subCategories = new HashMap<Long, SubCategoryButton[]>();
	//List<CategoryDTO> categories;
	BlueText title = new BlueText("Kategorie", 16);
	protected TableView<ProductDTO> table;
	private int count = 0;
	protected BlueText textCount;
	protected OrderDTO orderDTO;
	//protected CategoryDTO category;
	private TableColumn<ProductDTO, String> nameCol;
	private TableColumn<ProductDTO, BigDecimal> amountCol;
	private TableColumn col_action;
	//private List<CategoryDTO> selectedSubCategories;
	

	public ProductSearchPane(Controller controller) {
		super(controller);
	}

/*	protected void handleHyperlinkAction(ActionEvent arg0) {
		CategoryDTO cat = (CategoryDTO) ((SubCategoryButton)arg0.getSource()).getUserData();
		if(cat.isRoot()) {
			SubCategoryButton[] options = subCategories.get(cat.getId());
			if(options == null) {
				List<CategoryDTO> all = new ArrayList<CategoryDTO>();
				all.addAll(categories);
				selectedSubCategories = cat.getChildCategories();
				all.addAll(selectedSubCategories);
				options = createHyperlinks(all);
				this.subCategories.put(cat.getId(), options);
			}
			fillWithHyperlinks(options);
		}
		else {
			if(cat.getChildCategories().size() > 0) {
				List<CategoryDTO> all = new ArrayList<CategoryDTO>();
				all.addAll(categories);
				all.addAll(selectedSubCategories);
				int i = all.indexOf(cat);
				for (CategoryDTO categoryDTO : cat.getChildCategories()) {
					all.add(++i, categoryDTO);
				}
				SubCategoryButton[] options = createHyperlinks(all);
				fillWithHyperlinks(options);
			}
			category = cat;
			categoryText.setText(category.toString());
			refresh();
		}
	}*/

	@Override
	protected void fillHorizontalBox(HBox hbox) {
			Button buttonMenu = createMenuButton();
			hbox.getChildren().add(buttonMenu);
			BlueText arrow = new BlueText("\u2192");
			hbox.getChildren().add(arrow);
			BlueText label = new BlueText("Produkty");
			hbox.getChildren().add(label);
	}

	@Override
	protected void fillVBox(GridPane vbox) {
		// title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		title = new BlueText("Kategorie", 16);
		vbox.getChildren().add(title);
		if (categories != null) {
			SubCategoryButton options[] = createHyperlinks(categories);
			fillWithHyperlinks(options);
		}
		/*
		 * Hyperlink options[] = new Hyperlink[] { new Hyperlink("Sales"), new
		 * Hyperlink("Marketing"), new Hyperlink("Distribution"), new
		 * Hyperlink("Costs")};
		 * 
		 * fillWithHyperlinks(options);
		 */
	}

	@Override
	public Pane addGridPane() {
		{
			BorderPane borderPane = new BorderPane();
			HBox hbox = new HBox(); 
			hbox.setPadding(new Insets(8, 10, 5, 0));
			hbox.setSpacing(10);
			hbox.getChildren().add(new BlueText("Kategorie: ", 15));
			categoryText = new BlueText(" - nen\u00ED vybr\u00E1na - ", 15);
			hbox.getChildren().add(categoryText);
			
			if(this instanceof ProductSearchPaneOrders) {
				hbox.getChildren().add(new BlueText("Po\u010Det kus\u016F: ", 15));
				textCount = new BlueText("0", 18);
				hbox.getChildren().add(this.textCount);
			}
			hbox.setAlignment(Pos.CENTER_LEFT);
			borderPane.setTop(hbox);
			borderPane.setCenter(createMainPane());
			return borderPane;
			
/*			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 10, 0, 10));

			// Category in column 2, row 1
			Text category = new Text("Sales:");
			category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			grid.add(category, 1, 0);

			// Title in column 3, row 1
			Text chartTitle = new Text("Current Year");
			chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			grid.add(chartTitle, 2, 0);

			// Subtitle in columns 2-3, row 2
			Text chartSubtitle = new Text("Goods and Services");
			grid.add(chartSubtitle, 1, 1, 2, 1);

			// House icon in column 1, rows 1-2
			
			 * ImageView imageHouse = new ImageView( new
			 * Image(LayoutSample.class
			 * .getResourceAsStream("graphics/house.png")));
			 * grid.add(imageHouse, 0, 0, 1, 2);
			 
			// Left label in column 1 (bottom), row 3
			Text goodsPercent = new Text("Goods\n80%");
			GridPane.setValignment(goodsPercent, VPos.BOTTOM);
			grid.add(goodsPercent, 0, 2);

			// Chart in columns 2-3, row 3
			
			 * ImageView imageChart = new ImageView( new
			 * Image(LayoutSample.class
			 * .getResourceAsStream("graphics/piechart.png")));
			 * grid.add(imageChart, 1, 2, 2, 1);
			 
			// Right label in column 4 (top), row 3
			Text servicesPercent = new Text("Services\n20%");
			GridPane.setValignment(servicesPercent, VPos.TOP);
			grid.add(servicesPercent, 3, 2);

			return grid;
*/		}
	}

	public void setCategories(List<CategoryDTO> allCategories) {
		categories = allCategories;
		reloadVBox();
	}

	private void reloadVBox() {

		GridPane vbox = getVBox();
		vbox.getChildren().clear();
		fillVBox(vbox);
	}

    protected abstract TableCell<ProductDTO, Boolean> getButtonCell();

	
	protected Node createMainContent() {

		BorderPane pane = new BorderPane();
		table = new TableView<ProductDTO>();
		nameCol = new TableColumn<ProductDTO, String>(
				"N\u00E1zev");
		nameCol.setCellValueFactory(new PropertyValueFactory("name"));
		nameCol.setPrefWidth(250);
		nameCol.setStyle( "-fx-alignment: CENTER-LEFT;");

		amountCol = new TableColumn<ProductDTO, BigDecimal>(
				"Skladem");
		amountCol.setCellValueFactory(new PropertyValueFactory("unitAmount"));
		
/*		amountCol.setCellValueFactory(new PropertyValueFactory<ProductDTO, BigDecimal>("unitAmount"));

        // Table cell coloring
		amountCol.setCellFactory(new Callback<TableColumn<ProductDTO, BigDecimal>, TableCell<ProductDTO, BigDecimal>>() {
            @Override
            public TableCell<ProductDTO, BigDecimal> call(TableColumn<ProductDTO, BigDecimal> param) {
                return new AmountTableCell();
     
            }
        });		*/
		
		col_action = new TableColumn("Akce");
        col_action.setSortable(false);
        col_action.setPrefWidth(150);
        col_action.setStyle( "-fx-alignment: CENTER;");
         
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProductDTO, Boolean>, 
                javafx.beans.value.ObservableValue<Boolean>>() {
 
            @Override
            public javafx.beans.value.ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ProductDTO, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
 
        col_action.setCellFactory(
                new Callback<TableColumn<ProductDTO, Boolean>, TableCell<ProductDTO, Boolean>>() {
 
            @Override
            public TableCell<ProductDTO, Boolean> call(TableColumn<ProductDTO, Boolean> p) {
                return getButtonCell();
            }
         
        });
        
		table.getColumns().setAll(nameCol, amountCol, col_action);
		table.setBackground(Settings.getBackground());
		pane.setCenter(table);
		return pane;
	}

	protected void increment(int clickCount) {
		
		count = clickCount;
		textCount.setText(String.valueOf(count));
	}

	public void refresh() {
		if(category != null) {
			table.getColumns().clear();
			table.getColumns().setAll(nameCol, amountCol, col_action);
			List<ProductDTO> list = controller.getModel().getProductsByCategoryId(category.getId());
			ObservableList data = FXCollections.observableList(list == null ? Collections.emptyList() : list);
			table.setItems(data);
		}
	}
	

}
