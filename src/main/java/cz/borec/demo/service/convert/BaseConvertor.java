package cz.borec.demo.service.convert;

import cz.borec.demo.core.dto.BaseDTO;
import cz.borec.demo.core.entity.BaseEntity;


//TODO: consider use of lambda expressions instead of such code.
public abstract class BaseConvertor<T extends BaseDTO<Long>, U extends BaseEntity<Long>> {
	public abstract T convertToDto(U entity);
	public abstract U convertToEntity(T dto);
	
/*	private BaseConvertor() {
		
	}
*/	
	//public abstract static BaseConvertor getInstance();
	
}
