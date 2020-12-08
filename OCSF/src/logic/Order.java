package logic;

import javafx.beans.property.SimpleStringProperty;

public class Order {
	private SimpleStringProperty orderId;
	private SimpleStringProperty travelerId;
	private SimpleStringProperty parkId; // int in DB
	private SimpleStringProperty orderDate;
	private SimpleStringProperty orderTime;
	private SimpleStringProperty orderType;
	private SimpleStringProperty numberOfParticipants;
	private SimpleStringProperty email;
	private SimpleStringProperty price;
	private SimpleStringProperty orderStatus;

	public Order(SimpleStringProperty orderId, SimpleStringProperty travelerId, SimpleStringProperty parkId,
			SimpleStringProperty orderDate, SimpleStringProperty orderTime, SimpleStringProperty orderType,
			SimpleStringProperty numberOfParticipants, SimpleStringProperty email, SimpleStringProperty price,
			SimpleStringProperty orderStatus) {
		super();
		this.orderId = orderId;
		this.travelerId = travelerId;
		this.parkId = parkId;
		this.orderDate = orderDate;
		this.orderTime = orderTime;
		this.orderType = orderType;
		this.numberOfParticipants = numberOfParticipants;
		this.email = email;
		this.price = price;
		this.orderStatus = orderStatus;
	}

	public SimpleStringProperty getOrderId() {
		return orderId;
	}

	public void setOrderId(SimpleStringProperty orderId) {
		this.orderId = orderId;
	}

	public SimpleStringProperty getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(SimpleStringProperty travelerId) {
		this.travelerId = travelerId;
	}

	public SimpleStringProperty getParkId() {
		return parkId;
	}

	public void setParkId(SimpleStringProperty parkId) {
		this.parkId = parkId;
	}

	public SimpleStringProperty getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(SimpleStringProperty orderDate) {
		this.orderDate = orderDate;
	}

	public SimpleStringProperty getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(SimpleStringProperty orderTime) {
		this.orderTime = orderTime;
	}

	public SimpleStringProperty getOrderType() {
		return orderType;
	}

	public void setOrderType(SimpleStringProperty orderType) {
		this.orderType = orderType;
	}

	public SimpleStringProperty getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(SimpleStringProperty numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public SimpleStringProperty getEmail() {
		return email;
	}

	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}

	public SimpleStringProperty getPrice() {
		return price;
	}

	public void setPrice(SimpleStringProperty price) {
		this.price = price;
	}

	public SimpleStringProperty getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(SimpleStringProperty orderStatus) {
		this.orderStatus = orderStatus;
	}

}
