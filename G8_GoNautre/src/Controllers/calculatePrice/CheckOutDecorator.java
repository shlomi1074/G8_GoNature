package Controllers.calculatePrice;

public class CheckOutDecorator implements CheckOut {

	protected CheckOut regularCheckOut;

	public CheckOutDecorator(CheckOut tempCheckOut) {
		this.regularCheckOut = tempCheckOut;
	}

	public double getPrice() {
		return regularCheckOut.getPrice();
	}

}
