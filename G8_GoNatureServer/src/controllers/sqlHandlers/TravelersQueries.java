package controllers.sqlHandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logic.Employees;
import logic.Messages;
import logic.Subscriber;
import logic.Traveler;
import logic.WorkerType;

/**
 * This class handles all the queries which are related to travelers
 * 
 */
public class TravelersQueries {
	private Connection conn;

	public TravelersQueries(Connection conn) {
		this.conn = conn;
	}

	/**
	 * This function checks if a given id is already loggen in
	 * 
	 * @param parameters the id to check
	 * @return true if connected, false if not
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

	/**
	 * This function gets a traveler's id and retrieve the traveler from the database
	 * 
	 * @param parameters The traveler's id
	 * @return Traveler object
	 */
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

	/**
	 * This function INSERT row to loggedin table in the database
	 * 
	 * @param parameters id
	 */
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

	/**
	 * This function gets subscriber's subscription id a retrieve the subscriber from the database
	 * 
	 * @param parameters The subscriber's subscription id
	 * @return Subscriber object
	 */
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

	/**
	 * This function gets subscriber's id a retrieve the subscriber from the database
	 * 
	 * @param parameters The subscriber's id
	 * @return Subscriber object
	 */
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

	/**
	 * This function INSERT a new traveler to 'traveler' table in the database
	 * 
	 * @param obj Traveler object to insert
	 * @return true on success, false otherwise
	 */
	public boolean addTraveler(Object obj) {
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

	/* Alon 12.12.20 */
	public Employees isMemberExist(ArrayList<?> parameters) {
		WorkerType wt;
		Employees member = null;
		String sql = "SELECT * FROM g8gonature.employeesidentification WHERE employeeId = ? AND password = ? ";
		PreparedStatement query, query2;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			ResultSet res = query.executeQuery();
			if (res.next()) {
				/* new query */
				sql = "SELECT * FROM g8gonature.employees WHERE employeeId = ?";
				query2 = conn.prepareStatement(sql);
				query2.setInt(1, Integer.parseInt((String) parameters.get(0)));// changed to int
				res = query2.executeQuery();
				if (res.next()) {
					switch (res.getString(2)) {
					case "Entrance":
						wt = WorkerType.ENTRANCE;
						break;
					case "Park Manager":
						wt = WorkerType.PARK_MANAGER;
						break;
					case "Service":
						wt = WorkerType.SERVICE;
						break;
					case "Department Manager":
						wt = WorkerType.DEPARTMENT_MANAGER;
						break;
					default:
						throw new IllegalArgumentException("Wrong role type!");
					}
					member = new Employees(Integer.parseInt(res.getString(1)), wt, Integer.parseInt(res.getString(3)),
							res.getString(4), res.getString(5), res.getString(6));
				}
			}
		} catch (SQLException e) {
			System.out.println("Could not execute isMemberExist query");
			e.printStackTrace();
		}
		return member;
	}

	public void removeFromLoggedInTable(ArrayList<?> parameters) {
		String sql = "DELETE FROM g8gonature.loggedin WHERE id = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute removeFromLoggedInTable query");
			e.printStackTrace();
		}
	}
	/* End of Alon's 12.12.20 edit */

	/* Lior */
	public void deleteFromTravelerTable(ArrayList<?> parameters) {
		String sql = "DELETE FROM g8gonature.traveler WHERE travelerId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute deleteFromTravelerTable query");
			e.printStackTrace();
		}
	}

	/* Lior */
	public void insertSubscriberToSubscriberTable(ArrayList<?> parameters) {
		String sql = "INSERT INTO g8gonature.subscriber (travelerId, firstName, lastName, email, phoneNumber, creditCard, subscriberType, numberOfParticipants) values (?,?,?,?,?,?,?,?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, (String) parameters.get(3));
			query.setString(5, (String) parameters.get(4));
			query.setString(6, (String) parameters.get(5));
			query.setString(7, (String) parameters.get(6));
			query.setInt(8, Integer.parseInt((String) parameters.get(7)));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute insertSubscriberToSubscriberTable query");
			e.printStackTrace();
		}
	}

	/* Lior */
	public ArrayList<Messages> getMessages(ArrayList<?> parameters) {
		ArrayList<Messages> messeges = new ArrayList<Messages>();
		String sql = "SELECT * FROM g8gonature.messages WHERE toId = ? ORDER BY messageId DESC";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();
			/* getting all messages from query into array list */
			while (res.next()) {
				Messages message = new Messages(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7));
				messeges.add(message);
			}
		} catch (SQLException e) {
			System.out.println("Could not execute getMessages");
			e.printStackTrace();
		}
		return messeges;
	}

	/**
	 * This function add a new message for a specific traveler in the DB.
	 * 
	 * @param parameters - ArrayList containing: toId, sendDate, sendTime, subject, content, orderId
	 * @return true on success, false otherwise
	 */
	public boolean sendMessageToTraveler(ArrayList<?> parameters) {
		String sql = "INSERT INTO g8gonature.messages (toId,sendDate,sendTime,subject,content,orderId) "
				+ "VALUES (?,?,?,?,?,?)";
		PreparedStatement query;
		int res = 0;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, (String) parameters.get(3));
			query.setString(5, (String) parameters.get(4));
			query.setInt(6, Integer.parseInt((String) parameters.get(5)));
			res = query.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Could not execute sendMessageToTraveler query");
			e.printStackTrace();
		}
		return res == 1;
	}

	/* Lior */
	public void insertCardToCreditCardTable(ArrayList<?> parameters) {
		String sql = "INSERT INTO g8gonature.creditcard (subscriberId, cardNumber, cardExpiryDate, CVC) values (?,?,?,?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setInt(4, Integer.parseInt((String) parameters.get(3)));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute insertCardToCreditCardTable query");
			e.printStackTrace();
		}
	}

	/**
	 * This function gets an order id and return the email related to this order from the database
	 * 
	 * @param orderId the order id
	 * @return The email in this order as String
	 */
	public String getEmailByOrderID(int orderId) {
		String sql = "SELECT order.email FROM g8gonature.order WHERE orderId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, orderId);
			ResultSet res = query.executeQuery();

			if (res.next())
				return res.getString(1);

		} catch (SQLException e) {
			System.out.println("Could not execute getEmailByOrderID");
			e.printStackTrace();
		}
		return null;
	}

}
