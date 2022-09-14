package cruise_company.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.user.UserRole;

public class UserRoleController extends AbstractController<UserRole>{

	@Override
	public List<UserRole> getAll() throws DAOException {
		List<UserRole> res = new ArrayList<UserRole>();
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(UserDAOConstants.SELECT_ALL_USER_ROLES.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapUserRole(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all user roles";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public UserRole getEntityByID(int id) throws DAOException {
		UserRole route = null;
		try (Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(UserDAOConstants.SELECT_USER_ROLE_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		route = mapUserRole(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking user role with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return route;
	}

	@Override
	public boolean update(UserRole entity) {
		return false;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(UserDAOConstants.DELETE_USER_ROLE.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("User role deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting user role with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	@Override
	public int create(UserRole entity) {
		return -1;
	}

	private UserRole mapUserRole(ResultSet rs) throws SQLException {
		UserRole res = new UserRole();
		res.setId(rs.getInt("id"));
		res.setName(rs.getString("name"));
		return res;
	}
}
