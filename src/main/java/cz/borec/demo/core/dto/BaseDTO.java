package cz.borec.demo.core.dto;

import java.io.Serializable;

public abstract class BaseDTO<ID extends Serializable> implements  Serializable  {

	public abstract ID getId();
	
	private static final long serialVersionUID = 1L;

}
