package cruise_company.dao.user.receipt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.user.receipt.Receipt;

public class ReceiptController extends AbstractController<Receipt>{

	public List<Receipt> getAllWithLimit(int id, int currentPage, int recordsPerPage) throws DAOException {
		List<Receipt> res = new ArrayList<Receipt>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.SELECT_ALL_RECEIPTS_WITH_LIMIT.getSql());){  
			st.setInt(1, id);
			st.setInt(2, start);
			st.setInt(3, recordsPerPage);
			try(ResultSet rs = st.executeQuery()){;
			  while(rs.next()) {
	            	res.add(mapReceipt(rs));
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
	public List<Receipt> getAll() throws DAOException {
		List<Receipt> res = new ArrayList<Receipt>();
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.SELECT_ALL_RECEIPTS.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapReceipt(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all receipts";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public Receipt getEntityByID(int id) throws DAOException {
		Receipt res = null;
		try (Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.SELECT_RECEIPT_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		res = mapReceipt(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking receipt with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return res;
	}

	public boolean updateStatus(Receipt entity) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.UPDATE_RECEIPT.getSql());){
				st.setInt(1, entity.getReceiptStatusId());
				st.setInt(2, entity.getId());
				st.executeUpdate();
				this.notifyObservers("Receipt updated" + entity);
			}catch(SQLException ex) {
				String msg = "Error in updateing receipt status" + entity;
	        	this.notifyObservers(msg);
	            throw new DAOException(msg, ex);
			}
			return true;
	}


	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.DELETE_RECEIPT.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("Receipt deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting receipt with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	@Override
	public int create(Receipt entity) throws DAOException {
		int id = -1;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.INSERT_RECEIPT.getSql(), Statement.RETURN_GENERATED_KEYS);){
			st.setString(1, entity.getDocuments());
			st.setDouble(2, entity.getPrice());
			st.setInt(3, entity.getReceiptStatusId());
			st.setInt(4, entity.getLinerId());
			st.setInt(5, entity.getUserId());
			int count = st.executeUpdate();
			if (count > 0) {
				try(ResultSet rs = st.getGeneratedKeys();){
					if(rs.next()) {
						id = rs.getInt(1);
						 entity.setId(id);
					}
				}
			}
			this.notifyObservers("Receipt created with id " + entity.getId());
	    }catch (SQLException ex) {
	    	String msg = "Error in createing receipt with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return id;
	}
	
	private Receipt mapReceipt(ResultSet rs) throws SQLException {
		Receipt res = new Receipt();
		res.setId(rs.getInt("id"));
		res.setDocuments(rs.getString("documents"));
		res.setPrice(rs.getDouble("price"));
		res.setReceiptStatusId(rs.getInt("receipt_status_id"));
		res.setLinerId(rs.getInt("liner_id"));
		res.setUserId(rs.getInt("user_id"));
		return res;
	}

	@Override
	public boolean update(Receipt entity) {
		return false;
	}
	
	/*public List<Receipt> getUserReceipts(int id) throws DAOException {
		List<Receipt> res = new ArrayList<Receipt>();
		try (Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.SELECT_USER_RECEIPTS.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	while(rs.next()) {
		        		res.add(mapReceipt(rs));
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in geting user receipts";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return res;
	}*/
	
	public int countReceiptForLiner(int id) throws DAOException {
		int res = 0;
		try (Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(ReceiptDAOConstants.COUNT_ALL_RECEIPTS_FOR_LINER.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		res = rs.getInt("total");
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in geting count of receipts for liner with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return res;
	}

	public int getNumberOfRowsForUser(int id) throws DAOException {
		int numOfRows = 0;
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement("SELECT COUNT(id) AS total FROM receipt WHERE user_id=?");){  
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
	
}
