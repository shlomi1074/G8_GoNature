package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ParkControl;
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

	@FXML
	private JFXComboBox<String> parkComboBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComboBoxs();
	}

	private void initComboBoxs() {
		/* Set parks combobox to load dynamically from database */ // Shlomi
		ArrayList<String> parksNames = ParkControl.getParksNames();
		if (parksNames != null) {
			parkComboBox.getItems().addAll(parksNames);
		}

		/* Listener - activate on every item selected */
		parkComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (newItem != null) {
				loadParameters(ParkControl.getParkByName(newItem));
			}
		});

	}

	// Shlomi
	private void loadParameters(Park park) {
		if (park != null)
			setLabels(park);
	}

	// Shlomi
	private void setLabels(Park park) {
		currentLabel.setText(park.getCurrentVisitors() + "");
		maxLabel.setText(park.getMaxVisitors() + "");
		allowedLabel.setText(park.getGapBetweenMaxAndCapacity() + "");
		actualLabel.setText((park.getMaxVisitors() - park.getCurrentVisitors()) + "");
	}

}
