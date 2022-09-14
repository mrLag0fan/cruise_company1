package cruise_company.dao.language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.AbstractController;
import cruise_company.dao.DAOException;
import cruise_company.entity.language.Language;


public class LanguageController extends AbstractController<Language>{

	
	@Override
	public List<Language> getAll() throws DAOException {
		List<Language> res = new ArrayList<Language>();
		try(Connection conn =  getConnection();
			PreparedStatement st = conn.prepareStatement(LanguageDAOConstants.SELECT_ALL_LANGUAGES.getSql());
			ResultSet rs = st.executeQuery();){  
            while(rs.next()) {
            	res.add(mapLanguage(rs));
            }
        } catch (SQLException ex) {
        	String msg = "Error in taking all languages";
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
        }
		return res;
	}

	@Override
	public Language getEntityByID(int id) throws DAOException {
		Language language = null;
		try (Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(LanguageDAOConstants.SELECT_LANGUAGE_BY_ID.getSql())){  
			st.setInt(1, id);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		language = mapLanguage(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking language with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return language;
	}
	
	
	public Language getEntityByShortName(String name) throws DAOException {
		Language language = null;
		try (Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(LanguageDAOConstants.SELECT_LANGUAGE_BY_SHORT_NAME.getSql())){  
			st.setString(1, name);
			 try(ResultSet rs = st.executeQuery();){
		        	if(rs.next()) {
		        		language = mapLanguage(rs);
		        	}
		     }
	    } catch (SQLException ex) {
	    	String msg = "Error in taking language with name " + name;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return language;
	}


	@Override
	public boolean delete(int id) throws DAOException {
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(LanguageDAOConstants.DELETE_LANGUAGE.getSql());){
			st.setInt(1, id);
			st.executeUpdate();
			this.notifyObservers("Language deleted with id " + id);
	    }catch (SQLException ex) {
	    	String msg = "Error in deleting language with id " + id;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return true;
	}

	/**
	 * @param rs
	 * @throws SQLException
	 */
	private Language mapLanguage(ResultSet rs) throws SQLException {
		Language lang = new Language();
		lang.setFullName(rs.getString("full_name"));
		lang.setId(rs.getInt("id"));
		lang.setShortName(rs.getString("short_name"));
		return lang;
	}

	@Override
	public boolean update(Language entity) {
		return false;
	}

	@Override
	public int create(Language entity) throws DAOException {
		int id = -1;
		try(Connection conn =  getConnection();
				PreparedStatement st = conn.prepareStatement(LanguageDAOConstants.INSERT_LANGUAGE.getSql(), Statement.RETURN_GENERATED_KEYS);){
			st.setString(1, entity.getFullName());
			st.setString(2, entity.getShortName());
			int count = st.executeUpdate();
			if (count > 0) {
				try(ResultSet rs = st.getGeneratedKeys();){
					if(rs.next()) {
						id = rs.getInt(1);
						 entity.setId(id);
					}
				}
			}
			this.notifyObservers("Language created" + entity);
	    }catch (SQLException ex) {
	    	String msg = "Error in createing language with data " + entity;
        	this.notifyObservers(msg);
            throw new DAOException(msg, ex);
	    }
		return id;
	}
}
