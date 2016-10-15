package cz.borec.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomProperties extends AppProperties {

	private static final long serialVersionUID = 1L;
	private static final String FILE_NAME = "custom.properties";
	private static CustomProperties instance;

	private CustomProperties() {
		super();
	}
	
	protected void loadPropertiesFromFile() {
		try {
			FileInputStream i = new FileInputStream(FILE_NAME);
			load(i);
			i.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reloadProperties() {
		loadPropertiesFromFile();
	}

	public void storePropertiesToFile() throws FileNotFoundException, IOException {
		FileOutputStream o = new FileOutputStream(FILE_NAME);
		store(o, null);
		o.close();
	}

	public static CustomProperties getProperties() {
		if(instance == null) {
			instance = new CustomProperties();
		}
		return instance;
	}
	

}
