package entranceReport;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import controllers.sqlHandlers.MysqlConnection;
import controllers.sqlHandlers.ReportsQueries;
import logic.VisitReport;

public class EntranceReportServerSide {

	private static Connection mysqlconnection;
	private ReportsQueries dbQueries;
	private ArrayList<String> parameters;
	
	@Before
	public void setUp() throws Exception {
		mysqlconnection = MysqlConnection.getInstance().getConnection();
		dbQueries = new ReportsQueries(mysqlconnection);
		parameters = new ArrayList<>();
	}

	@Test
	public void CountSolosEnterTimeTest() {
		parameters.add("1");
		ArrayList<VisitReport> actual = dbQueries.CountSolosEnterTime(parameters);
		int expected = 10;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountSolosEnterTimeEmptyMonthTest() {
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountSolosEnterTime(parameters);
		int expected = 0;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountSolosEnterTimeFailedSQLTest() {
		dbQueries = new ReportsQueries(null);
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountSolosEnterTime(parameters);
		assertEquals(null, actual);
	}
	
	@Test
	public void CountSubsEnterTimeTest() {
		parameters.add("1");
		ArrayList<VisitReport> actual = dbQueries.CountSubsEnterTime(parameters);
		int expected = 13;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountSubsEnterTimeEmptyMonthTest() {
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountSubsEnterTime(parameters);
		int expected = 0;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountSubsEnterTimeFailedSQLTest() {
		dbQueries = new ReportsQueries(null);
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountSubsEnterTime(parameters);
		assertEquals(null, actual);
	}
	
	@Test
	public void CountGroupsEnterTimeTest() {
		parameters.add("1");
		ArrayList<VisitReport> actual = dbQueries.CountGroupsEnterTime(parameters);
		int expected = 22;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountGroupsEnterTimeEmptyMonthTest() {
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountGroupsEnterTime(parameters);
		int expected = 0;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountGroupsEnterTimeFailedSQLTest() {
		dbQueries = new ReportsQueries(null);
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountGroupsEnterTime(parameters);
		assertEquals(null, actual);
	}
	
	@Test
	public void CountSolosEnterTimeWithDaysTest() {
		parameters.add("1");
		parameters.add("1");
		ArrayList<VisitReport> actual = dbQueries.CountSolosEnterTimeWithDays(parameters);
		int expected = 1;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountSolosEnterTimeWithDaysEmptyDayTest() {
		parameters.add("1");
		parameters.add("5");
		ArrayList<VisitReport> actual = dbQueries.CountSolosEnterTimeWithDays(parameters);
		int expected = 0;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountSolosEnterTimeWithDaysFailedSQLTest() {
		dbQueries = new ReportsQueries(null);
		parameters.add("2");
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountSolosEnterTimeWithDays(parameters);
		assertEquals(null, actual);
	}
	
	@Test
	public void CountSubsEnterTimeWithDaysTest() {
		parameters.add("1");
		parameters.add("4");
		ArrayList<VisitReport> actual = dbQueries.CountSubsEnterTimeWithDays(parameters);
		int expected = 3;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountSubsEnterTimeWithDaysEmptyDayTest() {
		parameters.add("1");
		parameters.add("5");
		ArrayList<VisitReport> actual = dbQueries.CountSubsEnterTimeWithDays(parameters);
		int expected = 0;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountSubsEnterTimeWithDaysFailedSQLTest() {
		dbQueries = new ReportsQueries(null);
		parameters.add("2");
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountSubsEnterTimeWithDays(parameters);
		assertEquals(null, actual);
	}
	
	@Test
	public void CountGroupsEnterTimeWithDaysTest() {
		parameters.add("1");
		parameters.add("6");
		ArrayList<VisitReport> actual = dbQueries.CountGroupsEnterTimeWithDays(parameters);
		int expected = 11;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountGroupsEnterTimeWithDaysEmptyDayTest() {
		parameters.add("1");
		parameters.add("5");
		ArrayList<VisitReport> actual = dbQueries.CountGroupsEnterTimeWithDays(parameters);
		int expected = 0;
		assertEquals(expected, actual.size());
	}
	
	@Test
	public void CountGroupsEnterTimeWithDaysFailedSQLTest() {
		dbQueries = new ReportsQueries(null);
		parameters.add("2");
		parameters.add("2");
		ArrayList<VisitReport> actual = dbQueries.CountGroupsEnterTimeWithDays(parameters);
		assertEquals(null, actual);
	}

}
