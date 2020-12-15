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

	// Shlomi
	public static Subscriber getSubscriber(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_SUBSCRIBER,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Subscriber subscriber = (Subscriber) ChatClient.responseFromServer.getResultSet().get(0);
		return subscriber;
	}

	// Shlomi 
	/* This function get id and return true if there is subscriber or a regular traveler with this id */
	public static boolean isTravelerExist(String id) {
		/* First we check if the id is subscriber */
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_SUBSCRIBER,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Subscriber subscriber = (Subscriber) ChatClient.responseFromServer.getResultSet().get(0);
		if (subscriber != null) // If not null means the subscriber exist
			return true;
		else {
			/* Now we check if the id is regular traveler */
			request = new ClientToServerRequest<>(Request.TRAVELER_LOGIN_ID, new ArrayList<String>(Arrays.asList(id)));
			ClientUI.chat.accept(request);
			Traveler traveler = (Traveler) ChatClient.responseFromServer.getResultSet().get(0);
			if (traveler != null) // If not null means the traveler exist
				return true;

		}

		return false;

	}

	public static void deleteFromTravelerTable(String id) {
		// TODO Auto-generated method stub
		
	}

	public static void insertSubscriberToSubscriberTable(String id, String firstName, String lastName, String email,
			String phoneNumber, String cardNumber, String type, String numberOfParticipants) {
		// TODO Auto-generated method stub
		
	}

	public static void insertCardToCreditCardTable(String id, String cardNumber, String cardExpiryDate, String ccv) {
		// TODO Auto-generated method stub
		
	}

}
