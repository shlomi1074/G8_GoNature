package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MessagesTb {
	private SimpleIntegerProperty messageId;
	private SimpleStringProperty toId;
	private SimpleStringProperty sendDate;
	private SimpleStringProperty subject;
	private SimpleStringProperty content;

	public MessagesTb(int messageId, String toId, String sendDate,
			String subject, String content) {
		this.messageId = new SimpleIntegerProperty(messageId);
		this.toId = new SimpleStringProperty(toId);
		this.sendDate = new SimpleStringProperty(sendDate);
		this.subject =new SimpleStringProperty (subject);
		this.content =new SimpleStringProperty (content);
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

}
