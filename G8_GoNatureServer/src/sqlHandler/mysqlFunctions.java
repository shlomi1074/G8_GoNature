package sqlHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;

import logic.Discount;
import logic.DiscountTb;
import logic.Employees;
import logic.Messages;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;
import logic.Request;
import logic.Subscriber;
import logic.Traveler;
import logic.WorkerType;

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

		String sql = "SELECT * FROM g8gonature.order WHERE parkId = ? and orderDate = ? and orderTime >= ? and orderTime <= ? AND orderStatus != ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, Integer.parseInt((String) parameters.get(0)));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, (String) parameters.get(3));
			query.setString(5, OrderStatusName.cancel.name());
			ResultSet res = query.executeQuery();

			while (res.next())
				orders.add(new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),
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
				order = new Order(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7), res.getString(8), res.getDouble(9),
						res.getString(10));
		} catch (SQLException e) {
			System.out.println("Could not execute getRecentOrder query");
			e.printStackTrace();
		}
		return order;
	}
	
	/*Alon 12.12.20*/
	public Employees isMemberExist(ArrayList<?> parameters) {
		WorkerType wt;
		Employees member = null;
		String sql = "SELECT * FROM g8gonature.employeesidentification WHERE employeeId = ? AND password = ? ";
		PreparedStatement query,query2;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			ResultSet res = query.executeQuery();
			if (res.next()) {
				/*new query*/
				sql = "SELECT * FROM g8gonature.employees WHERE employeeId = ?";
				query2 = conn.prepareStatement(sql);
				query2.setInt(1,Integer.parseInt((String)parameters.get(0)));//changed to int
				res = query2.executeQuery();
				if(res.next()) {
					switch (res.getString(2)) {
					case "Entrance":
							wt=WorkerType.ENTRANCE;
						break;
					case "Park Manager":
						wt=WorkerType.PARK_MANAGER;
					break;
					case "Service":
						wt=WorkerType.SERVICE;
					break;
					case "Department Manager":
						wt=WorkerType.DEPARTMENT_MANAGER;
					break;
					default:
						throw new IllegalArgumentException("Wrong role type!");
					}
				member = new Employees(Integer.parseInt(res.getString(1)), wt , Integer.parseInt(res.getString(3)), res.getString(4),
						res.getString(5),res.getString(6));
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
	/*End of Alon's 12.12.20 edit*/
	
	//ofir 
	public boolean sendMessageToTraveler(ArrayList<?> parameters) {
        String sql = "INSERT INTO g8gonature.messages (toId,sendDate,sendTime,subject,content,orderId) " + "VALUES (?,?,?,?,?,?)";
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

		// Ofir Avraham Vaknin
		public ArrayList<Order> findMatchingOrdersInWaitingList(ArrayList<?> parameters) {
			// Need to send gap
			ArrayList<Order> resultArray = new ArrayList<Order>();

			String parkId = (String) parameters.get(0);
			String maxVisitors = (String) parameters.get(1);
			String estimatedStayTime = (String) parameters.get(2);
			String date = (String) parameters.get(3);
			String hour = (String) parameters.get(4);
			String gap = (String) parameters.get(5);

			int onlyHours = Integer.parseInt(hour.substring(0, 2));
			int estimated = Integer.parseInt(estimatedStayTime);
			int maxVisitor = Integer.parseInt(maxVisitors);
			int gapInPark = Integer.parseInt(gap);

			int maxAllowedInPark = maxVisitor - gapInPark;

			String hourAfterEstimated = String.valueOf(onlyHours + estimated) + hour.substring(3);
			String hourBeforeEstimated = String.valueOf(onlyHours - estimated) + hour.substring(3);
		
			
			int beforeParticipants = numberOfParticipantsBetweenTwoHours(parkId, date, hourBeforeEstimated, hour);
			beforeParticipants = beforeParticipants % 24;
			int afterParticipants = numberOfParticipantsBetweenTwoHours(parkId, date, hourAfterEstimated, hour);
			afterParticipants = beforeParticipants % 24;

			// Max should be changed, Disscuss in group
			//int maxParticipants = Math.max(beforeParticipants, afterParticipants);
			int maxParticipants = beforeParticipants + afterParticipants;
			

			String sql = "SELECT * FROM g8gonature.order WHERE parkId = ? AND "
					+ "orderDate = ? AND orderTime BETWEEN ? AND ? AND orderStatus = ?";
			ResultSet rs;
			PreparedStatement query;
			try {
				query = conn.prepareStatement(sql);
				query.setString(1, parkId);
				query.setString(2, date);
				query.setString(3, hourBeforeEstimated);
				query.setString(4, hourAfterEstimated);
				query.setString(5, "waiting");
				rs = query.executeQuery();
				while (rs.next()) {
					if (rs.getInt(7) + maxParticipants < maxAllowedInPark) {
						Order order = new Order(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
								rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getDouble(9),
								rs.getString(10));
						resultArray.add(order);
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
				query.setString(5, "waiting");
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

		/*Lior*/
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
		
		/*Lior*/
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
		
		/*Lior*/
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
		
		public void insertAllNewRequestsFromParkManager(ArrayList<?> managerRequests) { /// ofir n
			
			
			String sql = "INSERT INTO g8gonature.request (changeName,newValue,requestStatus) values (?,?,?)";
			String sql2 = "INSERT INTO g8gonature.discount (amount,startDate,endDate) values (?,?,?)";

			
			PreparedStatement query;
			PreparedStatement query2;

			try {
				query = conn.prepareStatement(sql); // handles updates
				
			    if(managerRequests.get(0)!=null) {
				query.setString(1, "UPDATE MAX VISITORS");
				query.setString(2, (String) managerRequests.get(0));
				query.setString(3, "pending");
				query.executeUpdate();
		
			    }
			    if(managerRequests.get(1)!=null) {

				query.setString(1, "UPDATE MAX VISITORS");
				query.setString(2, (String) managerRequests.get(1));
				query.setString(3, "pending");
				query.executeUpdate();
			    }
			
			    if(managerRequests.get(2)!=null) {

				query.setString(1, "UPDATE GAP");
				query.setString(2, (String) managerRequests.get(2));
				query.setString(3, "pending");
				query.executeUpdate();
			    }
				
			
				query2=conn.prepareStatement(sql2); /// handles discount
				

			    if(managerRequests.get(3)!=null && managerRequests.get(4)!=null && managerRequests.get(5)!=null) {

				
				query2.setString(1, (String)managerRequests.get(5));
				query2.setString(2, (String) managerRequests.get(3));
				query2.setString(3, (String) managerRequests.get(4));
				query2.executeUpdate();
			    }
			
			} catch (SQLException e) {
				System.out.println("Could not execute checkIfConnected query");
				e.printStackTrace();
			}
			
			
		}
		
		
		
		public ArrayList<Request> GetRequestsFromDB() { /// ofir n
			ArrayList<Request> requests = new ArrayList<>();
			int i=0;
			String sql = "SELECT * FROM g8gonature.request";
			PreparedStatement query;
			try {
				query = conn.prepareStatement(sql);
				ResultSet res = query.executeQuery();
				
				while (res.next()) {
					requests.set(i,new Request(res.getString(1),res.getString(2),res.getString(3),null,null,0,res.getString(4))) ;
				    i++;
				}
			} catch (SQLException e) {
				System.out.println("Could not execute GetRequestsFromDB query");
				e.printStackTrace();
			}

			return requests;
			
		}

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
							wt=WorkerType.ENTRANCE;
						break;
					case "Park Manager":
						wt=WorkerType.PARK_MANAGER;
					break;
					case "Service":
						wt=WorkerType.SERVICE;
					break;
					case "Department Manager":
						wt=WorkerType.DEPARTMENT_MANAGER;
					break;
					default:
						throw new IllegalArgumentException("Wrong role type!");
					}
					employee = new Employees(res.getInt(1), wt, res.getInt(3), res.getString(4),
							res.getString(5), res.getString(6));
				}
			} catch (SQLException e) {
				System.out.println("Could not execute getEmployeeById query");
				e.printStackTrace();
			}

			return employee;
		}

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
				/*getting all messages from query into array list*/
				while (res.next()) {
					Messages message = new Messages(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
							res.getString(5),res.getString(6),res.getInt(7));
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
			// new
			// ArrayList<String>(Arrays.asList(travelerId,parkId,enterTime,estimated,date)));

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

}
