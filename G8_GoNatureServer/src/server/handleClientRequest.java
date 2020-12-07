package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import Util.sendToClient;
import ocsf.server.ConnectionToClient;
import sqlHandler.mysqlConnection;
import sqlHandler.mysqlFunctions;

public class handleClientRequest implements Runnable {

	private ConnectionToClient client = null;
	private Object msg = null;

	private Connection mysqlconnection;
	private mysqlFunctions mysqlFunction;

	public handleClientRequest(ConnectionToClient client, Object msg) {
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
		System.out.println((String)msg);
		try {
			client.sendToClient("finished");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
