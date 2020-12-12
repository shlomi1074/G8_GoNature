package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Messages {
	private SimpleIntegerProperty messageId;
	private SimpleStringProperty toId;
	private SimpleStringProperty sendDate;
	private SimpleStringProperty subject;
	private SimpleStringProperty content;

	public Messages(SimpleIntegerProperty messageId, SimpleStringProperty toId, SimpleStringProperty sendDate,
			SimpleStringProperty subject, SimpleStringProperty content) {
		super();
		this.messageId = messageId;
		this.toId = toId;
		this.sendDate = sendDate;
		this.subject = subject;
		this.content = content;
	}

	public SimpleIntegerProperty getMessageId() {
		return messageId;
	}

	public void setMessageId(SimpleIntegerProperty messageId) {
		this.messageId = messageId;
	}

	public SimpleStringProperty getToId() {
		return toId;
	}

	public void setToId(SimpleStringProperty toId) {
		this.toId = toId;
	}

	public SimpleStringProperty getSendDate() {
		return sendDate;
	}

	public void setSendDate(SimpleStringProperty sendDate) {
		this.sendDate = sendDate;
	}

	public SimpleStringProperty getSubject() {
		return subject;
	}

	public void setSubject(SimpleStringProperty subject) {
		this.subject = subject;
	}

	public SimpleStringProperty getContent() {
		return content;
	}

	public void setContent(SimpleStringProperty content) {
		this.content = content;
	}

}
