package cz.borec.demo.core.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cz.borec.demo.Constants;



@Entity
@Table(name="order_t")
public class OrderEntity extends BaseEntity<Long> {

	
	public void setItems(List<OrderItemEntity> items) {
		this.items = items;
	}


	private static final long serialVersionUID = 1L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq")
	@SequenceGenerator(name="seq", sequenceName="ORDER_T_SEQUENCE")
    @Column(name="id")
    private Long id;

	@OneToMany(mappedBy = "order")  //@OneToMany(fetch = FetchType.LAZY) //, mappedBy = "stock")
	private List<OrderItemEntity> items;

	@Column(name="fullName", nullable=false, length=Constants.STRING_NORMAL)
    private String fullName;

	@Column(name="FIK", nullable=true, length=Constants.STRING_NORMAL)
    private String FIK;

	@Column(name="FIKStorno", nullable=true, length=Constants.STRING_NORMAL)
    private String FIKStorno;

	@Column(name="date_c", nullable=true)
    private Date date;
	
/*	@Column(name="date_of_ship", nullable=true)
    private Date dateOfShip;
	
	@Column(name="date_of_handover", nullable=true)
    private Date dateOfHandOver;
	
	@Column(name="date_of_storno", nullable=true)
    private Date dateOfStorno;
*/	
	@Column(name="state", nullable=false)
    private OrderState state;
	
	
	

/*    @ManyToOne(cascade=CascadeType.ALL, targetEntity=TableEntity.class)
    @JoinColumn(name="table_entity", nullable=true)
    private TableEntity table;
*/
	@Column(name="table_id", nullable=true)
    private Long tableId;

	@Column(name="first_call", nullable=false)
	private boolean firstFICall;

	@Column(name="payed", nullable=false)
	private boolean payed = true;

    @Column(name="discount", nullable=false, length=Constants.MAX_PRICE)
    private BigDecimal discount;
    
	@Column(name="storno", nullable=false)
	private boolean storno = false;

/*    public TableEntity getTable() {
		return table;
	}


	public void setTable(TableEntity table) {
		this.table = table;
	}
*/

	
	public boolean isStorno() {
		return storno;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public void setStorno(boolean storno) {
		this.storno = storno;
	}


	public BigDecimal getDiscount() {
		return discount;
	}


	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}


	public boolean isPayed() {
		return payed;
	}


	public void setPayed(boolean payed) {
		this.payed = payed;
	}


	public Date getDate() {
		return date;
	}


	public Long getTableId() {
		return tableId;
	}


	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}


	public void setDate(Date date) {
		this.date = date;
	}


/*    @Column(name="email", nullable=false, length=Constants.STRING_NORMAL)
    private String email;

    @Column(name="city", nullable=false, length=Constants.STRING_NORMAL)
    private String city;

    @Column(name="street", nullable=false, length=Constants.STRING_NORMAL)
    private String street;
*/

    public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public void setId(Long id) {
		this.id = id;
	}


	public List<OrderItemEntity> getItems() {
		return items;
	}


	@Override
	public Long getId() {
		
		return id;
	}

	public String getFIK() {
		return FIK;
	}

	public void setFIK(String fIK) {
		FIK = fIK;
	}


	public boolean isFirstFICall() {
		
		return this.firstFICall;
	}


	public void setFirstFICall(boolean firstFICall) {
		this.firstFICall = firstFICall;
	}

	public String getFIKStorno() {
		return FIKStorno;
	}

	public void setFIKStorno(String fIKStorno) {
		FIKStorno = fIKStorno;
	}


}
