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

import cz.borec.demo.Constants;

@Entity
@Table(name="product")
public class ProductEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable=false, length=Constants.STRING_NORMAL)
    private String name;

    @Column(name="description", nullable=true, length=Constants.STRING_NORMAL)
    private String description;

    @Column(name="price", nullable=false, length=Constants.MAX_PRICE)
    private BigDecimal price;

    @ManyToOne(targetEntity=CategoryEntity.class)
    @JoinColumn(name="category")
    private CategoryEntity category;

    @ManyToOne(targetEntity=UnitEntity.class)
    @JoinColumn(name="unit")
    private UnitEntity unit;

    @Column(name="critical_amount", nullable=false)
    private BigDecimal criticalAmount;

    @Column(name="amount", nullable=false)
    private BigDecimal amount = BigDecimal.ZERO;

	@Column(name="deleted", nullable=false)
	private boolean deleted = false;

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

   
	public UnitEntity getUnit() {
		return unit;
	}


	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}


	public BigDecimal getCriticalAmount() {
		return criticalAmount;
	}


	public void setCriticalAmount(BigDecimal criticalAmount) {
		this.criticalAmount = criticalAmount;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public CategoryEntity getCategory() {
		return category;
	}


	public void setCategory(CategoryEntity category) {
		this.category = category;
	}


	@Override
	public Long getId() {
		
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

}
