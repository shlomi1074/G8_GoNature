package logic;

public class Creditcard {
	private String subscriberId;
	private String cardNumber;
	private String cardExpiryDate;
	private String cvc;

	public Creditcard(String subscriberId, String cardNumber, String cardExpiryDate, String cvc) {
		this.subscriberId = subscriberId;
		this.cardNumber = cardNumber;
		this.cardExpiryDate = cardExpiryDate;
		this.cvc = cvc;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

}
