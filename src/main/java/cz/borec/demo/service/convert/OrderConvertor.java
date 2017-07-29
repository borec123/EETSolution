package cz.borec.demo.service.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.entity.OrderEntity;
import cz.borec.demo.core.entity.OrderItemEntity;


public class OrderConvertor extends BaseConvertor<OrderDTO, OrderEntity> {

	private static TableConvertor tableConvertor = new TableConvertor();
	
	@Override
	public OrderDTO convertToDto(OrderEntity entity) {
		OrderDTO dto = new OrderDTO();
		dto.setState(entity.getState());
		dto.setFullName(entity.getFullName());
		dto.setFIK(entity.getFIK());
		dto.setFIKStorno(entity.getFIKStorno());
		dto.setPayed(entity.isPayed());
		dto.setFirstFICall(entity.isFirstFICall());
		dto.setDate(entity.getDate());
		dto.setDateOfHandOver(entity.getDateOfHandOver());
		
		
		dto.setDiscount(entity.getDiscount());
		dto.setStorno(entity.isStorno());
		dto.setId(entity.getId());
		if(entity.getTableId() != null) {
			dto.setTableId(entity.getTableId());
		}
		copyItems(dto, entity.getItems());
		return dto;
	}
	

	

	private void copyItems(OrderDTO dto, List<OrderItemEntity> items) {
		
		OrderItemConvertor orderItemConvertor = new OrderItemConvertor(dto);
		
		ArrayList<OrderItemDTO> list = new ArrayList<OrderItemDTO>();
		
		for (OrderItemEntity orderItemEntity : items) {
			list.add(orderItemConvertor.convertToDto(orderItemEntity));
		}
		
		dto.setItems(list);
		
	}

	@Override
	public OrderEntity convertToEntity(OrderDTO dto) {
		OrderEntity entity = new OrderEntity();
		entity.setState(dto.getState());
		entity.setFullName(dto.getFullName());
		entity.setFIK(dto.getFIK());
		entity.setFIKStorno(dto.getFIKStorno());
		entity.setPayed(dto.isPayed());
		entity.setDate(dto.getDate());
		entity.setDateOfHandOver(dto.getDateOfHandOver());
		entity.setFirstFICall(dto.isFirstFICall());
		entity.setDiscount(dto.getDiscount());
		entity.setStorno(dto.isStorno());
		entity.setId(dto.getId());
		if(dto.getTableDTO() != null) {
			//entity.setTable(tableConvertor.convertToEntity(dto.getTableDTO(), false));
			entity.setTableId(dto.getTableDTO().getId());
		}
		else {
			entity.setTableId(dto.getTableId());
		}
		
		//copyItems(entity, dto.getItems());
		
		return entity;
	}

	private void copyItems(OrderEntity entity, Collection<OrderItemDTO> items) {
		
		OrderItemConvertor orderItemConvertor = new OrderItemConvertor(entity);
		
		ArrayList<OrderItemEntity> list = new ArrayList<OrderItemEntity>();
		
		for (OrderItemDTO orderItemDto : items) {
			list.add(orderItemConvertor.convertToEntity(orderItemDto));
		}
		
		entity.setItems(list);
		
	}

	public List<OrderDTO> convertListToDto(List<OrderEntity> orders) {
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		for (OrderEntity orderEntity : orders) {
			list.add(convertToDto(orderEntity));
		}
		return list;
	}

}
