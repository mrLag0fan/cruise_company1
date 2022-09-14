package cruise_company.entity.linar.personal;

public class Personal {
	
	private int id;
	private String name;
	private String surname;
	private int experience;
	private String phone;
	private int personalRoleId;
	private int linerId;
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experiance) {
		this.experience = experiance;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPersonalRoleId() {
		return personalRoleId;
	}
	public void setPersonalRoleId(int personalRoleId) {
		this.personalRoleId = personalRoleId;
	}
	public int getLinerId() {
		return linerId;
	}
	public void setLinerId(int linerId) {
		this.linerId = linerId;
	}
	@Override
	public String toString() {
		return "Personal [id=" + id + ", name=" + name + ", surname=" + surname + ", experience=" + experience
				+ ", phone=" + phone + ", personalRoleId=" + personalRoleId + ", linerId=" + linerId + "]";
	}

	
}
