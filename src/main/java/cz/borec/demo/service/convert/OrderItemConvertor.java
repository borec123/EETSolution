package cz.borec.demo.service.convert;

import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.entity.OrderEntity;
import cz.borec.demo.core.entity.OrderItemEntity;


public class OrderItemConvertor extends BaseConvertor<OrderItemDTO, OrderItemEntity> {

	ProductConvertor productConvertor = new ProductConvertor();
	private OrderDTO orderDto = null;
	private OrderEntity orderEntity = null;
	
	public OrderItemConvertor(OrderDTO dto) {
		this.orderDto = dto;
	}

	public OrderItemConvertor(OrderEntity entity) {
		this.orderEntity = entity;
	}

	@Override
	public OrderItemDTO convertToDto(OrderItemEntity entity) {
		OrderItemDTO dto = new OrderItemDTO();
		dto.setId(entity.getId());
		dto.setAmount(entity.getAmount());
		dto.setOrder(orderDto);
		dto.setPrice(entity.getPrice());
		dto.setProduct(entity.getProduct());
		return dto;
	}

	@Override
	public OrderItemEntity convertToEntity(OrderItemDTO dto) {
		OrderItemEntity entity = new OrderItemEntity();
		entity.setId(dto.getId());
		entity.setAmount(dto.getAmount());
		entity.setOrder(orderEntity);
		entity.setPrice(dto.getPrice());
		entity.setProduct(dto.getProduct());
		return entity;
	}

}
