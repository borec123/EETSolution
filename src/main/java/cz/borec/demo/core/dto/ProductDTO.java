package cz.borec.demo.core.dto;

import java.math.BigDecimal;

import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;

import cz.borec.demo.core.entity.UnitEntity;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.*;



public class ProductDTO extends BaseDTO<Long> {

	private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String description;
    
    private SimpleStringProperty descriptionProperty = new SimpleStringProperty(this, "desc", "");

    private SimpleStringProperty nameProperty = new SimpleStringProperty(this, "desc", "");


    private BigDecimal price = BigDecimal.ZERO;

    public BigDecimal getCriticalAmount() {
		return criticalAmount;
	}

	public void setCriticalAmount(BigDecimal criticalAmount) {
		this.criticalAmount = criticalAmount;
	}

	private BigDecimal criticalAmount = BigDecimal.ZERO;

    private CategoryDTO category;

	private UnitEntity unit;

	private BigDecimal amount = BigDecimal.ZERO;

	private boolean deleted;

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getName() {
		//return name;
		return nameProperty.get();
	}

	public void setName(String name) {
		//this.name = name;
		nameProperty.set(name);
	}

	public String getDescription() {
		//return description;
		return descriptionProperty.get();
	}

	public void setDescription(String description) {
		descriptionProperty.set(description);
		//this.description = description;
	}

	public SimpleStringProperty getDescriptionProperty() {
		return descriptionProperty;
	}

	public BigDecimal getPrice() {
		return price.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	@Override
	public Long getId() {
		
		return id;
	}

	public void setId(Long id2) {
		id = id2;
		
	}

	public SimpleStringProperty getNameProperty() {
		
		return nameProperty;
	}

	public void setUnit(UnitEntity value) {
		unit = value;
		
	}

	public UnitEntity getUnit() {
		return unit;
	}

	public BigDecimal getAmount() {
		
		return amount;
	}
	
	public String getUnitAmount() {
		return amount + " " + unit;
	}

	public void setDeleted(boolean b) {
		this.deleted = b;
		
	}

	public boolean isDeleted() {
		return deleted;
	}


}
