package cruise_company.dao.linar.route.port;

enum PortDAOConstants {
	SELECT_ALL_PORTS_WITH_LIMIT("SELECT * FROM port limit ?,?"),
	SELECT_ALL_PORTS("SELECT * FROM port"),
	SELECT_ALL_PORT_STATUSES("SELECT * FROM port_status"),
	SELECT_PORT_BY_ID("SELECT * FROM port WHERE id=?"),
	SELECT_PORT_STATUS_BY_LANGUAGE("SELECT * FROM port_status WHERE language_id=?"),
	SELECT_PORT_STATUS_BY_ID("SELECT * FROM port_status WHERE id=?"),
	DELETE_PORT("DELETE FROM port WHERE id=?"),
	INSERT_PORT("INSERT INTO port (name, port_status_id) VALUES (?, ?)"),
	INSERT_PORT_STATUS("INSERT INTO port_status (name) VALUES (?)"),
	UPDATE_PORT_STATUS("UPDATE port SET port_status_id=? WHERE id=?");
	
	private final String sql;
	
	private PortDAOConstants(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

}
