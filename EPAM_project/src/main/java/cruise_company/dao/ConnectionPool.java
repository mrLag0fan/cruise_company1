package cruise_company.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
	
	private static ConnectionPool instance = null;
	
	private ConnectionPool() {}
	
	public static ConnectionPool getConnectionPool() {
		if(instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	public Connection getConnection() throws NamingException{
        Connection conn = null;
        Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/TestDB");
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return conn;
    }

}
