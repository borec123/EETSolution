package cz.borec.demo.gui;

import java.util.List;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.gui.ProductSearchPaneProducts.ButtonCell;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ProductSearchPaneForSalesProducts extends ProductSearchPaneProducts {

	public ProductSearchPaneForSalesProducts(Controller controller, List<CategoryDTO> list) {
		super(controller, list);
	}

	@Override
	protected void createButtons(HBox hbox) {
		LiveButton buttonDone = new LiveButton("Zp\u011Bt");
		buttonDone.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				controller.salesProductDetailPane(null);
			}
		});

	}
	
	public class ButtonCell extends TableCell<ProductDTO, Boolean> {
	    final LiveButton cellButton = new LiveButton("Vybrat");
	     
	    public ButtonCell() {
	         
	        cellButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent t) {
	                // do something when button clicked
	                //...
	    	    	ProductDTO o = (ProductDTO) getTableRow().getItem();
	    	    	
	    	    	controller.salesProductEnterAmountPane(o);
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

}
