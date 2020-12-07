package sqlHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlConnection {

	private Connection connection = null;
	private static mysqlConnection instance = null;

	private mysqlConnection() throws SQLException,ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (ClassNotFoundException ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
			throw ex;
		}
		try {
			/*
			 * Need to make sure the details are right for each MySQLDB.
			 */
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/g8prototype?serverTimezone=UTC",
					"root", "root"); // pass changed
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			throw ex;
		}
	}

	public static mysqlConnection getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		if (instance == null)
			instance = new mysqlConnection();
		return instance;
	}

	public Connection getConnection() {
		return this.connection;
	}
	
	
	

}
