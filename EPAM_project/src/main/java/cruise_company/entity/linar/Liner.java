package cruise_company.entity.linar;

import java.time.LocalDate;

public class Liner {
	
	private int id;
	private int passengerCapacity; 
	private int visitedPorts;
	private LocalDate dateStart;
	private LocalDate dateEnd;
	private int durationInDays;
	private double price;
	private int start;
	private int end;
	 
	
	
	public Liner() {
	}
	public Liner(int passengerCapacity, int visitedPorts, LocalDate dateStart, LocalDate dateEnd,
			int durationInDays, double price, int start, int end) {
		this.passengerCapacity = passengerCapacity;
		this.visitedPorts = visitedPorts;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.durationInDays = durationInDays;
		this.price = price;
		this.start = start;
		this.end = end;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVisitedPorts() {
		return visitedPorts;
	}
	public void setVisitedPorts(int visitedPorts) {
		this.visitedPorts = visitedPorts;
	}
	public LocalDate getDateStart() {
		return dateStart;
	}
	public void setDateStart(LocalDate dateStart) {
		this.dateStart = dateStart;
	}
	public LocalDate getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(LocalDate dateEnd) {
		this.dateEnd = dateEnd;
	}
	public int getDurationInDays() {
		return durationInDays;
	}
	public void setDurationInDays(int durationInWeeks) {
		this.durationInDays = durationInWeeks;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getPassengerCapacity() {
		return passengerCapacity;
	}
	public void setPassengerCapacity(int passengerCapacity) {
		this.passengerCapacity = passengerCapacity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Liner [id=" + id + ", passengerCapacity=" + passengerCapacity + ", visitedPorts=" + visitedPorts
				+ ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", durationInDays=" + durationInDays
				+ ", price=" + price + ", start=" + start + ", end=" + end + "]";
	}

	
}
