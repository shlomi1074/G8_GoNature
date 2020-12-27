package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import logic.Employees;
import logic.Subscriber;
import logic.Traveler;

/**
 * 
 *
 */
public class AutenticationControl {

	/**
	 * This function handle the traveler login by id
	 * 
	 * @param id  the traveler's id
	 * @return 0  on success
	 * @return 1  traveler already connected
	 * @return 2  traveler id does not exist
	 */
	public static int loginById(String id) {
		if (isConnected(id))
			return 1;
		else {
			if (TravelerControl.isTravelerExist(id)) {
				insertTologgedinTable(id);
				return 0;
			}

			return 2;
		}

	}

	/**
	 * This function handle the traveler login by subscriber id
	 * 
	 * @param subID - the traveler's subscriber id
	 * @return 0  on success
	 * @return 1  traveler already connected
	 * @return 2  traveler id does not exist
	 */
	public static int loginBySubId(String subID) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.SUBSCRIBER_LOGIN_SUBID,
				new ArrayList<String>(Arrays.asList(subID)));
		ClientUI.chat.accept(request);
		Subscriber sub = (Subscriber) ChatClient.responseFromServer.getResultSet().get(0);
		if (sub == null)
			return 2;
		else {
			String id = sub.getTravelerId();
			if (isConnected(id)) {
				return 1;
			} else {
				insertTologgedinTable(id);
				ClientUI.chat.accept(request);
				return 0;
			}

		}

	}

	/**
	 * This function get a traveler's id and ask the server to
	 * insert it to "loggedIn" table.
	 *  
	 * @param id  the traveler's id.
	 */
	private static void insertTologgedinTable(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.INSERT_TO_LOGGEDIN,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);

	}

	/**
	 * This function gets a user id and check if the user is connected to the system.
	 *  
	 * @param id  the user id (traveler of member)
	 * @return true if connected, false otherwise.
	 */
	public static boolean isConnected(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.IS_CONNECTED,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isResult();
	}

	/**
	 * This function gets an id and checks if there is such traveler with the same id.
	 * 
	 * @param id  the id to check
	 * @return true if there is traveler with this id.
	 */
	public static boolean isTravelerExist(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.TRAVELER_LOGIN_ID,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Traveler traveler = (Traveler) ChatClient.responseFromServer.getResultSet().get(0);
		if (traveler == null)
			return false;
		return true;

	}
	
	/**
	 * This function gets an id and checks if there is such member(employee) with the same id
	 * 
	 * @param id  the traveler's id
	 * @param pass  the member's password
	 * @return true  traveler exists.
	 * @return false  traveler not exists.
	 */
	public static boolean isMemberExist(String id,String pass) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.MEMBER_LOGIN,
				new ArrayList<String>(Arrays.asList(id,pass)));
		ClientUI.chat.accept(request);
		Employees member = (Employees) ChatClient.responseFromServer.getResultSet().get(0);
		if (member == null)
			return false;
		return true;

	}
	
	/**
	 * This function handle the member(employee) login by subscriber id
	 * 
	 * @return 0 - on success
	 * @return 1 - member already connected
	 * @return 2 - member id does not exist
	 */
	public static int memberLoginHandler(String id,String password) {
		boolean connected =isConnected(id);
		boolean mem_exsit = isMemberExist(id,password);
		if(connected&&mem_exsit) 
			return 1;
		if(!connected&&mem_exsit) {
			insertTologgedinTable(id);
			return 0;
		}
		return 2;
			
	}
	
	/**
	 * This function get a user's id and ask the server to
	 * delete it from "loggedIn" table.
	 *  
	 * @param id  the user's id.
	 */
	public static void userLogout(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.LOGOUT,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
	}
	
	/*End of Alon's 12.12.20 edit*/

}
