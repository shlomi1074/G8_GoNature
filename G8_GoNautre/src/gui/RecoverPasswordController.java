package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import alerts.CustomAlerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class RecoverPasswordController implements Initializable {

	@FXML
	private Button recoverBtn;

	@FXML
	private JFXTextField idTextField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void recoverPasswordBtn() {

		if (idTextField.getText().isEmpty()) {
			new CustomAlerts(AlertType.ERROR, "Bad Input", "Bad Input", "Please enter your id.").showAndWait();
		} else {

			/* NEED TO GET THE EMAIL FROM THE DATABASE */
			
			/* NEED TO SEND EMAIL*/

			new CustomAlerts(AlertType.INFORMATION, "Password Recovery", "Password Recovery",
					"Check your email.\nWe sent your password to your email.").showAndWait();
			getStage().close();
		}

	}

	public Stage getStage() {
		return (Stage) recoverBtn.getScene().getWindow();
	}

}
