package cz.borec.demo.gui;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SalesProductEnterAmountPane extends AbstractPaneBase {

	private TextField textFieldAmount;
	private ProductDTO product;
	private BlueText storeProduct;
	private Label labelUnit;

	public SalesProductEnterAmountPane(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
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
		arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Mno\u017Estv\u00ED skladov\u00E9ho produktu");
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
		
		javafx.scene.control.Label label1 = new Label("Skladov\u00FD produkt:");
		storeProduct = new BlueText();
		grid.add(label1, 0, 0);
		grid.add(storeProduct, 1, 0, 2, 1);
		
		label1 = new Label("Mno\u017Estv\u00ED:");
		textFieldAmount = new TextField ();
		labelUnit = new Label();
		grid.add(label1, 0, 1);
		grid.add(textFieldAmount, 1, 1);
		grid.add(labelUnit, 2, 1);
		
		return grid;
	}

	public void setStoreProduct(ProductDTO o) {
		this.product = o;
		storeProduct.setText(o.getName());
		labelUnit.setText(o.getUnit().getName());
	}

	protected void createButtons(HBox hbox) {
		LiveButton buttonCancel = new LiveButton("Zru\u0161it");
		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.salesProductDetailPane(null);
				
			}
		});
		
		LiveButton buttonOK = new LiveButton("OK");
		buttonOK.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				if(!StringUtils.hasText(textFieldAmount.getText())) {
					AlertHelper.showInfoDialog("Chyba !", "Mno\u017Estv\u00ED mus\u00ED b\u00FDt zad\u00E1no !");
					return;
				}
				
				
				BigDecimal amount;
				try {
					amount = BigDecimal.valueOf(Double.parseDouble(textFieldAmount.getText()));
				} catch(Exception ex) {
					AlertHelper.showInfoDialog("Chyba !", "Mno\u017Estv\u00ED mus\u00ED obsahovat platn\u00E9 \u010D\u00EDslo !");
					return;
				}
				controller.salesProductDetailPane(product, amount);
			}
		});

		
		hbox.getChildren().add(buttonOK);
		hbox.getChildren().add(buttonCancel);

	}

}
