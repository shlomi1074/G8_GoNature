package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.OrderTb;
import logic.ClientToServerRequest.Request;
import logic.Park;

public class ParkControl {
	
	// Shlomi
	/* Gets a park by id  */
	public static Park getParkById(String parkId) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_PARK_BY_ID,
				new ArrayList<String>(Arrays.asList(parkId)));
		ClientUI.chat.accept(request);
		Park park = (Park) ChatClient.responseFromServer.getResultSet().get(0);
		return park;
		
	}
	
	// Shlomi
	/* Gets a park by name  */
	public static Park getParkByName(String parkName) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_PARK_BY_NAME,
				new ArrayList<String>(Arrays.asList(parkName)));
		ClientUI.chat.accept(request);	
		Park park = (Park) ChatClient.responseFromServer.getResultSet().get(0);
		return park;
	}
	
	// Shlomi
	/* Gets a list of the park's names  */
	public static ArrayList<String> getParksNames() {
		ArrayList<Park> parks= getAllParks();
		ArrayList<String> parksNames = new ArrayList<String>();
		for (Park park : parks) {
			parksNames.add(park.getParkName());
		}
		return parksNames;
		
	}
	
	// Shlomi
	/* Gets a park by id  */
	public static String getParkName(String parkId) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_PARK_BY_ID,
				new ArrayList<String>(Arrays.asList(parkId)));
		ClientUI.chat.accept(request);
		Park park = (Park) ChatClient.responseFromServer.getResultSet().get(0);
		if (park != null)
			return park.getParkName();
		return "Park";
		
	}
	
	// Shlomi
	/* Gets all parks from the server */
	public static ArrayList<Park> getAllParks() {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_ALL_PARKS);
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getResultSet();
		
	}
	
	// Ofir Avraham Vaknin
	// -1 indicate an error
	public static int getParkIdByOrderId(ArrayList<OrderTb> orders,int orderId)
	{
		for(OrderTb o : orders)
			if(o.getOrderId() == orderId)
				return o.getParkId();
		return -1;
	}
}
