package cruise_company.entity.user.receipt;

import java.util.Objects;

public class Receipt {
	
	private int id;
	private String documents;
	private double price;
	private int receiptStatusId;
	private int linerId;
	private int userId;
	
	
	
	public Receipt() {
		super();
	}
	public Receipt(String documents, double price, int receiptStatusId, int linerId, int userId) {
		super();
		this.documents = documents;
		this.price = price;
		this.receiptStatusId = receiptStatusId;
		this.linerId = linerId;
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocuments() {
		return documents;
	}
	public void setDocuments(String documents) {
		this.documents = documents;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getReceiptStatusId() {
		return receiptStatusId;
	}
	public void setReceiptStatusId(int receiptStatusId) {
		this.receiptStatusId = receiptStatusId;
	}
	public int getLinerId() {
		return linerId;
	}
	public void setLinerId(int linerId) {
		this.linerId = linerId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Receipt [id=" + id + ", documents=" + documents + ", price=" + price + ", receiptStatusId="
				+ receiptStatusId + ", linerId=" + linerId + ", userId=" + userId + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(documents, id, linerId, price, receiptStatusId, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receipt other = (Receipt) obj;
		return Objects.equals(documents, other.documents) && id == other.id && linerId == other.linerId
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& receiptStatusId == other.receiptStatusId && userId == other.userId;
	}
	
	
	
}
