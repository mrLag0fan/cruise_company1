package cruise_company.dao.language;

enum LanguageDAOConstants {
	SELECT_ALL_LANGUAGES("SELECT * FROM language"),
	SELECT_LANGUAGE_BY_ID("SELECT * FROM language WHERE id=?"),
	SELECT_LANGUAGE_BY_SHORT_NAME("SELECT * FROM language WHERE short_name=?"),
	DELETE_LANGUAGE("DELETE FROM language WHERE id=?"),
	INSERT_LANGUAGE("INSERT INTO language (full_name, short_name) VALUES (?, ?)");
	
	private final String sql;
	
	private LanguageDAOConstants(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
