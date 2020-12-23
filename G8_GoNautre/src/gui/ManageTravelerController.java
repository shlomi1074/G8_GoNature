package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import Controllers.OrderControl;
import Controllers.ParkControl;
import alerts.CustomAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.GoNatureFinals;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderTb;
import logic.Park;

public class ManageTravelerController implements Initializable {

	ObservableList<OrderTb> ov = FXCollections.observableArrayList(); // Ofir Avraham Vaknin

	@FXML
	private JFXTextField idTextField;

	@FXML
	private TableView<OrderTb> ordersTableView;

	@FXML
	private TableColumn<OrderTb, String> travelerIDCol;

	@FXML
	private TableColumn<OrderTb, Integer> orderIDCol;

	@FXML
	private TableColumn<OrderTb, String> dateCol;

	@FXML
	private TableColumn<OrderTb, String> timeCol;

	@FXML
	private TableColumn<OrderTb, String> statusCol;

	@FXML
	private Button occVisitBtn;

	@FXML
	private Button confirmBtn;

	@FXML
	private Button searchBtn;

	@FXML
	private Label headerLabel;

	@FXML
	private Label orderIdTxt;

	// Ofir Avraham Vaknin v3.

	@FXML
	private JFXTextField visitorsTextField;

	private OrderTb clickedRow;

	// Ofir Avraham Vaknin v3.
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTableView();
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				confirmButton();
				clickedRow = null;
				clearLabels();
			}
		});
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				searchTraveler();
				clickedRow = null;
				clearLabels();
			}
		});

	}

	// Ofir Avraham Vaknin v3.
	@FXML
	private void loadCasualVisit() {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CasualTravelerVisit.fxml"));
			CasualVisitController controller = new CasualVisitController();
			loader.setController(controller);
			loader.load();
			Parent p = loader.getRoot();
			Stage newStage = new Stage();

			/* Block parent stage until child stage closes */
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(thisStage);
			newStage.getIcons().add(new Image(GoNatureFinals.APP_ICON));
			newStage.setTitle("Casual Visit");
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			// Ofir v3
			newStage.setUserData(this);
			//
			newStage.show();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("faild to load form");
		}
	}

	private Stage getStage() {
		return (Stage) occVisitBtn.getScene().getWindow();
	}

	// Ofir Avraham Vaknin v3.
	@FXML
	public void loadTableView() {
		ArrayList<Order> ordersArrayList = OrderControl.getAllOrdersForParkId(MemberLoginController.member.getParkId());
		ArrayList<OrderTb> tbOrdersArrayList = OrderControl.convertOrderToOrderTb(ordersArrayList);
		init(tbOrdersArrayList);
		ordersTableView.setItems(getOrders(tbOrdersArrayList));
	}

	// Ofir Avraham Vaknin v3.
	private void init(ArrayList<OrderTb> orders) {

		travelerIDCol.setCellValueFactory(new PropertyValueFactory<OrderTb, String>("travelerId"));
		orderIDCol.setCellValueFactory(new PropertyValueFactory<OrderTb, Integer>("orderId"));
		dateCol.setCellValueFactory(new PropertyValueFactory<OrderTb, String>("orderDate"));
		timeCol.setCellValueFactory(new PropertyValueFactory<OrderTb, String>("orderTime"));
		statusCol.setCellValueFactory(new PropertyValueFactory<OrderTb, String>("orderStatus"));

		ordersTableView.setRowFactory(tv -> {
			TableRow<OrderTb> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					clickedRow = row.getItem(); // saves the order
					orderIdTxt.setText(String.valueOf(clickedRow.getOrderId()));
					visitorsTextField.setText(String.valueOf(clickedRow.getNumberOfParticipants()));
				}
			});
			return row;
		});
		
		
		
		
	}

	// Ofir Avraham Vaknin
	private ObservableList<OrderTb> getOrders(ArrayList<OrderTb> orderArray) {
		ordersTableView.getItems().clear();
		for (OrderTb order : orderArray) {
			ov.add(order);
		}
		return ov;
	}

	// Ofir Avraham Vaknin v3.
	private void clearLabels() {
		orderIdTxt.setText("");
		visitorsTextField.setText("");
	}

	// Ofir Avraham Vaknin v3.
	private void confirmButton() {
		// Did not choose row
		if (clickedRow == null) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please choose order to confirm")
					.showAndWait();
			return;
		}
		// Order time/date is not good
		// Order status is not good
		if (!canTravelerEnter()) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Order Error",
					"Make sure the order is in the right time/date and that the order is confirmed").showAndWait();
			return;
		}
		int numberOfParticipantsInOriginalOrder = clickedRow.getNumberOfParticipants();
		int numberOfParticipantsInCurrentOrder = Integer.parseInt(visitorsTextField.getText());
		if (numberOfParticipantsInOriginalOrder < numberOfParticipantsInCurrentOrder) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Order Error",
					"You can't list more people than the order mentioned").showAndWait();
			return;
		}
		if (numberOfParticipantsInCurrentOrder <= 0) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Order Error", "Order must have more than 0 participants")
					.showAndWait();
			return;
		}

		// Order is good to confirm

		// Change order status
		OrderControl.changeOrderStatus(String.valueOf(clickedRow.getOrderId()), OrderStatusName.COMPLETED);

		// Changing the number of participants
		Order tempOrder = new Order(clickedRow);
		tempOrder.setNumberOfParticipants(numberOfParticipantsInCurrentOrder);
		clickedRow = new OrderTb(tempOrder);

		// Add the visit
		OrderControl.addVisit(clickedRow); // return true if succeed

		// Update the current visitors number
		Park p = ParkControl.getParkById(String.valueOf(clickedRow.getParkId()));
		int updateNumber = p.getCurrentVisitors() + numberOfParticipantsInCurrentOrder;
		ParkControl.updateCurrentVisitors(clickedRow.getParkId(), updateNumber);
		
		// Shlomi + Ofir
		ParkControl.updateIfParkFull(p);
		
		loadTableView();
		new CustomAlerts(AlertType.INFORMATION, "Changes were made", "Changes were made", "Traveler can enter")
				.showAndWait();
	}

	// Ofir Avraham Vaknin v3.
	private boolean canTravelerEnter() {
		LocalTime orderTime = LocalTime.parse(clickedRow.getOrderTime());
		LocalDate orderDate = LocalDate.parse(clickedRow.getOrderDate());
		// Date is not today
		if (!orderDate.equals(LocalDate.now()))
			return false;
		// Check if the person came early at no more than 15 mins
		// Or came late by at most 1 hour

		// Order status is not valid
		if (!clickedRow.getOrderStatus().equals(OrderStatusName.CONFIRMED.toString()))
			return false;

		// Cant enter with that time
		if (orderTime.isAfter(LocalTime.now().minusMinutes(15)) && orderTime.isBefore(LocalTime.now().plusHours(1)))
			return true;
		return false;
	}

	// Ofir Avraham Vaknin V3.
	private void searchTraveler() {

		String id = idTextField.getText();
		if (id.isEmpty()) {
			loadTableView();
			return;
		}

		String parkId = String.valueOf(MemberLoginController.member.getParkId());
		ArrayList<Order> ordersArrayList = OrderControl.getOrdersForTravelerInPark(parkId,id);
		ArrayList<OrderTb> tbOrdersArrayList = OrderControl.convertOrderToOrderTb(ordersArrayList);
		if (ordersArrayList.isEmpty()) {
			new CustomAlerts(AlertType.ERROR, "Input error", "ID error", "No orders found for " + id).showAndWait();
			return;
		}
		init(tbOrdersArrayList);
		ordersTableView.setItems(getOrders(tbOrdersArrayList));
	}

}