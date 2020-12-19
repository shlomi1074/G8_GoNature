package Controllers.calculatePrice;

public class RegularPreOrderCheckOut extends CheckOutDecorator {

	private final double baseDiscount = 0.85;

	public RegularPreOrderCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	public double getPrice() {
		return regularCheckOut.getPrice() * baseDiscount;
	}
}
