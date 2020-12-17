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

	/**
	 * This function get an id and returns Subscriber object that match the given id.
	 *  
	 * @param id - the id of the subscriber
	 * @return Subscriber object, null if not exist
	 */
	public static Subscriber getSubscriber(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_SUBSCRIBER,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Subscriber subscriber = (Subscriber) ChatClient.responseFromServer.getResultSet().get(0);
		return subscriber;
	}

	/**
	 * This function gets an id and check if there is a traveler (regular of subscriber)
	 * that match this id.
	 * 
	 * @param id - the id to check 
	 * @return true if the exist in the system, false otherwise
	 */
	public static boolean isTravelerExist(String id) {
		/* First we check if the id is subscriber */
		Subscriber subscriber = getSubscriber(id);
		if (subscriber != null) // If not null means the subscriber exist
			return true;
		else {
			/* If there is no subscriber with such id
			 * we check if the id is regular traveler */
			ClientToServerRequest request = new ClientToServerRequest<>(Request.TRAVELER_LOGIN_ID, new ArrayList<String>(Arrays.asList(id)));
			ClientUI.chat.accept(request);
			Traveler traveler = (Traveler) ChatClient.responseFromServer.getResultSet().get(0);
			if (traveler != null) // If not null means the traveler exist
				return true;

		}
		return false;
	}

	// Lior
	public static void deleteFromTravelerTable(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.DELETE_TRAVELER,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
	}

	// Lior
	public static void insertSubscriberToSubscriberTable(String id, String firstName, String lastName, String email,
			String phoneNumber, String cardNumber, String type, String numberOfParticipants) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.INSERT_TO_SUBSCRIBER,
				new ArrayList<String>(Arrays.asList(id, firstName, lastName, email, phoneNumber, cardNumber, type,
						numberOfParticipants)));
		ClientUI.chat.accept(request);
	}

	// Lior
	public static void insertCardToCreditCardTable(String id, String cardNumber, String cardExpiryDate, String ccv) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.INSERT_TO_CREDITCARD,
				new ArrayList<String>(Arrays.asList(id, cardNumber, cardExpiryDate, ccv)));
		ClientUI.chat.accept(request);
	}

}
