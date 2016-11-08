package cz.borec.demo.core.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.CategoryDTO;

@Entity
@Table(name="sale_product")
public class SalesProductEntity extends DeletableEntity { //BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6608870396738948092L;

/*	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;
*/
    @ManyToOne(targetEntity=CategoryEntity.class)
    @JoinColumn(name="category")
    private CategoryEntity category;

    @Column(name="name", nullable=false, length=Constants.STRING_NORMAL)
    private String name;

    @Column(name="price", nullable=false, length=Constants.MAX_PRICE)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name="offer", nullable=false)
    private Boolean offer = Boolean.TRUE;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "salesProduct")
    private List<ProductRelationEntity> storeProducts = new ArrayList<ProductRelationEntity>();
    
    public List<ProductRelationEntity> getStoreProducts() {
		return storeProducts;
	}

	public void setStoreProducts(List<ProductRelationEntity> storeProducts) {
		this.storeProducts = storeProducts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

/*	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
*/
	public void setCategory(CategoryDTO category2) {
		CategoryEntity category = new CategoryEntity();
		category.setName(category2.getName());
		category.setId(category2.getId());
		setCategory(category);
	}

    public Boolean getOffer() {
		return offer;
	}

	public void setOffer(Boolean offer) {
		this.offer = offer;
	}

}
