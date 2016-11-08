package cz.borec.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.borec.demo.core.entity.OrderEntity;
import cz.borec.demo.core.entity.OrderItemEntity;


public interface OrderItemRepository extends GenericJpaRepository<OrderItemEntity, Long> {

	@Query("SELECT o.product, sum(o.amount), sum(o.amount*o.price) AS a FROM OrderItemEntity o WHERE o.order.date is not null AND o.order.date >= :from AND o.order.date <= :to AND o.order.payed = true GROUP BY o.product ORDER BY a DESC")
	List<Object> getSalesHistory(@Param("from") Date from, @Param("to") Date to);

}
