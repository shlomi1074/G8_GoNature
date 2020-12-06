package Util;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

public class sendToClient {
	
	public static void sendToClientMsg(ConnectionToClient client, String msg)
	{
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendToClientMsg(ConnectionToClient client, ArrayList<String> msg)
	{
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
