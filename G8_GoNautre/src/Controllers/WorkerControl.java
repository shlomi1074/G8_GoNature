package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.Employees;
import logic.ClientToServerRequest.Request;

public class WorkerControl {
	
	public static Employees getEmployeeByID(String id) {
		
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_EMPLOYEE,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Employees employee = (Employees) ChatClient.responseFromServer.getResultSet().get(0);
		return employee;
		
	}
	
	public static ArrayList<String> getEmployeeEmailAndPassword(String id) {
		
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_EMPLOYEE_PASSWORD,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		ArrayList<String> employeeInfo = ChatClient.responseFromServer.getResultSet();
		return employeeInfo;
		
	}
	
	

}
