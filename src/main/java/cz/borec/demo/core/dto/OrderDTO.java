package cz.borec.demo.core.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import javax.persistence.Column;

import cz.borec.demo.core.entity.OrderState;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.util.NumberFormatter;


public class OrderDTO extends BaseDTO<Long> {

	private static final long serialVersionUID = 1L;

    private Long id;
	protected LinkedHashMap<Long, OrderItemDTO> items;
    private String fullName;
/*    private String email;
    private String city;
    private String street;
    private String PSC;
*/
    private Date date;
    private TableDTO tableDTO;

	private String FIK;

	private boolean firstFICall;

	private Long tableId;

	private boolean payed = true;

	private BigDecimal discount = BigDecimal.ZERO;

	private boolean storno = false;

	private String FIKStorno;

	private List<OrderItemDTO> deletedItems;

    private OrderState state;
	
    private Date dateOfHandOver;

    public OrderState getState() {
		return state;
	}

	public Date getDateOfHandOver() {
		return dateOfHandOver;
	}

	public void setDateOfHandOver(Date dateOfHandOver) {
		this.dateOfHandOver = dateOfHandOver;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	public boolean isFirstFICall() {
		return firstFICall;
	}

	public BigDecimal getSumBase() {
    	BigDecimal sum = BigDecimal.valueOf(0L);
    	for (OrderItemDTO item : getItemMap().values()) {
    		sum = sum.add(item.getPriceTotal());
		}
    	return sum;
    }
    
    public BigDecimal getSum() {
    	return getSumBase().setScale(2, BigDecimal.ROUND_UP);
    }
    
    public BigDecimal getSumAfterDiscount() {
    	return getSumBase().subtract(discount);
    }
    
    public String getSumFormattedAfterDiscount() {
    	return NumberFormatter.format(getSumAfterDiscount().setScale(2, BigDecimal.ROUND_UP));
    }
    
    public String getSumFormatted() {
    	return NumberFormatter.format(getSum().setScale(2, BigDecimal.ROUND_UP));
    }
    
     public OrderDTO() {
		super();
		setState(OrderState.PREPARING);
	}

	public BigDecimal getVatAmount() {
    	//return (getSumAfterDiscount().multiply(new BigDecimal(0.21))).setScale(2, BigDecimal.ROUND_UP);
		
		//TODO count with discount...
    	BigDecimal sum = BigDecimal.valueOf(0L);
    	for (OrderItemDTO item : getItemMap().values()) {
    		sum = sum.add(item.getVatValue());
		}
    	return sum;
    }
    
    public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public Long getId() {
		return id;
	}


	public Collection<OrderItemDTO> getItems() {
		return getItemMap().values();
	}

	public LinkedHashMap<Long, OrderItemDTO> getItemMap() {
		if(items == null) {
			items = new LinkedHashMap<Long, OrderItemDTO>();
		}
		return items;
	}

	public void addItem(SalesProductEntity product) {
		if(items.containsKey(product.getId())) {
			OrderItemDTO orderItem = items.get(product.getId());
			orderItem.incrementAmount();
			orderItem.calculateVat();
		}
		else {
			OrderItemDTO value = new OrderItemDTO();
			value.setOrder(this);
			value.setProduct(product);
			value.setAmount(1);
			value.calculateVat();
			items.put(product.getId(), value );
		}
	}

	public void setItems(Collection<OrderItemDTO> items) {
		//items.forEach(aaa -> this.items.put(aaa.getProduct().getId(), aaa));
		
		for (OrderItemDTO orderItemDTO : items) {
			 getItemMap().put(orderItemDTO.getProduct().getId(), orderItemDTO);
		}
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	//Chujovo:
/*	public void addItem(OrderItemDTO i) {
		getItems().add(i);
		i.setOrder(this);
	}
	
	public void removeItem(OrderItemDTO i) {
		getItems().remove(i);
		//TODO: finalize i.
	}*/

	public void setTable(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
	}

	public TableDTO getTableDTO() {
		return tableDTO;
	}

	public void setFIK(String fik) {
		this.FIK = fik;
		
	}

	public String getFIK() {
		return FIK;
	}
	
    public boolean isSummarized() {
    	return false;
    }

	public void setFirstFICall(boolean b) {
		this.firstFICall = b;
		
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return tableId;
	}

	public boolean isPayed() {
		return this.payed;
	}
	
	public String getStateString() {
		return (getFIK() == null ? "Neodesl\u00E1no" : "Odesl\u00E1no") + "/"
				+ (isPayed() ? "Zaplaceno" : "Nezaplaceno") + "/" + (storno ? "Stornov\u00E1no" : "Nestornov\u00E1no");
	}

	public BigDecimal getDiscount() {
		
		return discount ;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public void setStorno(boolean b) {
		this.storno = b;
		
	}

	public boolean isStorno() {
		return storno;
	}

	public void setFIKStorno(String fik2) {
		this.FIKStorno = fik2;
		
	}

	public String getFIKStorno() {
		return FIKStorno;
	}

	public void setItemMap(LinkedHashMap<Long, OrderItemDTO> itemMap) {
		this.items = itemMap;
		
	}

	public List<OrderItemDTO> getDeletedItems() {
		if(deletedItems == null) {
			deletedItems = new ArrayList<OrderItemDTO>();
		}
		return deletedItems;
	}

}
