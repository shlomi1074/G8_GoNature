package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.report;
import logic.ClientToServerRequest.Request;

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
	
	//Alon
	public static void countSolosEnterTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_ENTER_SOLO_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}
	
	public static void countSubsEnterTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_ENTER_SUBS_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}
	
	public static void countGroupsEnterTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_ENTER_GROUP_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}
	
	public static void countSolosVisitTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_VISIT_SOLO_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}
	
	public static void countSubsVisitTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_VISIT_SUBS_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}
	
	public static void countGroupsVisitTime(int month) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.COUNT_VISIT_GROUP_VISITORS,
				new ArrayList<String>(Arrays.asList(String.valueOf(month))));
		ClientUI.chat.accept(request);

	}
	
	/*Lior*/
	/*get reports from DB*/
	public static ArrayList<report> getReports() {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_REPORTS,
				new ArrayList<String>(Arrays.asList()));
		ClientUI.chat.accept(request);
		ArrayList<report> report = ChatClient.responseFromServer.getResultSet();
		return ChatClient.responseFromServer.getResultSet();
		
	}
	/*Lior*/
	/*get cancelled orders number*/
	public static ArrayList<Integer> getParkCancels(int parkID,int month) {
		ClientToServerRequest<Integer> request = new ClientToServerRequest<>(Request.GET_CANCELS,
				new ArrayList<Integer>(Arrays.asList(parkID,month)));
		ClientUI.chat.accept(request);
		ArrayList<Integer> cancels = ChatClient.responseFromServer.getResultSet();
		return ChatClient.responseFromServer.getResultSet();
	}
	
	// ofir n
	public static void showReport(ArrayList<String> arrayOfRequests){ 
		  
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.MANAGER_REPORT,
				arrayOfRequests);
		
		ClientUI.chat.accept(request);
	
	}
	
	// ofir n
	public static void addNewReportToDB(ArrayList<String> monthAndType) {
		
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.ADD_REPORT_TO_DB,
				monthAndType);
		
		ClientUI.chat.accept(request);
		
		
	}

}
