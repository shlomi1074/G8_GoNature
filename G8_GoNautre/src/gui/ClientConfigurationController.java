package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import client.ClientController;
import client.ClientUI;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientConfigurationController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private JFXTextField ipTextField;

	@FXML
	private JFXTextField portTextField;

	@FXML
	private JFXButton connectButton;

	@FXML
	private Circle circleStatus;

	@FXML
	private JFXButton startAppBtn;
	
	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();

	}

	private void init() {
		/* When the user press close(X) */
		getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				ClientUI.chat.getClient().quit();
			}
		});

	}

	@FXML
	private void connectButtonClick() {
		if (!ipTextField.getText().isEmpty() && !portTextField.getText().isEmpty())
			try {
				ClientUI.chat = new ClientController(ipTextField.getText(), Integer.parseInt(portTextField.getText()));
				circleStatus.setFill(Color.GREEN);
			} catch (NumberFormatException e) {
				circleStatus.setFill(Color.RED);
				e.printStackTrace();
			} catch (IOException e) {
				circleStatus.setFill(Color.RED);
				e.printStackTrace();
			}
		else {
			/* Show alert error */
		}

	}

	@FXML
	private void loadApp() {
		if (ClientUI.chat != null) {
			try {
				Stage thisStage = getStage();
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainScreen.fxml"));
				MainScreenController controller = new MainScreenController();
				loader.setController(controller);
				Stage newStage = new Stage();
				controller.setStage(newStage);
				loader.load();
				Parent p = loader.getRoot();
				newStage.setTitle("GoNature System");
				newStage.setScene(new Scene(p));
				newStage.setResizable(false);
				newStage.show();
				thisStage.close();
			} catch (Exception e) {
				System.out.println("faild to load form");
				e.printStackTrace();
			}
		}
		else {
			/* First connect */
		}
	}

	private Stage getStage() {
		return stage;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
