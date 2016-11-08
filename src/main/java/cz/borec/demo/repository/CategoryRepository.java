package cz.borec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cz.borec.demo.core.entity.CategoryEntity;
import cz.borec.demo.core.entity.OrderEntity;


public interface CategoryRepository extends GenericJpaRepository<CategoryEntity, Long> {
	
	@Query("FROM CategoryEntity WHERE parentCategory is null")
	List<CategoryEntity> findTopLevelCategories();

}
