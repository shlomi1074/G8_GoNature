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
	/**
	 * This function gets park's id and returns Park object.
	 * 
	 * @param parkId - park's id
	 * @return Park object
	 */
	public static Park getParkById(String parkId) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_PARK_BY_ID,
				new ArrayList<String>(Arrays.asList(parkId)));
		ClientUI.chat.accept(request);
		Park park = (Park) ChatClient.responseFromServer.getResultSet().get(0);
		return park;

	}

	// Shlomi
	/**
	 * This function gets park's name and returns Park object.
	 * 
	 * @param parkName
	 * @return Park object
	 */
	public static Park getParkByName(String parkName) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_PARK_BY_NAME,
				new ArrayList<String>(Arrays.asList(parkName)));
		ClientUI.chat.accept(request);
		Park park = (Park) ChatClient.responseFromServer.getResultSet().get(0);
		return park;
	}

	// Shlomi
	/**
	 * This function returns an array list with all the park's names.
	 * 
	 * @return ArrayList<String> - all the park's names
	 */
	public static ArrayList<String> getParksNames() {
		ArrayList<Park> parks = getAllParks();
		ArrayList<String> parksNames = new ArrayList<String>();
		for (Park park : parks) {
			parksNames.add(park.getParkName());
		}
		return parksNames;

	}

	// Shlomi
	/**
	 * This function gets park's id and returns the park name.
	 * 
	 * @param parkId
	 * @return String - park name
	 */
	public static String getParkName(String parkId) {
		Park park = getParkById(parkId);
		if (park != null)
			return park.getParkName();
		return "Park";

	}

	// Shlomi
	/**
	 * This function returns an array list with all the parks in the DB.
	 * 
	 * @return ArrayList<Park>
	 */
	public static ArrayList<Park> getAllParks() {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_ALL_PARKS);
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getResultSet();

	}

	// Ofir Avraham Vaknin
	// -1 indicate an error
	public static int getParkIdByOrderId(ArrayList<OrderTb> orders, int orderId) {
		for (OrderTb o : orders)
			if (o.getOrderId() == orderId)
				return o.getParkId();
		return -1;
	}
	
	// Ofir Avraham Vaknin v2.
	// respone hold if query succeeded
	public static void updateCurrentVisitors(int pId,int num)
	{	
		String cVisitors = String.valueOf(num);
		String parkId = String.valueOf(pId);
		ClientToServerRequest<String> request = new ClientToServerRequest<String>(Request.UPDATE_CURRENT_VISITORS_ID,
				new ArrayList<String>(Arrays.asList(cVisitors,parkId)));
		ClientUI.chat.accept(request);
	}
	
	// ofir n
	
	public static void changeParkParameters(ArrayList<Integer> changedParameters) {
		
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.CHANGE_PARK_PARAMETERS,changedParameters);
		
		ClientUI.chat.accept(request);

		
	}
}
