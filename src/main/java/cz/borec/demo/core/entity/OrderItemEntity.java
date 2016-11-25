package cz.borec.demo.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cz.borec.demo.Constants;


@Entity
@Table(name="order_item")
public class OrderItemEntity extends BaseEntity<Long> {

	public void setId(Long id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq")
	@SequenceGenerator(name="seq", sequenceName="ORDER_ITEM_SEQUENCE")
	//@GenericGenerator(name="increment", strategy = "")
    @Column(name="id")
    private Long id;

	//@OneToOne(cascade=CascadeType.ALL, targetEntity=ProductEntity.class)
    @ManyToOne(targetEntity=SalesProductEntity.class)
    @JoinColumn(name="product")
    private SalesProductEntity product;
    
    @ManyToOne
    @JoinColumn(name="order_parent")
    private OrderEntity order;
    
    @Column(name="price", nullable=false, length=Constants.MAX_PRICE)
    private BigDecimal price;

    @Column(name="vatValue", nullable=false, length=Constants.MAX_PRICE)
    private BigDecimal vatValue;

    public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public OrderEntity getOrder() {
		return order;
	}


	public void setOrder(OrderEntity order) {
		this.order = order;
	}


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	@Column(name="amount", nullable=false)
    private Integer amount;

	@Override
	public Long getId() {
		return id;
	}


	public SalesProductEntity getProduct() {
		return product;
	}


	public void setProduct(SalesProductEntity product) {
		this.product = product;
	}


	public BigDecimal getVatValue() {
		return vatValue;
	}


	public void setVatValue(BigDecimal vatValue) {
		this.vatValue = vatValue;
	}
	
}
