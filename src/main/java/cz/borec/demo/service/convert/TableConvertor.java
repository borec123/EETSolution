package cz.borec.demo.service.convert;

import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.core.entity.TableEntity;

public class TableConvertor extends BaseConvertor<TableDTO, TableEntity> {

	private static OrderConvertor orderConvertor = new OrderConvertor();
	
	private RoomConvertor roomConvertor = new RoomConvertor();

	@Override
	public TableDTO convertToDto(TableEntity entity) {
		return convertToDto(entity, true);
	}

	@Override
	public TableEntity convertToEntity(TableDTO dto) {
		return convertToEntity(dto, true);
	}

	public TableDTO convertToDto(TableEntity entity, boolean convertOrder) {
		TableDTO dto = new TableDTO(entity.X, entity.Y, entity.width,
				entity.height);
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		//dto.setType(entity.getType());
		dto.setRoomDTO(roomConvertor.convertToDto(entity.getRoom()));
		if (convertOrder) {
			if (entity.getOrder() != null) {
				OrderDTO ord = orderConvertor.convertToDto(entity.getOrder());
				dto.setOrderDTO(ord);
				ord.setTable(dto);
			}
		}
		return dto;
	}

	public TableEntity convertToEntity(TableDTO dto, boolean convertOrder) {
		TableEntity ent = new TableEntity();
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		ent.setHeight(dto.height);
		if (convertOrder) {
			if (dto.getOrderDTO() != null) {
				ent.setOrder(orderConvertor.convertToEntity(dto.getOrderDTO()));
			}
		}
		ent.setRoom(roomConvertor.convertToEntity(dto.getRoomDTO()));
		ent.setWidth(dto.width);
		ent.setX(dto.X);
		ent.setY(dto.Y);
		return ent;
	}

}
