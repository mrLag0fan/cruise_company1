package cruise_company.dao.linar.personal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.linar.personal.Personal;

public class PersonalController extends AbstractController<Personal>{

	public List<Personal> getAllWithLimit(int currentPage, int recordsPerPage) throws DAOException {
		List<Personal> res = new ArrayList<Personal>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PersonalDAOConstants.SELECT_ALL_PERSONAL_WITH_LIMIT.getSql());){  
			st.setInt(1, start);
			st.setInt(2, recordsPerPage);
			try(ResultSet rs = st.executeQuery()){;
			  while(rs.next()) {
	            	res.add(mapPersonal(rs));
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
	public List<Personal> getAll() throws DAOException {
		List<Personal> res = new ArrayList<Personal>();
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(PersonalDAOConstants.SELECT_ALL_PERSONAL.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapPersonal(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all personal";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public Personal getEntityByID(int id) throws DAOException {
		Personal personal = null;
		try (Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(PersonalDAOConstants.SELECT_PERSONAL_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		personal = mapPersonal(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking personal with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return personal;
	}

	@Override
	public boolean update(Personal entity) {
		/*try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(PersonalDAOConstants.UPDATE_PERSONAL.getSql());){
			st.setInt(1, entity.getExperience());
			st.setString(2, entity.getPhone());
			st.setInt(3, entity.getPersonalRoleId());
			st.executeUpdate();
			this.notifyObservers("Personal updated with id " + entity.getId());
		}catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;*/
		return false;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PersonalDAOConstants.DELETE_PERSONAL.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("Personal deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting personal with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	@Override
	public int create(Personal entity) throws DAOException {
		int id = -1;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(PersonalDAOConstants.INSERT_PERSONAL.getSql(), Statement.RETURN_GENERATED_KEYS);){
			st.setString(1, entity.getName());
			st.setString(2, entity.getSurname());
			st.setString(3, entity.getPhone());
			st.setInt(4, entity.getExperience());
			st.setInt(5, entity.getPersonalRoleId());
			st.setInt(6, entity.getLinerId());
			int count = st.executeUpdate();
			if (count > 0) {
				try(ResultSet rs = st.getGeneratedKeys();){
					if(rs.next()) {
						id = rs.getInt(1);
						entity.setId(id);
					}
				}
			}
			this.notifyObservers("Personal created with id " + entity.getId());
	    }catch (SQLException ex) {
	    	String msg = "Error in createing personal with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return id;
	}
	
	/**
	 * @param rs
	 * @throws SQLException
	 */
	private Personal mapPersonal(ResultSet rs) throws SQLException {
		Personal personal = new Personal();
		personal.setId(rs.getInt("id"));
		personal.setName(rs.getString("name"));
		personal.setSurname(rs.getString("surname"));
		personal.setExperience(rs.getInt("experience"));
		personal.setPhone(rs.getString("phone"));
		personal.setPersonalRoleId(rs.getInt("personal_role_iid"));
		personal.setLinerId(rs.getInt("linar_id"));
		return personal;
	}

}
