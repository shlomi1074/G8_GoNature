package gui;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import Controllers.OrderControl;
import alerts.CustomAlerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.Order;
import logic.OrderStatusName;
import logic.Traveler;

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
	
	private Order order;
	private Traveler traveler;


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
	
	@FXML
	private void enterWaitingList() {
		this.order.setOrderStatus(OrderStatusName.pending.name());
		if (OrderControl.addOrder(order, traveler)) {
			System.out.println("Order added successfuly ");
			new CustomAlerts(AlertType.INFORMATION, "Enter Waiting List", "Enter Waiting List", "You have been entered to the waiting list.\n"
					+ "We will let you know if some one cancel their visit.").showAndWait();
			/* NEED TO SEND EMAIL AND SEND MESSAGE */
		} else {
		}
	}
	
	public void SetSelectedTimeLabel (String DateAndTime) {
		this.selectedTimeLabel.setText(DateAndTime);
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
		
	}

}
