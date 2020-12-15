package gui;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import Controllers.NotificationControl;
import Controllers.OrderControl;
import alerts.CustomAlerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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
	private Stage rescheduleStage;

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
		this.order.setOrderStatus(OrderStatusName.waiting.name());
		if (OrderControl.addOrder(order, traveler)) {
			System.out.println("Order added successfuly - waiting list");
			/* NEED TO SEND EMAIL AND SEND MESSAGE */
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String dateAndTime = dtf.format(now);
			String date = dateAndTime.split(" ")[0];
			String time = dateAndTime.split(" ")[1];
			Order recentOrder = OrderControl.getTravelerRecentOrder(traveler.getTravelerId());

			/* Insert massage to data base */ /* NEED TO BE CHANGED WHEN ADDED MESSAGES */
			if (recentOrder != null)
				NotificationControl.sendMessageToTraveler(traveler.getTravelerId(), date, time, "Enter waiting list",
						"You have been entered the waiting list." + "Order id: " + recentOrder.getOrderId()
								+ "\nVisit date: " + recentOrder.getOrderDate() + " " + recentOrder.getOrderTime(),
						String.valueOf(recentOrder.getOrderId()));
			
			CustomAlerts alert = new CustomAlerts(AlertType.INFORMATION, "Enter Waiting List", "Enter Waiting List",
					"You have been entered to the waiting list.\n"
							+ "We will let you know if some one cancel their visit.");

			Optional<ButtonType> result = alert.showAndWait();
			if (!result.isPresent() || result.get() == ButtonType.OK) {
				rescheduleStage.close();
			}
		} else {
			new CustomAlerts(AlertType.ERROR, "Error Waiting List", "Error Waiting List",
					"There was error trying to put you in the waiting list.\n" + "Please try again later.")
							.showAndWait();
		}
	}

	public void SetSelectedTimeLabel(String DateAndTime) {
		this.selectedTimeLabel.setText(DateAndTime);
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public void SetRescheduleStage(Stage stage) {
		this.rescheduleStage = stage;
	}

}
