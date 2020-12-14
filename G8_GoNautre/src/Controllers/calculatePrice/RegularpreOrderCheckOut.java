package Controllers.calculatePrice;

public class RegularpreOrderCheckOut extends CheckOutDecorator {

	private final double baseDiscount = 0.85;

	public RegularpreOrderCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	public double getPrice() {
		return regularCheckOut.getPrice() * baseDiscount;
	}
}
