package cz.borec.demo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.dto.RoomDTO;
import cz.borec.demo.core.dto.SummarizedOrderDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.core.entity.UnitEntity;

public interface ServiceInterface {

	public String getPiczus();
	
	public void setPiczus(String piczus);
	
	public List<RoomDTO> getAllRooms();

	//List<CategoryDTO> getAllCategories();
	
	//List<ProductDTO> getProductsByCategoryId(Long idCategory);

	//long createCategory(CategoryDTO categoryDTO);

	
	public List<ProductDTO> getAllProducts();

	public List<CategoryDTO> getAllCategories(boolean reloadFromDatabase);

	public long createProduct(ProductDTO productDTO);

    //TODO: remove it as categories are set manually.
    public long createCategory(CategoryDTO categoryDTO);

	List<OrderDTO> getAllOrders();

	public long createOrder(OrderDTO orderDTO);
	
	public ProductDTO getProductById(Long id);

	public void updateProduct(ProductDTO dto);

	public void deleteProductById(Long id);

	public List<ProductDTO> getEmptyListOfProducts();

	public List<ProductDTO> getProductsByCategoryId(Long idCategory);

	//public CategoryDTO getCategoryById(Long id);

	public OrderDTO getOrderById(Long id);

	//public List<OrderDTO> getOrdersByEmail(String emailAddress);

	public List<OrderDTO> getOrderHistoryOfTable(TableDTO dto);

	public void updateOrder(OrderDTO orderDTO);

	public void saveOrderItems(OrderDTO orderDTO);	
	
	public long createTable(TableDTO dto);
	
	public long createRoom(RoomDTO dto);

	public void saveTable(TableDTO tableDTO);
	
	public void saveOrderItem(OrderItemDTO orderItemDTO, boolean checkOrderDate);

	public void completeOrder(OrderDTO orderDTO);
	
	public void updateOrderInternal(OrderDTO orderDTO);
	
	public List<UnitEntity> getAllUnits();

	public List<SalesProductEntity> getSalesProductsByCategoryId(Long id, boolean onlyOffer);

	public long createSalesProduct(SalesProductEntity product);

	public void updateSalesProduct(SalesProductEntity product);

	public void loadStoreProducts(SalesProductEntity o);

	public void performStoreIncome(ProductDTO product, BigDecimal amount, BigDecimal price, String company);

	public SummarizedOrderDTO getSalesHistory(Date time, Date to);
	
	public List<OrderDTO> findNotSentOrders();
	
	public List<ProductDTO> getInsufficientProducts();

	public List<OrderDTO> findNotStornoedOrders();

	public TableDTO getTableById(Long id);

	public void updateCategory(CategoryDTO category);

	public void deleteCategory(CategoryDTO cat);

	public void deleteOrderItem(OrderItemDTO o);

	public void deleteOrderItems(OrderDTO orderDecreasedDTO);
	
	/*
	public void saveOrUpdateConfig(ConfigEntity e);

	public ConfigEntity getConfig(String id);

	public void loadProperties();
	
	public String getProperty(String id) ;
	*/

}
