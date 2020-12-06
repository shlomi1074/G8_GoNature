package sqlHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

public class mysqlFunctions {

	private Connection conn;

	public mysqlFunctions(Connection conn) {
		this.conn = conn;
	}

	/*
	 * Handles the connection to the DB
	 */

	// Our function
	/*
	 * This function updates the email with given ID.
	 */
	public boolean updateEmailWithGivenVerNum(String id, String email) // query was done successfully or not.
	{
		String sql = "UPDATE g8prototype.visitor SET email=? WHERE identification_number=?"; // users - the name of the
																								// // Alon,Lior,Shlomi
		SQLWarning warn;
		boolean res = false;
		try {
			PreparedStatement updateEmailWithVerNum = conn.prepareStatement(sql);
			updateEmailWithVerNum.setString(2, id); // changed to 2 by Alon,Lior,Shlomi
			updateEmailWithVerNum.setString(1, email);// changed to 1 by Alon,Lior,Shlomi

			res = updateEmailWithVerNum.executeUpdate() > 0; // Execute update
			warn = updateEmailWithVerNum.getWarnings(); // get warning if there is Alon,Lior,Shlomi
			if (warn != null)
				System.err.println("Warning:" + warn.getMessage());
			return res; // data entered successfully
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage()); // return the SQL exception that occurred
		}
		return res; // data did not enter
	}

	public ArrayList<String> viewInformation(String id) // query was done successfully or not.
	{
		String sql = "SELECT * FROM Visitor WHERE identification_number=" + id; // which users info we should get
		ResultSet rs;
		ArrayList<String> arrayListOfData = new ArrayList<>();
		String res = "";
		SQLWarning warn;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			warn = stmt.getWarnings(); // get warning if there is
			if (warn != null)
				System.err.println("Warning:" + warn.getMessage());

			while (rs.next()) {
				res = rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + ","
						+ rs.getString(5);
				arrayListOfData.add(res);
			}

			if (res.equals(""))
				return null;
			return arrayListOfData;

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage()); // return the SQL exception that occurred
		}
		return null;
	}

	// Incase we should return the whole table
	public ArrayList<String> viewInformation() // query was done successfully or not.
	{
		String sql = "SELECT * FROM Visitor";
		ResultSet rs;
		String res = "";
		ArrayList<String> arrayListOfData = new ArrayList<>();
		SQLWarning warn;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			warn = stmt.getWarnings(); // get warning if there is
			if (warn != null)
				System.err.println("Warning:" + warn.getMessage());

			while (rs.next()) {
				res = rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + ","
						+ rs.getString(5);
				arrayListOfData.add(res);
			}
			if (res.equals(""))
				return null;
			return arrayListOfData;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage()); // return the SQL exception that occurred
		}
		return null;
	}

}
