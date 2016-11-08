package cz.borec.demo.core.dto;

import java.util.ArrayList;
import java.util.List;

import cz.borec.demo.core.entity.CategoryEntity;


public class CategoryDTO extends NamedDTO {

	private static final long serialVersionUID = 1L;
	private List<CategoryDTO> childCategories = new ArrayList<CategoryDTO>();
	private boolean root;
	private boolean secondLevel;
	/*private CategoryDTO parentCategory;

	public void setParentCategory(CategoryDTO parentCategory) {
		this.parentCategory = parentCategory;
	}

	public CategoryDTO getParentCategory() {
		return parentCategory;
	}*/

	public List<CategoryDTO> getChildCategories() {
		
		return childCategories;
	}

	public void setRoot(boolean b) {
		 root = b;
		
	}

	public boolean isRoot() {
		return root;
	}


	public boolean isSecondLevel() {
		return secondLevel;
	}

	public void setSecondLevel(boolean secondLevel) {
		this.secondLevel = secondLevel;
	}
}
