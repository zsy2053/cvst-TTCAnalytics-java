

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	private static final String driverName = "com.mysql.jdbc.Driver";
	private static final String url="jdbc:mysql://142.150.77.153:3306/CVST_RAW_DATA?user=cvst_admin&password=Cvst@2015";
	public static Connection getConn(){
		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}

