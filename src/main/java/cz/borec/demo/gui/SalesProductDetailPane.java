package cz.borec.demo.gui;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.StringUtils;

import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.entity.ProductEntity;
import cz.borec.demo.core.entity.ProductRelationEntity;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.core.entity.UnitEntity;
import cz.borec.demo.gui.ProductSearchPaneOrders.ButtonCell;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.service.convert.ProductConvertor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class SalesProductDetailPane extends AbstractPaneBase {

	private SalesProductEntity product;
	private TextField textFieldName;
	private TextField textFieldPrice;
	private TableView<ProductRelationEntity> table;
	private TableColumn<ProductRelationEntity, String> nameCol;
	private TableColumn<ProductRelationEntity, BigDecimal> amountCol;
	private ProductConvertor productConvertor = new ProductConvertor();
	private TableColumn<ProductRelationEntity, String> unitCol;
	private TableColumn col_action;
	private CheckBox checkBoxOffer;

	public SalesProductDetailPane(Controller controller) {
		super(controller);
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Produkty prodejn\u00ED");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Detail produktu");
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

	protected Node createMainContent() {

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(30, 0, 0, 70));
		
		javafx.scene.control.Label label1 = new Label("Name:");
		textFieldName = new TextField ();
		grid.add(label1, 0, 0, 2, 1);
		grid.add(textFieldName, 1, 0, 2, 1);
		
		label1 = new Label("Cena (k\u010D):");
		textFieldPrice = new TextField ();
		grid.add(label1, 0, 1);
		grid.add(textFieldPrice, 1, 1);
		
		label1 = new Label("Zobrazit v nab\u00EDdce:");
		checkBoxOffer = new CheckBox();
		grid.add(label1, 2, 1);
		grid.add(checkBoxOffer, 3, 1);
		
		label1 = new Label("Skladov\u00E9 produkty:");
		grid.add(label1, 0, 2);

		table = new TableView<ProductRelationEntity>();
		table.setPlaceholder(new javafx.scene.control.Label(""));
		nameCol = new TableColumn<ProductRelationEntity, String>(
				"N\u00E1zev");
		nameCol.setCellValueFactory(new PropertyValueFactory("name"));
		nameCol.setPrefWidth(250);
		nameCol.setStyle( "-fx-alignment: CENTER-LEFT;");

		amountCol = new TableColumn<ProductRelationEntity, BigDecimal>(
				"Mno\u017Estv\u00ED");
		amountCol.setCellValueFactory(new PropertyValueFactory("amount"));
		
		unitCol = new TableColumn<ProductRelationEntity, String>(
				"Jednotka");
		unitCol.setCellValueFactory(new PropertyValueFactory("unit"));
		
		col_action = new TableColumn("Akce");
        col_action.setSortable(false);
        col_action.setPrefWidth(150);
        col_action.setStyle( "-fx-alignment: CENTER;");
         
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProductRelationEntity, Boolean>, 
                javafx.beans.value.ObservableValue<Boolean>>() {
 
            @Override
            public javafx.beans.value.ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ProductRelationEntity, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
 
        col_action.setCellFactory(
                new Callback<TableColumn<ProductRelationEntity, Boolean>, TableCell<ProductRelationEntity, Boolean>>() {
 
            @Override
            public TableCell<ProductRelationEntity, Boolean> call(TableColumn<ProductRelationEntity, Boolean> p) {
                return getButtonCell();
            }
         
        });
        
		table.getColumns().setAll(nameCol, amountCol, unitCol, col_action);
		table.setBackground(Settings.getBackground());
		grid.add(table, 0, 3, 4, 2);

		
		return grid;
	}
	
	public void setProduct(SalesProductEntity o) {
		this.product = o;
		this.textFieldName.setText(product.getName());
		this.textFieldPrice.setText(product.getPrice().toString());
		this.checkBoxOffer.setSelected(product.getOffer());
		
/*		this.textAreaDescription.setText(product.getDescription());
		this.textFieldName.setText(product.getName());
		this.textFieldPrice.setText(product.getPrice().toString());
		textFieldCriticalAmount.setText(product.getCriticalAmount().toString());
		comboBoxUnits.setValue(product.getUnit());
*/	}

	protected void createButtons(HBox hbox) {
		LiveButton buttonAddStoreProduct = new LiveButton("P\u0159idat skladov\u00FD produkt");
		buttonAddStoreProduct.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.productSearchPane(ProductSearchMode.SALES_PRODUCTS);
				
			}
		});
		
		LiveButton buttonDone = new LiveButton("OK");
		buttonDone.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				if(!StringUtils.hasText(textFieldName.getText())) {
					AlertHelper.showInfoDialog("Chyba !", "N\u00E1zev mus\u00ED b\u00FDt zad\u00E1n !");
					return;
				}
				
				product.setName(textFieldName.getText());
				
				BigDecimal price;
				try {
				price = BigDecimal.valueOf(Double.parseDouble(textFieldPrice.getText()));
				} catch(Exception ex) {
					AlertHelper.showInfoDialog("Chyba !", "Cena mus\u00ED obsahovat platn\u00E9 \u010D\u00EDslo !");
					return;
				}
				product.setPrice(price );
				product.setOffer(checkBoxOffer.isSelected());
				
				if(product.getId() == null) {
					controller.getModel().createSalesProduct(product);
				} 
				else {
					controller.getModel().updateSalesProduct(product);
				}
				controller.productSalesSearchPane();
			}
		});

		LiveButton buttonAdd = new LiveButton("Zru\u0161it");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				controller.productSalesSearchPane();
			}
		});
		
		
		hbox.getChildren().add(buttonAddStoreProduct);
		hbox.getChildren().add(buttonDone);
		hbox.getChildren().add(buttonAdd);

	}

	public void addStoreProduct(ProductDTO product2, BigDecimal amount) {
		ProductRelationEntity relation = new ProductRelationEntity();
		relation.setAmount(amount);
		relation.setStoreProduct(productConvertor.convertToEntity(product2));
		relation.setSalesProduct(product);
		this.product.getStoreProducts().add(relation);
		refresh();
	}

	public void refresh() {
		table.getColumns().clear();
		table.getColumns().setAll(nameCol, amountCol, unitCol, col_action);
		List<ProductRelationEntity> list = this.product.getStoreProducts();
		ObservableList data = FXCollections.observableList(list);
		table.setItems(data);
	}

	public class ButtonCell extends TableCell<ProductRelationEntity, Boolean> {
	    final LiveButton cellButton = new LiveButton("Odstranit");
	     
	    public ButtonCell() {
	         
	        cellButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent t) {
	                // do something when button clicked
	                //...
	            	ProductRelationEntity o = (ProductRelationEntity) getTableRow().getItem();
	    	    	product.getStoreProducts().remove(o);
	    	    	refresh();
	            }
	        });
	        
	    }

	    //Display button if the row is not empty
	    @Override
	    protected void updateItem(Boolean t, boolean empty) {
	        super.updateItem(t, empty);
	        if(!empty){
	            setGraphic(cellButton);
	        }
	        else {
	            setGraphic(null);
	        }
	    }
	}

	
	protected TableCell<ProductRelationEntity, Boolean> getButtonCell() {

		return new ButtonCell();
	}

	
}
