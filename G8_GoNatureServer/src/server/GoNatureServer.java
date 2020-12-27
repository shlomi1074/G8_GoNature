package server;

import java.sql.Connection;
import java.sql.SQLException;

import controllers.sqlHandlers.MysqlConnection;
import gui.ServerGUIController;
import javafx.scene.paint.Color;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Shlomi Amar
 * @author Alon Ivshin
 * @author Ofir Vaknin
 * @author Lior Keren
 * @author Ofir Newman
 * 
 * @version December 2020
 */
public class GoNatureServer extends AbstractServer {


	private ServerGUIController serverGUIController;
	public static Connection mysqlconnection;	
	
	/**
	 * Constructs an instance of the GoNature Server.
	 *
	 * @param port The port number to connect on.
	 * @param serverGUIController The Gui Controller of the server
	 * 
	 * @throws Exception If failed to load the server
	 */
	public GoNatureServer(int port, ServerGUIController serverGUIController) throws Exception {
		super(port);
		this.serverGUIController = serverGUIController;
		try {
			mysqlconnection = MysqlConnection.getInstance().getConnection();
			serverGUIController.updateTextAreaLog("Server Connected");
			serverGUIController.updateTextAreaLog("DB Connected");
			serverGUIController.setCircleColor(Color.GREEN);
		} catch (Exception e) {
			serverGUIController.setCircleColor(Color.RED);
			serverGUIController.updateTextAreaLog("Failed to load DB");
			throw e;
		}
		
		/* Create notify thread */
		NotifyTravelers notifyTravelers = new NotifyTravelers(mysqlconnection);
		new Thread(notifyTravelers).start();
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 * For each client's request a new thread is created 
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		HandleClientRequest thread = new HandleClientRequest(client, msg);
		new Thread(thread).start();
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
		serverGUIController.updateTextAreaLog("Total connections to the server: " + this.getNumberOfClients());
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
		serverGUIController.updateTextAreaLog("Total connections to the server: " + (this.getNumberOfClients() - 1));
	}

	/**
	 * Hook method.
	 * Called when The server closed. After 'serverStopped' method.
	 */
	@Override
	final protected void serverClosed() {
		try {
			if (mysqlconnection != null)
				mysqlconnection.close();
			System.out.println("Server has been closed");
		} catch (SQLException e) {
			System.out.println("Faild to close JDBC connection");
		}
		System.exit(0);
	}

}
