package cz.borec.demo.gui;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;

public class ProductSearchPaneOrders extends ProductSearchPane {

	BlueText label;

	public ProductSearchPaneOrders(Controller controller, List<CategoryDTO> list) {
		super(controller);
		setCategories(list);
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		LiveButton buttonRooms = createButtonRooms();
		hbox.getChildren().add(buttonRooms);
		arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		label = new BlueText("St\u016Fl: ");
		hbox.getChildren().add(label);
		arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("V\u00FDb\u011Br produktu");
		hbox.getChildren().add(arrow);

	}

	public void setOrder(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
		label.setText(orderDTO.getTableDTO().getRoomDTO().getName()
				+ " \u2192 St\u016Fl: " + orderDTO.getTableDTO().getName());
	}

	public class ButtonCell extends TableCell<ProductDTO, Boolean> {
	    final LiveButton cellButton = new LiveButton("P\u0159idej 1ks");
	     
	    public ButtonCell() {
	         
	        cellButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent t) {
	                // do something when button clicked
	                //...
	    	    	ProductDTO o = (ProductDTO) getTableRow().getItem();
	    	    	//orderDTO.addItem(o);
	    	    	System.out.println("Nothing done: " + o.getName());
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
		LiveButton buttonAdd = new LiveButton("Hotovo");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.saveOrUpdateOrder(orderDTO);
				controller.tablePane();
			}
		});
		hbox.getChildren().add(buttonAdd);

	}
	

}
