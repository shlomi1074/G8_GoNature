package Util;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

/**
 * This class is being used as a wrapper to the messages that are sent to the client
 */
public class sendToClient {

	/**
	 * This function sends String message to a given client
	 * 
	 * @param parameters - client to send the message to, ArrayList of object string that contain message
	 */
	public static void sendToClientMsg(ConnectionToClient client, String msg) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function sends String message to a given client
	 * 
	 * @param parameters - client to send the message to, ArrayList of object string that contain message
	 */
	public static void sendToClientMsg(ConnectionToClient client, ArrayList<String> msg) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
