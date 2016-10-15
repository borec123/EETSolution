package cz.borec.demo.core.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import cz.borec.demo.core.entity.SalesProductEntity;


public class OrderItemDTO extends BaseDTO<Long> implements AmountObject {

	private static final long serialVersionUID = 1L;

    private Long id;
    private SalesProductEntity product;
    private OrderDTO order;
    private Integer amount;
    private BigDecimal price = null;
    
    public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	@Override
	public Long getId() {
		return id;
	}


	public SalesProductEntity getProduct() {
		return product;
	}

	public String getProductName() {
		return product.getName();
	}

	public BigDecimal getPriceTotal() {
		BigDecimal sum = getPrice().multiply(BigDecimal.valueOf(amount));
		return sum.setScale(2, BigDecimal.ROUND_UP);
	}


	public BigDecimal getPrice() {
		if(price == null) {
			price = product.getPrice();
		}
		return price;
	}


	public void setProduct(SalesProductEntity product) {
		this.product = product;
	}


	public OrderDTO getOrder() {
		return order;
	}


	public void setOrder(OrderDTO order) {
		this.order = order;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void incrementAmount() {
		amount++;
		
	}


	public void setPrice(BigDecimal price2) {
		price = price2;
		
	}

}
