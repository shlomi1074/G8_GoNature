package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Discount {
	private SimpleStringProperty discountId;
	private SimpleDoubleProperty amount;
	private SimpleStringProperty startDate;
	private SimpleStringProperty endDate;
	private SimpleStringProperty parkId;

	public Discount(SimpleStringProperty discountId, SimpleDoubleProperty amount, SimpleStringProperty startDate, SimpleStringProperty endDate, SimpleStringProperty parkId) {
		this.discountId = discountId;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parkId = parkId;
	}

	public SimpleStringProperty getDiscountId() {
		return discountId;
	}

	public void setDiscountId(SimpleStringProperty discountId) {
		this.discountId = discountId;
	}

	public SimpleDoubleProperty getAmount() {
		return amount;
	}

	public void setAmount(SimpleDoubleProperty amount) {
		this.amount = amount;
	}

	public SimpleStringProperty getStartDate() {
		return startDate;
	}

	public void setStartDate(SimpleStringProperty startDate) {
		this.startDate = startDate;
	}

	public SimpleStringProperty getEndDate() {
		return endDate;
	}

	public void setEndDate(SimpleStringProperty endDate) {
		this.endDate = endDate;
	}

	public SimpleStringProperty getParkId() {
		return parkId;
	}

	public void setParkId(SimpleStringProperty parkId) {
		this.parkId = parkId;
	}

}
