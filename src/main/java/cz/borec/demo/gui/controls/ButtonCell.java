package cz.borec.demo.gui.controls;

import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.ProductDTO;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;

/*public class ButtonCell extends TableCell<ProductDTO, Boolean> {
    final LiveButton cellButton = new LiveButton("P\u0159idej 1ks");
     
    public ButtonCell(){
         
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                // do something when button clicked
                //...
            	//System.out.println("button cell");
            }
        });
        
        cellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
            	System.out.println(arg0.getClickCount());
				
				// TODO Auto-generated method stub
				
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
    }
}*/