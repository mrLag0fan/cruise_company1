package cruise_company.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.user.User;

public class UserController extends AbstractController<User>{

	public List<User> getAllWithLimit(int currentPage, int recordsPerPage) throws DAOException {
		List<User> res = new ArrayList<User>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(UserDAOConstants.SELECT_ALL_PORTS_WITH_LIMIT.getSql());){  
			st.setInt(1, start); 
			st.setInt(2, recordsPerPage);
			try(ResultSet rs = st.executeQuery()){;
			  while(rs.next()) { 
	            	res.add(mapUser(rs));
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
	public List<User> getAll() throws DAOException {
		List<User> res = new ArrayList<User>();
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(UserDAOConstants.SELECT_ALL_USERS.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapUser(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all users";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public User getEntityByID(int id) throws DAOException {
		User res = null;
		try (Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(UserDAOConstants.SELECT_USER_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		res = mapUser(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking user with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return res;
	}

	@Override
	public boolean update(User entity) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(UserDAOConstants.UPDATE_BALANCE.getSql());){
			st.setDouble(1, entity.getBalance());
			st.setInt(2, entity.getId());
			st.executeUpdate();
			this.notifyObservers("User balance updated with id " + entity.getId());
		}catch(SQLException | NullPointerException ex) {
	    	String msg = "Error in updateing user balance with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
		}
		return true;
	}
	
	public boolean promote(User entity) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(UserDAOConstants.PROMOTE_USER.getSql());){
			st.setDouble(1, entity.getUserRoleId());
			st.setInt(2, entity.getId());
			st.executeUpdate();
			this.notifyObservers("User promted to admin " + entity);
		}catch(SQLException | NullPointerException ex) {
	    	String msg = "Error in promting user to admmin " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
		}
		return true;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(UserDAOConstants.DELETE_USER.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("User deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting user with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	@Override
	public int create(User entity) throws DAOException {
		int id = -1;
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(UserDAOConstants.INSERT_USER.getSql(), Statement.RETURN_GENERATED_KEYS);){
			st.setString(1, entity.getEmail());
			st.setString(2, entity.getPassword());
			st.setInt(3, entity.getUserRoleId());
			int count = st.executeUpdate();
			if (count > 0) {
				try(ResultSet rs = st.getGeneratedKeys();){
					if(rs.next()) {
						id = rs.getInt(1);
						 entity.setId(id);
					}
				}
			}
			this.notifyObservers("User registered" + entity.getEmail());
	    }catch (SQLException ex) {
	    	String msg = "Error in createing user with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return id;
	}
	
	private User mapUser(ResultSet rs) throws SQLException {
		User res = new User();
		res.setId(rs.getInt("id"));
		res.setEmail(rs.getString("email"));
		res.setPassword(rs.getString("password"));
		res.setUserRoleId(rs.getInt("user_role_id"));
		res.setBalance(rs.getDouble("balance"));
		return res;
	}
	
	public User getUserForLogin(String email, String password) throws DAOException {
		try (Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(UserDAOConstants.SELECT_USER_BY_EMAIL_AND_PASSWORD.getSql())){  
			st.setString(1, email);
			st.setString(2, password);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		return mapUser(rs);
		        	}
		     }
	    } catch (SQLException | NullPointerException ex) {
	    	String msg = "Error in geting user email and password " + email + " " + password;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return null;
	}
	
	
}
