package cruise_company.dao.linar.personal.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.linar.personal.PersonalRole;

public class PersonalRoleController extends AbstractController<PersonalRole>{

	public List<PersonalRole> getEntityByLanguage(int id) throws DAOException {
		List<PersonalRole> res = new ArrayList<PersonalRole>();
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PersonalRoleDAOConstants.SELECT_ROLE_BY_LANGUAGE.getSql());
			){  
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery();){
				while(rs.next()) {
	            	res.add(mapPersonalRole(rs));
	            }
			}
        } catch (SQLException ex) {
        	String msg = "Error in taking all personal role";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}
	
	@Override
	public List<PersonalRole> getAll() throws DAOException {
		List<PersonalRole> res = new ArrayList<PersonalRole>();
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(PersonalRoleDAOConstants.SELECT_ALL_ROLES.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapPersonalRole(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all personal roles";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public PersonalRole getEntityByID(int id) throws DAOException {
		PersonalRole res = null;
		try (Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PersonalRoleDAOConstants.SELECT_ROLE_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		res = mapPersonalRole(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking personal role with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return res;
	}

	@Override
	public boolean update(PersonalRole entity) {
		return false;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PersonalRoleDAOConstants.DELETE_ROLE.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("Personal role deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting personal role with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	@Override
	public int create(PersonalRole entity) {
		return -1;
	}

	
	/**
	 * @param rs
	 * @throws SQLException
	 */
	private PersonalRole mapPersonalRole(ResultSet rs) throws SQLException {
		PersonalRole res = new PersonalRole();
		res.setId(rs.getInt("iid"));
		res.setName(rs.getString("name"));
		res.setLanguageId(rs.getInt("language_id"));
		return res;
	}
}
