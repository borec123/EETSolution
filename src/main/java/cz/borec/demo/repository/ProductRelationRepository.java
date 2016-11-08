package cz.borec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cz.borec.demo.core.entity.ProductRelationEntity;

public interface ProductRelationRepository extends GenericJpaRepository<ProductRelationEntity, Long> {
	
	@Modifying
	@Transactional
	@Query("DELETE FROM ProductRelationEntity WHERE salesProduct.id = :idSalesProduct")
    void deleteRelationSalesProductId(@Param("idSalesProduct") Long idSalesProduct);

	@Query("FROM ProductRelationEntity WHERE salesProduct.id = :idSalesProduct")
    List<ProductRelationEntity> getStoreProducts(@Param("idSalesProduct") Long idSalesProduct);

}
