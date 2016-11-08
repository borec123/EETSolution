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
@Table(name="product_relation")
public class ProductRelationEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 3847162876981989956L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

	@ManyToOne(targetEntity=ProductEntity.class)
    @JoinColumn(name="store_product")
    private ProductEntity storeProduct;

	@ManyToOne(targetEntity=SalesProductEntity.class)
    @JoinColumn(name="sales_product")
    private SalesProductEntity salesProduct;

    @Column(name="amount")
    private BigDecimal amount;

	@Override
	public Long getId() {
		
		return id;
	}

	public SalesProductEntity getSalesProduct() {
		return salesProduct;
	}

	public void setSalesProduct(SalesProductEntity salesProduct) {
		this.salesProduct = salesProduct;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return this.storeProduct.getName();
	}
	public String getUnit() {
		return this.storeProduct.getUnit().toString();
	}
}
