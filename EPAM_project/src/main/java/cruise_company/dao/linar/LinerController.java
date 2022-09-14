package cruise_company.dao.linar;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.dao.linar.route.RouteController;
import cruise_company.entity.linar.Liner;
import cruise_company.entity.linar.route.Route;

public class LinerController extends AbstractController<Liner>{

	
	
	/**
	 * @param rs
	 * @throws SQLException
	 */
	private Liner mapLiner(ResultSet rs) throws SQLException {
		Liner liner = new Liner();
		liner.setPassengerCapacity(rs.getInt("passenger_capacity"));
		liner.setVisitedPorts(rs.getInt("visited_ports"));
		liner.setDateStart(rs.getDate("date_start").toLocalDate());
		liner.setDateEnd(rs.getDate("date_end").toLocalDate());
		liner.setDurationInDays(rs.getInt("duration_in_days"));
		liner.setStart(rs.getInt("start"));
		liner.setEnd(rs.getInt("end"));
		liner.setId(rs.getInt("id"));
		liner.setPrice(rs.getDouble("price"));
		return liner;
	}

	public List<Liner> getAllWithLimit(int currentPage, int recordsPerPage) throws DAOException {
		List<Liner> res = new ArrayList<Liner>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LinerDAOConstants.SELECT_ALL_LINERS_WITH_LIMIT.getSql());){
			st.setInt(1, start);
			st.setInt(2, recordsPerPage);
			try(ResultSet rs = st.executeQuery()){;
			  while(rs.next()) {
	            	res.add(mapLiner(rs));
	            }
			}
        } catch (SQLException ex) {
        	String msg = "Error in taking all liners";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}
	
	
	@Override
	public Liner getEntityByID(int id) throws DAOException {
		Liner route = null;
		try (Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LinerDAOConstants.SELECT_LINER_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		route = mapLiner(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking liner with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return route;
	}

	@Override
	public boolean update(Liner entity) {
		/*try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LinerDAOConstants.UPDATE_LINER.getSql());){
			st.setDate(1, entity.getDateStart());
			st.setDate(2, entity.getDateEnd());
			st.setInt(3, entity.getDurationInWeeks());
			st.setInt(4, entity.getStart());
			st.setInt(5, entity.getEnd());
			st.setInt(6, entity.getId());
			st.executeUpdate();
			this.notifyObservers("Liner updated with id " + entity.getId());
		}catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}*/
		return false;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LinerDAOConstants.DELETE_LINER.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("Liner deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting liner with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	@Override
	public int create(Liner entity) throws DAOException {
		int id = -1;
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LinerDAOConstants.INSERT_LINER.getSql(), Statement.RETURN_GENERATED_KEYS);){
			st.setInt(1, entity.getPassengerCapacity());
			st.setDate(2,  Date.valueOf(entity.getDateStart()));
			st.setDate(3,  Date.valueOf(entity.getDateEnd()));
			st.setInt(4, entity.getDurationInDays());
			st.setDouble(5, entity.getPrice());
			st.setInt(6, entity.getVisitedPorts());
			st.setInt(7, entity.getStart());
			st.setInt(8, entity.getEnd());
			int count = st.executeUpdate();
			if (count > 0) {
				try(ResultSet rs = st.getGeneratedKeys();){
					if(rs.next()) {
						id = rs.getInt(1);
						 entity.setId(id);
					}
				}
			}
			this.notifyObservers("Liner created" + entity);
	    }catch (SQLException | NullPointerException ex) {
	    	String msg = "Error in creating liner with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return id;
	}
	
	public boolean setRouteToLiner(Liner liner, List<Route> routes) throws DAOException {
		PreparedStatement st = null;
		Connection conn = getConnection();
		try{
			conn.setAutoCommit(false); 
			System.out.println(routes.toString());
			st = getConnection().prepareStatement(LinerDAOConstants.INSERT_ROUTE_FOR_LINER.getSql());
			for(Route route: routes) {
				st.setInt(1, liner.getId());
				st.setInt(2, route.getId());
				st.executeUpdate();  
			}
			conn.commit();
			for(Route route: routes) {
				this.notifyObservers("Route seted " + route + " for liner " + liner);
			}
		} catch (SQLException | NullPointerException e) {
			try {
				conn.rollback();
				String msg = "Error in seting routes" + routes + "for liner" + liner;
	        	this.notifyObservers(msg);
	        	e.printStackTrace();
	            throw new DAOException(msg, e);
			} catch (SQLException e1) {
				e1.printStackTrace();
				String msg = "Error in transaction rollback";
	        	this.notifyObservers(msg);
	            throw new DAOException(msg, e1);
			}
		}finally {
			close(st);
			close(conn);
		}
		return true;
	}
	
	public List<Route> getLinerRouteWithLimit(Liner liner, int currentPage, int recordsPerPage) throws DAOException {
		List<Route> res = new ArrayList<Route>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		System.out.println(start);
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LinerDAOConstants.SELECT_LINER_ROUTE_WITH_LIMIT.getSql());){
			st.setInt(1, liner.getId());
			st.setInt(2, start);
			st.setInt(3, recordsPerPage);
			try(ResultSet rs = st.executeQuery()){;
			  while(rs.next()) {
				  res.add(new RouteController().getEntityByID(rs.getInt("route_id")));
	            }
			}
        } catch (SQLException | NullPointerException ex) {
        	String msg = "Error in geting liner route";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}
	
	public int getNumberOfRowsForLinerRoute(int id) throws DAOException {
		int numOfRows = 0;
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement("SELECT COUNT(route_id) AS total FROM liner_has_route WHERE liner_id=?");){  
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery();){
				 if(rs.next()) {
		            	numOfRows = rs.getInt("total");
		         }
			}
        } catch (SQLException ex) {
        	String msg = "Error in taking number of receipts";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return numOfRows;
	}
	
	public List<Route> getLinerRoute(Liner liner) throws DAOException{
		List<Route> res = new ArrayList<Route>();
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LinerDAOConstants.SELECT_LINER_ROUTE.getSql());){  
			st.setInt(1, liner.getId());
			try(ResultSet rs = st.executeQuery();){
	        	while(rs.next()) {
	        		res.add(new RouteController().getEntityByID(rs.getInt("route_id")));
	        	}
	        }
        }catch (SQLException | NullPointerException ex) {
            String msg = "Error in geting" + liner + "route";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public List<Liner> getAll() throws DAOException {
		List<Liner> res = new ArrayList<Liner>();
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LinerDAOConstants.SELECT_ALL_LINERS.getSql());){
			ResultSet rs = st.executeQuery();
            while(rs.next()) {
            	res.add(mapLiner(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all liners";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}
}
