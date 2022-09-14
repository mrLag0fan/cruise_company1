package cruise_company.entity.linar.route;

public class Route {

	private int id;
	private int from;
	private int to;
	
	
	
	public Route() {
		super();
	}
	
	public Route(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int start) {
		this.from = start;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int end) {
		this.to = end;
	}
	@Override
	public String toString() {
		return "Route [id=" + id + ", from=" + from + ", to=" + to + "]";
	}
	
	
}
