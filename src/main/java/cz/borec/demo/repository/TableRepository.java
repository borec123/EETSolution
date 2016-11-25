package cz.borec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.borec.demo.core.entity.ProductEntity;
import cz.borec.demo.core.entity.TableEntity;

public interface TableRepository extends GenericJpaRepository<TableEntity, Long> {

	@Query("FROM TableEntity WHERE deleted = false AND room.id = :idRoom")
    List<TableEntity> getTablesByRoomId(@Param("idRoom") Long idRoom);

}
