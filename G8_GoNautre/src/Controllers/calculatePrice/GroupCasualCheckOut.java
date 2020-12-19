package Controllers.calculatePrice;

public class GroupCasualCheckOut extends CheckOutDecorator {

	private final double subscriberDiscount = 0.90;

	public GroupCasualCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	public double getPrice() {
		return regularCheckOut.getPrice() * subscriberDiscount;
	}
}
