package cz.borec.demo.core.entity;

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
@Table(name="store_income")
public class StoreIncomeEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 4241557149782697379L;
	
	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

	@ManyToOne(targetEntity=ProductEntity.class)
    @JoinColumn(name="store_product")
	private ProductEntity storeProduct;

    @Column(name="amount", nullable=false)
	private BigDecimal amount;
	
    @Column(name="price", nullable=false)
	private BigDecimal price;
	
    @Column(name="company")
	private String supplierCompany;
	
	@Override
	public Long getId() {
		
		return id;
	}

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSupplierCompany() {
		return supplierCompany;
	}

	public void setSupplierCompany(String supplierCompany) {
		this.supplierCompany = supplierCompany;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
