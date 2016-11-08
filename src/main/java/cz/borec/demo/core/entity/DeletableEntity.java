package cz.borec.demo.core.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  
public abstract class DeletableEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 2139439035910912615L;

	@Id
 	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

	@Column(name="deleted", nullable=false)
	private boolean deleted = false;

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
