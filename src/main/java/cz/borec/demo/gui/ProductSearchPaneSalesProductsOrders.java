package cz.borec.demo.gui;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.gui.ProductSearchPaneOrders.ButtonCell;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;

public class ProductSearchPaneSalesProductsOrders extends
		ProductSearchPaneSalesProducts {

	private BlueText label;
	private BlueText textCount;

	public ProductSearchPaneSalesProductsOrders(Controller controller,
			List<CategoryDTO> list) {
		super(controller, list);
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


	@Override
	public Pane addGridPane() {
		BorderPane borderPane = new BorderPane();
		HBox hbox = new HBox(); 
		hbox.setPadding(new Insets(8, 10, 5, 0));
		hbox.setSpacing(10);
		
		hbox.getChildren().add(new BlueText("Po\u010Det kus\u016F: ", 15));
		textCount = new BlueText("0", 18);
		hbox.getChildren().add(this.textCount);

		hbox.getChildren().add(new BlueText("Kategorie: ", 15));
		categoryText = new BlueText(" -- ", 15);
		hbox.getChildren().add(categoryText);
		hbox.setAlignment(Pos.CENTER_LEFT);
		borderPane.setTop(hbox);
		borderPane.setCenter(createMainPane());
		return borderPane;	}

	public void setOrder(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
		label.setText(orderDTO.getTableDTO().getRoomDTO().getName()
				+ " \u2192 St\u016Fl: " + orderDTO.getTableDTO().getName());
	}

	public class ButtonCell extends TableCell<SalesProductEntity, Boolean> {
	    final LiveButton cellButton = new LiveButton("P\u0159idej 1ks");
	     
	    public ButtonCell() {
	         
	        cellButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent t) {
	                // do something when button clicked
	                //...
	    	    	SalesProductEntity o = (SalesProductEntity) getTableRow().getItem();
	    	    	
	    	    	orderDTO.addItem(o);

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
	protected TableCell<SalesProductEntity, Boolean> getButtonCell() {

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
	
	protected void increment(int clickCount) {
		count = clickCount;
		textCount.setText(String.valueOf(count));
	}

	protected boolean isOrder() {
		return true;
	}

	protected void setColumns(
			ObservableList<TableColumn<SalesProductEntity, ?>> columns) {
		columns.setAll(nameCol, priceCol, col_action);
	}



}
