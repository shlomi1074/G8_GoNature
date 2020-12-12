package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RequestTb {
	private SimpleStringProperty requestId;
	private SimpleStringProperty changeName;
	private SimpleStringProperty newValue;
	private SimpleStringProperty oldValue;
	private SimpleStringProperty requestDate;
	private SimpleIntegerProperty parkId;
	private SimpleStringProperty requestStatus;

	public RequestTb(String requestId, String changeName, String newValue,
			String oldValue, String requestDate, int parkId,
			String requestStatus) {
		super();
		this.requestId = new SimpleStringProperty(requestId);
		this.changeName = new SimpleStringProperty(changeName);
		this.newValue = new SimpleStringProperty(newValue);
		this.oldValue = new SimpleStringProperty(oldValue);
		this.requestDate = new SimpleStringProperty(requestDate);
		this.parkId = new SimpleIntegerProperty(parkId);
		this.requestStatus = new SimpleStringProperty(requestStatus);
	}

	public String getRequestId() {
		return requestId.get();
	}

	public void setRequestId(SimpleStringProperty requestId) {
		this.requestId = requestId;
	}

	public String getChangeName() {
		return changeName.get();
	}

	public void setChangeName(SimpleStringProperty changeName) {
		this.changeName = changeName;
	}

	public String getNewValue() {
		return newValue.get();
	}

	public void setNewValue(SimpleStringProperty newValue) {
		this.newValue = newValue;
	}

	public String getOldValue() {
		return oldValue.get();
	}

	public void setOldValue(SimpleStringProperty oldValue) {
		this.oldValue = oldValue;
	}

	public String getRequestDate() {
		return requestDate.get();
	}

	public void setRequestDate(SimpleStringProperty requestDate) {
		this.requestDate = requestDate;
	}

	public int getParkId() {
		return parkId.get();
	}

	public void setParkId(SimpleIntegerProperty parkId) {
		this.parkId = parkId;
	}

	public String getRequestStatus() {
		return requestStatus.get();
	}

	public void setRequestStatus(SimpleStringProperty requestStatus) {
		this.requestStatus = requestStatus;
	}

}
