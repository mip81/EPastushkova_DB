package kz.past.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;




public class Conn {
	private static String DB= AppSettings.getKeyDB("DB");
	private static String USERDB=AppSettings.getKeyDB("USERDB");;
	private static String PASSWORD=AppSettings.getKeyDB("PASSWORD");;
	private static String HOST=AppSettings.getKeyDB("HOST");;
	
//	public static final String DB="v_2995_EP";
//	public static final String USERDB="v_2995_ep";
//	public static final String PASSWORD="16062006@Ep";
//	public static final String HOST="jenny.kz";
	
	public static Connection getConnection(){
		Connection connection = null;
		 
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection =  DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+DB+"?user="+USERDB+"&password="+PASSWORD+"&useUnicode=true&characterEncoding=utf8");
			System.out.println(" Connection string: jdbc:mysql://"+HOST+"/"+DB+"?user="+USERDB+"&password="+PASSWORD);
		} catch (Exception e) {
			System.out.println("Error : Conn : getConnection() : "+e);
		}
		return connection;	
	}
}
