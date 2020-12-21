package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import Controllers.OrderControl;
import Controllers.ParkControl;
import Controllers.TravelerControl;
import Controllers.calculatePrice.CheckOut;
import Controllers.calculatePrice.GroupCasualCheckOut;
import Controllers.calculatePrice.RegularCheckOut;
import Controllers.calculatePrice.SubscriberPayAtParkCheckOut;
import alerts.CustomAlerts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderTb;
import logic.OrderType;
import logic.Subscriber;
import util.UtilityFunctions;

public class CasualVisitController implements Initializable {

	@FXML
	private JFXTextField idInputCasualVisit;

	@FXML
	private JFXTextField emailInputCasualVisit;

	@FXML
	private JFXComboBox<OrderType> typeComboBox;

	@FXML
	private JFXTextField numOfVisitorsCasualVisit;

	@FXML
	private Label headerLabel;

	@FXML
	private JFXButton placeOrderBtn;

	@FXML
	private Label totalPriceLabel;

	@FXML
	private JFXButton checkPriceBtn;

	// Ofir Avraham Vaknin v3.
	@FXML
	private Label permissionLabel;

	private int checkPriceCount = 0;
	private Subscriber subscriber;
	

	// Ofir Avraham Vaknin v3.
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComboBoxOrderType();
		initListeners();
		checkPriceBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (validInfo()) {
					checkPricebtnAction();
					checkPriceCount++;
				} else {
					checkPriceCount = 0;
				}
			}
		});

		placeOrderBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (validInfo()) {
					placeOrderAction();
					checkPriceCount = 0;
				}
			}
		});

		// get park by member
		// need to add to order + visit
		// need to check that there is enough place in the park
		// need to add to park current visitors
		// Need to update in approved visits !!!!!
		// Family can order on how much people in the subsciber
		// Not registered - Solo, Group - not guide
		// Registered - Solo or group
		// Family - Solo family group
		// Group - Will have discount only for group, same as simple traveler
		// Canceled time need to be changed to any time.
	}

	// Ofir Avraham Vaknin v2.
	// Changed IN FXML Set disabled
	// <JFXTextField fx:id="numOfVisitorsCasualVisit" disable="true" layoutX="487.0"
	// layoutY="144.0" prefHeight="30.0" prefWidth="106.0" promptText="Visitor's
	// Number" />

	public void initComboBoxOrderType() {

		typeComboBox.getItems().clear();

		typeComboBox.getItems().addAll(Arrays.asList(OrderType.values()));
		if (!permissionLabel.getText().equals("Family")) {
			typeComboBox.getItems().remove(1);
		}

		/* Listener to order type ComboBox. activate on every item selected */
		typeComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (newItem == null) {
			} else {
				if (newItem.toString().equals("Solo Visit")) {
					numOfVisitorsCasualVisit.setText("1");
					numOfVisitorsCasualVisit.setEditable(false);

				} else {
					numOfVisitorsCasualVisit.setText("");
					numOfVisitorsCasualVisit.setPromptText("Visitor's Number");
					numOfVisitorsCasualVisit.setEditable(true);
				}
			}
		});
	}

	public void initListeners() {
		idInputCasualVisit.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.length() == 9) {
					subscriber = TravelerControl.getSubscriber(arg2);
					if (subscriber == null)
						permissionLabel.setText("Guest");
					else
						permissionLabel.setText(subscriber.getSubscriberType());
				} else {
					permissionLabel.setText("Guest");
				}

				initComboBoxOrderType();
			}
		});
	}

	// Ofir Avraham Vaknin v3.
	// SOLO,FAMILY,GROUP, NULL
	private String currentOrderType() {
		if (typeComboBox.getValue() == null)
			return "null";

		return typeComboBox.getValue().toString();
	}

	// Ofir Avraham Vaknin v3.
	// Might need to change enum names\ names in DB (Of types)
	/*
	 * 0 - success 1 - didnt fill the form properly 2 - cant order for group with
	 * that number of people
	 */
	private boolean validInfo() {

		String idOfTraveler = idInputCasualVisit.getText();
		String orderType = currentOrderType();
		String email = emailInputCasualVisit.getText();
		String numOfCasualVisitors = numOfVisitorsCasualVisit.getText();
		if (idOfTraveler.isEmpty() || orderType.equals("null") || email.isEmpty() || numOfCasualVisitors.isEmpty()) {
			popNotification(AlertType.ERROR, "Input Error", "Please fill all of the fields correctly ");
			return false;
		}
		if (!UtilityFunctions.isNumeric(idOfTraveler) || idOfTraveler.length() != 9
				|| !UtilityFunctions.isValidEmailAddress(email)) {
			popNotification(AlertType.ERROR, "Input Error", "Please fill the form correctly");
			return false;
		}

		int numOfVisitors = Integer.parseInt(numOfCasualVisitors);
		Subscriber sub = TravelerControl.getSubscriber(idOfTraveler);

		if (numOfVisitors > 15 || numOfVisitors <= 0) {
			popNotification(AlertType.ERROR, "Input Error",
					"Please fill the form correctly and then press check price");
			return false;
		}

		if (orderType.equals(OrderType.GROUP.toString()) && numOfVisitors < 2) {
			popNotification(AlertType.ERROR, "Input Error", "Can't order for group of 1");
			return false;
		}

		if (sub == null)
			return true;

		// Ofir Avraham Vaknin v3.
		// Check if Family order is larger than the number of people in the sub.
		if(orderType.equals(OrderType.FAMILY.toString()) && numOfVisitors > sub.getNumberOfParticipants())
		{
			popNotification(AlertType.ERROR, "Input Error", "Check number of participants");
			return false;
		}
		
		// if the order is for group , the order is done by a guide, number of visitors is larger than 
		// number of participants in the sub.
		
		// Ofir Avraham Vaknin v3.
		if(orderType.equals(OrderType.GROUP.toString()) && sub.getSubscriberType().equals("Guide") 
				&& numOfVisitors > sub.getNumberOfParticipants())
		{
			popNotification(AlertType.ERROR, "Input Error", "Check number of participants");
			return false;
		}
		
//		if (!orderType.equals(OrderType.SOLO.toString()) && numOfVisitors > sub.getNumberOfParticipants()) {
//			popNotification(AlertType.ERROR, "Input Error", "Check number of participants");
//			return false;
//		}
		
	
		
		return true;
	}

	// Ofir Avraham Vaknin v3.
	// Does guide count as subscriber ?? if no code should be adjust 
	private void checkPricebtnAction() {
		double price = 0;

		// Pulling input from form
		String idOfTraveler = idInputCasualVisit.getText();
		int numberOfVisitors = Integer.parseInt(numOfVisitorsCasualVisit.getText());
		String orderType = currentOrderType();
		//String email = emailInputCasualVisit.getText();

		// Setting up vars
		Subscriber sub = null;
		boolean existTraveler = TravelerControl.isTravelerExist(idOfTraveler);
		LocalDate today = LocalDate.now();
		int parkId = MemberLoginController.member.getParkId();

		CheckOut chk = new RegularCheckOut(numberOfVisitors, parkId, today.toString());

		if (orderType.equals(OrderType.GROUP.toString())) {
			price = (new GroupCasualCheckOut(chk)).getPrice();
			totalPriceLabel.setText(String.valueOf(price));
			return;
		}
		if (existTraveler)
			sub = TravelerControl.getSubscriber(idOfTraveler);
		if (sub != null)
			price = (new SubscriberPayAtParkCheckOut(chk)).getPrice();
		else
			price = chk.getPrice();
		totalPriceLabel.setText(String.valueOf(price));
	}

	// Ofir Avraham Vaknin v2.
	private void popNotification(AlertType type, String header, String content) {
		new CustomAlerts(type, header, header, content).showAndWait();
	}

	// Ofir Avraham Vaknin v2.
	private void placeOrderAction() {
		if (checkPriceCount == 0) {
			popNotification(AlertType.ERROR, "Price error", "Please check the price in order to continue");
			return;
		}
		String idOfTraveler = idInputCasualVisit.getText();
		int numberOfVisitors = Integer.parseInt(numOfVisitorsCasualVisit.getText());
		String orderType = currentOrderType();
		String email = emailInputCasualVisit.getText();
		int parkId = MemberLoginController.member.getParkId();

		Subscriber sub = TravelerControl.getSubscriber(idOfTraveler);
		if (sub != null)
			email = sub.getEmail();

		Order order = new Order(idOfTraveler, parkId, LocalDate.now().toString(), LocalTime.now().toString(), orderType,
				numberOfVisitors, email, Double.parseDouble(totalPriceLabel.getText()),
				OrderStatusName.COMPLETED.toString());
		OrderTb orderTb = new OrderTb(order);

		if (OrderControl.addCasualOrder(order)) {
			OrderControl.addVisit(orderTb);
			int updateNumber = ParkControl.getParkById(String.valueOf(parkId)).getCurrentVisitors() + numberOfVisitors;
			ParkControl.updateCurrentVisitors(parkId, updateNumber);
			
			
			
			popNotification(AlertType.INFORMATION, "Visit Added", "Traveler can continue to the park");
			Stage stage = (Stage) idInputCasualVisit.getScene().getWindow();
			// Ofir Avraham Vaknin v3.
			ManageTravelerController manageTravelerController = (ManageTravelerController)stage.getUserData();
			manageTravelerController.loadTableView();
			//
			stage.close();
			
		} else {
			popNotification(AlertType.ERROR, "System Error", "An error has occurred, please try again");
		}
	}

}