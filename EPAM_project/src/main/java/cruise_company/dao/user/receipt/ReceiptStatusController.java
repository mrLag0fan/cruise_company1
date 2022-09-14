package cruise_company.dao.user.receipt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.user.receipt.ReceiptStatus;

public class ReceiptStatusController extends AbstractController<ReceiptStatus>{

	public List<ReceiptStatus> getEntityByLanguage(int id) throws DAOException {
		List<ReceiptStatus> res = new ArrayList<ReceiptStatus>();
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.SELECT_PORT_BY_LANGUAGE.getSql());
			){  
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery();){
				while(rs.next()) {
	            	res.add(mapReceiptStatus(rs));
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
	public List<ReceiptStatus> getAll() throws DAOException {
		List<ReceiptStatus> res = new ArrayList<ReceiptStatus>();
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.SELECT_ALL_RECEIPT_STATUSES.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapReceiptStatus(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all receipt statuses";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public ReceiptStatus getEntityByID(int id) throws DAOException {
		ReceiptStatus res = null;
		try (Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.SELECT_RECEIPT_STATUS_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		res = mapReceiptStatus(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking receipt status with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return res;
	}

	@Override
	public boolean update(ReceiptStatus entity) {
		return false;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.DELETE_RECEIPT_STATUS.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("Receipt status deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting receipt status with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	@Override
	public int create(ReceiptStatus entity) {
		return -1;
	}
	
	private ReceiptStatus mapReceiptStatus(ResultSet rs) throws SQLException {
		ReceiptStatus res = new ReceiptStatus();
		res.setId(rs.getInt("id"));
		res.setName(rs.getString("name"));
		res.setLanguageId(rs.getInt("language_id"));
		return res;
	}

}
