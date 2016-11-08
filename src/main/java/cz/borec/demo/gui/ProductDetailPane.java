package cz.borec.demo.gui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import javafx.collections.FXCollections;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.entity.ProductRelationEntity;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.core.entity.UnitEntity;
import cz.borec.demo.gui.TableHistoryPane.ButtonCell;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.service.convert.ProductConvertor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


public class ProductDetailPane extends AbstractPaneBase {

	private static final String LABEL_CRITICAL_AMOUNT = "Kritick\u00E9 mno\u017Estv\u00ED (%s):";
	private TextField textFieldName;
	//private TextField textFieldPrice;
	private TextArea textAreaDescription;
	private ProductDTO product;
	private ComboBox comboBoxUnits;
	private List<UnitEntity> units = null;
	private TextField textFieldCriticalAmount;
	private Label labelCriticalAmount;
	private TextField textFieldAmount;
	private CheckBox checkBoxCreateSalesProduct;
	private ProductConvertor productConvertor = new ProductConvertor();

	public ProductDetailPane(Controller controller, List<UnitEntity> units) {
		super(controller);
		textAreaDescription.setEditable(true);
		setUnits(units);
	}

	private void setUnits(List<UnitEntity> units2) {
		this.units = units2;
		comboBoxUnits.setItems(FXCollections.observableArrayList(units));
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Skladov\u00E9 produkty");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Detail produktu");
		hbox.getChildren().add(arrow);
	}

	@Override
	protected void fillVBox(GridPane vbox) {
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
	
	protected void createButtons(HBox hbox) {
		LiveButton buttonDone = new LiveButton("OK");
		buttonDone.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				if(!StringUtils.hasText(textFieldName.getText())/* || !StringUtils.hasText(textFieldPrice.getText())*/) {
					AlertHelper.showInfoDialog("Chyba !", "N\u00E1zev i cena mus\u00ED b\u00FDt zad\u00E1ny !");
					return;
				}
				
				product.setDescription(textAreaDescription.getText());
				product.setName(textFieldName.getText());
				
/*				BigDecimal price;
				try {
				price = BigDecimal.valueOf(Double.parseDouble(textFieldPrice.getText()));
				} catch(Exception ex) {
					AlertHelper.showInfoDialog("Chyba !", "Cena mus\u00ED obsahovat platn\u00E9 \u010D\u00EDslo !");
					return;
				}
				product.setPrice(price );
*/				
				product.setUnit((UnitEntity) comboBoxUnits.getValue());
				
				BigDecimal criticalAm;
				try {
					criticalAm = BigDecimal.valueOf(Double.parseDouble(textFieldCriticalAmount.getText()));
				} catch(Exception ex) {
					AlertHelper.showInfoDialog("Chyba !", "Kritick\u00E9 mno\u017Estv\u00ED mus\u00ED obsahovat platn\u00E9 \u010D\u00EDslo !");
					return;
				}
				product.setCriticalAmount(criticalAm );
				
				BigDecimal amount;
				try {
					amount = BigDecimal.valueOf(Double.parseDouble(textFieldAmount.getText()));
				} catch(Exception ex) {
					AlertHelper.showInfoDialog("Chyba !", "Mno\u017Estv\u00ED na sklad\u011B mus\u00ED obsahovat platn\u00E9 \u010D\u00EDslo !");
					return;
				}
				product.setAmount(amount );
				
				if(product.getId() == null) {
					controller.getModel().createProduct(product);
					if(checkBoxCreateSalesProduct.isSelected()) {
						SalesProductEntity saleProduct = new SalesProductEntity();
						saleProduct.setName(product.getName());
						saleProduct.setCategory(product.getCategory());
						List<ProductRelationEntity> storeProducts = new ArrayList<ProductRelationEntity>();
						ProductRelationEntity e = new ProductRelationEntity();
						e.setAmount(BigDecimal.ONE);
						e.setStoreProduct(productConvertor.convertToEntity(product));
						e.setSalesProduct(saleProduct);
						storeProducts.add(e );
						saleProduct.setStoreProducts(storeProducts );
						controller.getModel().createSalesProduct(saleProduct);
					}
					
				} 
				else {
					controller.getModel().updateProduct(product);
				}
				
				controller.productSearchPane(ProductSearchMode.PRODUCTS);
			}
		});

		LiveButton buttonAdd = new LiveButton("Zru\u0161it");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				controller.productSearchPane(ProductSearchMode.PRODUCTS);
			}
		});
		hbox.getChildren().add(buttonDone);
		hbox.getChildren().add(buttonAdd);

	}

	protected Node createMainContent() {

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(30, 0, 0, 70));
		
		javafx.scene.control.Label label1 = new Label("N\u00E1zev produktu:");
		textFieldName = new TextField ();
		grid.add(label1, 0, 0);
		grid.add(textFieldName, 1, 0);
		
/*		label1 = new Label("Cena (kc):");
		textFieldPrice = new TextField ();
		grid.add(label1, 0, 1);
		grid.add(textFieldPrice, 1, 1);
*/		
		label1 = new Label("Popis:");
		textAreaDescription = new TextArea ();
		grid.add(label1, 0, 1);
		grid.add(textAreaDescription, 1, 1);
		
		label1 = new Label("Mno\u017Estv\u00ED na sklad\u011B:");
		textFieldAmount = new TextField ();
		grid.add(label1, 0, 2);
		grid.add(textFieldAmount, 1, 2);
		
		label1 = new Label("Jednotka:");
		ObservableList<UnitEntity> unitList = 
			    FXCollections.observableArrayList(
			    		getEmptyUnitList()
			        );

		comboBoxUnits = new ComboBox (unitList);
		grid.add(label1, 0, 3);
		grid.add(comboBoxUnits, 1, 3);
		
		comboBoxUnits.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				labelCriticalAmount.setText(String.format(LABEL_CRITICAL_AMOUNT, comboBoxUnits.getValue()));
				
			}
		});
		
		labelCriticalAmount = new Label(String.format(LABEL_CRITICAL_AMOUNT, ""));
		textFieldCriticalAmount = new TextField ();
		grid.add(labelCriticalAmount, 0, 4);
		grid.add(textFieldCriticalAmount, 1, 4);
		
		checkBoxCreateSalesProduct = new CheckBox();
		label1 = new Label("Vytvo\u0159it i prodejn\u00ED produkt:");
		grid.add(label1, 0, 5);
		grid.add(checkBoxCreateSalesProduct, 1, 5);
		return grid;
	}
	
	private List<UnitEntity> getEmptyUnitList() {
		return Collections.emptyList();
	}

	public void setProduct(ProductDTO o) {
		this.product = o;
		this.textAreaDescription.setText(product.getDescription());
		this.textFieldName.setText(product.getName());
		//this.textFieldPrice.setText(product.getPrice().toString());
		textFieldCriticalAmount.setText(product.getCriticalAmount().toString());
		textFieldAmount.setText(product.getAmount().toString());
		comboBoxUnits.setValue(product.getUnit());
	}

}
