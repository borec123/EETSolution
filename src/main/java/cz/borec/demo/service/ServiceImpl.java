package cz.borec.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.ProductDTO;
import cz.borec.demo.core.dto.RoomDTO;
import cz.borec.demo.core.dto.SummarizedOrderDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.core.entity.CategoryEntity;
import cz.borec.demo.core.entity.OrderEntity;
import cz.borec.demo.core.entity.OrderItemEntity;
import cz.borec.demo.core.entity.OrderState;
import cz.borec.demo.core.entity.ProductEntity;
import cz.borec.demo.core.entity.ProductRelationEntity;
import cz.borec.demo.core.entity.RoomEntity;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.core.entity.StoreIncomeEntity;
import cz.borec.demo.core.entity.TableEntity;
import cz.borec.demo.core.entity.UnitEntity;
import cz.borec.demo.repository.CategoryRepository;
import cz.borec.demo.repository.OrderItemRepository;
import cz.borec.demo.repository.OrderRepository;
import cz.borec.demo.repository.ProductRelationRepository;
import cz.borec.demo.repository.ProductRepository;
import cz.borec.demo.repository.RoomRepository;
import cz.borec.demo.repository.SalesProductRepository;
import cz.borec.demo.repository.StoreIncomeRepository;
import cz.borec.demo.repository.TableRepository;
import cz.borec.demo.repository.UnitRepository;
import cz.borec.demo.service.convert.CategoryConvertor;
import cz.borec.demo.service.convert.OrderConvertor;
import cz.borec.demo.service.convert.OrderItemConvertor;
import cz.borec.demo.service.convert.ProductConvertor;
import cz.borec.demo.service.convert.RoomConvertor;
import cz.borec.demo.service.convert.TableConvertor;

//@Component
@Service
@Transactional
public class ServiceImpl implements ServiceInterface/*, InitializingBean*/ {

	@Autowired
	private CategoryRepository categoryRepository;

/*	@Autowired
	private ConfigRepository configRepository;
*/
	@Autowired
	private SalesProductRepository salesProductRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private ProductRelationRepository productRelationRepository;

	private String piczus;

	private List<RoomDTO> rooms;

	public String getPiczus() {
		return piczus;
	}

	@Override
	public List<RoomDTO> getAllRooms() {
		// if (rooms == null) {
		reloadRooms();
		// }
		return rooms;
	}

	/*
	 * public static <T, U> List<U> convertList(List<T> from, Function<T, U>
	 * func){ return from.stream().map(func).collect(Collectors.toList()); }
	 */
	private void reloadRooms() {
		// rooms = new ArrayList<RoomDTO>();
		/*
		 * RoomDTO r = new RoomDTO(); r.setName("Venku");
		 */

		List<RoomEntity> roomDTOs = roomRepository.findAll();

		// rooms = convertList(roomRepository.findAll(), a ->
		// roomConvertor.convertToDto(a));

		rooms = new ArrayList<RoomDTO>();
		for (RoomEntity roomEntity : roomDTOs) {
			RoomDTO dto = roomConvertor.convertToDto(roomEntity);
			rooms.add(dto);
			List<TableEntity> tables = tableRepository.getTablesByRoomId(dto
					.getId());
			for (TableEntity tableEntity : tables) {
				TableDTO dto2 = tableConvertor.convertToDto(tableEntity);
				// dto2.setOrderDTO(orderConvertor.convertToDto(orderRepository.findById(tableEntity.)));
				dto.getTables().add(dto2);
			}
		}

		/*
		 * RoomDTO r = rooms.get(0); //rooms.add(r); TableDTO t = new
		 * TableDTO(650, 50, 100, 50); t.setName("V1"); t.setRoomDTO(r); t = new
		 * TableDTO(650, 140, 100, 50); t.setName("V2"); t.setRoomDTO(r); t =
		 * new TableDTO(400, 140, 150, 50); t.setName("V3"); t.setRoomDTO(r); t
		 * = new TableDTO(400, 230, 150, 50); t.setName("V4"); t.setRoomDTO(r);
		 * t = new TableDTO(200, 230, 100, 50); t.setName("V6");
		 * t.setRoomDTO(r); t = new TableDTO(400, 320, 150, 50);
		 * t.setName("V5"); t.setRoomDTO(r); t = new TableDTO(200, 320, 100,
		 * 50); t.setName("V7"); t.setRoomDTO(r);
		 * 
		 * r = rooms.get(1);
		 * 
		 * //r.setName("Hospoda"); //rooms.add(r); t = new TableDTO(50, 250,
		 * 150, 10); t.setName("BAR"); t.setRoomDTO(r); t = new TableDTO(100,
		 * 50, 100, 60); t.setName("0"); t.setRoomDTO(r); t = new TableDTO(400,
		 * 50, 60, 100); t.setName("1"); t.setRoomDTO(r); t = new TableDTO(550,
		 * 50, 60, 100); t.setName("2"); t.setRoomDTO(r); t = new TableDTO(700,
		 * 50, 60, 100); t.setName("3"); t.setRoomDTO(r); t = new TableDTO(400,
		 * 250, 60, 100); t.setName("4"); t.setRoomDTO(r); t = new TableDTO(550,
		 * 250, 60, 100); t.setName("5"); t.setRoomDTO(r); t = new TableDTO(700,
		 * 250, 60, 100); t.setName("6"); t.setRoomDTO(r);
		 */
	}

	/*
	 * @Override public List<CategoryDTO> getAllCategories() {
	 * ArrayList<CategoryDTO> list = new ArrayList<CategoryDTO>() ; CategoryDTO
	 * c = new CategoryDTO(); c.setName("Pivo"); list.add(c); c = new
	 * CategoryDTO(); c.setName("Nealko"); list.add(c); c = new CategoryDTO();
	 * c.setName("Alkoholick\u00E9 n\u00E1poje"); list.add(c); c = new
	 * CategoryDTO(); c.setName("V\u00EDno"); list.add(c); c = new
	 * CategoryDTO(); c.setName("K\u00E1va a \u010Daj"); list.add(c); return
	 * list; }
	 * 
	 * @Override public List<ProductDTO> getProductsByCategoryId(Long
	 * idCategory) { ArrayList<ProductDTO> list = new ArrayList<ProductDTO>() ;
	 * ProductDTO c = new ProductDTO(); c.setId(1L);
	 * c.setName("Radegast 12\u00B0"); c.setPrice(new BigDecimal(27));
	 * list.add(c); c = new ProductDTO(); c.setId(2L);
	 * c.setName("Pillsner Urquel 12\u00B0"); c.setPrice(new BigDecimal(35));
	 * list.add(c); list.add(c); list.add(c); list.add(c); list.add(c);
	 * list.add(c); list.add(c); list.add(c); list.add(c); list.add(c);
	 * list.add(c); list.add(c); list.add(c); list.add(c); list.add(c);
	 * list.add(c); list.add(c); list.add(c); list.add(c); list.add(c);
	 * list.add(c); list.add(c); list.add(c); list.add(c); list.add(c);
	 * list.add(c); list.add(c); list.add(c); list.add(c); list.add(c);
	 * list.add(c); list.add(c); list.add(c); list.add(c); list.add(c);
	 * list.add(c); list.add(c); list.add(c); list.add(c); list.add(c); return
	 * list; }
	 * 
	 * @Override public long createCategory(CategoryDTO categoryDTO) {
	 * CategoryEntity ent = new CategoryEntity();
	 * ent.setId(categoryDTO.getId()); ent.setName(categoryDTO.getName());
	 * categoryRepository.save(ent); categoryRepository.flush(); return
	 * ent.getId(); }
	 */

	/*
	 * public static <T, U> List<U> convertList(List<T> from, Function<T, U>
	 * func){ return from.stream().map(func).collect(Collectors.toList()); }
	 */
	public static void main(String[] args) {

		/*
		 * List<RoomEntity> rooms = new ArrayList<RoomEntity>(); RoomEntity r =
		 * new RoomEntity(); r.setName("borec"); rooms.add(r );
		 * 
		 * RoomConvertor roomConvertor2 = new RoomConvertor();
		 * 
		 * List<Object> roomsDTOs = convertList(rooms, a ->
		 * roomConvertor2.convertToDto(a));
		 * 
		 * roomsDTOs.forEach(a -> System.out.println(a.getClass().getName()));
		 */

		/*
		 * ApplicationContext applicationContext = new
		 * ClassPathXmlApplicationContext( "spring/application.xml");
		 * 
		 * ServiceInterface service = (ServiceInterface) applicationContext
		 * .getBean("ServiceImpl");
		 * 
		 * List<RoomDTO> roomss = service.getAllRooms(); for (RoomDTO roomDTO :
		 * roomss) { for (TableDTO t : roomDTO.getTables()) {
		 * System.out.println(t.getName()); } }
		 */

		/*
		 * ProductDTO p = new ProductDTO(); p.setCategory(c);
		 * p.setName("Radegast 12\u00B0"); p.setPrice(new BigDecimal(27));
		 * service.createProduct(p);
		 */
		// Main.main(args);
	}

	public void setPiczus(String piczus) {
		this.piczus = piczus;
	}

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	private ProductConvertor productConvertor = new ProductConvertor();

	private OrderConvertor orderConvertor = new OrderConvertor();

	private RoomConvertor roomConvertor = new RoomConvertor();

	private TableConvertor tableConvertor = new TableConvertor();

	private List<CategoryDTO> categories;

	private Map<Long, List<ProductDTO>> products;

	private List<UnitEntity> units;

	@Autowired
	private StoreIncomeRepository storeIncomeRepository;

	private CategoryConvertor categoryConvertor = new CategoryConvertor();

	private HashMap<String, String> properties;

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getAllProducts() {
		List<ProductEntity> entities = productRepository.findAll();

		return productConvertor.convertListToDto(entities);

	}

	@Override
	@Transactional
	public long createProduct(ProductDTO productDTO) {

		// LogMF.debug(LOG, "Trying to create product {0}", new
		// Object[]{productDTO.getClass(), productDTO.getName()}); // create*
		// metody by m�ly m�t debug/trace logovani na za��tku (co
		// ukl�d�m) a
		// info na konci

		ProductEntity entity = productConvertor.convertToEntity(productDTO);
		productRepository.save(entity);
		productRepository.flush();

		// LogMF.info(LOG, "New product created: {0}", entity.getName()); //
		// create* metody by m�ly m�t debug/trace logovani na za��tku
		// (co
		// ukl�d�m) a info na konci

		productDTO.setId(entity.getId());

		reloadProductsOFCategory(productDTO.getCategory());

		return entity.getId();
	}

	@Override
	// @Transactional(readOnly = true)
	public List<CategoryDTO> getAllCategories(boolean reloadFromDatabase) {
		if (categories == null || reloadFromDatabase) {
			loadCategories();
			loadProducts();
		}
		return categories;
	}

	private void loadProducts() {
		products = new LinkedHashMap<Long, List<ProductDTO>>();
		for (CategoryDTO categoryDTO : categories) {
			for (CategoryDTO childCategoryDTO2 : categoryDTO
					.getChildCategories()) {

				reloadProductsOFCategory(childCategoryDTO2);
			}
		}

	}

	private void reloadProductsOFCategory(CategoryDTO categoryDTO) {
		products.put(categoryDTO.getId(),
				loadProductsByCategoryId(categoryDTO.getId()));
	}

	public List<CategoryDTO> loadCategories() {

		List<CategoryEntity> entities = categoryRepository
				.findTopLevelCategories();
		categories = new ArrayList<CategoryDTO>(entities.size());

		for (CategoryEntity entity : entities) {
			CategoryDTO dto = convertToDto(entity);
			dto.setRoot(true);
			categories.add(dto);
		}

		return categories;
	}

	private CategoryDTO convertToDto(CategoryEntity entity) {
		CategoryDTO categoryDTO = new CategoryDTO();

		categoryDTO.setId(entity.getId());
		categoryDTO.setName(entity.getName());
		categoryDTO.setVat(entity.getVat());

		for (CategoryEntity category : entity.getChildCategories()) {
			categoryDTO.getChildCategories().add(convertToDto(category));
		}

		return categoryDTO;
	}

	// TODO: remove, set manually.
	@Override
	public long createCategory(CategoryDTO categoryDTO) {
		CategoryEntity ent = new CategoryEntity();
		ent = categoryConvertor.convertToEntity(categoryDTO);
		/*
		 * ent.setId(categoryDTO.getId()); ent.setName(categoryDTO.getName());
		 */

		categoryRepository.save(ent);
		categoryRepository.flush();

		return ent.getId();
	}

	public long createRoom(RoomDTO dto) {
		RoomEntity ent = roomConvertor.convertToEntity(dto);
		roomRepository.save(ent);
		roomRepository.flush();
		return ent.getId();
	}

	@Transactional
	public long createTable(TableDTO dto) {
		TableEntity ent = tableConvertor.convertToEntity(dto);
		tableRepository.save(ent);
		tableRepository.flush();
		return ent.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderDTO> getAllOrders() {
		List<OrderEntity> orders = orderRepository.findAll();
		return orderConvertor.convertListToDto(orders);
	}

	@Override
	@Transactional
	public long createOrder(OrderDTO orderDTO) {
		// LogMF.debug(LOG, "Trying to create order {0}",
		// orderDTO.getFullName()); // create* metody by m�ly m�t
		// debug/trace
		// logovani na za��tku (co ukl�d�m) a info na konci

		OrderEntity entity = orderConvertor.convertToEntity(orderDTO);

		// --- begin transaction
		orderRepository.save(entity);
		orderRepository.flush();

		createOrderItems(entity, orderDTO);

		// --- end

		// LogMF.info(LOG, "New order created: {0}", entity.getFullName()); //
		// create* metody by m�ly m�t debug/trace logovani na za��tku
		// (co
		// ukl�d�m) a info na konci

		orderDTO.setId(entity.getId());
		return entity.getId();
	}

	private void createOrderItems(OrderEntity entity, OrderDTO orderDTO) {
		OrderItemConvertor conv = new OrderItemConvertor(entity);
		for (OrderItemDTO dto : orderDTO.getItems()) {
			OrderItemEntity e = conv.convertToEntity(dto);
			orderItemRepository.saveAndFlush(e);
			dto.setId(e.getId());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public ProductDTO getProductById(Long id) {

		return productConvertor.convertToDto(productRepository.findById(id));
	}

	@Override
	@Transactional
	public void updateProduct(ProductDTO dto) {

		// LogMF.debug(LOG, "Trying to update product {0}", dto.getName()); //
		// create* metody by m�ly m�t debug/trace logovani na za��tku
		// (co
		// ukl�d�m) a info na konci

		ProductEntity e = productRepository.findById(dto.getId());
		e = productConvertor.convertToEntity(dto);
		productRepository.save(e);
		productRepository.flush();

		// LogMF.info(LOG, "Product updated: {0}", e.getName()); // create*
		// metody by m�ly m�t debug/trace logovani na za��tku (co
		// ukl�d�m) a
		// info na konci
		reloadProductsOFCategory(dto.getCategory());
	}

	@Override
	public void deleteProductById(Long id) {
		ProductEntity e = productRepository.findById(id);

		// LogMF.debug(LOG, "Trying to update product {0}", e.getName()); //
		// create* metody by m�ly m�t debug/trace logovani na za��tku
		// (co
		// ukl�d�m) a info na konci

		productRepository.delete(id);

		// LogMF.info(LOG, "Product updated: {0}", e.getName()); // create*
		// metody by m�ly m�t debug/trace logovani na za��tku (co
		// ukl�d�m) a
		// info na konci
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getEmptyListOfProducts() {

		return Collections.emptyList();
	}

	@Override
	public List<ProductDTO> getProductsByCategoryId(Long idCategory) {

		return products.get(idCategory);
	}

	@Transactional(readOnly = true)
	private List<ProductDTO> loadProductsByCategoryId(Long idCategory) {

		return productConvertor.convertListToDto(productRepository
				.getProductsByCategoryId(idCategory));
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public CategoryDTO getCategoryById(Long
	 * id) { List<CategoryDTO> list = getAllCategories(false); for (CategoryDTO
	 * categoryDTO : list) { if (categoryDTO.getId() == id) { return
	 * categoryDTO; } } return null; }
	 */
	@Override
	@Transactional(readOnly = true)
	public OrderDTO getOrderById(Long id) {
		OrderEntity entity = orderRepository.findById(id);
		return orderConvertor.convertToDto(entity);
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public List<OrderDTO>
	 * getOrdersByEmail(String emailAddress) { List<OrderEntity> orders =
	 * orderRepository .findOrdersByEmail(emailAddress); return
	 * orderConvertor.convertListToDto(orders);
	 * 
	 * }
	 */

	@Override
	public void updateOrder(OrderDTO orderDTO) {
		checkOrderDate(orderDTO);
		updateOrderInternal(orderDTO);
	}

	private boolean checkOrderDate(OrderDTO orderDTO) {
		if (orderDTO.getDate() != null) {
			System.out.println("Editing already completed order !!!!!!!!");
			orderDTO.setDate(null);
			return false;
			// throw new ApplicationException("smazat");
		}
		return true;
	}

	@Transactional
	public void updateOrderInternal(OrderDTO orderDTO) {
		OrderEntity e = orderRepository.findById(orderDTO.getId());
		e = orderConvertor.convertToEntity(orderDTO);
		orderRepository.save(e);
		orderRepository.flush();
	}

	@Override
	@Transactional
	public void saveOrderItems(OrderDTO orderDTO) {
		if (!checkOrderDate(orderDTO)) {
			updateOrderInternal(orderDTO);
		}
		OrderEntity orderEntity = null;
		OrderItemConvertor orderItemConvertor = null;

		Collection<OrderItemDTO> items = orderDTO.getItems();
		for (OrderItemDTO item : items) {

			if (item.getId() == null) {
				// --- lazy initialize:
				if (orderEntity == null) {
					orderEntity = orderRepository.findById(orderDTO.getId());
					orderItemConvertor = new OrderItemConvertor(orderEntity);
				}
				OrderItemEntity e = orderItemConvertor.convertToEntity(item);
				orderItemRepository.saveAndFlush(e);
				item.setId(e.getId());
			} else {
				saveOrderItem(item, false);
			}
		}

	}

	@Override
	@Transactional
	public void saveTable(TableDTO tableDTO) {
		TableEntity en = tableRepository.findById(tableDTO.getId());
		en = tableConvertor.convertToEntity(tableDTO);
		tableRepository.save(en);
		tableRepository.flush();
	}

	@Override
	@Transactional
	public void saveOrderItem(OrderItemDTO orderItemDTO, boolean checkOrderDate) {
		if (checkOrderDate) {
			if (!checkOrderDate(orderItemDTO.getOrder())) {
				updateOrderInternal(orderItemDTO.getOrder());
			}
		}
		OrderItemEntity en = orderItemRepository.findById(orderItemDTO.getId());
		en.setAmount(orderItemDTO.getAmount());
		en.setVatValue(orderItemDTO.getVatValue());
		orderItemRepository.save(en);
		orderItemRepository.flush();
	}

	@Override
	@Transactional
	public void completeOrder(OrderDTO orderDTO) {
		if (orderDTO.getDate() == null) {
			throw new ApplicationException(
					"Cannot complete order if date is null.");
		}
		updateOrderInternal(orderDTO);

		// --- store product amount actualisation:
		Collection<OrderItemDTO> itemS = orderDTO.getItems();
		for (OrderItemDTO orderItemDTO : itemS) {
			List<ProductRelationEntity> storeProducts = orderItemDTO
					.getProduct().getStoreProducts();
			for (ProductRelationEntity productRelationEntity : storeProducts) {
				BigDecimal amount = productRelationEntity.getAmount();
				amount = amount.multiply(BigDecimal.valueOf(orderItemDTO
						.getAmount()));
				ProductEntity p = productRelationEntity.getStoreProduct();
				p.setAmount(p.getAmount().subtract(amount));
				updateProduct(p);
			}
		}
	}

	private void updateProduct(ProductEntity p) {
		ProductEntity e = productRepository.findById(p.getId());
		productRepository.save(p);
		productRepository.flush();
		CategoryDTO dto = new CategoryDTO();
		dto.setId(p.getCategory().getId());
		dto.setName(p.getCategory().getName());
		reloadProductsOFCategory(dto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderDTO> getOrderHistoryOfTable(TableDTO dto, OrderState mode) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -Constants.HISTORY);

		List<OrderEntity> orders = null;
		if(mode == null) {
			orders = orderRepository.findOrderHistory (
				 cal.getTime());
		}
		else {
			orders = orderRepository.findOrderHistory (
					 cal.getTime(), mode);
		}
		return orderConvertor.convertListToDto(orders);

	}

	@Override
	public List<UnitEntity> getAllUnits() {
		if (units == null) {
			units = unitRepository.findAll();
		}
		return units;
	}

	@Override
	public List<SalesProductEntity> getSalesProductsByCategoryId(Long id,
			boolean onlyOffer) {

		// TODO cache !!!
		return onlyOffer ? salesProductRepository
				.getProductsByCategoryOnlyOfferId(id) : salesProductRepository
				.getProductsByCategoryId(id);

	}

	@Override
	@Transactional
	public long createSalesProduct(SalesProductEntity product) {
		updateSalesProductInternal(product);
		return product.getId();
	}

	private void updateSalesProductInternal(SalesProductEntity product) {
		salesProductRepository.save(product);
		salesProductRepository.flush();
		productRelationRepository.deleteRelationSalesProductId(product.getId());
		for (ProductRelationEntity productRelationEntity : product
				.getStoreProducts()) {
			productRelationEntity.setId(null);
			productRelationRepository.save(productRelationEntity);
			productRelationRepository.flush();
		}
	}

	@Override
	@Transactional
	public void updateSalesProduct(SalesProductEntity product) {
		SalesProductEntity e = salesProductRepository.findById(product.getId());
		updateSalesProductInternal(product);
	}

	@Override
	public void loadStoreProducts(SalesProductEntity o) {
		List<ProductRelationEntity> storeProducts = productRelationRepository
				.getStoreProducts(o.getId());
		o.setStoreProducts(storeProducts);
	}

	@Override
	public void performStoreIncome(ProductDTO product, BigDecimal amount,
			BigDecimal price, String company) {
		StoreIncomeEntity storeIncomeEntity = new StoreIncomeEntity();
		ProductEntity p = productConvertor.convertToEntity(product);
		storeIncomeEntity.setStoreProduct(p);
		storeIncomeEntity.setAmount(amount);
		storeIncomeEntity.setPrice(price);
		storeIncomeEntity.setSupplierCompany(company);
		storeIncomeRepository.saveAndFlush(storeIncomeEntity);

		// --- store product amount actualisation:

		p.setAmount(p.getAmount().add(amount));
		updateProduct(p);

	}

	@Override
	public SummarizedOrderDTO getSalesHistory(Date time, Date to) {
		List<Object> objects = orderItemRepository.getSalesHistory(time, to);
		SummarizedOrderDTO orderDTO = new SummarizedOrderDTO();
		orderDTO.setFullName(Constants.SALES_HISTORY);
		List<OrderItemDTO> items = new ArrayList<OrderItemDTO>();
		for (Object o : objects) {
			if (o instanceof Object[]) {
				Object[] objs = (Object[]) o;
				OrderItemDTO item = new OrderItemDTO();
				SalesProductEntity p = (SalesProductEntity) objs[0];
				item.setProduct(p );
				item.setAmount(((Long) objs[1]).intValue());
				item.setPrice((BigDecimal) objs[2]);
				item.setVatValue((BigDecimal) objs[3]);
				items.add(item);
			}
		}
		/*
		 * Collections.sort(items, new Comparator<OrderItemDTO>() {
		 * 
		 * @Override public int compare(OrderItemDTO o1, OrderItemDTO o2) {
		 * 
		 * return o1.getPrice().compareTo(o2.getPrice()); }
		 * 
		 * });
		 */
		orderDTO.setItems(items);
		return orderDTO;
	}

	@Override
	public List<OrderDTO> findNotSentOrders() {

		List<OrderDTO> ords = orderConvertor.convertListToDto(orderRepository
				.findNotSentOrders());
		/*
		 * for (OrderDTO orderDTO : ords) {
		 * orderDTO.setTable(tableRepository.findById(orderDTO.getTableId())); }
		 */

		return ords;
	}

	@Override
	public List<ProductDTO> getInsufficientProducts() {
		return productConvertor.convertListToDto(productRepository
				.getInsufficientProducts());
	}

	@Override
	public List<OrderDTO> findNotStornoedOrders() {
		List<OrderDTO> ords = orderConvertor.convertListToDto(orderRepository
				.findNotStornoedOrders());
		return ords;
	}

	@Override
	@Transactional(readOnly = true)
	public TableDTO getTableById(Long id) {
		TableEntity entity = tableRepository.findById(id);
		return tableConvertor.convertToDto(entity);
	}

	@Override
	public void updateCategory(CategoryDTO category) {
		CategoryEntity e = categoryRepository.findById(category.getId());
		e = categoryConvertor.convertToEntity(category);
		categoryRepository.save(e);
		categoryRepository.flush();
	}

	@Override
	public void deleteCategory(CategoryDTO cat) {
		//categoryRepository.delete(categoryConvertor.convertToEntity(cat));
		categoryRepository.delete(cat.getId());

	}

	@Override
	public void deleteOrderItem(OrderItemDTO o) {
		orderItemRepository.delete(o.getId());
		
	}

	@Override
	public void deleteOrderItems(OrderDTO orderDecreasedDTO) {
		List<OrderItemDTO> list = orderDecreasedDTO.getDeletedItems();
		for (OrderItemDTO orderItemDTO : list) {
			deleteOrderItem(orderItemDTO);
		}
		list.clear();
	}
	
	

	/*	@Override
	public void saveOrUpdateConfig(ConfigEntity e) {
		if (StringUtils.isEmpty(e.getId()))
			throw new IllegalArgumentException("Id is null.");


		configRepository.saveAndFlush(e);

	}

	@Override
	public ConfigEntity getConfig(String id) {
		if (StringUtils.isEmpty(id))
			throw new IllegalArgumentException("Id is null.");

		return configRepository.findById(id);
	}
	@Override
	public void loadProperties() {
		properties = new HashMap<String, String>();
		List<ConfigEntity> all = configRepository.findAll();
		for (ConfigEntity configEntity : all) {
			properties.put(configEntity.getId(), configEntity.getValue());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		loadProperties();
		
	}

	public String getProperty(String id) {
		return properties.get(id);
	}
*/

}