package Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import logic.Discount;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderTb;
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
		if ((order.getOrderStatus().equals(OrderStatusName.pending.name()) && isDateAvailable(order))
				|| order.getOrderStatus().equals(OrderStatusName.waiting.name())) {
			ClientToServerRequest<Order> request = new ClientToServerRequest<>(Request.ADD_ORDER);
			request.setObj(order);
			ClientUI.chat.accept(request);
			/* Add order */
			if (ChatClient.responseFromServer.isResult()) {
				if (TravelerControl.isTravelerExist(traveler.getTravelerId())) {
					return true; // Finished add order
				} else {
					/* Add traveler */
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
			return false; // Date not available

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
	public static boolean isDateAvailable(Order order) {
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
		} 

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

	/*
	 * Ofir Avraham Vaknin
	 */
	/**
	 * This function return all of the orders that a certion ID has.
	 * 
	 * @param id
	 */
	public static ArrayList<Order> getOrders(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_ALL_ORDER_FOR_ID,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		ArrayList<Order> orders = ChatClient.responseFromServer.getResultSet();
		return ChatClient.responseFromServer.getResultSet();
	}

	// Ofir Avraham Vaknin
	public static ArrayList<Order> getAllOrders() {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_ALL_ORDERS,
				new ArrayList<String>());
		ClientUI.chat.accept(request);
		ArrayList<Order> orders = ChatClient.responseFromServer.getResultSet();
		return ChatClient.responseFromServer.getResultSet();
	}

	// Ofir Avraham Vaknin
	public static ArrayList<OrderTb> convertOrderToOrderTb(ArrayList<Order> ordersArrayList) {
		ArrayList<OrderTb> orderTbArrayList = new ArrayList<OrderTb>();
		for (Order order : ordersArrayList) {
			OrderTb orderTb = new OrderTb(order);
			orderTbArrayList.add(orderTb);
		}
		return orderTbArrayList;
	}

	// Ofir Avraham Vaknin
	public static boolean changeOrderStatus(String orderId, OrderStatusName statusName) {
		String status = statusName.name();
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.CHANGE_ORDER_STATUS_BY_ID,
				new ArrayList<String>(Arrays.asList(status, orderId)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isResult();
	}

	// Ofir Avraham Vaknin
	// Create message for him
	public static void notifyPersonFromWaitingList(String date, String hour, Park park) {
		String parkId = String.valueOf(park.getParkId());
		String maxVisitors = String.valueOf(park.getMaxVisitors());
		String estimatedStayTime = String.valueOf(park.getEstimatedStayTime());
		String gap = String.valueOf(park.getGapBetweenMaxAndCapacity());
		ClientToServerRequest<String> request = new ClientToServerRequest<>(
				Request.GET_ORDERS_THAT_MATCH_AFTER_ORDER_CANCEL,
				new ArrayList<String>(Arrays.asList(parkId, maxVisitors, estimatedStayTime, date, hour, gap)));
		ClientUI.chat.accept(request);
		ArrayList<Order> ordersThatMatchWaitingList = ChatClient.responseFromServer.getResultSet();

		// Send message to client - Function return boolean
		Order o = ordersThatMatchWaitingList.get(0);
		if (o.equals(null))
			return;
		String content = "A place for order number " + o.getOrderId()
				+ " has cleared for you, you have one hour untill " + "it moves to the next person";
		NotificationControl.sendMessageToTraveler(o.getTravelerId(), LocalDate.now().toString(),
				LocalTime.now().toString(), "Grab Your waiting list spot", content, String.valueOf(o.getOrderId()));

	}

}
