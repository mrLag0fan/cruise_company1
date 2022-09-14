package cruise_company.dao.linar.personal;

enum PersonalDAOConstants {
	SELECT_ALL_PERSONAL_WITH_LIMIT("SELECT * FROM personal limit ?,?"),
	SELECT_ALL_PERSONAL("SELECT * FROM personal"),
	SELECT_PERSONAL_BY_ID("SELECT * FROM personal WHERE id=?"),
	DELETE_PERSONAL("DELETE FROM personal WHERE id=?"),
	INSERT_PERSONAL("INSERT INTO personal (name, surname, phone, experience, personal_role_iid, linar_id) VALUES (?, ?, ?, ?, ?, ?)"),
	UPDATE_PERSONAL("UPDATE personal SET experiance=?, phone=?, personal_role_iid=? WHERE id=?");
	
	private final String sql;
	
	private PersonalDAOConstants(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
