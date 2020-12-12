package Controllers.calculatePrice;

import Controllers.OrderControl;
import logic.Discount;
import logic.DiscountTb;

public class CheckOutDecorator implements CheckOut {

	private int numberOfVisitors;
	private int parkId;
	private String visitDate;

	private double fullPrice = 100;

	public CheckOutDecorator(int numberOfVisitors, int parkId, String visitDate) {
		this.numberOfVisitors = numberOfVisitors;
		this.parkId = parkId;
		this.visitDate = visitDate;
	}

	@Override
	public double getPrice() {
		double discountPercentage;
		Discount maxDiscount = getMaxDiscount();
		if (maxDiscount == null)
			discountPercentage = 0;
		else 
			discountPercentage = maxDiscount.getAmount();
		double basePrice = (this.numberOfVisitors * this.fullPrice);
		double priceAfterParkDiscount = basePrice * ((100-discountPercentage) / 100);
		return  priceAfterParkDiscount;
	}

	private Discount getMaxDiscount() {
		Discount maxDiscount = OrderControl.getMaxDiscount(visitDate, String.valueOf(parkId));
		return maxDiscount;
	}

}
