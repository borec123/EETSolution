package cz.borec.demo.core.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.TableType;

@Entity
@Table(name="table_t")
public class TableEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
 	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
	private Long id;

	@Column(name="name", unique = true, nullable=false, length=Constants.STRING_NORMAL)
	private String name;
	
    @ManyToOne(targetEntity=RoomEntity.class)
    @JoinColumn(name="room", nullable=false)
	private RoomEntity room;
    
    @OneToOne(cascade=CascadeType.ALL, targetEntity=OrderEntity.class)
    @JoinColumn(name="order_entity", nullable=true)
    private OrderEntity order;
    
    @Column
	public int X;
    
    @Column
	public int Y;
    
    @Column
	public int width;
    
    @Column
	public int height;

	@Column(name="deleted", nullable=false)
	private boolean deleted = false;


	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public RoomEntity getRoom() {
		return room;
	}

	public void setRoom(RoomEntity room) {
		this.room = room;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
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

/*	public TableType getType() {
		
		return this.type;
	}*/

}
