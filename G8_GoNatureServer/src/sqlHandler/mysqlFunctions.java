package sqlHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import logic.Discount;
import logic.DiscountTb;
import logic.Order;
import logic.Park;
import logic.Subscriber;
import logic.Traveler;

public class mysqlFunctions {

	private Connection conn;

	public mysqlFunctions(Connection conn) {
		this.conn = conn;
	}

	/*
	 * Handles the connection to the DB
	 */

	public boolean checkIfConnected(ArrayList<?> parameters) {
		String sql = "SELECT * FROM g8gonature.loggedin WHERE id = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));

			ResultSet res = query.executeQuery();
			if (res.next())
				return true;
		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

		return false;
	}

	public Traveler isTravelerExist(ArrayList<?> parameters) {
		Traveler traveler = null;
		String sql = "SELECT * FROM g8gonature.traveler WHERE travelerId = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();

			if (res.next())
				traveler = new Traveler(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5));
		} catch (SQLException e) {
			System.out.println("Could not execute isTravelerExist query");
			e.printStackTrace();
		}

		return traveler;
	}

	public void insertToLoggedInTable(ArrayList<?> parameters) {
		String sql = "INSERT INTO g8gonature.loggedin (id) values (?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute insertToLoggedInTable query");
			e.printStackTrace();
		}
	}

	public Subscriber getSubscriberBySubId(ArrayList<?> parameters) {
		Subscriber sub = null;
		String sql = "SELECT * FROM g8gonature.subscriber WHERE subscriberNumber = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();

			if (res.next())
				sub = new Subscriber(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getInt(9));
		} catch (SQLException e) {
			System.out.println("Could not execute getSubscriberBySubId query");
			e.printStackTrace();
		}

		return sub;

	}

	// Shlomi
	public Park getParkById(ArrayList<?> parameters) {
		Park park = null;
		String sql = "SELECT * FROM g8gonature.park WHERE parkId = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt((String) parameters.get(0)));
			ResultSet res = query.executeQuery();

			if (res.next())
				park = new Park(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4), res.getInt(5),
						res.getInt(6));
		} catch (SQLException e) {
			System.out.println("Could not execute getParkById query");
			e.printStackTrace();
		}

		return park;
	}

	// Shlomi
	public Park getParkByName(ArrayList<?> parameters) {
		Park park = null;
		String sql = "SELECT * FROM g8gonature.park WHERE parkName = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();

			if (res.next())
				park = new Park(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4), res.getInt(5),
						res.getInt(6));
		} catch (SQLException e) {
			System.out.println("Could not execute getParkByName query");
			e.printStackTrace();
		}

		return park;
	}

	// Shlomi
	public Subscriber getSubscriberById(ArrayList<?> parameters) {
		Subscriber sub = null;
		String sql = "SELECT * FROM g8gonature.subscriber WHERE travelerId = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();

			if (res.next())
				sub = new Subscriber(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getInt(9));
		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

		return sub;

	}

	// shlomi
	public ArrayList<Park> getAllParks(ArrayList<?> parameters) {
		ArrayList<Park> parks = new ArrayList<Park>();
		String sql = "SELECT * FROM g8gonature.park";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			ResultSet res = query.executeQuery();

			while (res.next())
				parks.add(new Park(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4), res.getInt(5),
						res.getInt(6)));
		} catch (SQLException e) {
			System.out.println("Could not execute getAllParks query");
			e.printStackTrace();
		}

		return parks;
	}

	// shlomi
	public Discount getMaxDisount(ArrayList<?> parameters) {

		Discount discount = null;
		String sql = "SELECT * FROM g8gonature.discount WHERE status = ? and parkId = ? and startDate <= ? and endDate >= ? ORDER BY amount DESC";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, "confirm");
			query.setInt(2, (int) Integer.parseInt((String) parameters.get(1)));
			query.setString(3, (String) parameters.get(0));
			query.setString(4, (String) parameters.get(0));
			ResultSet res = query.executeQuery();
			if (res.next())
				discount = new Discount(res.getString(1), res.getDouble(2), res.getString(3), res.getString(4),
						res.getInt(5), res.getString(6));
		} catch (SQLException e) {
			System.out.println("Could not execute getMaxDisount query");
			e.printStackTrace();
		}
		return discount;
	}

	// shlomi
	public ArrayList<Order> getOrderBetweenTimes(ArrayList<?> parameters) {
		ArrayList<Order> orders = new ArrayList<Order>();

		String sql = "SELECT * FROM g8gonature.order WHERE parkId = ? and orderDate = ? and orderTime >= ? and orderTime <= ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt((String) parameters.get(0)));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, (String) parameters.get(3));
			ResultSet res = query.executeQuery();

			while (res.next())
				orders.add(new Order(String.valueOf(res.getInt(1)), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10)));
		} catch (SQLException e) {
			System.out.println("Could not execute getOrderBetweenTimes query");
			e.printStackTrace();
		}

		return orders;
	}

	// shlomi
	public boolean AddNewOrder(Object obj) {
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
	public boolean AddTraveler(Object obj) {
		Traveler travelerToAdd = (Traveler) obj;
		int result = 0;
		String sql = "INSERT INTO g8gonature.traveler (travelerId, firstName, lastName, email, phoneNumber) "
				+ "values (?, ?, ?, ?, ?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, travelerToAdd.getTravelerId());
			query.setString(2, travelerToAdd.getFirstName());
			query.setString(3, travelerToAdd.getLastName());
			query.setString(4, travelerToAdd.getEmail());
			query.setString(5, travelerToAdd.getPhoneNumber());
			result = query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute AddTraveler query");
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
				order = new Order(String.valueOf(res.getInt(1)), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10));
		} catch (SQLException e) {
			System.out.println("Could not execute getRecentOrder query");
			e.printStackTrace();
		}
		return order;
	}

}
