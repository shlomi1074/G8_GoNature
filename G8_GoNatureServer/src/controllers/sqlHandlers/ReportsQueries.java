package controllers.sqlHandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import logic.OrderStatusName;
import logic.VisitReport;
import logic.report;

/**
 * This class handles all the queries which are related to reports
 * 
 */
public class ReportsQueries {
	private Connection conn;

	public ReportsQueries(Connection conn) {
		this.conn = conn;
	}

	// shlomi
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

	// shlomi
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
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ?) as test GROUP BY TIMEDIFF(test.exitTime, test.entrenceTime);";
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
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ? ) as test GROUP BY TIMEDIFF(test.exitTime, test.entrenceTime);;";
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
				+ "AND MONTH(visit.visitDate) = ? AND YEAR(visit.visitDate) = ?) as test GROUP BY TIMEDIFF(test.exitTime, test.entrenceTime);";
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
		String sql = "SELECT * FROM g8gonature.reports ORDER BY reportID DESC";
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
		int parkId = (int) parameters.get(0);
		int month = (int) parameters.get(1);
		System.out.println("park ID=" + parkId);
		System.out.println("month=" + month);

		String sql = "SELECT COUNT(*) FROM g8gonature.order WHERE parkId = ? AND MONTH(orderDate) = ? AND orderStatus = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, parkId);
			query.setInt(2, month);
			query.setString(3, OrderStatusName.CANCELED.toString());

			ResultSet res = query.executeQuery();
			while (res.next())
				cancels.add(new Integer(res.getInt(1)));
		} catch (SQLException e) {
			System.out.println("Could not execute getParkCancels query");
			e.printStackTrace();
		}
		return cancels;
	}

	/// ofir n
	public ArrayList<?> createNumberOfVisitorsReport(int month) { // individual visitors (solo,familty) , orginized (Group) , subscribers.
		int solo = 0;
		int family = 0;
		int group = 0;
		int subscriber = 0;
		ArrayList<Integer> numberOfVisitorsPerType = new ArrayList<>();

		String sql1 = "SELECT * FROM g8gonature.order WHERE month(orderDate)=" + month
				+ " AND orderType='Solo Visit' AND g8gonature.order.travelerId NOT IN (  SELECT g8gonature.subscriber.travelerId   FROM  g8gonature.subscriber)";

		String sql2 = "SELECT * FROM g8gonature.order WHERE month(orderDate)=" + month
				+ " AND orderType='Family Visit' AND g8gonature.order.travelerId NOT IN (  SELECT g8gonature.subscriber.travelerId   FROM  g8gonature.subscriber)";
		String sql3 = "SELECT * FROM g8gonature.order WHERE month(orderDate)=" + month
				+ " AND orderType='Group Visit' AND g8gonature.order.travelerId NOT IN (  SELECT g8gonature.subscriber.travelerId   FROM  g8gonature.subscriber)";

		String sql4 = "SELECT * " + "FROM g8gonature.order,g8gonature.subscriber " + "WHERE month(orderDate)=" + month
				+ " AND g8gonature.order.travelerId=g8gonature.subscriber.travelerId";

		PreparedStatement query;

		try {
			query = conn.prepareStatement(sql1);
			ResultSet res = query.executeQuery();

			while (res.next())
				solo = solo + res.getInt(7);

			query = conn.prepareStatement(sql2);
			res = query.executeQuery();

			while (res.next())
				family = family + res.getInt(7); // individuals - family+solo

			query = conn.prepareStatement(sql3);
			res = query.executeQuery();

			while (res.next())
				group = group + res.getInt(7); // groups

			query = conn.prepareStatement(sql4);
			res = query.executeQuery();

			while (res.next())
				subscriber = subscriber + res.getInt(7); // subscribers

			numberOfVisitorsPerType.add(solo + family);
			numberOfVisitorsPerType.add(group);
			numberOfVisitorsPerType.add(subscriber);
			numberOfVisitorsPerType.add(solo + family + group + subscriber);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return numberOfVisitorsPerType;
	}

	/// ofir n
	
	public ArrayList<?> createIncomeReport(int month){ // individual visitors (solo,familty) , orginized (Group) , subscribers.
		    int solo = 0,family = 0,group = 0,subscriber = 0;
		    
			ArrayList<Integer> totalIncomePerType=new ArrayList<>();
		
			String sql1="SELECT g8gonature.order.price FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Solo Visit' AND g8gonature.order.travelerId NOT IN (  SELECT g8gonature.subscriber.travelerId   FROM  g8gonature.subscriber)";

			String sql2="SELECT g8gonature.order.price FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Family Visit' AND g8gonature.order.travelerId NOT IN (  SELECT g8gonature.subscriber.travelerId   FROM  g8gonature.subscriber)";
			String sql3="SELECT g8gonature.order.price FROM g8gonature.order WHERE month(orderDate)="+month+" AND orderType='Group Visit' AND g8gonature.order.travelerId NOT IN (  SELECT g8gonature.subscriber.travelerId   FROM  g8gonature.subscriber)";
			
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
				totalIncomePerType.add(solo+family+group+subscriber);// total


				
				
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

	/* Lior */
	/* get pending after date passed for park ID */
	public ArrayList<Integer> getParkPendingDatePassed(ArrayList<?> parameters) {
		ArrayList<Integer> pending = new ArrayList<Integer>();
		int parkId = (int) parameters.get(0);
		int month = (int) parameters.get(1);

		String sql = "SELECT COUNT(*) FROM g8gonature.order WHERE parkId = ? AND MONTH(orderDate) = ? AND orderStatus = ? AND orderDate < curdate()";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, parkId);
			query.setInt(2, month);
			query.setString(3, OrderStatusName.CONFIRMED.toString());

			ResultSet res = query.executeQuery();
			while (res.next())
				pending.add(new Integer(res.getInt(1)));
		} catch (SQLException e) {
			System.out.println("Could not execute getParkPendingDatePassed query");
			e.printStackTrace();
		}
		return pending;
	}

}
