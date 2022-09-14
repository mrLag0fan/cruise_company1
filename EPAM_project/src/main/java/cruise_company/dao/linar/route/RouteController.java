package cruise_company.dao.linar.route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.linar.route.Route;

public class RouteController extends AbstractController<Route>{

	public List<Route> getAllWithLimit(int currentPage, int recordsPerPage) throws DAOException {
		List<Route> res = new ArrayList<Route>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(RouteDAOConstants.SELECT_ALL_ROUTES_WITH_LIMIT.getSql());){  
			st.setInt(1, start);
			st.setInt(2, recordsPerPage);
			try(ResultSet rs = st.executeQuery()){;
			  while(rs.next()) {
	            	res.add(mapRoute(rs));
	            }
			}
        } catch (SQLException ex) {
        	String msg = "Error in taking all ports";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}
	
	
	@Override
	public List<Route> getAll() throws DAOException {
		List<Route> res = new ArrayList<Route>();
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(RouteDAOConstants.SELECT_ALL_ROUTES.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapRoute(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all routes";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public Route getEntityByID(int id) throws DAOException {
		Route route = null;
		try (Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(RouteDAOConstants.SELECT_ROUTE_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		route = mapRoute(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking route with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return route;
	}

	@Override
	public boolean update(Route entity) {
		return false;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(RouteDAOConstants.DELETE_ROUTE.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("Route deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting route with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	@Override
	public int create(Route entity) throws DAOException {
		int id = -1;
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(RouteDAOConstants.INSERT_ROUTE.getSql(), Statement.RETURN_GENERATED_KEYS);){
			st.setInt(1, entity.getFrom());
			st.setInt(2, entity.getTo());
			int count = st.executeUpdate();
			if (count > 0) {
				try(ResultSet rs = st.getGeneratedKeys();){
					if(rs.next()) {
						id = rs.getInt(1);
						entity.setId(id);
					}
				}
			}
			this.notifyObservers("Route created with id " + entity.getId());
	    }catch (SQLException | NullPointerException ex) {
	    	String msg = "Error in createing route with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return id;
	}
	
	/**
	 * @param rs
	 * @throws SQLException
	 */
	private Route mapRoute(ResultSet rs) throws SQLException {
		Route route = new Route();
		route.setId(rs.getInt("id"));
		route.setFrom(rs.getInt("from"));
		route.setTo(rs.getInt("to"));
		return route;
	}

}
