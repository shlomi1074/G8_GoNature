package gui;

import java.awt.dnd.peer.DropTargetPeer;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.standard.DateTimeAtCompleted;

import Controllers.OrderControl;
import Controllers.ParkControl;
import alerts.CustomAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import logic.Order;
import logic.OrderTb;
import logic.Park;
import logic.Subscriber;
import logic.Traveler;
import logic.OrderStatusName;

public class TravelerViewOrders implements Initializable {

	// Order,String?
	// Everything was ?

	/*
	 * Ofir Avraham Vaknin Deleted 2 labels of visit date Deleted Col of VisitDate
	 */
	ObservableList<OrderTb> ov = FXCollections.observableArrayList(); // Ofir Avraham Vaknin

	@FXML
	private TableView<OrderTb> ordersTableView;

	// int?
	
	@FXML
	private TableColumn<OrderTb, Integer> orderIdCol;

	@FXML
	private TableColumn<OrderTb, String> visitDateCol;

	@FXML
	private TableColumn<OrderTb, String> visitTimeCol;

	@FXML
	private TableColumn<OrderTb, String> orderStatusCol;

	@FXML
	private Button confirmOrderBtn;

	@FXML
	private Button cancelOrderBtn;

	@FXML
	private Label headerLabel;

	@FXML
	private Label orderIdTxt;

	@FXML
	private Label visitDateTxt;

	@FXML
	private Label visitTimeTxt;

	@FXML
	private Label orderStatusTxt;

	// Ofir Avraham Vaknin
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTableView(); // Added
		confirmOrderBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				confirmButton();
				clearLabals();
			}
		});
		cancelOrderBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cancelButton();
				clearLabals();
			}
		});

	}

	// Ofir Avraham Vaknin
	@FXML
	public void loadTableView() {
		//
		/*
		 * Create SQL query to server and ask for all orders.
		 */
		//

		// ArrayList<Order> orders
		String id;
		Traveler trv = TravelerLoginController.traveler;
		if (trv == null) {
			Subscriber sbc = TravelerLoginController.subscriber;
			id = String.valueOf(sbc.getTravelerId());
		} else {
			id = String.valueOf(trv.getTravelerId());
		}
		ArrayList<Order> ordersArrayList = OrderControl.getOrders(id);
		ArrayList<OrderTb> tbOrdersArrayList = OrderControl.convertOrderToOrderTb(ordersArrayList);
		init(tbOrdersArrayList);
		ordersTableView.setItems(getOrders(tbOrdersArrayList));
	}

	// Ofir Avraham Vaknin
	private ObservableList<OrderTb> getOrders(ArrayList<OrderTb> orderArray) {
		ordersTableView.getItems().clear();
		for (OrderTb order : orderArray) {
			ov.add(order);
		}
		return ov;
	}

	// Ofir Avraham Vaknin
	private void init(ArrayList<OrderTb> orders) {
		orderIdCol.setCellValueFactory(new PropertyValueFactory<OrderTb, Integer>("orderId"));
		visitDateCol.setCellValueFactory(new PropertyValueFactory<OrderTb, String>("orderDate"));
		visitTimeCol.setCellValueFactory(new PropertyValueFactory<OrderTb, String>("orderTime"));
		orderStatusCol.setCellValueFactory(new PropertyValueFactory<OrderTb, String>("orderStatus"));

		/*
		 * Set doubleClick on row event On double click on a row, it's load the id into
		 * labels textView
		 */

		ordersTableView.setRowFactory(tv -> {
			TableRow<OrderTb> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					OrderTb clickedRow = row.getItem();
					orderIdTxt.setText(String.valueOf(clickedRow.getOrderId()));
					visitDateTxt.setText(clickedRow.getOrderDate());
					visitTimeTxt.setText(clickedRow.getOrderTime());
					orderStatusTxt.setText(clickedRow.getOrderStatus());
				}
			});
			return row;
		});
	}

	// Ofir Avraham Vaknin
	private void confirmButton() {
		if (orderIdTxt.getText().isEmpty()) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please select one of the orders")
					.showAndWait();
			return;
		}
		if (!orderStatusTxt.getText().equals("pending")) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Order status is not pending")
					.showAndWait();
			return;
		}
		int res = isDateBetween2422(visitDateTxt.getText(), visitTimeTxt.getText());
		switch (res) {
		case -1:
			new CustomAlerts(AlertType.ERROR, "System Error", "System Error",
					"Couldn't choose order to confirm, please try again later").showAndWait();
			break;
		case 1:
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Confrim date has passed").showAndWait();
			break;
		case 2:
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Too early to confirm").showAndWait();
			break;
		case 0:
			boolean orderControlResult = OrderControl.changeOrderStatus(orderIdTxt.getText(),
					OrderStatusName.confirmed);
			if (!orderControlResult) {
				new CustomAlerts(AlertType.ERROR, "System Error", "System Error",
						"Could not confirm this order,please try again later.").showAndWait();

			} else {
				loadTableView();
				new CustomAlerts(AlertType.INFORMATION, "Changes were made", "Changes were made", "Order confirmed")
						.showAndWait();
			}
			break;
		}
	}

	// Ofir Avraham Vaknin
	private void cancelButton() {
		if (orderIdTxt.getText().isEmpty()) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please select one of the orders")
					.showAndWait();
			return;
		}
		if (orderStatusTxt.getText().equals("cancel") || orderStatusTxt.getText().equals("confirmed")) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Order cannot be canceled").showAndWait();
			return;
		}
		if (orderStatusTxt.getText().equals("waiting")) {
			boolean orderControlResult = OrderControl.changeOrderStatus(orderIdTxt.getText(), OrderStatusName.cancel);
			if (!orderControlResult) {
				new CustomAlerts(AlertType.ERROR, "System Error", "System Error",
						"Could not cancel this order,please try again later.").showAndWait();

			} else {
				loadTableView();
				new CustomAlerts(AlertType.INFORMATION, "Changes were made", "Changes were made", "Order canceled")
						.showAndWait();
			}
			return;
		}
		int res = isDateBetween2422(visitDateTxt.getText(), visitTimeTxt.getText());
		switch (res) {
		case -1:
			new CustomAlerts(AlertType.ERROR, "System Error", "System Error",
					"Couldn't choose order to cancel, please try again later").showAndWait();
			break;
		case 1:
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "cancel date has passed").showAndWait();
			break;
		case 2:
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Too early to cancel").showAndWait();
			break;
		case 0:
			boolean orderControlResult = OrderControl.changeOrderStatus(orderIdTxt.getText(), OrderStatusName.cancel);
			if (!orderControlResult) {
				new CustomAlerts(AlertType.ERROR, "System Error", "System Error",
						"Could not cancel this order,please try again later.").showAndWait();
			} else {
				loadTableView();

				new CustomAlerts(AlertType.INFORMATION, "Changes were made", "Changes were made", "Order canceled")
						.showAndWait();

				notifyNextPersonInWaitingListAfterCancel(Integer.parseInt(orderIdTxt.getText()), visitDateTxt.getText(),
						visitTimeTxt.getText());
			}
			break;
		}
	}

	// Ofir Avraham Vaknin
	private void notifyNextPersonInWaitingListAfterCancel(int orderId, String date, String hour) {
		ArrayList<OrderTb> orders = new ArrayList<OrderTb>();
		for (OrderTb o : ov)
			orders.add(o);
		int parkId = ParkControl.getParkIdByOrderId(orders, orderId);
		Park park = ParkControl.getParkById(String.valueOf(parkId));
		OrderControl.notifyPersonFromWaitingList(date, hour, park);

	}

	/*
	 * Ofir Avraham Vaknin Each number represent a result 0 - confirm 1 - to late to
	 * confirm 2 - to early to confirm -1 - an error has occured
	 */
	private int isDateBetween2422(String date, String time) {

		String combinedVisit = date + " " + time;
		String combinedToday = LocalDate.now().toString() + " " + LocalTime.now().toString();

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date visitDate = new Date();
		Date todayDate = new Date();

		try {
			todayDate = sdfDate.parse(combinedToday);
			visitDate = sdfDate.parse(combinedVisit);
		} catch (ParseException e) {
			System.out.println("Failed to prase dates");
			e.printStackTrace();
			return -1;
		}

		long diffInMills = visitDate.getTime() - todayDate.getTime();
		long diffInHour = TimeUnit.MILLISECONDS.toHours(diffInMills);

		if (diffInHour < 22)
			return 1;
		if (diffInHour > 24)
			return 2;
		return 0;
	}

	
	// Ofir Avraham Vaknin
	private void clearLabals()
	{
		orderIdTxt.setText("");
		visitDateTxt.setText("");
		visitTimeTxt.setText("");
		orderStatusTxt.setText("");
	}
}