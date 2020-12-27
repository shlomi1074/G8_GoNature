package Controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.OrderTb;
import logic.ClientToServerRequest.Request;
import logic.Park;

public class ParkControl {

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
	public static void updateCurrentVisitors(int pId, int num) {
		String cVisitors = String.valueOf(num);
		String parkId = String.valueOf(pId);
		ClientToServerRequest<String> request = new ClientToServerRequest<String>(Request.UPDATE_CURRENT_VISITORS_ID,
				new ArrayList<String>(Arrays.asList(cVisitors, parkId)));
		ClientUI.chat.accept(request);
	}

	// ofir n

	public static void changeParkParameters(ArrayList<String> changeParkParameterList) {

		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.CHANGE_PARK_PARAMETERS,
				changeParkParameterList);

		ClientUI.chat.accept(request);

	}

	/**
	 * This function get a date and a park id and check if the park was full at that date.
	 * 
	 * @param date The date to check
	 * @param parkID The park to check
	 * @return ArrayList of Strings with all the comments on that dates.
	 */
	public static ArrayList<String> isParkIsFullAtDate(String date, String parkID) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.CHECK_IF_PARK_FULL_AT_DATE,
				new ArrayList<String>(Arrays.asList(date, parkID)));
		ClientUI.chat.accept(request);
		return (ArrayList<String>) ChatClient.responseFromServer.getResultSet();
	}

	/**
	 * This function insert into "fullparkdate" table when the park is full.
	 * 
	 * @param park The park that is full
	 */
	public static void updateIfParkFull(Park park) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);
        String date = dateAndTime.split(" ")[0];
        String time = dateAndTime.split(" ")[1];
		
	
		String comment = "Full at: "+time;
		
		String parkId = String.valueOf(park.getParkId());
		String maxVisitors = String.valueOf(park.getMaxVisitors());
		
		if (park.getCurrentVisitors() >= park.getMaxVisitors()) {
			ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.INSERT_TO_FULL_PARK_DATE,
					new ArrayList<String>(Arrays.asList(parkId,date,maxVisitors,comment)));
			ClientUI.chat.accept(request);
		}
	}
}
