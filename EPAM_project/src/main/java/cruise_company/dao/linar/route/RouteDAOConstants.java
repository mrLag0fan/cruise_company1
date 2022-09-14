package cruise_company.dao.linar.route;

enum RouteDAOConstants{
	SELECT_ALL_ROUTES_WITH_LIMIT("SELECT * FROM route limit ?,?"),
	SELECT_ALL_ROUTES("SELECT * FROM route"),
	SELECT_ROUTE_BY_ID("SELECT * FROM route WHERE id=?"),
	DELETE_ROUTE("DELETE FROM route WHERE id=?"),
	INSERT_ROUTE("INSERT INTO route (`from`, `to`) VALUES (?, ?)");
	
	private final String sql;
	
	private RouteDAOConstants(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
