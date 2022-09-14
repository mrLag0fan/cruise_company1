package cruise_company.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import cruise_company.observer.LoggerObserver;
import cruise_company.observer.Observable;


public abstract class AbstractController<E> implements Observable{
	
	private ConnectionPool connectionPool;
	
	public AbstractController() { 
        connectionPool = ConnectionPool.getConnectionPool();
        addObserver(new LoggerObserver<E>(this));
    }
	
	public abstract List<E> getAll() throws DAOException ;
	public abstract E getEntityByID(int id) throws DAOException ;
	public abstract boolean update(E entity) throws DAOException ;
	public abstract boolean delete(int id) throws DAOException ;
	public abstract int create(E entity) throws DAOException ;
	
	public void close(AutoCloseable ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	public Connection getConnection() {
		try {
			return connectionPool.getConnection();
		}catch(NamingException e) {
			try {
				return DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/cruise_company","root","159357");
			} catch (SQLException e1) {
				e1.printStackTrace();
				return null;
			}  
		}
		
	}
	
	public int getNumberOfRows(String sql) throws DAOException {
		int numOfRows = 0;
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();){  
            if(rs.next()) {
            	numOfRows = rs.getInt("total");
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking number of liners";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return numOfRows;
	}
	
	
}
