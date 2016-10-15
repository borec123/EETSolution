package cz.borec.demo.core.dto;

public abstract class NamedDTO extends BaseDTO<Long> {
	
 	private static final long serialVersionUID = 1L;
	private String name;
    private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Long getId() {
		
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		
	}

	@Override
	public String toString() {
		return getName();
	}


}
