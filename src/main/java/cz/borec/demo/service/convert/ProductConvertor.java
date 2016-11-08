package cz.borec.demo.service.convert;

import java.util.ArrayList;
import java.util.List;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.entity.CategoryEntity;
import cz.borec.demo.core.entity.ProductEntity;


public class ProductConvertor extends BaseConvertor<ProductDTO, ProductEntity> {

	@Override
	public ProductDTO convertToDto(ProductEntity entity) {
		ProductDTO dto = new ProductDTO();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        CategoryDTO c = new CategoryDTO();
        c.setName(entity.getCategory().getName());
        c.setId(entity.getCategory().getId());
        dto.setCategory(c);
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCriticalAmount(entity.getCriticalAmount());
        dto.setUnit(entity.getUnit());
        dto.setAmount(entity.getAmount());
        dto.setDeleted(entity.isDeleted());
		return dto;
	}

	@Override
	public ProductEntity convertToEntity(ProductDTO dto) {
		ProductEntity entity = new ProductEntity();
		entity.setDescription(dto.getDescription());
		CategoryEntity category = new CategoryEntity();
		category.setName(dto.getCategory().getName());
		category.setId(dto.getCategory().getId());
		entity.setCategory(category );
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setAmount(dto.getAmount());
		entity.setCriticalAmount(dto.getCriticalAmount());
		entity.setUnit(dto.getUnit());
		entity.setId(dto.getId());
		entity.setDeleted(dto.isDeleted());
		return entity;
	}

	public List<ProductDTO> convertListToDto(List<ProductEntity> entities) {
        List<ProductDTO> dtos = new ArrayList<ProductDTO>(entities.size());
        for (ProductEntity entity : entities) {
        	ProductDTO dto = this.convertToDto(entity);
            dtos.add(dto);
        }

        return dtos;

		
	}

}
