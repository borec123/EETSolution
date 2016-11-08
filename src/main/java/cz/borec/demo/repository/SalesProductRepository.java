package cz.borec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.borec.demo.core.entity.SalesProductEntity;

public interface SalesProductRepository extends GenericJpaRepository<SalesProductEntity, Long> {
    @Query("FROM SalesProductEntity WHERE category.id = :idCategory AND deleted = false ORDER BY id")
    List<SalesProductEntity> getProductsByCategoryId(@Param("idCategory") Long idCategory);

    @Query("FROM SalesProductEntity WHERE category.id = :idCategory AND offer = true AND deleted = false ORDER BY id")
    List<SalesProductEntity> getProductsByCategoryOnlyOfferId(@Param("idCategory") Long idCategory);

}
