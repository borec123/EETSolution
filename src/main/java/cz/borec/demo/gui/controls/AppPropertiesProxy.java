package cz.borec.demo.gui.controls;

import cz.borec.demo.AppProperties;
import cz.borec.demo.Constants;
import cz.borec.demo.CustomProperties;
import cz.borec.demo.service.ServiceInterface;

public class AppPropertiesProxy {

	private static Integer displaySize = null;
	private static String[] rmiListeners = null;
	ServiceInterface serviceInterface;

	public AppPropertiesProxy(ServiceInterface i) {
		super();
		serviceInterface = i;
	}

	public static String get(String id) {
		Object ret = CustomProperties.getProperties().get(id);
		if (ret != null) {
			return ret.toString();
		} else {
		
			return AppProperties.getProperties().get(id) != null ? 
					AppProperties.getProperties().get(id).toString()
					: null;
		}
	}
	
	public static String[] getRmiListeners() {
		if (rmiListeners == null) {
			String s = (String) get("rmi.listeners");
			rmiListeners = s.split(",");
		}

		return rmiListeners;
	}



	public static int getDisplaySize() {
		if(displaySize  == null) {
			displaySize = Integer.parseInt(get(Constants.CONFIG_DISPLAY_SIZE));
		}
		return displaySize;
	}


}
