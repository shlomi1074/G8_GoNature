package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import Util.sendToClient;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import logic.Discount;
import logic.Order;
import logic.Park;
import logic.ServerToClientResponse;
import logic.ServerToClientResponse.Response;
import logic.Subscriber;
import logic.Traveler;
import ocsf.server.ConnectionToClient;
import sqlHandler.mysqlConnection;
import sqlHandler.mysqlFunctions;

public class HandleClientRequest implements Runnable {

	private ConnectionToClient client = null;
	private Object msg = null;

	private Connection mysqlconnection;
	private mysqlFunctions mysqlFunction;

	public HandleClientRequest(ConnectionToClient client, Object msg) {
		this.client = client;
		this.msg = msg;

		try {
			mysqlconnection = mysqlConnection.getInstance().getConnection();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			sendToClient.sendToClientMsg(client, "DB Error");
			e.printStackTrace();
		}
		mysqlFunction = new mysqlFunctions(mysqlconnection);
	}

	/* HERE WE NEED TO HANDLE THE CLIENT MSG */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		ServerToClientResponse response;
		if (msg instanceof ClientToServerRequest)
			try {
				ClientToServerRequest<?> request = (ClientToServerRequest<?>) msg;
				if (request.getRequestType().equals(Request.IS_CONNECTED)) {
					boolean res = mysqlFunction.checkIfConnected(request.getParameters());
					response = new ServerToClientResponse(Response.IS_CONNECTED_RESPONSE);
					response.setResult(res);
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.TRAVELER_LOGIN_ID)) {
					Traveler traveler = mysqlFunction.isTravelerExist(request.getParameters());
					response = new ServerToClientResponse(Response.TRAVELER_LOGIN_ID_RESPONSE);
					response.setResultSet(new ArrayList<Traveler>(Arrays.asList(traveler)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.INSERT_TO_LOGGEDIN)) {
					mysqlFunction.insertToLoggedInTable(request.getParameters());
					client.sendToClient("Finished Insert");
				}
				if (request.getRequestType().equals(Request.SUBSCRIBER_LOGIN_SUBID)) {	
					Subscriber sub = mysqlFunction.getSubscriberBySubId(request.getParameters());
					response = new ServerToClientResponse(Response.SUBSCRIBER_LOGIN_ID_RESPONSE);
					response.setResultSet(new ArrayList<Traveler>(Arrays.asList(sub)));
					client.sendToClient(response);
				}
				// Shlomi
				if (request.getRequestType().equals(Request.GET_PARK_BY_ID)) {	
					Park park = mysqlFunction.getParkById(request.getParameters());
					response = new ServerToClientResponse(Response.GET_PARK_BY_ID_RESPONSE);
					response.setResultSet(new ArrayList<Park>(Arrays.asList(park)));
					client.sendToClient(response);
				}
				
				// Shlomi
				if (request.getRequestType().equals(Request.GET_PARK_BY_NAME)) {	
					Park park = mysqlFunction.getParkByName(request.getParameters());
					response = new ServerToClientResponse(Response.GET_PARK_BY_NAME_RESPONSE);
					response.setResultSet(new ArrayList<Park>(Arrays.asList(park)));
					client.sendToClient(response);
				}
				// Shlomi
				if (request.getRequestType().equals(Request.GET_SUBSCRIBER)) {	
					Subscriber sub = mysqlFunction.getSubscriberById(request.getParameters());
					response = new ServerToClientResponse(Response.GET_SUBSCRIBER_RESPONSE);
					response.setResultSet(new ArrayList<Subscriber>(Arrays.asList(sub)));
					client.sendToClient(response);
				}
				// Shlomi
				if (request.getRequestType().equals(Request.GET_ALL_PARKS)) {	
					ArrayList<Park> parks = mysqlFunction.getAllParks(request.getParameters());
					response = new ServerToClientResponse(Response.GET_ALL_PARKS_RESPONSE);
					response.setResultSet(parks);
					client.sendToClient(response);
				}
				// Shlomi
				if (request.getRequestType().equals(Request.GET_MAX_DISCOUNT)) {	
					Discount discount = mysqlFunction.getMaxDisount(request.getParameters());
					response = new ServerToClientResponse(Response.GET_MAX_DISCOUNT_RESPONSE);
					response.setResultSet(new ArrayList<Discount>(Arrays.asList(discount)));
					client.sendToClient(response);
				}
				
				// Shlomi
				if (request.getRequestType().equals(Request.GET_ORDERS_BETWEEN_DATES)) {	
					ArrayList<Order> orders = mysqlFunction.getOrderBetweenTimes(request.getParameters());
					response = new ServerToClientResponse(Response.GET_ORDERS_BETWEEN_DATES_RESPONSE);
					response.setResultSet(orders);
					client.sendToClient(response);
				}
				// Shlomi
				if (request.getRequestType().equals(Request.ADD_ORDER)) {	
					boolean result = mysqlFunction.AddNewOrder(request.getObj());
					response = new ServerToClientResponse(Response.ADD_ORDER_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}
				// Shlomi
				if (request.getRequestType().equals(Request.ADD_TRAVELER)) {	
					boolean result = mysqlFunction.AddTraveler(request.getObj());
					response = new ServerToClientResponse(Response.ADD_TRAVELER_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}
				
				// Shlomi
				if (request.getRequestType().equals(Request.GET_RECENT_ORDER)) {	
					Order order = mysqlFunction.getRecentOrder(request.getParameters());
					response = new ServerToClientResponse(Response.GET_RECENT_ORDER_RESPONSE);
					response.setResultSet(new ArrayList<Order>(Arrays.asList(order)));
					client.sendToClient(response);
				}

				client.sendToClient("");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
