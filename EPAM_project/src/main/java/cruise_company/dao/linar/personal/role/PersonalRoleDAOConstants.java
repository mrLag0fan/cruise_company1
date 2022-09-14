package cruise_company.dao.linar.personal.role;

public enum PersonalRoleDAOConstants {
	SELECT_ROLE_BY_LANGUAGE("SELECT * FROM personal_role WHERE language_id=?"),
	SELECT_ALL_ROLES("SELECT * FROM personal_role"),
	SELECT_ROLE_BY_ID("SELECT * FROM personal_role WHERE iid=?"),
	DELETE_ROLE("DELETE FROM personal_role WHERE iid=?"),
	INSERT_ROLE("INSERT INTO personal_role (name, language_id) VALUES (?, ?)");
	
	private final String sql;
	
	private PersonalRoleDAOConstants(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
