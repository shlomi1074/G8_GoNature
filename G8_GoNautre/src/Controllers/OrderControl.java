package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import logic.Discount;

public class OrderControl {
	
	public static Discount getMaxDiscount(String visitDate, String parkId) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_MAX_DISCOUNT,
				new ArrayList<String>(Arrays.asList(visitDate, parkId)));
		ClientUI.chat.accept(request);
		Discount discount = (Discount)ChatClient.responseFromServer.getResultSet().get(0);
		return discount;
	}

}
