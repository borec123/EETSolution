package cz.borec.demo.service.convert;

import cz.borec.demo.core.dto.RoomDTO;
import cz.borec.demo.core.entity.RoomEntity;

public class RoomConvertor extends BaseConvertor<RoomDTO, RoomEntity> {

	@Override
	public RoomDTO convertToDto(RoomEntity entity) {
		RoomDTO dto = new RoomDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	@Override
	public RoomEntity convertToEntity(RoomDTO dto) {
		RoomEntity ent = new RoomEntity();
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		return ent;
	}

}
