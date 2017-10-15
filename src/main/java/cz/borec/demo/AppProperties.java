package cz.borec.demo;

import java.io.IOException;
import java.util.Properties;

import cz.borec.demo.gui.controls.AppPropertiesProxy;

/**
 * 
 * @Deprecated Use AppPropertiesProxy instead.
 *
 */
@Deprecated
public class AppProperties extends Properties {

	private static final long serialVersionUID = 1993452751869853641L;
	private static AppProperties instance;
	private Boolean superUser;
	private Integer lookId;
	private String[] rmiListeners = null;

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
		if (instance == null) {
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
		return Integer.parseInt((String) get("restaurant.id"));
	}

	public int getLookId() {
		if (lookId == null) {
			lookId = Integer.parseInt((String) AppPropertiesProxy.get("look"));
		}
		return lookId;
	}

	public String getStyle() {
		int id = getLookId();
		switch (id) {
		case 2:
			return addSize("css/style2");

		case 3:
			return addSize("css/style3");

		default:
			return addSize("css/style");
		}
	}

	private String addSize(String string) {
		int size = AppPropertiesProxy.getDisplaySize();
		switch (size) {
		case 2:
			return string + "_size2.css";

		case 3:
			return string + "_size3.css";

		default:
			return string + ".css";
		}
	}

	public String getRestaurantIdString() {
		return (String) get("restaurant.id");
	}

	public String getCashId() {
		return (String) get("cash.id");
	}

	public boolean isPrintLogo() {

		return Boolean.parseBoolean((String) get("printlogo"));
	}

	public boolean isMultiNoded() {

		return Boolean.parseBoolean((String) get(Constants.CONFIG_IS_MULTINODED));
	}

	public boolean isServer() {

		return Boolean.parseBoolean((String) get(Constants.CONFIG_IS_SERVER));
	}

	public boolean print() {

		return Boolean.parseBoolean((String) get("print"));
	}

	public boolean isSuperUser() {
		if (superUser == null) {
			String s = (String) get("adminusers");
			if (s == null) {
				superUser = Boolean.TRUE;
				return superUser;
			}
			superUser = new Boolean(s.contains(System.getProperty("user.name")));
		}
		return superUser;
	}

	public String[] getRmiListeners() {
		if (rmiListeners == null) {
			String s = (String) get("rmi.listeners");
			rmiListeners = s.split(",");
		}

		return rmiListeners;
	}

	public String getRestaurantName() {
		return (String) get(Constants.CONFIG_RESTAURANT_NAME);
	}

	public PrinterWidth getPrinterWidth() {
		return PrinterWidth.valueOf((String) get(Constants.CONFIG_PRINTING_WIDTH));
	}

}
