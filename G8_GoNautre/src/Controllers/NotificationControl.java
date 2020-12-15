package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
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

}
