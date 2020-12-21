package logic;

public enum OrderStatusName {
	// Ofir Avraham Vaknin v2.
	CONFIRMED, CANCELED, PENDING, PENDING_EMAIL_SENT, WAITING, WAITING_HAS_SPOT, COMPLETED;

	@Override
	public String toString() {
		switch (this) {
		case CONFIRMED:
			return "Confirmed";
		case CANCELED:
			return "Canceled";
		case PENDING:
			return "Pending";
		case WAITING:
			return "Waiting";
		case COMPLETED:
			return "Completed";
		case PENDING_EMAIL_SENT:
			return "Pending email sent";
		case WAITING_HAS_SPOT:
			return "Waiting has spot";
		default:
			throw new IllegalArgumentException();
		}
	}
}