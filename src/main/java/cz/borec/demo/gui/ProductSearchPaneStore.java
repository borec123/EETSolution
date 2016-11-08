package cz.borec.demo.gui;

import java.util.List;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.gui.ProductSearchPaneOrders.ButtonCell;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ProductSearchPaneStore extends ProductSearchPane {

	public ProductSearchPaneStore(Controller controller, List<CategoryDTO> list) {
		super(controller);
		setCategories(list);
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("P\u0159\u00EDjem na sklad");
		hbox.getChildren().add(arrow);

	}


	public class ButtonCell extends TableCell<ProductDTO, Boolean> {
	    final LiveButton cellButton = new LiveButton("P\u0159\u00EDjem");
	     
	    public ButtonCell() {
	         
	        cellButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent t) {
	                // do something when button clicked
	                //...
	    	    	ProductDTO o = (ProductDTO) getTableRow().getItem();

	    	    	controller.storeIncomePane(o);
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
		LiveButton buttonAdd = new LiveButton("Hotovo");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.mainMenu();

			}
		});
		hbox.getChildren().add(buttonAdd);

	}
	

}
