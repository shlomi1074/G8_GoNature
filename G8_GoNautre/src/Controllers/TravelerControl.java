package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.Traveler;
import logic.ClientToServerRequest.Request;
import logic.Subscriber;

public class TravelerControl {
	
	//Shlomi
	public static Subscriber getSubscriber(String id) {	
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_SUBSCRIBER,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Subscriber subscriber = (Subscriber) ChatClient.responseFromServer.getResultSet().get(0);
		return subscriber;
	}

}
