package cz.borec.demo.repository;

import java.io.Serializable;

public class EntityNotFoundException extends Exception {

	public <T> EntityNotFoundException(Class<T> domainClass, Serializable id) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
