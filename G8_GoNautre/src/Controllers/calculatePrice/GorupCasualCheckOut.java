package Controllers.calculatePrice;

public class GorupCasualCheckOut extends CheckOutDecorator {

	private final double subscriberDiscount = 0.90;

	public GorupCasualCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	public double getPrice() {
		return regularCheckOut.getPrice() * subscriberDiscount;
	}
}
