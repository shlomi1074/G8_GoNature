package controllers.sqlHandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import logic.Order;
import logic.Park;

/**
 * This class handles all the queries which are related to parks
 * 
 */
public class ParkQueries {
	private Connection conn;

	public ParkQueries(Connection conn) {
		this.conn = conn;
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

	// shlomi
	public ArrayList<String> getSimulatorTravelersId() {

		ArrayList<String> travelersID = new ArrayList<>();
		int i = 0;
		String sql = "SELECT * FROM g8gonature.card_reader_simulator";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				travelersID.add(rs.getString(1) + " " + rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

		return travelersID;
	}

	// shlomi
	public void updateVisitExitTimeSimulator(Order order) {
		String sql = "UPDATE g8gonature.visit SET exitTime = ? WHERE travelerId = ? AND parkId = ? AND entrenceTime = ? AND visitDate = ?";
		PreparedStatement query;
		String time = order.getOrderTime();
		String newExitTime = String.valueOf(Integer.parseInt(time.split(":")[0]) + 3) + ":" + time.split(":")[1];
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, newExitTime);
			query.setString(2, order.getTravelerId());
			query.setInt(3, order.getParkId());
			query.setString(4, order.getOrderTime());
			query.setString(5, order.getOrderDate());
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute updateVisitExitTimeSimulator");
			e.printStackTrace();
		}

	}

	//ofir n
	
	public void changeParkParametersInDB(ArrayList<?> parameters) {
		
		System.out.println("1");
		PreparedStatement query;
        String typeOfRequest = null;
		String sql;

		System.out.println((String)parameters.get(0));
		
	if(((String)parameters.get(0)).equals("UPDATE MAX VISITORS")) typeOfRequest="maxVisitors";
	if(((String)parameters.get(0)).equals("UPDATE ESTIMATED STAY TIME")) typeOfRequest="estimatedStayTime";
	if(((String)parameters.get(0)).equals("UPDATE GAP")) typeOfRequest="gapBetweenMaxAndCapacity";

		
		
		try {
			
			sql = "UPDATE g8gonature.park SET " +typeOfRequest+"=? WHERE parkId="+parameters.get(2)+ "";
			System.out.println(sql);
			query = conn.prepareStatement(sql);		   
			query.setInt(1,Integer.parseInt((String)parameters.get(1)));
			query.executeUpdate();
	
		} 
		
		
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}
}
