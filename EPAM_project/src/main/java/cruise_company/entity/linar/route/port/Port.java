package cruise_company.entity.linar.route.port;

public class Port {
	
	private int id;
	private String name;
	private int portStatusId;
	
	

	public Port() {
		super();
	}
	public Port(String name, int portStatusId) {
		super();
		this.name = name;
		this.portStatusId = portStatusId;
	}
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
	public int getPortStatusId() {
		return portStatusId;
	}
	public void setPortStatusId(int port_status_id) {
		this.portStatusId = port_status_id;
	}
	
	@Override
	public String toString() {
		return "Port [id=" + id + ", name=" + name + ", portStatusId=" + portStatusId + "]";
	}

}
