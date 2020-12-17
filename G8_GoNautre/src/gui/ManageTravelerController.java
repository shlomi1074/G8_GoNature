package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import Controllers.OrderControl;
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
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderTb;

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

    @FXML
    private JFXTextField visitorsTextField;

	private OrderTb clickedRow;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTableView();
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				confirmButton();
				clickedRow = null;
			}
		});
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				searchTraveler();
				clickedRow = null;
			}
		});

	}

	// Ofir Avraham Vaknin
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

			newStage.setTitle("Casul Visit");
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("faild to load form");
		}

	}

	private Stage getStage() {
		return (Stage) occVisitBtn.getScene().getWindow();
	}

	// Ofir Avraham Vaknin
	@FXML
	public void loadTableView() {

		ArrayList<Order> ordersArrayList = OrderControl.getAllOrders();
		ArrayList<OrderTb> tbOrdersArrayList = OrderControl.convertOrderToOrderTb(ordersArrayList);
		init(tbOrdersArrayList);
		ordersTableView.setItems(getOrders(tbOrdersArrayList));
	}

	// Ofir Avraham Vaknin
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

	// Ofir Avraham Vaknin
	private void confirmButton() {
		// Did not choose row
		if (clickedRow == null) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please choose order to confirm")
					.showAndWait();
			return;
		}
		// Order time/date is not good
		if (!canTravelerEnter()) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Time Error",
					"Make sure the order is in the right time/date").showAndWait();
			return;
		}

		// order is not pending
		if (!clickedRow.getOrderStatus().equals("pending")) {
			new CustomAlerts(AlertType.ERROR, "Input Error", "Order Error", "Make sure the order status is pending")
					.showAndWait();
			return;
		}
		// Order is good to confirm
		boolean orderControlResult = OrderControl.changeOrderStatus(String.valueOf(clickedRow.getOrderId()),
				OrderStatusName.confirmed);
		if (!orderControlResult) {
			new CustomAlerts(AlertType.ERROR, "System Error", "System Error",
					"Could not confirm this order,please try again later.").showAndWait();

		} else {
			loadTableView();
			new CustomAlerts(AlertType.INFORMATION, "Changes were made", "Changes were made", "Order confirmed")
					.showAndWait();
		}

	}

	// Ofir Avraham Vaknin
	private boolean canTravelerEnter() {
		LocalTime orderTime = LocalTime.parse(clickedRow.getOrderTime());
		LocalDate orderDate = LocalDate.parse(clickedRow.getOrderDate());
		// Date is not today
		if (!orderDate.equals(LocalDate.now()))
			return false;
		// Check if the person came early at no more than 15 mins
		// Or came late by at most 1 hour

		if (orderTime.isAfter(LocalTime.now().minusMinutes(15)) && orderTime.isBefore(LocalTime.now().plusHours(1)))
			return true;
		return false;
	}

	// Ofir Avraham Vaknin
	private void searchTraveler() {

		String id = idTextField.getText();
		if (id.isEmpty()) {
			loadTableView();
			return;
		}

		ArrayList<Order> ordersArrayList = OrderControl.getOrders(id);
		ArrayList<OrderTb> tbOrdersArrayList = OrderControl.convertOrderToOrderTb(ordersArrayList);
		if (ordersArrayList.isEmpty()) {
			new CustomAlerts(AlertType.ERROR, "Input error", "ID error", "No orders found for" + id).showAndWait();
			return;
		}
		init(tbOrdersArrayList);
		ordersTableView.setItems(getOrders(tbOrdersArrayList));
	}

}