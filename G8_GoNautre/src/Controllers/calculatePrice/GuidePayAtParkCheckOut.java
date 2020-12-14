package Controllers.calculatePrice;

public class GuidePayAtParkCheckOut extends CheckOutDecorator {

	private final double discountForGuidesPayAtPark = 0.75;

	public GuidePayAtParkCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	public double getPrice() {
		return regularCheckOut.getPrice() * discountForGuidesPayAtPark;
	}

}
