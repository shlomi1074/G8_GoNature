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
		// return false;

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
	public static boolean sendEmail(Messages msg) {
		ClientToServerRequest<Messages> request = new ClientToServerRequest<>(Request.SEND_EMAIL);
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
	public static void sendMailInBackgeound(Messages msg) {
		Task<Boolean> mailTask = new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				NotificationControl.sendEmail(msg);
				return true;
			}
		};
		// send email in a background thread
		new Thread(mailTask).start();

	}

	/* For future use - when we want to send html */
	public String getEmailHtmlAsString() {
		StringBuilder emailText = new StringBuilder();
		try {
			Scanner scanner = new Scanner(getClass().getResourceAsStream("/resources/INVOICE.html"));
			while (scanner.hasNext()) {
				emailText.append(scanner.nextLine()).append("\n");
			}
			scanner.close();
			System.out.println(emailText);
		} catch (Exception ex) {
			System.out.println("Error while trying to convert html file as string");
			ex.printStackTrace();
		}
		return emailText.toString();
	}

}
