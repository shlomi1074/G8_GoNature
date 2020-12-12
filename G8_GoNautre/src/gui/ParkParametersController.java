package gui;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ParkControl;
import client.ChatClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.Park;

public class ParkParametersController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private Label currentLabel;

	@FXML
	private Label maxLabel;

	@FXML
	private Label allowedLabel;

	@FXML
	private Label actualLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadParameters();// Shlomi

	}

	// Shlomi
	private void loadParameters() {
		ParkControl.viewParkParameters("2"); // NEED TO BE CHANGED WHEN THERE IS MEMBER LOGIN
		Park park = (Park) ChatClient.responseFromServer.getResultSet().get(0);
		if (park != null) 
			setLabels(park);
	}
	
	//Shlomi
	private void setLabels(Park park) {
		currentLabel.setText(park.getCurrentVisitors() + "");
		maxLabel.setText(park.getMaxVisitors() + "");
		allowedLabel.setText(park.getGapBetweenMaxAndCapacity() + "");
		actualLabel.setText((park.getMaxVisitors() - park.getCurrentVisitors()) + "");
	}

}
