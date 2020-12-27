package controllers.sqlHandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import logic.Order;
import logic.OrderStatusName;

/**
 * This class handles all the queries which are related to orders
 * 
 */
public class OrderQueries {
	private Connection conn;

	public OrderQueries(Connection conn) {
		this.conn = conn;
	}
	
	// shlomi + Ofir edit
	public ArrayList<Order> getOrderBetweenTimes(ArrayList<?> parameters) {
		ArrayList<Order> orders = new ArrayList<Order>();

		// String sql = "SELECT * FROM g8gonature.order WHERE parkId = ? and orderDate =
		// ? and orderTime >= ? and orderTime <= ? AND orderStatus != ?";
		String sql = "SELECT * FROM g8gonature.order WHERE parkId = ? and orderDate = ? and orderTime >= ? and orderTime <= ? AND (orderStatus = ? OR orderStatus = ?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt((String) parameters.get(0)));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, (String) parameters.get(3));
			// query.setString(5, OrderStatusName.CANCELED.toString());
			query.setString(5, OrderStatusName.PENDING.toString());
			query.setString(6, OrderStatusName.CONFIRMED.toString());
			ResultSet res = query.executeQuery();

			while (res.next())
				orders.add(new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getString(5),
						res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9), res.getString(10)));
		} catch (SQLException e) {
			System.out.println("Could not execute getOrderBetweenTimes query");
			e.printStackTrace();
		}

		return orders;
	}

	// shlomi
	public boolean addNewOrder(Object obj) {
		Order orderToAdd = (Order) obj;
		int result = 0;
		String sql = "INSERT INTO g8gonature.order (travelerId, parkId, orderDate, orderTime, orderType, numberOfParticipants, email, price, orderStatus) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, orderToAdd.getTravelerId());
			query.setInt(2, orderToAdd.getParkId());
			query.setString(3, orderToAdd.getOrderDate());
			query.setString(4, orderToAdd.getOrderTime());
			query.setString(5, orderToAdd.getOrderType());
			query.setInt(6, orderToAdd.getNumberOfParticipants());
			query.setString(7, orderToAdd.getEmail());
			query.setDouble(8, orderToAdd.getPrice());
			query.setString(9, orderToAdd.getOrderStatus());
			result = query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute AddNewOrder query");
			e.printStackTrace();
		}
		return result > 0;
	}
	
	// shlomi
	public Order getRecentOrder(ArrayList<?> parameters) {
		Order order = null;

		String sql = "SELECT * FROM g8gonature.order WHERE travelerId = ? ORDER BY orderId DESC";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();
			if (res.next())
				order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getString(5),
						res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9), res.getString(10));
		} catch (SQLException e) {
			System.out.println("Could not execute getRecentOrder query");
			e.printStackTrace();
		}
		return order;
	}
	
	// Ofir Avraham Vaknin v4.
	public ArrayList<Order> findMatchingOrdersInWaitingList(ArrayList<?> parameters) {
		// Need to send gap
		ArrayList<Order> resultArray = new ArrayList<Order>();

		String parkId = (String) parameters.get(0);
		String maxVisitors = (String) parameters.get(1);
		String estimatedStayTime = (String) parameters.get(2);
		String date = (String) parameters.get(3);
		String timeToCheck = (String) parameters.get(4);
		String gap = (String) parameters.get(5);

		int estimated = Integer.parseInt(estimatedStayTime);
		int maxVisitor = Integer.parseInt(maxVisitors);
		int gapInPark = Integer.parseInt(gap);

		int maxAllowedInPark = maxVisitor - gapInPark;

		int hour = Integer.parseInt(timeToCheck.split(":")[0]);

		String hourAfterEstimated = (hour + estimated) + ":00";
		String hourBeforeEstimated = (hour - estimated) + ":00";

		ArrayList<String> par = new ArrayList<String>(
				Arrays.asList(parkId, date, hourBeforeEstimated, hourAfterEstimated));
		ArrayList<Order> resultOrders = getOrderBetweenTimes(par);
		int count = 0;
		for (Order o : resultOrders)
			count += o.getNumberOfParticipants();

		String sql = "SELECT * FROM g8gonature.order WHERE parkId = ? AND "
				+ "orderDate = ? AND orderTime BETWEEN ? AND ? AND orderStatus = ?";
		ResultSet rs;
		PreparedStatement query;
		System.out.println("parkId = " + parkId);
		System.out.println("date = " + date);
		System.out.println("hourBeforeEstimated = " + hourBeforeEstimated);
		System.out.println("hourAfterEstimated = " + hourAfterEstimated);

		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt(parkId));
			query.setString(2, date);
			query.setString(3, hourBeforeEstimated);
			query.setString(4, hourAfterEstimated);
			query.setString(5, OrderStatusName.WAITING.toString());
			rs = query.executeQuery();
			while (rs.next()) {
				System.out.println("Number of participants = " + rs.getInt(7));
				System.err.println("Count = " + count);
				if (rs.getInt(7) + count <= maxAllowedInPark) {
					Order order = new Order(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getDouble(9),
							rs.getString(10));
					resultArray.add(order);
					System.out.println("OrderID" + rs.getInt(1));
				}
			}

		} catch (SQLException e) {
			System.out.println("Could not execute findMatchingOrdersInWaitingList");
			e.printStackTrace();
		}

		return resultArray;
	}
	
	// Ofir Avraham Vaknin v2.
	public ArrayList<Order> getOrdersForPark(ArrayList<?> parameters) {
		int parkId = Integer.parseInt((String) parameters.get(0));
		ArrayList<Order> orders = new ArrayList<Order>();

		String sql = "SELECT * FROM g8gonature.order WHERE parkId = ? ORDER BY orderId DESC";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, parkId);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				Order order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10));
				orders.add(order);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute getOrdersForPark query");
			e.printStackTrace();
		}
		return orders;

	}

	// Ofir Avraham Vaknin v2.
	public ArrayList<Order> getOrderForTravelerInPark(ArrayList<?> parameters) {
		int parkId = Integer.parseInt((String) parameters.get(0));
		String travId = (String) parameters.get(1);
		ArrayList<Order> orders = new ArrayList<Order>();

		String sql = "SELECT * FROM g8gonature.order WHERE parkId = ? AND travelerId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, parkId);
			query.setString(2, travId);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				Order order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10));
				orders.add(order);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute getOrderForTravelerInPark query");
			e.printStackTrace();
		}
		return orders;
	}
	

	public ArrayList<Order> getPendingOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM g8gonature.order WHERE orderStatus = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, OrderStatusName.PENDING.toString());
			ResultSet res = query.executeQuery();
			while (res.next()) {
				Order order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10));
				orders.add(order);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute getAllOrdersForId");
			e.printStackTrace();
		}
		return orders;
	}

	public Order getOrderByID(int orderId) {
		Order order = null;
		String sql = "SELECT * FROM g8gonature.order WHERE orderId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, orderId);
			ResultSet res = query.executeQuery();
			if (res.next()) {
				order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getString(5),
						res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9), res.getString(10));
			}

		} catch (SQLException e) {
			System.out.println("Could not execute getAllOrdersForId");
			e.printStackTrace();
		}
		return order;

	}
	
	// shlomi
	public ArrayList<Order> getRelevantOrderForParkEntrance(ArrayList<?> parameters) {		
		ArrayList<Order> orders = new ArrayList<>();
		String sql = "SELECT * FROM g8gonature.order where travelerId = ? AND orderDate = ? AND orderTime >= ? AND orderTime <= ? AND orderStatus = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, (String) parameters.get(3));
			query.setString(5, OrderStatusName.CONFIRMED.toString());
			ResultSet res = query.executeQuery();

			if (res.next()) {
				orders.add(new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getString(5),
						res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9), res.getString(10)));
			}
			else {
				orders.add(null);
			}
		} catch (SQLException e) {
			System.out.println("Could not execute getRelevantOrderForParkEntrance query");
			e.printStackTrace();
		}

		return orders;
	}

	// shlomi
	public ArrayList<Order> getRelevantOrderForParkExit(ArrayList<?> parameters) {
		ArrayList<Order> orders = new ArrayList<>();
		String sql = "SELECT * FROM g8gonature.order where travelerId = ? AND orderStatus = ? order by orderId DESC";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt((String) parameters.get(0)));
			query.setString(2, OrderStatusName.COMPLETED.toString());
			ResultSet res = query.executeQuery();

			if (res.next()) {
				orders.add(new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getString(5),
						res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9), res.getString(10)));
			}
			else {
				orders.add(null);
			}
		} catch (SQLException e) {
			System.out.println("Could not execute getRelevantOrderForParkExit query");
			e.printStackTrace();
		}

		return orders;
	}
	
	// Ofir Avraham Vaknin
	public ArrayList<Order> getAllOrdersForID(ArrayList<?> parameters) {
		ArrayList<Order> orders = new ArrayList<Order>();

		String sql = "SELECT * FROM g8gonature.order WHERE travelerId = ? ORDER BY orderId DESC";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt((String) parameters.get(0))); // was setString
			ResultSet res = query.executeQuery();
			while (res.next()) {
				Order order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10));
				orders.add(order);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute getAllOrdersForID");
			e.printStackTrace();
		}
		return orders;
	}
	
	// Ofir Avraham Vaknin
	public ArrayList<Order> getAllOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();

		String sql = "SELECT * FROM g8gonature.order";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				Order order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10));
				orders.add(order);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute getAllOrdersForId");
			e.printStackTrace();
		}
		return orders;
	}
	

	// Ofir Avraham Vaknin
	public boolean setOrderStatusWithIDandStatus(ArrayList<?> parameters) {
		String sql = "UPDATE g8gonature.order SET orderStatus = ? WHERE orderId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setInt(2, Integer.parseInt((String) parameters.get(1)));
			int res = query.executeUpdate();
			return res == 1;
		} catch (SQLException e) {
			System.out.println("Could not execute setOrderStatusWithIDandStatus");
			e.printStackTrace();
		}
		return false;
	}
	

	// Ofir Avraham Vaknin
	private int numberOfParticipantsBetweenTwoHours(String parkId, String date, String hourBefore, String hour) {
		String sql = "SELECT numberOfParticipants FROM g8gonature.order WHERE parkId = ? AND "
				+ "orderDate = ? AND orderTime BETWEEN ? AND ? AND orderStatus = ?";
		PreparedStatement query;
		ResultSet rs;
		int sum = 0;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, parkId);
			query.setString(2, date);
			query.setString(3, hourBefore);
			query.setString(4, hour);
			query.setString(5, OrderStatusName.WAITING.toString());
			rs = query.executeQuery();
			while (rs.next()) {
				sum += rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Could not execute numberOfParticipantsBetweenTwoHours");
			e.printStackTrace();
		}
		return sum;
	}
	
	/*
	 * Ofir Avraham Vaknin
	 * Orginzed Code
	 * Update the number of visitors for order.
	 */
	public boolean UpdateNumberOfVisitorsForOrder(ArrayList<?> parameters) {

		String sql = "UPDATE g8gonature.order SET numberOfParticipants = ? WHERE orderId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt((String) parameters.get(0)));
			query.setInt(2, Integer.parseInt((String) parameters.get(1)));
			int res = query.executeUpdate();
			return res == 1;
		} catch (SQLException e) {
			System.out.println("Could not execute UpdateNumberOfVisitorsForOrder query");
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * Ofir Avraham Vaknin
	 * Orginzed Code
	 * Update the price for order.
	 */
	public boolean UpdatePriceForOrder(ArrayList<?> parameters) {
		
		String sql = "UPDATE g8gonature.order SET price = ? WHERE orderId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setDouble(1, Double.parseDouble((String) parameters.get(0)));
			query.setInt(2, Integer.parseInt((String) parameters.get(1)));
			int res = query.executeUpdate();
			return res == 1;
		} catch (SQLException e) {
			System.out.println("Could not execute UpdatePriceForOrder query");
			e.printStackTrace();
		}
		return false;
	}
}
