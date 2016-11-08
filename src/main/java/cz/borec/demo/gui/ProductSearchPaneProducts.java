package cz.borec.demo.gui;

import java.util.List;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.gui.ProductSearchPaneOrders.ButtonCell;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ProductSearchPaneProducts extends ProductSearchPane {

	public ProductSearchPaneProducts(Controller controller, List<CategoryDTO> list) {
		super(controller);
		setCategories(list);
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Skladov\u00E9 produkty");
		hbox.getChildren().add(arrow);
	}

	public class ButtonCell extends TableCell<ProductDTO, Boolean> {
	    final LiveButton cellButton = new LiveButton("Upravit");
	     
	    public ButtonCell() {
	         
	        cellButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent t) {
	                // do something when button clicked
	                //...
	    	    	ProductDTO o = (ProductDTO) getTableRow().getItem();
	    	    	
	    	    	controller.productDetailPane(o);
	            }
	        });
	        
	        cellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					increment(arg0.getClickCount());
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

	@Override
	protected TableCell<ProductDTO, Boolean> getButtonCell() {

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

				if(category == null) {
					AlertHelper.showInfoDialog("Nen\u00ED zvolena kategorie.", "Zvolte kategorii !");
				}
				else {
	    	    	ProductDTO o = new ProductDTO();
	    	    	o.setCategory(category);
	    	    	controller.productDetailPane(o);
				}
			}
		});
		hbox.getChildren().add(buttonAdd);

		LiveButton buttonDelete = new LiveButton("Smazat");
		buttonDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				ProductDTO o = (ProductDTO) table.getSelectionModel().getSelectedItem();
				if(o == null) {
					AlertHelper.showInfoDialog("Zvolte produkt !", "");
					return;
				}
				boolean b = AlertHelper.showConfirmDialog("Opravdu smazat produkt ?", o.getName());
				if (b) {
					o.setDeleted(true);
					controller.getModel().updateProduct(o);
					refresh();
				}
			}
		});
		hbox.getChildren().add(buttonDelete);
		hbox.getChildren().add(buttonDone);
	}


}
