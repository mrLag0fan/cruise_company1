package cruise_company.dao.linar.route.port;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.linar.route.port.Port;

public class PortController extends AbstractController<Port>{

	public List<Port> getAllWithLimit(int currentPage, int recordsPerPage) throws DAOException {
		List<Port> res = new ArrayList<Port>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.SELECT_ALL_PORTS_WITH_LIMIT.getSql());){  
			st.setInt(1, start); 
			st.setInt(2, recordsPerPage);
			try(ResultSet rs = st.executeQuery()){;
			  while(rs.next()) {
	            	res.add(mapPort(rs));
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
	public List<Port> getAll() throws DAOException {
		List<Port> res = new ArrayList<Port>();
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.SELECT_ALL_PORTS.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapPort(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all ports";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public Port getEntityByID(int id) throws DAOException {
		Port port = null;
		try (Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.SELECT_PORT_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		port = mapPort(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking port with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return port;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.DELETE_PORT.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("Port deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting port with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}
	
	/**
	 * @param rs
	 * @throws SQLException
	 */
	private Port mapPort(ResultSet rs) throws SQLException {
		Port port = new Port();
		port.setId(rs.getInt("id"));
		port.setName(rs.getString("name"));
		port.setPortStatusId(rs.getInt("port_status_id"));
		return port;
	}

	@Override
	public boolean update(Port entity) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.UPDATE_PORT_STATUS.getSql());){
			st.setInt(1, entity.getPortStatusId());
			st.setInt(2, entity.getId());
			st.executeUpdate();
			this.notifyObservers("Port updated with id " + entity.getId());
		}catch(SQLException | NullPointerException ex) {
	    	String msg = "Error in updateing port with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
		}
		return true;
	}

	@Override
	public int create(Port entity) throws DAOException {
		int id = -1;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.INSERT_PORT.getSql(), Statement.RETURN_GENERATED_KEYS);){
			st.setString(1, entity.getName());
			st.setInt(2, entity.getPortStatusId());
			int count = st.executeUpdate();
			if (count > 0) {
				try(ResultSet rs = st.getGeneratedKeys();){
					if(rs.next()) {
						id = rs.getInt(1);
						 entity.setId(id);
					}
				}
			}
			this.notifyObservers("Port created with id " + entity.getId());
	    }catch (SQLException | NullPointerException ex) {
	    	String msg = "Error in createing port with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return id;
	}

}
