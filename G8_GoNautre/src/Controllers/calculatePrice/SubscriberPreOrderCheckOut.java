package Controllers.calculatePrice;

public class SubscriberPreOrderCheckOut extends CheckOutDecorator {

	private final double baseDiscount = 0.85;
	private final double discountForSubscribers = 0.8;

	public SubscriberPreOrderCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	public double getPrice() {
		return regularCheckOut.getPrice() * baseDiscount * discountForSubscribers;
	}

}
