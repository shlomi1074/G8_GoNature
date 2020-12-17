package resources;

public class MsgTemplates {
	
	public static String [] orderConfirmation = {"Thank you for your order!", "Thanks you for your order!\n"
			+ "We will send you a reminder one day before your visit.\n"
			+ "You need to confirm your order when you receive the reminder.\n\n"
			+ "Your order information:\n"
			+ "Order id: %s.\n"
			+ "Park: %s.\n"
			+ "Date: %s.\n"
			+ "Time: %s.\n"
			+ "Type: %s.\n"
			+ "Visitors: %s.\n"
			+ "Total price: %s.\n\n"
			+ "We will see you at the park,\n"
			+ "GoNature8 Family.\n\n"};



	public static String[] enterToWaitingList = { "Enter waiting list", "You are now in the waiting list\n"
			+ "We will send you an email if someone will cancel their visit\n" 
			+ "Order info:\n"
			+ "Park: %s\n"
			+ "Visit date: %s\n"
			+ "Visit time %s\n"
			+ "\n"
			+ "Thank you,\n"
			+ "GoNature8 Family" };
	
	public static String[] errorWaitingList = { "Error Waiting Lis", "There was error trying to put you in the waiting list.\n" 
	+ "Please try again later."
 };

}
