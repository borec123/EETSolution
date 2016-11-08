package cz.borec.demo.core.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cz.borec.demo.Constants;


@Entity
@Table(name="category")
public class CategoryEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

	@Column(name="name", nullable=false, length=Constants.STRING_NORMAL)
    private String name;
	
    @ManyToOne(targetEntity=CategoryEntity.class)
    @JoinColumn(name="parent_category")
	private CategoryEntity parentCategory;

	@OneToMany(mappedBy = "parentCategory")
	private List<CategoryEntity> childCategories;

	public CategoryEntity getParentCategory() {
		return parentCategory;
	}

	public List<CategoryEntity> getChildCategories() {
		return childCategories;
	}

	public void setParentCategory(CategoryEntity parentCategory) {
		this.parentCategory = parentCategory;
	}

	@Override
	public String toString() {
		
		return getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Long getId() {
		
		return id;
	}

	//TODO: avoid this shit setId method.
	public void setId(Long id2) {
		id = id2;
		
	}

	
}
