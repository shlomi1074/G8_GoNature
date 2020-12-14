package Controllers.calculatePrice;

public class GuidePrePayCheckOut extends CheckOutDecorator{
	
	private final double baseDiscount = 0.75;
	private final double prePayDiscount = 0.88;
	public GuidePrePayCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	public double getPrice() {
		return regularCheckOut.getPrice() * baseDiscount * prePayDiscount;	
	}
}
