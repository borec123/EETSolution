package cz.borec.demo.core.dto;

import java.util.List;

public class TableDTO extends NamedDTO {

	private static final long serialVersionUID = 1L;

	public int X;
	public int Y;
	public int width;
	public int height;
	
	private RoomDTO roomDTO;
	private OrderDTO orderDTO;
	private TableType type = TableType.STANDARD;

	private boolean deleted;
	
	public TableType getType() {
		return type;
	}

	public void setType(TableType type) {
		this.type = type;
		switch(type) {
		case STANDARD:
			width = 70;
			height = 70;
			break;
		case BIG:
			width = 100;
			height = 100;
			break;
		case SMALL:
			width = 0;
			height = 0;
			break;
		case HORIZONTAL:
			width = 100;
			height = 50;
			break;
		case VERTICAL:
			width = 60;
			height = 100;
			break;
		default:
			throw new RuntimeException("Unknown table type.");
		}
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
		//orderDTO.setTable(this);
	}

	public RoomDTO getRoomDTO() {
		return roomDTO;
	}

	public void setRoomDTO(RoomDTO roomDTO) {
		this.roomDTO = roomDTO;
		roomDTO.getTables().add(this);
	}

	public void removeRoomDTO() {
		roomDTO.getTables().remove(this);
		//roomDTO = null;
	}

	public TableDTO(int x, int y, int width, int height, String name) {
		super();
		tableDTO(x, y, width, height);
		this.setName(name);
	}
	
	private void tableDTO(int x, int y, int width, int height) {
		X = x;
		Y = y;
		this.width = width;
		this.height = height;
	}

	public TableDTO(int x, int y, int width, int height) {
		super();
		tableDTO(x, y, width, height);
	}

	public void setDeleted(boolean b) {
		deleted = b;
		
	}

	public boolean isDeleted() {
		return deleted;
	}


}
