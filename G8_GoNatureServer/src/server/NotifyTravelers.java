package server;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import controllers.EmailControl;
import logic.Messages;
import logic.Order;
import logic.OrderStatusName;
import sqlHandler.mysqlFunctions;

/**
 * NotifyTravelers class implements Runnable.
 * 
 * This class handle all the automated functionality: > Send reminder 24 hours
 * before visit. > Cancel the visit if the traveler did not confirmed within two
 * hours. > Notify the next in the waiting list
 *
 */
public class NotifyTravelers implements Runnable {

	private final int second = 1000;
	private final int minute = second * 60;
	private mysqlFunctions mysqlFunction;

	public NotifyTravelers(Connection mysqlconnection) {
		this.mysqlFunction = new mysqlFunctions(mysqlconnection);
	}

	@Override
	public void run() {

		while (true) {

			ArrayList<Order> orders = getRelevantOrders();

			for (Order order : orders) {
				if (isDateLessThan24Hours(order.getOrderDate(), order.getOrderTime())) {
					System.out.println(order.getOrderId());
					runNotifySent(order);
					System.out.println("IM FREE");
				}
			}

			try {
				Thread.sleep(5 * minute);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This function create thread that check if the traveler confirmed his order.
	 * if he did not confirmed his order within two hours - the order canceled
	 * automatically and we notify the first in the waiting list (if there is
	 * someone)
	 * 
	 */
	private void runNotifySent(Order order) {
		Thread notifySent = new Thread(new Runnable() {

			@Override
			public void run() {
				String status = OrderStatusName.PENDING_EMAIL_SENT.toString();
				String orderId = String.valueOf(order.getOrderId());
				mysqlFunction.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));

				sendMessages(order);

				int totalSleep = 0;
				Order updatedOrder = null;
				
				while (totalSleep != 120) {
					updatedOrder = mysqlFunction.getOrderByID(order.getOrderId());
					/* NEED TO CHECK STATUS */
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
					//NEED TO CREATE A NEW THREAD
				}

			}

		});
		notifySent.start();
	}

	private void sendMessages(Order order) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String dateAndTime = dtf.format(now);

		String date = dateAndTime.split(" ")[0];
		String time = dateAndTime.split(" ")[1];
		String travelerId = order.getTravelerId();
		int orderId = order.getOrderId();
		String subject = "Review your order: " + orderId;
		String content = "Plesae confirm your order.\n" + "You have 2 hours!";

		Messages msg = new Messages(0, travelerId, date, time, subject, content, orderId);

		/* Send email */
		EmailControl.sendEmail(msg);

		/* Add message to DB */
		ArrayList<String> parameters = new ArrayList<>(
				Arrays.asList(travelerId, date, time, subject, content, String.valueOf(orderId)));
		mysqlFunction.sendMessageToTraveler(parameters);

	}

	private ArrayList<Order> getRelevantOrders() {
		return mysqlFunction.getPendingOrders();
	}

	private boolean isDateLessThan24Hours(String date, String time) {

		String combinedVisit = date + " " + time;
		String combinedToday = LocalDate.now().toString() + " " + LocalTime.now().toString();

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date visitDate = new Date();
		Date todayDate = new Date();

		try {
			todayDate = sdfDate.parse(combinedToday);
			visitDate = sdfDate.parse(combinedVisit);
		} catch (ParseException e) {
			System.out.println("Failed to parse dates");
			e.printStackTrace();
			return false;
		}

		long diffInMills = visitDate.getTime() - todayDate.getTime();
		long diffInHour = TimeUnit.MILLISECONDS.toHours(diffInMills);

		if (diffInHour < 24 && diffInHour > 0)
			return true;
		return false;
	}

	/* Maybe in the future */
	private void sleep(int minutes) {

	}

}
