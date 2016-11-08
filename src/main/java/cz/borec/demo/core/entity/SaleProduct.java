/*package cz.borec.demo.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sale_product")
public class SaleProduct extends BaseEntity<Long> {

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = -6608870396738948092L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

    @ManyToOne(targetEntity=ProductEntity.class)
    @JoinColumn(name="store_product")
    private ProductEntity storeProduct;

    @Column(name="amount")
    private BigDecimal amount;

	public ProductEntity getStoreProduct() {
		return storeProduct;
	}

	public void setStoreProduct(ProductEntity storeProduct) {
		this.storeProduct = storeProduct;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
*/