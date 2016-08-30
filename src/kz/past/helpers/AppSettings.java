package kz.past.helpers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AppSettings {

	private  AppSettings() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Return keys of settings of application
	 * @param key
	 * @return String
	 */
	static public String getKey(String key){
		InputStream is = null;
		Properties prop = new Properties();
		
		try {
			is = new FileInputStream("config.properties");
			prop.load(is);
			
			
		} catch (Exception e) {
			System.out.println("Err: AppSettings : getKey : "+e);
		}
		
		return prop.getProperty(key);
	}
	
	static public String getKeyDB(String key){
		InputStream is = null;
		Properties prop = new Properties();
		
		try {
			is = new FileInputStream("configDB.properties");
			prop.load(is);
			
			
		} catch (Exception e) {
			System.out.println("Err: AppSettings : getKeyDB : "+e);
		}
		
		return prop.getProperty(key);
	}
	
	static void saveKey(String key, String val){
		
		System.out.println("Doesn't work yet");
	}

}
