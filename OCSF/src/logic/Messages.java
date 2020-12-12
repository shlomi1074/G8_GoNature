package logic;


public class Messages {
	private int messageId;
	private String toId;
	private String sendDate;
	private String subject;
	private String content;
	
	
	public Messages(int messageId, String toId, String sendDate, String subject, String content) {
		super();
		this.messageId = messageId;
		this.toId = toId;
		this.sendDate = sendDate;
		this.subject = subject;
		this.content = content;
	}
	
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
