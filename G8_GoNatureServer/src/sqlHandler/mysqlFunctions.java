package sqlHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import logic.Discount;
import logic.Employees;
import logic.Messages;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;
import logic.Request;
import logic.Subscriber;
import logic.Traveler;
import logic.VisitReport;
import logic.WorkerType;
import logic.report;

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
				discount = new Discount(res.getInt(1), res.getDouble(2), res.getString(3), res.getString(4),
						res.getInt(5), res.getString(6));
		} catch (SQLException e) {
			System.out.println("Could not execute getMaxDisount query");
			e.printStackTrace();
		}
		return discount;
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

	// ofir
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

	// Ofir Avraham Vaknin
	public ArrayList<Order> getAllOrdersForID(ArrayList<?> parameters) {
		ArrayList<Order> orders = new ArrayList<Order>();

		String sql = "SELECT * FROM g8gonature.order WHERE travelerId = ?";
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

	public void insertAllNewRequestsFromParkManager(ArrayList<?> managerRequests) {

		String sql = "INSERT INTO g8gonature.request (changeName,newValue,requestDate,parkId,requestStatus) values (?,?,?,?,?)";
		String sql2 = "INSERT INTO g8gonature.discount (amount,startDate,endDate,parkId,status) values (?,?,?,?,?)";

		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		String parkID = (String) managerRequests.get(6);

		PreparedStatement query;
		PreparedStatement query2;

		try {
			query = conn.prepareStatement(sql); // handles updates

			if (managerRequests.get(0) != null) {
				query.setString(1, "UPDATE MAX VISITORS");
				query.setString(2, (String) managerRequests.get(0));
				query.setDate(3, date);
				query.setInt(4, Integer.parseInt(parkID));
				query.setString(5, "pending");
				query.executeUpdate();

			}
			if (managerRequests.get(1) != null) {

				query.setString(1, "UPDATE ESTIMATED STAY TIME");
				query.setString(2, (String) managerRequests.get(1));
				query.setDate(3, date);
				query.setInt(4, Integer.parseInt(parkID));
				query.setString(5, "pending");
				query.executeUpdate();
			}

			if (managerRequests.get(2) != null) {

				query.setString(1, "UPDATE GAP");
				query.setString(2, (String) managerRequests.get(2));
				query.setDate(3, date);
				query.setInt(4, Integer.parseInt(parkID));
				query.setString(5, "pending");
				query.executeUpdate();
			}

			query2 = conn.prepareStatement(sql2); /// handles discount

			// if(managerRequests.get(3)!=null && managerRequests.get(4)!=null &&
			// managerRequests.get(5)!=null) {

			// System.out.println(managerRequests.get(3)); //

			query2.setString(1, (String) managerRequests.get(5));
			query2.setString(2, (String) managerRequests.get(3)); //
			query2.setString(3, (String) managerRequests.get(4)); //
			query2.setInt(4, Integer.parseInt(parkID));
			query2.setString(5, "pending");

			query2.executeUpdate();
			// }

		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

	}

	public ArrayList<?> GetRequestsFromDB() { /// ofir n
		ArrayList<Request> requests = new ArrayList<>();
		int i = 0;
		String sql = "SELECT * FROM g8gonature.request";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			ResultSet res = query.executeQuery();

			while (res.next()) {
				requests.add(i, new Request(res.getInt(1), res.getString(2), res.getString(3), null, null, 0,
						res.getString(7))); // id was changed to int from String
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

		return requests;

	}

	// shlomi
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
	
	// shlomi
	public Employees getEmployeeById(ArrayList<?> parameters) {
		Employees employee = null;
		String sql = "SELECT * FROM g8gonature.employees WHERE employeeId = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();

			if (res.next()) {
				WorkerType wt;
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
				employee = new Employees(res.getInt(1), wt, res.getInt(3), res.getString(4), res.getString(5),
						res.getString(6));
			}
		} catch (SQLException e) {
			System.out.println("Could not execute getEmployeeById query");
			e.printStackTrace();
		}

		return employee;
	}
	
	// shlomi
	public String getEmployeePasswordById(int employeeId) {
		String sql = "SELECT employeesidentification.password FROM g8gonature.employeesidentification WHERE employeeId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, employeeId);
			ResultSet res = query.executeQuery();

			if (res.next())
				return res.getString(1);

		} catch (SQLException e) {
			System.out.println("Could not execute getEmployeePasswordById");
			e.printStackTrace();
		}
		return null;
	}

	/* Lior */
	public ArrayList<Messages> getMessages(ArrayList<?> parameters) {
		ArrayList<Messages> messeges = new ArrayList<Messages>();
		String sql = "SELECT * FROM g8gonature.messages WHERE toId = ?";
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

	// Ofir Avraham Vaknin v2.
	public boolean addVisit(ArrayList<?> parameters) {
		// new
		// ArrayList<String>(Arrays.asList(travelerId,parkId,enterTime,estimated,date)));

		String enterTime = (String) parameters.get(2);
		String estimated = (String) parameters.get(3);

		LocalTime exitTime = LocalTime.parse(enterTime).plusHours(Integer.parseInt(estimated));
		int res = 0;

		String sql = "INSERT INTO g8gonature.visit (travelerId,parkId,entrenceTime,exitTime,visitDate) "
				+ "VALUES (?,?,?,?,?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, exitTime.toString());
			query.setString(5, (String) parameters.get(4));
			res = query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute addVisit query");
			e.printStackTrace();
		}
		return res == 1;
	}

	// Ofir Avraham Vaknin v2.
	public boolean updateNumberOfVisitors(ArrayList<?> parameters) {
		String parkId = (String) parameters.get(0);
		int number = Integer.parseInt((String) parameters.get(1));

		int res = 0;

		String sql = "UPDATE g8gonature.park SET currentVisitors = ? WHERE parkId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setInt(2, number);
			res = query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute updateNumberOfVisitors query");
			e.printStackTrace();
		}
		return res == 1;
	}

	// Ofir Avraham Vaknin v2.
	public ArrayList<Order> getOrdersForPark(ArrayList<?> parameters) {
		int parkId = Integer.parseInt((String) parameters.get(0));
		ArrayList<Order> orders = new ArrayList<Order>();

		String sql = "SELECT * FROM g8gonature.order WHERE parkId = ?";
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

	public ArrayList<String> isParkIsFullAtDate(ArrayList<?> parameters) {
		ArrayList<String> comment = new ArrayList<String>();

		String sql = "SELECT comment FROM g8gonature.fullparkdate WHERE date = ? and parkId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setInt(2, Integer.parseInt((String) parameters.get(1)));
			ResultSet res = query.executeQuery();

			while (res.next())
				comment.add(res.getString(1));

			if (comment.size() == 0)
				comment.add("notFull");

		} catch (SQLException e) {
			System.out.println("Could not execute isParkIsFullAtDate");
			e.printStackTrace();
		}
		return comment;
	}

	public void deleteReport(ArrayList<?> parameters) {
		String sql = "DELETE FROM g8gonature.reports where reportType = ? and parkID = ? and month = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			report r = (report) parameters.get(0);
			query.setString(1, r.getReportType());
			query.setInt(2, r.getParkID());
			query.setInt(3, r.getMonth());
			query.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Could not execute deleteReport");
			e.printStackTrace();
		}
	}

	public boolean insertReport(ArrayList<?> parameters) {

		String sql = "INSERT INTO g8gonature.reports  (reportType, parkID, month, comment)  values (?, ?,?,?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			report r = (report) parameters.get(0);
			query.setString(1, r.getReportType());
			query.setInt(2, r.getParkID());
			query.setInt(3, r.getMonth());
			query.setString(4, r.getComment());
			query.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Could not execute insertReport");
			e.printStackTrace();
			return false;
		}
	}

	// Shlomi + Ofir
	public boolean insertToFullParkDate(ArrayList<?> parameters) {
		String sql = "INSERT INTO g8gonature.fullparkdate  (parkId, date, maxVisitors,comment)  values (?, ?,?,?)";
		int res = 0;
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt((String) parameters.get(0)));
			query.setString(2, (String) parameters.get(1));
			query.setInt(3, Integer.parseInt((String) parameters.get(2)));
			query.setString(4, (String) parameters.get(3));
			res = query.executeUpdate();
			return res == 1;
		} catch (SQLException e) {
			System.out.println("Could not execute insertToFullParkDate query");
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList <Order> getPendingOrders(){
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
				order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10));
			}

		} catch (SQLException e) {
			System.out.println("Could not execute getAllOrdersForId");
			e.printStackTrace();
		}
		return order;
		
	}
	
	/*Alon*/
	public ArrayList<VisitReport> CountSolosEnterTime(ArrayList<?> parameters) {
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		int month = Integer.parseInt((String) parameters.get(0));
		int year = Calendar.getInstance().get(Calendar.YEAR);

		String sql = "SELECT SUM(test.numberOfParticipants),test.entrenceTime "
				+ "FROM (SELECT visit.travelerId,order.numberOfParticipants, visit.entrenceTime FROM g8gonature.visit , g8gonature.order "
				+ "WHERE NOT EXISTS ( SELECT subscriber.travelerId FROM g8gonature.subscriber WHERE visit.travelerId = subscriber.travelerId) "
				+ "AND visit.travelerId = order.travelerId AND order.orderType = 'Solo Visit' "
				+ "AND order.orderStatus = 'Completed' AND visit.visitDate = order.orderDate "
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ?) as test GROUP BY test.entrenceTime;";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, month);
			query.setInt(2, year);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				VisitReport report = new VisitReport(res.getInt(1), res.getString(2));
				rep.add(report);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute CountSolos query");
			e.printStackTrace();
		}
		return rep;
	}

	public ArrayList<VisitReport> CountSubsEnterTime(ArrayList<?> parameters) {
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		int month = Integer.parseInt((String) parameters.get(0));
		int year = Calendar.getInstance().get(Calendar.YEAR);

		String sql = "SELECT SUM(test.numberOfParticipants),test.entrenceTime "
				+ "FROM (SELECT visit.travelerId,order.numberOfParticipants, visit.entrenceTime "
				+ "FROM g8gonature.visit, g8gonature.order, g8gonature.subscriber "
				+ "WHERE visit.travelerId = order.travelerId "
				+ "AND (order.orderType = 'Solo Visit' OR order.orderType = 'Family Visit') "
				+ "AND visit.travelerId = subscriber.travelerId AND order.orderStatus = 'Completed' AND visit.visitDate = order.orderDate "
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ? ) as test GROUP BY test.entrenceTime;";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, month);
			query.setInt(2, year);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				VisitReport report = new VisitReport(res.getInt(1), res.getString(2));
				rep.add(report);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute CountSubs query");
			e.printStackTrace();
		}
		return rep;
	}
	
	public ArrayList<VisitReport> CountGroupsEnterTime(ArrayList<?> parameters) {
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		int month = Integer.parseInt((String) parameters.get(0));
		int year = Calendar.getInstance().get(Calendar.YEAR);

		String sql = "SELECT SUM(test.numberOfParticipants),test.entrenceTime "
				+ "FROM (SELECT visit.travelerId,order.numberOfParticipants, visit.entrenceTime FROM g8gonature.visit,g8gonature.order  "
				+ "WHERE (NOT exists ( SELECT subscriber.travelerId FROM g8gonature.subscriber WHERE visit.travelerId = subscriber.travelerId) "
				+ "AND NOT exists ( SELECT traveler.travelerId FROM g8gonature.traveler "
				+ "WHERE visit.travelerId = traveler.travelerId) "
				+ "OR (SELECT subscriber.travelerId FROM g8gonature.subscriber WHERE visit.travelerId = subscriber.travelerId)) "
				+ "AND visit.travelerId = order.travelerId AND order.orderType = 'Group Visit' "
				+ "AND order.orderStatus = 'Completed' AND visit.visitDate = order.orderDate "
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ?) as test GROUP BY test.entrenceTime;";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, month);
			query.setInt(2, year);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				VisitReport report = new VisitReport(res.getInt(1), res.getString(2));
				rep.add(report);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute CountGroup query");
			e.printStackTrace();
		}
		return rep;
	}
	
	public ArrayList<VisitReport> CountSolosVisitTime(ArrayList<?> parameters) {
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		int month = Integer.parseInt((String) parameters.get(0));
		int year = Calendar.getInstance().get(Calendar.YEAR);

		String sql = "SELECT SUM(test.numberOfParticipants),TIMEDIFF (test.exitTime,test.entrenceTime) "
				+ "FROM (SELECT visit.travelerId,order.numberOfParticipants, visit.entrenceTime,visit.exitTime "
				+ "FROM g8gonature.visit , g8gonature.order WHERE NOT exists ( SELECT subscriber.travelerId FROM g8gonature.subscriber "
				+ "WHERE visit.travelerId = subscriber.travelerId) AND visit.travelerId = order.travelerId AND order.orderType = 'Solo Visit' "
				+ "AND order.orderStatus = 'Completed' AND visit.visitDate = order.orderDate "
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ?) as test GROUP BY test.entrenceTime;";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, month);
			query.setInt(2, year);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				VisitReport report = new VisitReport(res.getInt(1), res.getString(2));
				rep.add(report);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute CountSolos query");
			e.printStackTrace();
		}
		return rep;
	}

	public ArrayList<VisitReport> CountSubsVisitTime(ArrayList<?> parameters) {
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		int month = Integer.parseInt((String) parameters.get(0));
		int year = Calendar.getInstance().get(Calendar.YEAR);

		String sql = "SELECT SUM(test.numberOfParticipants),TIMEDIFF (test.exitTime,test.entrenceTime) "
				+ "FROM (SELECT visit.travelerId,order.numberOfParticipants, visit.entrenceTime, visit.exitTime "
				+ "FROM g8gonature.visit, g8gonature.order, g8gonature.subscriber WHERE visit.travelerId = order.travelerId "
				+ "AND (order.orderType = 'Solo Visit' OR order.orderType = 'Family Visit') AND visit.travelerId = subscriber.travelerId "
				+ "AND order.orderStatus = 'Completed' AND visit.visitDate = order.orderDate "
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ? ) as test GROUP BY test.entrenceTime;";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, month);
			query.setInt(2, year);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				VisitReport report = new VisitReport(res.getInt(1), res.getString(2));
				rep.add(report);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute CountSubs query");
			e.printStackTrace();
		}
		return rep;
	}
	
	public ArrayList<VisitReport> CountGroupsVisitTime(ArrayList<?> parameters) {
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		int month = Integer.parseInt((String) parameters.get(0));
		int year = Calendar.getInstance().get(Calendar.YEAR);

		String sql = "SELECT SUM(test.numberOfParticipants),TIMEDIFF (test.exitTime,test.entrenceTime) "
				+ "FROM (SELECT visit.travelerId,order.numberOfParticipants, visit.entrenceTime, visit.exitTime "
				+ "FROM g8gonature.visit,g8gonature.order  WHERE (NOT exists ( SELECT subscriber.travelerId "
				+ "FROM g8gonature.subscriber WHERE visit.travelerId = subscriber.travelerId) AND NOT exists ( SELECT traveler.travelerId "
				+ "FROM g8gonature.traveler WHERE visit.travelerId = traveler.travelerId) OR (SELECT subscriber.travelerId FROM g8gonature.subscriber "
				+ "WHERE visit.travelerId = subscriber.travelerId)) AND visit.travelerId = order.travelerId AND order.orderType = 'Group Visit' "
				+ "AND order.orderStatus = 'Completed' AND visit.visitDate = order.orderDate "
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ?) as test GROUP BY test.entrenceTime;";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, month);
			query.setInt(2, year);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				VisitReport report = new VisitReport(res.getInt(1), res.getString(2));
				rep.add(report);
			}

		} catch (SQLException e) {
			System.out.println("Could not execute CountGroup query");
			e.printStackTrace();
		}
		return rep;
	}
	
	/*End Alon*/
	
	/* Lior */
	/* get reports from data base */
	public ArrayList<report> getReports(ArrayList<?> parameters) {
		ArrayList<report> reports = new ArrayList<report>();
		String sql = "SELECT * FROM g8gonature.reports";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			ResultSet res = query.executeQuery();
			/* getting all reports from query into array list */
			while (res.next()) {
				report rep = new report(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4),
						res.getString(5));
				reports.add(rep);
			}
		} catch (SQLException e) {
			System.out.println("Could not execute getMessages");
			e.printStackTrace();
		}
		return reports;
	}

	/* Lior */
	/* get cancels for park ID */
	public ArrayList<Integer> getParkCancels(ArrayList<?> parameters) {
		System.out.println("TEST if got to sql");
		ArrayList<Integer> cancels = new ArrayList<Integer>();
		int parkId =(int) parameters.get(0);
		int month =(int) parameters.get(1);
		System.out.println("park ID="+parkId);
		System.out.println("month="+month);
		
		String sql = "SELECT COUNT(*) FROM g8gonature.order WHERE parkId = ? AND MONTH(orderDate) = ? AND orderStatus = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, parkId);
			query.setInt(2, month);
			query.setString(3,OrderStatusName.CANCELED.toString());
			
			ResultSet res = query.executeQuery();
			while (res.next())
				cancels.add(new Integer(res.getInt(1)));
		} catch (SQLException e) {
			System.out.println("Could not execute getParkCancels query");
			e.printStackTrace();
		}
		return cancels;
	}
	
	// ofir n
	public void changeStatusOfRequest(boolean bool,int requestsID) { 

		String sql;

		if(bool)
			sql = "UPDATE g8gonature.request SET requestStatus='confirmed' WHERE requestId="+requestsID;

		else {sql = "UPDATE g8gonature.request SET requestStatus='declined' WHERE requestId="+requestsID;}

		PreparedStatement query;

		try {
			query = conn.prepareStatement(sql);
			query.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}			

	}
	
	// ofir n

	public void changeStatusOfDiscount(boolean bool, int discountsID) { 

		String sql;

		if(bool)
			sql = "UPDATE g8gonature.discount SET status='confirmed' WHERE discountId="+discountsID;

		else {sql = "UPDATE g8gonature.discount SET status='declined' WHERE discountId="+discountsID;}

		PreparedStatement query;

		try {
			query = conn.prepareStatement(sql);
			query.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}




	}

/// ofir n
	public ArrayList<?> createNumberOfVisitorsReport(int month){ // individual visitors (solo,familty) , orginized (Group) , subscribers.
        int solo = 0;
		ArrayList<Integer> numberOfVisitorsPerType=new ArrayList<>();
		
		String sql1="SELECT COUNT(OrderId) FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Solo Visit'";

		String sql2="SELECT COUNT(OrderId) FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Family Visit'";
		String sql3="SELECT COUNT(OrderId) FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Group Visit'";
		
		String sql4="SELECT COUNT(OrderId) "
				+ "FROM g8gonature.order,g8gonature.subscriber "
				+ "WHERE month(orderDate)="+month+" AND g8gonature.order.travelerId=g8gonature.subscriber.travelerId";


		PreparedStatement query;
		
		try {
			query = conn.prepareStatement(sql1);
			ResultSet res=query.executeQuery();

			while(res.next())solo=res.getInt(1);

			query = conn.prepareStatement(sql2);
			res=query.executeQuery();

			while(res.next())numberOfVisitorsPerType.add(0,res.getInt(1)+solo); // individuals - family+solo

			query = conn.prepareStatement(sql3);
			res=query.executeQuery();

			while(res.next())numberOfVisitorsPerType.add(res.getInt(1));   // groups

			
			query = conn.prepareStatement(sql4);
			res=query.executeQuery();

			while(res.next())numberOfVisitorsPerType.add(res.getInt(1)); // subscribers

			
			numberOfVisitorsPerType.add(numberOfVisitorsPerType.get(0)+numberOfVisitorsPerType.get(1));// total
			
		}catch (SQLException e) {
			e.printStackTrace();
		}		


		return numberOfVisitorsPerType;
	}

	/// ofir n
	
	public ArrayList<?> createIncomeReport(int month){ // individual visitors (solo,familty) , orginized (Group) , subscribers.
		    int solo = 0,family = 0,group = 0,subscriber = 0;
		    
			ArrayList<Integer> totalIncomePerType=new ArrayList<>();
		
			String sql1="SELECT g8gonature.order.price FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Solo Visit'";

			String sql2="SELECT g8gonature.order.price FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Family Visit'";
			String sql3="SELECT g8gonature.order.price FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Group Visit'";
			
			String sql4="SELECT g8gonature.order.price "
					+ "FROM g8gonature.order,g8gonature.subscriber "
					+ "WHERE month(orderDate)="+month+" AND g8gonature.order.travelerId=g8gonature.subscriber.travelerId";

	
			
			PreparedStatement query;
			
			try {
				query = conn.prepareStatement(sql1);
				ResultSet res=query.executeQuery();

				while(res.next())solo=solo+res.getInt(1);

				query = conn.prepareStatement(sql2);
				res=query.executeQuery();

				while(res.next())family=family+res.getInt(1);

				query = conn.prepareStatement(sql3);
				res=query.executeQuery();

				while(res.next())group=group+res.getInt(1);

				
				query = conn.prepareStatement(sql4);
				res=query.executeQuery();

				while(res.next())subscriber=subscriber+res.getInt(1);

				
				totalIncomePerType.add(solo+family);// total
				totalIncomePerType.add(group);// total
				totalIncomePerType.add(subscriber);// total
				totalIncomePerType.add(solo+family+group);// total


				
				
			}catch (SQLException e) {
				e.printStackTrace();
			}		
			
	
	return totalIncomePerType;
	}

	
	// ofir n
	
	
	public void createNewReportInDB(ArrayList<?> parameters) {
      
		String sql = "INSERT INTO g8gonature.reports (reportType,parkId,month,comment) values (?,?,?,?)";
		
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			
			query.setString(1,(String)parameters.get(1));
			query.setString(2,(String)parameters.get(2));
			query.setString(3,(String)parameters.get(0));
			query.setString(4,(String)parameters.get(3));

			query.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public ArrayList<?> GetDiscountsFromDB() {

		ArrayList<Discount> discount = new ArrayList<>();
		int i=0;
		String sql = "SELECT * FROM g8gonature.discount";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			ResultSet res = query.executeQuery();

			while (res.next()) {
				discount.add(i, new Discount(res.getInt(1),res.getDouble(2),res.getString(3),res.getString(4),res.getInt(5),res.getString(6)));	// id was changed to int from String
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

		return discount;
	}
	
}
