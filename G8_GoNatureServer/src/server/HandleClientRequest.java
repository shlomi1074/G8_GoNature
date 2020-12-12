package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import Util.sendToClient;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
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
					Subscriber sub = mysqlFunction.GetSubscriberBySubId(request.getParameters());
					response = new ServerToClientResponse(Response.SUBSCRIBER_LOGIN_ID_RESPONSE);
					response.setResultSet(new ArrayList<Traveler>(Arrays.asList(sub)));
					client.sendToClient(response);
				}
				client.sendToClient("");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
