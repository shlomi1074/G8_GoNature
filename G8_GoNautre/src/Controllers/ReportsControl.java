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

}
