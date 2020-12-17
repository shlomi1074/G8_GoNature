package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MessagesTb {
	private SimpleIntegerProperty messageId;
	private SimpleStringProperty toId;
	private SimpleStringProperty sendDate;
	private SimpleStringProperty sendTime;
	private SimpleStringProperty subject;
	private SimpleStringProperty content;
	private SimpleIntegerProperty orderId;

	public MessagesTb(int messageId, String toId, String sendDate, String sendTime, String subject, String content,
			int orderId) {
		this.messageId = new SimpleIntegerProperty(messageId);
		this.toId = new SimpleStringProperty(toId);
		this.sendDate = new SimpleStringProperty(sendDate);
		this.sendTime = new SimpleStringProperty(sendTime);
		this.subject = new SimpleStringProperty(subject);
		this.content = new SimpleStringProperty(content);
		this.orderId = new SimpleIntegerProperty(orderId);
	}

	public int getMessageId() {
		return messageId.get();
	}

	public void setMessageId(SimpleIntegerProperty messageId) {
		this.messageId = messageId;
	}

	public String getToId() {
		return toId.get();
	}

	public void setToId(SimpleStringProperty toId) {
		this.toId = toId;
	}

	public String getSendDate() {
		return sendDate.get();
	}

	public void setSendDate(SimpleStringProperty sendDate) {
		this.sendDate = sendDate;
	}
	
	public String getSendTime() {
		return sendTime.get();
	}

	public void setSendTime(SimpleStringProperty sendTime) {
		this.sendTime = sendTime;
	}

	public String getSubject() {
		return subject.get();
	}

	public void setSubject(SimpleStringProperty subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content.get();
	}

	public void setContent(SimpleStringProperty content) {
		this.content = content;
	}
	
	public int getOrderId() {
		return orderId.get();
	}

	public void setOrderId(SimpleIntegerProperty orderId) {
		this.orderId = orderId;
	}

}
