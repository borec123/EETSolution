package cz.borec.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.borec.demo.core.entity.OrderEntity;
import cz.borec.demo.core.entity.OrderState;


public interface OrderRepository extends GenericJpaRepository<OrderEntity, Long> {

/*	@Query("FROM OrderEntity WHERE email = :emailAddress")
	List<OrderEntity> findOrdersByEmail(@Param("emailAddress") String emailAddress);
*/
	@Query("FROM OrderEntity WHERE date > :date AND state <> 2 OR (date_of_handover > :date2 AND state = 2) ORDER BY date DESC") // 
	//@Query("FROM OrderEntity WHERE table_id = :id AND date is not null AND :date = :date ORDER BY date DESC")
	List<OrderEntity> findOrderHistory(@Param("date") Date date, @Param("date2") Date date2);

	@Query("FROM OrderEntity WHERE date is not null AND date > :date AND state = :state ORDER BY date DESC")
	//@Query("FROM OrderEntity WHERE table_id = :id AND date is not null AND :date = :date ORDER BY date DESC")
	List<OrderEntity> findOrderHistory(@Param("date") Date date, @Param("state") OrderState state);

	@Query("FROM OrderEntity WHERE FIK is null AND date is not null AND payed = true AND storno = false ORDER BY date")
	List<OrderEntity> findNotSentOrders();

	@Query("FROM OrderEntity WHERE FIKStorno is null AND storno = true ORDER BY date")
	List<OrderEntity> findNotStornoedOrders();

}
