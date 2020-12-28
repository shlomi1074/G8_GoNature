package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.report;
import logic.ClientToServerRequest.Request;

@SuppressWarnings("unchecked")
public class ReportsControl {

	/**
	 * This function gets a report and insert it to the database.
	 * 
	 * @param r - the report to add to database
	 * @return true on success, false otherwise
	 */
	public static boolean addReport(report r) {

		removeReport(r);
		ClientToServerRequest<report> request = new ClientToServerRequest<>(Request.INSERT_REPORT,
				new ArrayList<report>(Arrays.asList(r)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isResult();

	}

	/**
	 * This function gets a report and delete it from the database.
	 * 
	 * @param r - the report to delete from the database
	 */
	private static void removeReport(report r) {
		ClientToServerRequest<report> request = new ClientToServerRequest<>(Request.DELETE_REPORT,
				new ArrayList<report>(Arrays.asList(r)));
		ClientUI.chat.accept(request);
	}

	/**
	 * This function receive month of current year and asks the server
	 * to send the number of solo visitors at this month by entrance hour.
	 * 
	 * @param r - the current year month
	 */
	public static void countSolosEnterTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_ENTER_SOLO_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}

	/**
	 * This function receive month of current year and asks the server
	 * to send the number of subscriber visitors at this month by entrance hour.
	 * 
	 * @param r - the current year month
	 */
	public static void countSubsEnterTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_ENTER_SUBS_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}

	/**
	 * This function receive month of current year and asks the server to send the number of group visitors at this month by entrance hour.
	 * 
	 * @param r - the current year month
	 */

	public static void countGroupsEnterTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_ENTER_GROUP_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}

	/**
	 * This function receive month of current year and asks the server
	 * to send the number of solo visitors at this month dived by their visit time.
	 * 
	 * @param r - the current year month
	 */
	public static void countSolosVisitTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_VISIT_SOLO_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}

	/**
	 * This function receive month of current year and asks the server
	 * to send the number of subscriber visitors at this month dived by their visit time.
	 * 
	 * @param r - the current year month
	 */
	public static void countSubsVisitTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_VISIT_SUBS_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}

	/**
	 * This function receive month of current year and asks the server
	 * to send the number of group visitors at this month dived by their visit time.
	 * 
	 * @param r - the current year month
	 */
	public static void countGroupsVisitTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_VISIT_GROUP_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}

	/**
	 * This function gets reports from DB
	 * 
	 * @return ArrayList of reports
	 */
	public static ArrayList<report> getReports() {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_REPORTS,
				new ArrayList<String>(Arrays.asList()));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getResultSet();

	}

	/**
	 * This function gets cancelled orders number for a certain park in a specific month - for cancel report.
	 * 
	 * @param parkID - park's ID
	 * @param month  - month we want report for
	 * @return ArrayList with number of cancelled orders
	 */
	public static ArrayList<Integer> getParkCancels(int parkID, int month) {
		ClientToServerRequest<Integer> request = new ClientToServerRequest<>(Request.GET_CANCELS,
				new ArrayList<Integer>(Arrays.asList(parkID, month)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getResultSet();
	}

	// ofir n
	public static void showReport(ArrayList<String> arrayOfRequests) {

		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.MANAGER_REPORT, arrayOfRequests);

		ClientUI.chat.accept(request);

	}

	// ofir n
	public static void addNewReportToDB(ArrayList<String> monthAndType) {

		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.ADD_REPORT_TO_DB, monthAndType);

		ClientUI.chat.accept(request);

	}

	/**
	 * This function get pending orders after date has passed for a certain park in a specific month - for cancel report.
	 * 
	 * @param parkID - park's ID
	 * @param month  - month we want report for
	 * @return ArrayList with number of pending orders that passed todays date
	 */
	public static ArrayList<Integer> getParkPendingDatePassed(int parkID, int month) {
		ClientToServerRequest<Integer> request = new ClientToServerRequest<>(Request.GET_PENDING_AFTER_DATE_PASSED,
				new ArrayList<Integer>(Arrays.asList(parkID, month)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getResultSet();
	}

}
