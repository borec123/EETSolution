package cz.borec.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.borec.demo.core.entity.OrderEntity;


public interface OrderRepository extends GenericJpaRepository<OrderEntity, Long> {

/*	@Query("FROM OrderEntity WHERE email = :emailAddress")
	List<OrderEntity> findOrdersByEmail(@Param("emailAddress") String emailAddress);
*/
	@Query("FROM OrderEntity WHERE table_id = :id AND date is not null AND date > :date ORDER BY date DESC")
	//@Query("FROM OrderEntity WHERE table_id = :id AND date is not null AND :date = :date ORDER BY date DESC")
	List<OrderEntity> findOrderHistoryOfTable(@Param("id") Long id, @Param("date") Date date);

	@Query("FROM OrderEntity WHERE FIK is null AND date is not null AND payed = true AND storno = false ORDER BY date")
	List<OrderEntity> findNotSentOrders();

	@Query("FROM OrderEntity WHERE FIKStorno is null AND storno = true ORDER BY date")
	List<OrderEntity> findNotStornoedOrders();

}
