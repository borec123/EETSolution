/*package cz.borec.demo.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cz.borec.demo.Constants;

@Entity
@Table(name="config")
public class ConfigEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="id", length=Constants.STRING_ID)
	private String id;
	
	
	@Column(name="name", nullable=false, length=Constants.STRING_NORMAL)
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
*/