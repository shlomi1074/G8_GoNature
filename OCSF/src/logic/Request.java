package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Request {
	private SimpleStringProperty requestId;
	private SimpleStringProperty changeName;
	private SimpleStringProperty newValue;
	private SimpleStringProperty oldValue;
	private SimpleStringProperty requestDate;
	private SimpleIntegerProperty parkId;
	private SimpleStringProperty requestStatus;

	public Request(SimpleStringProperty requestId, SimpleStringProperty changeName, SimpleStringProperty newValue,
			SimpleStringProperty oldValue, SimpleStringProperty requestDate, SimpleIntegerProperty parkId,
			SimpleStringProperty requestStatus) {
		super();
		this.requestId = requestId;
		this.changeName = changeName;
		this.newValue = newValue;
		this.oldValue = oldValue;
		this.requestDate = requestDate;
		this.parkId = parkId;
		this.requestStatus = requestStatus;
	}

	public SimpleStringProperty getRequestId() {
		return requestId;
	}

	public void setRequestId(SimpleStringProperty requestId) {
		this.requestId = requestId;
	}

	public SimpleStringProperty getChangeName() {
		return changeName;
	}

	public void setChangeName(SimpleStringProperty changeName) {
		this.changeName = changeName;
	}

	public SimpleStringProperty getNewValue() {
		return newValue;
	}

	public void setNewValue(SimpleStringProperty newValue) {
		this.newValue = newValue;
	}

	public SimpleStringProperty getOldValue() {
		return oldValue;
	}

	public void setOldValue(SimpleStringProperty oldValue) {
		this.oldValue = oldValue;
	}

	public SimpleStringProperty getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(SimpleStringProperty requestDate) {
		this.requestDate = requestDate;
	}

	public SimpleIntegerProperty getParkId() {
		return parkId;
	}

	public void setParkId(SimpleIntegerProperty parkId) {
		this.parkId = parkId;
	}

	public SimpleStringProperty getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(SimpleStringProperty requestStatus) {
		this.requestStatus = requestStatus;
	}

}
