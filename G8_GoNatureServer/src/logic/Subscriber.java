package logic;

public class Subscriber extends Traveler {
	private String subscriberNumber;
	private String creditCard;
	private String subscriberType;
	private int numberOfParticipants;

	public Subscriber(String travelerId, String firstName, String lastName, String email, String phoneNumber,
			String subscriberNumber, String creditCard, String subscriberType, int numberOfParticipants) {
		super(travelerId, firstName, lastName, email, phoneNumber);
		this.subscriberNumber = subscriberNumber;
		this.creditCard = creditCard;
		this.subscriberType = subscriberType;
		this.numberOfParticipants = numberOfParticipants;
	}

	public String getSubscriberNumber() {
		return subscriberNumber;
	}

	public void setSubscriberNumber(String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}

	public int getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

}
