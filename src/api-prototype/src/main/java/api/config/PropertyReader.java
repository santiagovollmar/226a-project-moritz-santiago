package api.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class loads content of a given property file and parses its string and
 * int values
 *
 * @author Santiago Gabriel Vollmar
 */
public class PropertyReader {
	
	private static final HashMap<String, Properties> readFileProperties = new HashMap<String, Properties>();
	
	private static Properties getCachedProperties(String fileURL) {
		synchronized(readFileProperties) {
			return readFileProperties.get(fileURL);
		}
	}
	
	private static void cacheProperties(String fileURL, Properties properties) {
		synchronized(readFileProperties) {
			readFileProperties.put(fileURL, properties);
		}
	} 
	
	private Properties properties;

	/**
	 * @param fileURL
	 */
	public PropertyReader(String fileURL) throws IOException {
		properties = getCachedProperties(fileURL);
		
		if (properties == null) {
			properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream(fileURL));
			cacheProperties(fileURL, properties);
		}
	}

	/**
	 * This method gets the requested property as a string and parses it into an
	 * integer
	 *
	 * @param propName Requested property
	 *
	 * @return Requested property as an integer
	 */
	public Integer getIntProperty(String propName) {
		String propVal = properties.getProperty(propName);
		if (propVal != null) {
			Integer intPropVal = Integer.parseInt(propVal);
			return intPropVal;
		}
		return null; 
	}

	/**
	 * This method gets the requested property as a string
	 *
	 * @param propName Requested property
	 *
	 * @return Requested property as a string
	 */
	public String getStringProperty(String propName) {
		String propVal = properties.getProperty(propName);
		return propVal;
	}

}