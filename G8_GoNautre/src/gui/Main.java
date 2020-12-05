package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/MainScreen.fxml"));
			primaryStage.setTitle("GoNature System");
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(true);
			primaryStage.setMinHeight(800);
			primaryStage.setMinWidth(1100);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}