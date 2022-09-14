package cruise_company.entity.language;

public class Language {
	private int id;
	private String fullName;
	private String shortName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Language(String fullName, String shortName) {
		super();
		this.fullName = fullName;
		this.shortName = shortName;
	}
	public Language() {
		super();
	}
	@Override
	public String toString() {
		return "Language [id=" + id + ", fullName=" + fullName + ", shortName=" + shortName + "]";
	}

}
