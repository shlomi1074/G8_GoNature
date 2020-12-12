package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.Traveler;

public class ProfileController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private Label profileNameLabel;

	@FXML
	private Label profileLastNameLabel;

	@FXML
	private Label profileIDLabel;

	@FXML
	private Label ProfileEmailLabel;

	@FXML
	private Label profileAccountTypeLabel;

	@FXML
	private Label profileParkLabel;

	@FXML
	private Label parkLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}

	/* NEED TO CHANGE TO IMPORT PROFILE DATA FROM DATABASE */
	private void init() {
//		profileParkLabel.setDisable(true);
//		profileParkLabel.setVisible(false);
//		parkLabel.setDisable(true);
//		parkLabel.setVisible(false);
//		profileAccountTypeLabel.setText("Family subscriber");
	}

}
