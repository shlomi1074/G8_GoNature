package Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import resources.MsgTemplates;
import logic.Discount;
import logic.Messages;
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

	/**
	 * This function gets a traveler and his order. The function adds the order to
	 * DB if the date is available. And also notify the traveler via email, and
	 * message in client
	 * 
	 * @param order
	 * @param traveler
	 * @return Order object - the order that was inserted to the database
	 */
	public static Order addOrderAndNotify(Order order, Traveler traveler) {
		Order recentOrder = null;
		if (OrderControl.addOrder(order, traveler)) {
			recentOrder = OrderControl.getTravelerRecentOrder(traveler.getTravelerId());
			
			/* Insert massage to data base */
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String dateAndTime = dtf.format(now);
			String date = dateAndTime.split(" ")[0];
			String time = dateAndTime.split(" ")[1];
			if (recentOrder != null) { // Insert email to the database
				/* Create message content */
				String emailContent = String.format(MsgTemplates.orderConfirmation[1].toString(),
						String.valueOf(recentOrder.getOrderId()),
						ParkControl.getParkById(String.valueOf(recentOrder.getParkId())), recentOrder.getOrderDate(),
						recentOrder.getOrderTime(), recentOrder.getOrderType(),
						String.valueOf(recentOrder.getNumberOfParticipants()), String.valueOf(recentOrder.getPrice()));

				/* Add message to data base */
				NotificationControl.sendMessageToTraveler(traveler.getTravelerId(), date, time,
						MsgTemplates.orderConfirmation[0], emailContent, String.valueOf(recentOrder.getOrderId()));

				/* Send message by mail */
				Messages msg = new Messages(0, traveler.getTravelerId(), date, time, MsgTemplates.orderConfirmation[0],
						emailContent, recentOrder.getOrderId());
				NotificationControl.sendMailInBackgeound(msg, null);
			}

		}
		return recentOrder;
	}

	// Shlomi
	/**
	 * This function gets a traveler and his order. The function adds the order to
	 * DB if the date is available.
	 * 
	 * @param order    - Order object
	 * @param traveler - Traveler object
	 * @return true on success, false otherwise
	 */
	public static boolean addOrder(Order order, Traveler traveler) {
		if ((order.getOrderStatus().equals(OrderStatusName.PENDING.toString()) && isDateAvailable(order))
				|| order.getOrderStatus().equals(OrderStatusName.WAITING.toString())) {
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
	/**
	 * This function gets an id of a traveler and return his most recent order.
	 * 
	 * @param travelerId - the traveler id
	 * @return Order object - the traveler most recent order
	 */
	public static Order getTravelerRecentOrder(String travelerId) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_RECENT_ORDER,
				new ArrayList<String>(Arrays.asList(travelerId)));
		ClientUI.chat.accept(request);
		Order order = (Order) ChatClient.responseFromServer.getResultSet().get(0);
		return order;

	}

	// Shlomi
	/**
	 * This function gets an order and check if the order's date and time is
	 * available
	 * 
	 * @param order - the order to check
	 * @return true if available, false otherwise
	 */
	public static boolean isDateAvailable(Order order) {
		String parkId = String.valueOf(order.getParkId());
		Park park = ParkControl.getParkById(parkId);
		String timeToCheck = order.getOrderTime();
		int hour = Integer.parseInt(timeToCheck.split(":")[0]);
		int estimatedStayTime = park.getEstimatedStayTime();

		String visitDate = order.getOrderDate();
		String startTime = (hour - estimatedStayTime) + ":00";
		String endTime = (hour + estimatedStayTime) + ":00";

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

	// Ofir Avraham Vaknin v2.
	public static ArrayList<Order> getAllOrders() {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_ALL_ORDERS);
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

	
	// Ofir + Shlomi Edit
	public static boolean changeOrderStatus(String orderId, OrderStatusName statusName) {
		String status = statusName.toString();
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.CHANGE_ORDER_STATUS_BY_ID,
				new ArrayList<String>(Arrays.asList(status, orderId)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isResult();
	}

	// Ofir Avraham Vaknin v3.
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

		// No one is waiting in the waiting list
		if (ordersThatMatchWaitingList.size() == 0)
			return;

		// Send message to client - Function return boolean
		Order o = ordersThatMatchWaitingList.get(0);
		if (o.equals(null))
			return;
		String content = "A place for order number " + o.getOrderId()
				+ " has cleared for you, you have one hour untill " + "it moves to the next person";
		NotificationControl.sendMessageToTraveler(o.getTravelerId(), LocalDate.now().toString(),
				LocalTime.now().toString(), "Grab Your waiting list spot", content, String.valueOf(o.getOrderId()));

	}

	// Ofir Avraham Vaknin v3.
	public static boolean addVisit(OrderTb order) {
		String travelerId = order.getTravelerId();
		String parkId = String.valueOf(order.getParkId());
		String date = order.getOrderDate();
		String enterTime = order.getOrderTime();

		Park park = ParkControl.getParkById(parkId);
		String estimated = String.valueOf(park.getEstimatedStayTime());

		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.ADD_VISIT,
				new ArrayList<String>(Arrays.asList(travelerId, parkId, enterTime, estimated, date)));
		ClientUI.chat.accept(request);

		return ChatClient.responseFromServer.isResult();
	}

	// Ofir Avrahm Vaknin v3.
	public static boolean addCasualOrder(Order order) {
		ClientToServerRequest<Order> request = new ClientToServerRequest<>(Request.ADD_CASUAL_ORDER);
		request.setObj(order);
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isResult();
	}

	// Ofir Avraham Vaknin v3.
	public static ArrayList<Order> getAllOrdersForParkId(int parkId) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_ALL_ORDERS_FOR_PARK,
				new ArrayList<String>(Arrays.asList(String.valueOf(parkId))));
		ClientUI.chat.accept(request);
		ArrayList<Order> orders = ChatClient.responseFromServer.getResultSet();
		return ChatClient.responseFromServer.getResultSet();
	}

	// Ofir Avraham Vaknin v3.
	public static ArrayList<Order> getOrdersForTravelerInPark(String parkId, String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(
				Request.GET_ALL_ORDERS_FOR_PARK_WITH_TRAVLER, new ArrayList<String>(Arrays.asList(parkId, id)));
		ClientUI.chat.accept(request);
		ArrayList<Order> orders = ChatClient.responseFromServer.getResultSet();
		return ChatClient.responseFromServer.getResultSet();
	}

}
