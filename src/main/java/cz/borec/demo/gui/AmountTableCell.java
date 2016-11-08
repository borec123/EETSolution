package cz.borec.demo.gui;

import java.math.BigDecimal;



import cz.borec.demo.core.dto.ProductDTO;
import javafx.scene.paint.Color;

public class AmountTableCell extends javafx.scene.control.TableCell<ProductDTO, BigDecimal> {

	
    @Override
    public void updateItem(BigDecimal item, boolean empty) {
        super.updateItem(item, empty);
        if (!isEmpty()) {
	    	ProductDTO o = (ProductDTO) getTableRow().getItem();
	    	if(item.doubleValue() < o.getCriticalAmount().doubleValue()) {
	    		this.setTextFill(Color.RED);
	    	}
            
/*                            if(item.contains("@")) 
                this.setTextFill(Color.BLUEVIOLET);
            setText(item);
*/
        }
    }

}
