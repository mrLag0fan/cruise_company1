package cruise_company.dao.linar.route.port;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.linar.route.port.PortStatus;

public class PortStatusController extends AbstractController<PortStatus>{

	public List<PortStatus> getEntityByLanguage(int id) throws DAOException {
		List<PortStatus> res = new ArrayList<PortStatus>();
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.SELECT_PORT_STATUS_BY_LANGUAGE.getSql());
			){  
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery();){
				while(rs.next()) {
	            	res.add(mapPortStatus(rs));
	            }
			}
        } catch (SQLException ex) {
        	String msg = "Error in taking all port statuses";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public List<PortStatus> getAll() throws DAOException {
		List<PortStatus> res = new ArrayList<PortStatus>();
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.SELECT_ALL_PORT_STATUSES.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapPortStatus(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all port statuses";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}
	
	private PortStatus mapPortStatus(ResultSet rs) throws SQLException {
		PortStatus ps = new PortStatus();
		ps.setId(rs.getInt("id"));
		ps.setName(rs.getString("name"));
		ps.setLanguageId(rs.getInt("language_id"));
		return ps;
	}

	@Override
	public PortStatus getEntityByID(int id) throws DAOException {
		PortStatus port = null;
		try (Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PortDAOConstants.SELECT_PORT_STATUS_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		port = mapPortStatus(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking port status with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return port;
	}

	@Override
	public boolean update(PortStatus entity) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public int create(PortStatus entity) {
		return -1;
	}

}
