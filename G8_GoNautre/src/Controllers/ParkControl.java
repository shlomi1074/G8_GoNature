package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import logic.Park;

public class ParkControl {
	
	// Shlomi
	public static void viewParkParameters(String parkId) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.VIEW_PARAMETERS,
				new ArrayList<String>(Arrays.asList(parkId)));
		ClientUI.chat.accept(request);
		
	}
	
	// Shlomi
	public static ArrayList<String> getParksNames() {
		ArrayList<Park> parks= getAllParks();
		ArrayList<String> parksNames = new ArrayList<String>();
		for (Park park : parks) {
			parksNames.add(park.getParkName());
		}
		return parksNames;
		
	}
	// Shlomi
	public static ArrayList<Park> getAllParks() {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_ALL_PARKS);
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getResultSet();
		
	}
}
