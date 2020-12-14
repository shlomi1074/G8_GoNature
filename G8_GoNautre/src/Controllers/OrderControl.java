package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import logic.Discount;
import logic.Order;
import logic.Park;
import logic.Traveler;

public class OrderControl {

	// Shlomi
	public static Discount getMaxDiscount(String visitDate, String parkId) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_MAX_DISCOUNT,
				new ArrayList<String>(Arrays.asList(visitDate, parkId)));
		ClientUI.chat.accept(request);
		Discount discount = (Discount) ChatClient.responseFromServer.getResultSet().get(0);
		return discount;
	}

	// Shlomi
	/* This function add a new order if there is enough place in the park */
	public static boolean addOrder(Order order, Traveler traveler) {
		if (isDateAvailable(order)) {
			ClientToServerRequest<Order> request = new ClientToServerRequest<>(Request.ADD_ORDER);
			request.setObj(order);
			ClientUI.chat.accept(request);
			/* Add order */
			if (ChatClient.responseFromServer.isResult()) {
				if (TravelerControl.isTravelerExist(traveler.getTravelerId())) {
					return true; // Finished add order
				} else {
					/* Add traveler*/
					ClientToServerRequest<Traveler> addTravelerRequest = new ClientToServerRequest<>(
							Request.ADD_TRAVELER);
					addTravelerRequest.setObj(traveler);
					ClientUI.chat.accept(addTravelerRequest);
					if (ChatClient.responseFromServer.isResult()) {
						return true; // Finished add order
					}
				}
			}
		} else
			return false; //Date not available
		
		return false;

	}
	
	// Shlomi
	/* This function gets the most recent order of a traveler */
	public static Order getTravelerRecentOrder(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_RECENT_ORDER,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Order order = (Order) ChatClient.responseFromServer.getResultSet().get(0);
		return order;
		
	}

	// Shlomi
	/* This function check if there is enough place for a given order */
	private static boolean isDateAvailable(Order order) {
		String parkId = String.valueOf(order.getParkId());
		Park park = ParkControl.getParkById(parkId);
		String timeToCheck = order.getOrderTime();
		int hour = Integer.parseInt(timeToCheck.split(":")[0]);
		int estimatedStayTime = park.getEstimatedStayTime();

		String visitDate = order.getOrderDate();
		String startTime = (hour - estimatedStayTime) + ":" + Integer.parseInt(timeToCheck.split(":")[1]);
		String endTime = (hour + estimatedStayTime) + ":" + Integer.parseInt(timeToCheck.split(":")[1]);

		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_ORDERS_BETWEEN_DATES,
				new ArrayList<String>(Arrays.asList(parkId, visitDate, startTime, endTime)));
		ClientUI.chat.accept(request);

		ArrayList<Order> orders = ChatClient.responseFromServer.getResultSet();

		int numberOfVisitors = 0; /* This variable holds the number of visitors on the relevant date */
		for (Order ord : orders) {
			numberOfVisitors += ord.getNumberOfParticipants();
		}// 9 | 3

		/*
		 * Check if the amount of visitors including this order exceeds the allowed
		 * number
		 */
		if (numberOfVisitors + order.getNumberOfParticipants() >= park.getMaxVisitors()
				- park.getGapBetweenMaxAndCapacity()) {

			return false;
		}

		return true;
	}

}
