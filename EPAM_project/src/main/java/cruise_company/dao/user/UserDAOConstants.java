package cruise_company.dao.user;

enum UserDAOConstants {
	SELECT_ALL_PORTS_WITH_LIMIT("SELECT * FROM user limit ?,?"),
	SELECT_ALL_USERS("SELECT * FROM user"),
	SELECT_ALL_USER_ROLES("SELECT * FROM user_role"),
	SELECT_USER_BY_ID("SELECT * FROM user WHERE id=?"),
	SELECT_USER_BY_EMAIL_AND_PASSWORD("SELECT * FROM user WHERE email=? AND password=?"),
	SELECT_USER_ROLE_BY_ID("SELECT * FROM user_role WHERE id=?"),
	DELETE_USER("DELETE FROM user WHERE id=?"),
	DELETE_USER_ROLE("DELETE FROM user_role WHERE id=?"),
	INSERT_USER("INSERT INTO user (email, password, user_role_id) VALUES (?, ?, ?)"),
	INSERT_USER_ROLE("INSERT INTO user_role (name) VALUES (?)"),
	INSERT_RECEIPTS_FOR_USER("INSERT INTO user_has_receipt (user_id, receipt_id) VALUES (?, ?)"),
	UPDATE_BALANCE("UPDATE user SET balance=? WHERE id=?"),
	PROMOTE_USER("UPDATE user SET user_role_id=? WHERE id=?");
	
	private final String sql;
	
	private UserDAOConstants(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
