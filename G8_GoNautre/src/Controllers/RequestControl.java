package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ClientUI;
import logic.ClientToServerRequest;
import logic.Park;
import logic.ClientToServerRequest.Request;

public class RequestControl {
	
	
	public static void addNewRequest(ArrayList<?> arrayOfRequests){ 
		  
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.MANAGER_REQUEST,
				arrayOfRequests);
		
		ClientUI.chat.accept(request);

		
		
	}
	
	
	public static void viewcurrentRequests() {
		
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.VIEW_MANAGER_REQUEST,
				new ArrayList<>());
		
		ClientUI.chat.accept(request);
	
	}

}
