package logic;

public enum OrderStatusName {
	// Ofir Avraham Vaknin v2.
	confirmed, cancel, pending, waiting, completed;

	@Override
	public String toString() {
		switch (this) {
		case confirmed:
			return "Confirmed";
		case cancel:
			return "Canceled";
		case pending:
			return "Pending";
		case waiting:
			return "Waiting";
		case completed:
			return "Completed";
		default:
			throw new IllegalArgumentException();
		}
	}
}