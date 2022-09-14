package cruise_company.entity.user;

public class User {
	
	private int id;
	private String email;
	private String password;
	private int userRoleId;
	private double balance = 0;
	
	public User(String email, String password) {
		this.email = email; 
		this.password = password;
		this.userRoleId = 2;
	}
	public User() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", userRoleId=" + userRoleId + "]";
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance += balance;
	}
	
}
