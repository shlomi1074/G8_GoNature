package sqlHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

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
			System.out.println("Could not execute checkIfConnected query");
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
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}
	}

	public Subscriber GetSubscriberBySubId(ArrayList<?> parameters) {
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
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

		return sub;
		
	}

}
