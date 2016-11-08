package cz.borec.demo.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cz.borec.demo.Constants;

@Entity
@Table(name="unit")
public class UnitEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1287980801774707049L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

	@Column(name="name", nullable=false, length=Constants.STRING_NORMAL)
    private String name;

	@Override
	public String toString() {
		
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		
		return id;
	}

}
