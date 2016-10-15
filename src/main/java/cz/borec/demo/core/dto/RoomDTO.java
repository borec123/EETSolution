package cz.borec.demo.core.dto;

import java.util.ArrayList;
import java.util.List;


public class RoomDTO extends NamedDTO {

	private static final long serialVersionUID = 1L;
	private List<TableDTO> tables;
	
	public List<TableDTO> getTables() {
		if(tables == null) {
			tables = new ArrayList<TableDTO>();
		}
		return tables;
	}
	public void setTables(List<TableDTO> tables) {
		this.tables = tables;
	}
	
	
}
