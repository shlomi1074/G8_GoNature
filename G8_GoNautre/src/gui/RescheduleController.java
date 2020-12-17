package gui;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import Controllers.NotificationControl;
import Controllers.OrderControl;
import Controllers.ParkControl;
import alerts.CustomAlerts;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Messages;
import logic.Order;
import logic.OrderStatusName;
import logic.Traveler;
import resources.MsgTemplates;

public class RescheduleController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private JFXTextArea rescheduleTextArea;

	@FXML
	private JFXListView<String> datesListView;

	@FXML
	private Button newTimeBtn;

	@FXML
	private Button waitingListBtn;

	@FXML
	private Label selectedTimeLabel;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private ProgressIndicator pi;

	private Order order;
	private Order recentOrder;
	private Traveler traveler;
	private Stage rescheduleStage;
	private Stage orderStage;
	boolean isOrderFromMain = false;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
		getAlternativeDates();
	}

	@FXML
	private void placeOrder() {
		String newDateAndTime = datesListView.getSelectionModel().getSelectedItem();
		String newDate = newDateAndTime.split(",")[0];
		String newTime = newDateAndTime.split(",")[1].trim();
		Order newOrder = order;
		newOrder.setOrderDate(newDate);
		newOrder.setOrderTime(newTime);

		if (OrderControl.addOrder(order, traveler)) {
			System.out.println("Order added successfuly ");
			recentOrder = OrderControl.getTravelerRecentOrder(traveler.getTravelerId());

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String dateAndTime = dtf.format(now);
			String date = dateAndTime.split(" ")[0];
			String time = dateAndTime.split(" ")[1];
			if (recentOrder != null) {

				String emailContent = getEmailConent();
				/* Add message to data base */
				NotificationControl.sendMessageToTraveler(traveler.getTravelerId(), date, time,
						MsgTemplates.orderConfirmation[0], emailContent, String.valueOf(recentOrder.getOrderId()));

				/* Send message by mail */
				Messages msg = new Messages(0, traveler.getTravelerId(), date, time, MsgTemplates.orderConfirmation[0],
						emailContent, recentOrder.getOrderId());
				NotificationControl.sendMailInBackgeound(msg);

				if (isOrderFromMain)
					orderStage.close();

				rescheduleStage.close();
				loadOrderConfirmation(); // load recipe

			}
		}

	}

	private String getEmailConent() {
		String parkName = ParkControl.getParkName(String.valueOf(recentOrder.getParkId()));
		return String.format(MsgTemplates.orderConfirmation[1].toString(), String.valueOf(recentOrder.getOrderId()),
				parkName, recentOrder.getOrderDate(), recentOrder.getOrderTime(), recentOrder.getOrderType(),
				String.valueOf(recentOrder.getNumberOfParticipants()), String.valueOf(recentOrder.getPrice()));
	}

	private void getAlternativeDates() {

		ArrayList<String> availableDatesList = get6AvailableDatesList();
		datesListView.getItems().addAll(availableDatesList);

	}

	/* This function returns a list with 6 available dates */
	private ArrayList<String> get6AvailableDatesList() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Order tempOrder = order;
		String originalDate = order.getOrderDate();
		String currentTime = "8:00";
		String currentDate = originalDate;
		int counter = 0;
		ArrayList<String> availableDatesList = new ArrayList<String>();
		while (counter != 6) {
			tempOrder.setOrderDate(currentDate);
			tempOrder.setOrderTime(currentTime);
			if (OrderControl.isDateAvailable(tempOrder)) {
				availableDatesList.add(tempOrder.getOrderDate() + ", " + tempOrder.getOrderTime());
				counter++;
			}

			int hour = Integer.parseInt((currentTime.split(":")[0]));

			hour += 1;
			if (hour > 17) {
				currentTime = "8:00";
				try {
					c.setTime(sdf.parse(currentDate));
				} catch (Exception e) {
					// TODO: handle exception
				}
				// Number of Days to add
				c.add(Calendar.DAY_OF_MONTH, 1);
				// Date after adding the days to the given date
				currentDate = sdf.format(c.getTime());

			} else {
				currentTime = String.valueOf(hour) + ":00";
			}

		}

		return availableDatesList;
	}

	private void init() {
		/* Temp just so we can imagine how it suppose to be */
		datesListView.getItems().clear();
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
			if (recentOrder != null) {
				String parkName = ParkControl.getParkName(String.valueOf(recentOrder.getParkId()));

				String msgContent = String.format(MsgTemplates.enterToWaitingList[1].toString(), parkName,
						order.getOrderDate(), order.getOrderTime());
				NotificationControl.sendMessageToTraveler(traveler.getTravelerId(), date, time,
						MsgTemplates.enterToWaitingList[0], msgContent, String.valueOf(recentOrder.getOrderId()));

				CustomAlerts alert = new CustomAlerts(AlertType.INFORMATION, MsgTemplates.enterToWaitingList[0],
						MsgTemplates.enterToWaitingList[0], msgContent);

				Optional<ButtonType> result = alert.showAndWait();

				if (!result.isPresent() || result.get() == ButtonType.OK) {
					rescheduleStage.close();
				}
			}

		} else {
			new CustomAlerts(AlertType.ERROR, MsgTemplates.enterToWaitingList[0], MsgTemplates.enterToWaitingList[0],
					MsgTemplates.enterToWaitingList[1]).showAndWait();
		}

		if (isOrderFromMain) {
			orderStage.close();
		}
		rescheduleStage.close();
	}

	private void loadOrderConfirmation() {
		try {
			Stage thisStage = this.rescheduleStage;
			Stage newStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrderConfirmation.fxml"));
			OrderConfirmationController controller = new OrderConfirmationController();
			controller.setOrder(recentOrder);
			controller.setTraveler(traveler);
			controller.setSummaryPayment(String.valueOf(recentOrder.getPrice()));
			controller.setStage(newStage);
			loader.setController(controller);
			loader.load();
			Parent p = loader.getRoot();

			newStage.setTitle("Order Confirmation");
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
			if (isOrderFromMain)
				thisStage.close();
		} catch (IOException e) {
			System.out.println("faild to load form");
			e.printStackTrace();
		}

	}

	public void setSelectedTimeLabel(String DateAndTime) {
		this.selectedTimeLabel.setText(DateAndTime);
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public void setRescheduleStage(Stage stage) {
		this.rescheduleStage = stage;
	}

	public void setOrderStage(Stage stage) {
		this.orderStage = stage;
	}

	public void setOrderFromMain(boolean isOrderFromMain) {
		this.isOrderFromMain = isOrderFromMain;
	}

}
