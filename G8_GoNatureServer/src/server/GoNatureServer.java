// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import Util.sendToClient;
import gui.ServerGUIController;
import javafx.scene.paint.Color;
import ocsf.server.*;
import sqlHandler.mysqlConnection;
import sqlHandler.mysqlFunctions;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */

public class GoNatureServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */

	private ServerGUIController serverGUIController;

	private Connection mysqlconnection;
	private mysqlFunctions mysqlFunction;

	public GoNatureServer(int port, ServerGUIController serverGUIController) throws Exception {
		super(port);
		this.serverGUIController = serverGUIController;
		try {
			mysqlconnection = mysqlConnection.getInstance().getConnection();
			mysqlFunction = new mysqlFunctions(mysqlconnection);
			serverGUIController.updateTextAreaLog("Server Connected");
			serverGUIController.updateTextAreaLog("DB Connected");
			serverGUIController.setCircleColor(Color.GREEN);
		} catch (Exception e) {
			serverGUIController.setCircleColor(Color.RED);
			serverGUIController.updateTextAreaLog("Failed to load DB");
			throw e;
		}
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. 
	 * Called when a client connect to the server.
	 */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		serverGUIController.updateTextAreaLog(client.toString() + " Connected");
	}

	/**
	 * This method overrides the one in the superclass. 
	 * Called when a client disconnect from the server.
	 */
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		serverGUIController.updateTextAreaLog(client.toString() + " Disonnected");
	}

	/**
	 * This method overrides the one in the superclass. 
	 * Called when a client disconnect and throw exception from the server.
	 */
	synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
		serverGUIController.updateTextAreaLog("Client Disonnected");
	}

	/* When The server closed this function called */
	@Override
	final protected void serverClosed() {
		try {
			if (mysqlconnection != null)
				mysqlconnection.close();
			System.out.println("Server has been closed");
		} catch (SQLException e) {
			System.out.println("Faild to close JDBC connection");
		}
	}

}
//End of EchoServer class
