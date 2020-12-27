package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import client.ChatClient;
import client.ClientUI;
import javafx.concurrent.Task;
import logic.ClientToServerRequest;
import logic.Messages;
import logic.ClientToServerRequest.Request;

public class NotificationControl {

	public static boolean sendMessageToTraveler(String toId, String sendDate, String sendTime, String subject,
			String content, String orderId) {
		// Ofir Vaknin
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.SEND_MSG_TO_TRAVELER,
				new ArrayList<String>(Arrays.asList(toId, sendDate, sendTime, subject, content, orderId)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isResult();

	}

	/**
	 * This function gets and message and ask the server to send it by email.
	 * 
	 * @param msg - the message to send
	 * @return true on success, false otherwise.
	 */
	private static boolean sendEmail(Messages msg, String email) {
		ClientToServerRequest<Messages> request = null;
		if (email == null) {
			request = new ClientToServerRequest<>(Request.SEND_EMAIL);
		}
		else {
			request = new ClientToServerRequest<>(Request.SEND_EMAIL_WITH_EMAIL);
			request.setInput(email);
		}
		request.setObj(msg);
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isResult();
	}

	/**
	 * This function gets a message and send it by mail. The function creates a new
	 * thread and runs at the background.
	 * 
	 * @param msg - the message to send
	 */
	public static void sendMailInBackgeound(Messages msg, String email) {
		Task<Boolean> mailTask = new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				NotificationControl.sendEmail(msg, email);
				return true;
			}
		};
		// send email in a background thread
		new Thread(mailTask).start();

	}
	
	/**
	 * This function gets traveler messages by his ID
	 * 
	 * @param id - the traveler's id
	 * @return ArrayList of messages
	 */
	public static ArrayList<Messages> getMessages(String id){
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_MESSAGES_BY_ID,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		ArrayList<Messages> messages = ChatClient.responseFromServer.getResultSet();
		
		return ChatClient.responseFromServer.getResultSet();
	}

}
