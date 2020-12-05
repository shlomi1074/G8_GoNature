package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MemberLoginController implements Initializable {

	@FXML
	private AnchorPane loginContainer;

	@FXML
	private Rectangle rectangle;

	@FXML
	private Label forgotPasswordLabel;

	@FXML
	private AnchorPane personImageContainer;

	@FXML
	private ImageView userImageView;

	@FXML
	private AnchorPane lockImageContainer;

	@FXML
	private ImageView lockImageView;

	@FXML
	private Label createAccountLabel;

	@FXML
	private JFXTextField usernameTextField;

	@FXML
	private JFXPasswordField passwordTextField;

	@FXML
	private JFXButton loginButton;

	private Stage parentStage;

	public MemberLoginController(Stage parentStage) {
		this.parentStage = parentStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/* LOGIN BUTTON LISTENER - NEED TO CHANGE TO LOGIN VIA DATABASE */
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* NEED TO CHANGE WHEN WE IMPLEMNT LOGIN VIA DATABASE */
				String type = "depManager";
				if (type.equals("service"))
					switchScene("ServiceWorker.fxml", "GoNature8 - Service Worker", type);
				else if (type.equals("parkManager"))
					switchScene("ParkManager.fxml", "GoNature8 - Park Manager", type);
				else if (type.equals("entrance"))
					switchScene("EntranceWorker.fxml", "GoNature8 - Entrance Worker", type);
				else if (type.equals("depManager"))
					switchScene("DepartmentManagerScreen.fxml", "GoNature8 - Department Manager", type);
			}
		});
	}

	private void switchScene(String fxmlName, String title, String type) {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
			if (type.equals("service")) {
				ServiceWorkerController controller = new ServiceWorkerController();
				loader.setController(controller);
			} else if (type.equals("parkManager")) {
				ParkManagerController controller = new ParkManagerController();
				loader.setController(controller);
			} else if (type.equals("entrance")) {
				EntranceWorkerController controller = new EntranceWorkerController();
				loader.setController(controller);
			} else if (type.equals("depManager")) {
				DepartmentManagerController controller = new DepartmentManagerController();
				loader.setController(controller);
			}
			loader.load();
			Parent p = loader.getRoot();
			Stage newStage = new Stage();

			newStage.setTitle(title);
			// stage.getIcons().add(new Image("url"));
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
			thisStage.close();
			parentStage.hide();
		} catch (Exception e) {
			System.out.println("faild to load form");
			e.printStackTrace();
		}
	}

	private Stage getStage() {
		return (Stage) loginButton.getScene().getWindow();
	}

}
