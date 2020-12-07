package gui;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RescheduleController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private JFXTextArea rescheduleTextArea;

	@FXML
	private JFXListView<Date> datesListView;

	@FXML
	private Button newTimeBtn;

	@FXML
	private Button waitingListBtn;

	@FXML
	private Label selectedTimeLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();

	}

	private void init() {
		/* Temp just so we can imagine how it suppose to be */
		for (int i = 0; i < 10; i++) {
			try {
				datesListView.getItems().add(new SimpleDateFormat("dd-MM-yyyy, HH:mm").parse("22-12-2020, 12:00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	}

}
