package server.threads;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import controllers.sqlHandlers.OrderQueries;

import logic.Order;
import logic.OrderStatusName;

/**
 * updateOrderStatusVisitCompleted class implements Runnable.
 * 
 * This class updates the order's status when the traveler leaves the park
 */
public class UpdateOrderStatusVisitCompleted implements Runnable {

	private final int second = 1000;
	private final int minute = second * 60;
	private OrderQueries orderQueries;

	public UpdateOrderStatusVisitCompleted(Connection mysqlconnection) {
		orderQueries = new OrderQueries(mysqlconnection);
	}

	/**
	 * This function updates the order's status when the traveler leaves the park
	 */
	@Override
	public void run() {

		while (true) {

			ArrayList<Order> orders = getRelevantOrders();

			for (Order order : orders) {
				String status = OrderStatusName.COMPLETED.toString();
				String orderId = String.valueOf(order.getOrderId());
				orderQueries.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));
			}

			try {
				Thread.sleep(1 * minute);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private ArrayList<Order> getRelevantOrders() {
		return orderQueries.getCompletedOrders();
	}

}
