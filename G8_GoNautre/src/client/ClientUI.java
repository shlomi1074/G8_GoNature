package client;

import client.ClientController;
import gui.ClientConfigurationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ClientUI extends Application {
	public static ClientController chat;
	@Override
	public void start(Stage primaryStage) {
		try {
			//Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/gui/clientConfiguration.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/clientConfiguration.fxml"));
			ClientConfigurationController controller = new ClientConfigurationController();
			loader.setController(controller);
			controller.setStage(primaryStage);
			loader.load();
			Parent p = loader.getRoot();

			primaryStage.setTitle("GoNature Client Set UP");
			primaryStage.setScene(new Scene(p));
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}