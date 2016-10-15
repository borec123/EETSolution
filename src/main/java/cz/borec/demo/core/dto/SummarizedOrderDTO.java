package cz.borec.demo.core.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SummarizedOrderDTO extends OrderDTO {
    private Date dateTo;

	public BigDecimal getSumBase() {
    	BigDecimal sum = BigDecimal.valueOf(0L);
    	for (OrderItemDTO item : items.values()) {
    		sum = sum.add(item.getPrice());
		}
    	return sum;
    }
    
    public boolean isSummarized() {
    	return true;
    }

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
		
	}

	public Date getDateTo() {
		return dateTo;
	}
    
}
