package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
	private SimpleStringProperty orderId;
	private SimpleStringProperty travelerId;
	private SimpleIntegerProperty parkId;
	private SimpleStringProperty orderDate;
	private SimpleStringProperty orderTime;
	private SimpleStringProperty orderType;
	private SimpleIntegerProperty numberOfParticipants;
	private SimpleStringProperty email;
	private SimpleDoubleProperty price;
	private SimpleStringProperty orderStatus;

	public Order(SimpleStringProperty orderId, SimpleStringProperty travelerId, SimpleIntegerProperty parkId,
			SimpleStringProperty orderDate, SimpleStringProperty orderTime, SimpleStringProperty orderType,
			SimpleIntegerProperty numberOfParticipants, SimpleStringProperty email, SimpleDoubleProperty price,
			SimpleStringProperty orderStatus) {
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

	public SimpleIntegerProperty getParkId() {
		return parkId;
	}

	public void setParkId(SimpleIntegerProperty parkId) {
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

	public SimpleIntegerProperty getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(SimpleIntegerProperty numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public SimpleStringProperty getEmail() {
		return email;
	}

	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}

	public SimpleDoubleProperty getPrice() {
		return price;
	}

	public void setPrice(SimpleDoubleProperty price) {
		this.price = price;
	}

	public SimpleStringProperty getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(SimpleStringProperty orderStatus) {
		this.orderStatus = orderStatus;
	}

}
