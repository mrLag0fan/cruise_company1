package cruise_company.dao.user.receipt;

enum ReceiptDAOConstants {
	SELECT_PORT_BY_LANGUAGE("SELECT * FROM receipt_status WHERE language_id=?"),
	SELECT_ALL_RECEIPTS_WITH_LIMIT("SELECT * FROM receipt WHERE user_id =? limit ?,? "),
	SELECT_ALL_RECEIPTS("SELECT * FROM receipt"), 
	COUNT_ALL_RECEIPTS_FOR_LINER("SELECT COUNT(*) AS total FROM receipt WHERE (liner_id=? AND (receipt_status_id=5 OR receipt_status_id=11))"),
	SELECT_ALL_RECEIPT_STATUSES("SELECT * FROM receipt_status"),
	SELECT_USER_RECEIPTS("SELECT * FROM receipt WHERE user_id=?"),
	SELECT_RECEIPT_BY_ID("SELECT * FROM receipt WHERE id=?"),
	SELECT_RECEIPT_STATUS_BY_ID("SELECT * FROM receipt_status WHERE id=?"),
	DELETE_RECEIPT("DELETE FROM receipt WHERE id=?"),
	DELETE_RECEIPT_STATUS("DELETE FROM receipt_status WHERE id=?"),
	INSERT_RECEIPT("INSERT INTO receipt (documents, price, receipt_status_id, liner_id, user_id) VALUES (?, ?, ?, ?, ?)"),
	INSERT_RECEIPT_STATUS("INSERT INTO user_role (name, language_id) VALUES (?, ?)"),
	UPDATE_RECEIPT("UPDATE receipt SET receipt_status_id=? WHERE id=?"),
	SET_DOCUMENTS("UPDATE receipt SET documents=?, receipt_status_id=? WHERE id=?");
	
	private final String sql;
	
	private ReceiptDAOConstants(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
