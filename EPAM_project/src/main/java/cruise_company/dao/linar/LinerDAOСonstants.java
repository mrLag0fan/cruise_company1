package cruise_company.dao.linar;

enum LinerDAOConstants {
	SELECT_ALL_LINERS_WITH_LIMIT("SELECT * FROM liner limit ?,?"),
	SELECT_ALL_LINERS("SELECT * FROM liner"),
	SELECT_LINER_BY_ID("SELECT * FROM liner WHERE id=?"),
	SELECT_LINER_ROUTE_WITH_LIMIT("SELECT route_id FROM liner_has_route WHERE liner_id=? limit ?,?"),
	SELECT_LINER_ROUTE("SELECT route_id FROM liner_has_route WHERE liner_id=?"),
	COUNT_NUMBER_OF_ROWS("SELECT COUNT(id) AS total FROM liner"),
	DELETE_LINER("DELETE FROM liner WHERE id=?"),
	UPDATE_LINER("UPDATE liner SET date_start=?, date_end=?, duration_in_weeks=?, start=?, end=? WHERE id=?"),
	INSERT_LINER("INSERT INTO liner (passenger_capacity, date_start, date_end, duration_in_days, price, visited_ports, start, end) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"),
	INSERT_ROUTE_FOR_LINER("INSERT INTO liner_has_route (liner_id, route_id) VALUES (?, ?)");
	
	private final String sql;
	
	private LinerDAOConstants(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
	
	
}
