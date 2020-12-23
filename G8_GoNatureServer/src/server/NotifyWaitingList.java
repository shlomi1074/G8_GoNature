package server;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import controllers.EmailControl;
import logic.Messages;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;
import sqlHandler.mysqlConnection;
import sqlHandler.mysqlFunctions;

public class NotifyWaitingList implements Runnable {

	private String date, hour;
	private Park park;
	private Connection mysqlconnection;
	private mysqlFunctions mysqlFunction;
	private Order order;

	// Can go to constants
	private final int second = 1000;
	private final int minute = second * 60;

	public NotifyWaitingList(Order order) {
		try {
			mysqlconnection = mysqlConnection.getInstance().getConnection();
			mysqlFunction = new mysqlFunctions(mysqlconnection);
			this.date = order.getOrderDate();
			this.hour = order.getOrderTime();
			ArrayList<String> parameters = new ArrayList<>(Arrays.asList(String.valueOf(order.getParkId())));
			this.park = mysqlFunction.getParkById(parameters);
			this.order = order;
		} catch (Exception e) {
			System.out.println("Exception was thrown - notify waiting list");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Order order = notifyPersonFromWaitingList(date, hour, park);

		System.out.println("Entered notify waiting list");
		if (order == null) {
			System.out.println("Null");
			return;
		}

		String orderId = String.valueOf(order.getOrderId());
		String status = OrderStatusName.WAITING_HAS_SPOT.toString();
		mysqlFunction.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));
		sendMessages(order);

		int totalSleep = 0;
		Order updatedOrder = null;

		while (totalSleep != 60) {
			System.out.println("Entred while");
			updatedOrder = mysqlFunction.getOrderByID(order.getOrderId());
			if (updatedOrder.getOrderStatus().equals(OrderStatusName.CANCELED.toString())
					|| updatedOrder.getOrderStatus().equals(OrderStatusName.CONFIRMED.toString()))
				break;
			try {
				Thread.sleep(2 * minute);
				totalSleep += 2;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (!updatedOrder.getOrderStatus().equals(OrderStatusName.CONFIRMED.toString())) {
			status = OrderStatusName.CANCELED.toString();
			orderId = String.valueOf(updatedOrder.getOrderId());
			mysqlFunction.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));

			// Passing the orignal order that was canceled.
			NotifyWaitingList notifyWaitingList = new NotifyWaitingList(this.order);
			new Thread(notifyWaitingList).start();
		}

	}

	// Email + DB
	private void sendMessages(Order order) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String dateAndTime = dtf.format(now);

		String date = dateAndTime.split(" ")[0];
		String time = dateAndTime.split(" ")[1];
		String travelerId = order.getTravelerId();
		int orderId = order.getOrderId();
		String subject = "A spot availabe for order: " + orderId;
		String content = "A spot is available for you.\n" + "You have 1 hour!";

		Messages msg = new Messages(0, travelerId, date, time, subject, content, orderId);

		/* Send email */
		EmailControl.sendEmail(msg);

		/* Add message to DB */
		ArrayList<String> parameters = new ArrayList<>(
				Arrays.asList(travelerId, date, time, subject, content, String.valueOf(orderId)));
		mysqlFunction.sendMessageToTraveler(parameters);

	}

	public Order notifyPersonFromWaitingList(String date, String hour, Park park) {
		String parkId = String.valueOf(park.getParkId());
		String maxVisitors = String.valueOf(park.getMaxVisitors());
		String estimatedStayTime = String.valueOf(park.getEstimatedStayTime());
		String gap = String.valueOf(park.getGapBetweenMaxAndCapacity());
		ArrayList<String> parameters = new ArrayList<String>(
				Arrays.asList(parkId, maxVisitors, estimatedStayTime, date, hour, gap));
		ArrayList<Order> ordersThatMatchWaitingList = mysqlFunction.findMatchingOrdersInWaitingList(parameters);
		return ordersThatMatchWaitingList.size() == 0 ? null : ordersThatMatchWaitingList.get(0);
	}

}