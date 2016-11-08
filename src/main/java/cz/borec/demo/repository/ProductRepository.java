package cz.borec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.borec.demo.core.entity.ProductEntity;


public interface ProductRepository extends GenericJpaRepository<ProductEntity, Long> {
    @Query("FROM ProductEntity WHERE category.id = :idCategory AND deleted = false ORDER BY id")
    List<ProductEntity> getProductsByCategoryId(@Param("idCategory") Long idCategory);

    @Query("FROM ProductEntity WHERE amount < critical_amount AND deleted = false ORDER BY id")
    List<ProductEntity> getInsufficientProducts();

}
