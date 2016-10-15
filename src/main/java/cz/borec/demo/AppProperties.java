package cz.borec.demo;

import java.io.IOException;
import java.util.Properties;

public class AppProperties extends Properties {
	
	private static final long serialVersionUID = 1993452751869853641L;
	private static AppProperties instance;
	private Boolean superUser;
	private Integer lookId;


	protected AppProperties() {
		super();
		loadPropertiesFromFile();
	}
	
	protected void loadPropertiesFromFile() {
		try {
			load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static AppProperties getProperties() {
		if(instance == null) {
			instance = new AppProperties();
		}
		return instance;
	}
	
	public String getCustomerName() {
		return (String) get("customer.name");
	}

	public String getCustomerStreet() {
		return (String) get("customer.street");
	}

	public String getCustomerAddress() {
		return (String) get("customer.address");
	}

	public String getCustomerICO() {
		return (String) get(Constants.CONFIG_CUSTOMER_ICO);
	}

	public int getRestaurantId() {
		return Integer.parseInt( (String) get("restaurant.id"));
	}

	public int getLookId() {
		if(lookId == null) {
			lookId = Integer.parseInt( (String) get("look"));
		}
		return lookId;
	}

	public String getStyle() {
		int id = getLookId();
		switch (id) {
		case 2:
			return "css/style2.css";

		case 3:
			return "css/style3.css";

		default:
			return "css/style.css";
		}
	}

	public String getRestaurantIdString() {
		return  (String) get("restaurant.id");
	}

	public String getCashId() {
		return (String) get("cash.id");
	}

	public boolean isPrintLogo() {
		
		return Boolean.parseBoolean((String) get("printlogo"));
	}

	public boolean isMultiNoded() {
		
		return Boolean.parseBoolean((String) get("application.isMultiNoded"));
	}

	public boolean isServer() {
		
		return Boolean.parseBoolean((String) get("node.isServer"));
	}

	public boolean print() {
		
		return Boolean.parseBoolean((String) get("print"));
	}

	public boolean isSuperUser() {
		if(superUser == null) {
			String s = (String) get("adminusers");
			if(s == null) {
				superUser = Boolean.TRUE;
				return superUser;
			}
			superUser = new Boolean(s.contains(System.getProperty("user.name")));
		}
		return superUser;
	}

	public String getRestaurantName() {
		return (String) get(Constants.CONFIG_RESTAURANT_NAME);
	}

	public PrinterWidth getPrinterWidth() {
		return PrinterWidth.valueOf((String) get(Constants.CONFIG_PRINTING_WIDTH));
	}

}
