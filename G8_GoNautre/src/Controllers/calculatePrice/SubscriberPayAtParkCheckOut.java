package Controllers.calculatePrice;

public class SubscriberPayAtParkCheckOut extends CheckOutDecorator {

	private final double subscriberDiscount = 0.80;

	public SubscriberPayAtParkCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	public double getPrice() {
		return regularCheckOut.getPrice() * subscriberDiscount;
	}
}
