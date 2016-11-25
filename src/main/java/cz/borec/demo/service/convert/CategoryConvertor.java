package cz.borec.demo.service.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.entity.CategoryEntity;

public class CategoryConvertor extends BaseConvertor<CategoryDTO, CategoryEntity> {

	@Override
	public CategoryDTO convertToDto(CategoryEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryEntity convertToEntity(CategoryDTO dto) {
		CategoryEntity ent = new CategoryEntity();
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		ent.setVat(dto.getVat());
		if(dto.getParentCategory() != null) {
			ent.setParentCategory(convertToEntity(dto.getParentCategory()));
		}
		/*List<CategoryEntity> list = new ArrayList<CategoryEntity>();;
		for (CategoryDTO childDTO : dto.getChildCategories()) {
			CategoryEntity childEnt =  convertToEntity(childDTO);
			list.add(childEnt);
		}
		ent.setChildCategories(list);*/
		return ent;
	}

}
