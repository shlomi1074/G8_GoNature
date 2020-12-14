package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import Controllers.OrderControl;
import Controllers.ParkControl;
import Controllers.TravelerControl;
import Controllers.calculatePrice.CheckOut;
import Controllers.calculatePrice.GuidePayAtParkCheckOut;
import Controllers.calculatePrice.GuidePrePayCheckOut;
import Controllers.calculatePrice.RegularCheckOut;
import Controllers.calculatePrice.RegularpreOrderCheckOut;
import Controllers.calculatePrice.SubscriberPreOrderCheckOut;
import alerts.CustomAlerts;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderType;
import logic.Park;
import logic.Subscriber;
import logic.Traveler;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import java.time.LocalDate;

public class OrderVisitController implements Initializable {

	@FXML
	private Accordion accordion;

	@FXML
	private TitledPane identificationTP;

	@FXML
	private AnchorPane identificationAP;

	@FXML
	private JFXTextField idInputOrderVisit;

	@FXML
	private AnchorPane informationAP;

	@FXML
	private JFXComboBox<String> parksComboBox;

	@FXML
	private JFXDatePicker datePicker;

	@FXML
	private JFXTimePicker timePicker;

	@FXML
	private JFXTextField emailInputOrderVisit;

	@FXML
	private JFXComboBox<OrderType> typeComboBox;

	@FXML
	private Label requiredFieldsLabel;

	@FXML
	private AnchorPane paymentAP;

	@FXML
	private JFXRadioButton payNowRadioBox;

	@FXML
	private JFXRadioButton payLaterRadioBox;

	@FXML
	private AnchorPane paymentPane;

	@FXML
	private JFXTextField fullNameInput; // shlomi

	@FXML
	private JFXTextField phoneInput; // shlomi

	@FXML
	private JFXTextField cardHolderName;

	@FXML
	private JFXTextField cardHolderLastName;

	@FXML
	private JFXTextField CardNumber;

	@FXML
	private JFXTextField CCV;

	@FXML
	private JFXTextField numOfVisitorsOrderVisit;

	@FXML
	private JFXDatePicker CardExpiryDate;

	@FXML
	private Label permissionLabel;

	@FXML
	private Label orderVisitHeaderLabel;

	@FXML
	private Label summaryID;

	@FXML
	private Label summaryPark;

	@FXML
	private Label summaryDate;

	@FXML
	private Label summaryPayment;

	@FXML
	private Label summaryType;

	@FXML
	private Label summaryVisitors;

	@FXML
	private Label summaryEmail;

	@FXML
	private Label summaryTotalPrice;

	@FXML
	private Label summaryFullName;

	@FXML
	private Label summaryPhone; // shlomi

	@FXML
	private Label summaryTime; // shlomi

	DecimalFormat df = new DecimalFormat("####0.00");
	private Subscriber subscriber;
	private Traveler traveler;
	private Order recentOrder;
	private boolean isOrderFromMain = false;

	private int[] AllowedHours = { 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		accordion.setExpandedPane(identificationTP);
		initComboBoxes();
		initRadioBoxes();
		initDatePicker();
		initLabels(); // shlomi

	}

	@FXML
	private void placeOrderButton() {
		// Shlomi
		if (isValidInput()) {

			Order order = new Order(null, summaryID.getText(), getSelectedParkId(), summaryDate.getText(),
					summaryTime.getText(), summaryType.getText(), Integer.parseInt(summaryVisitors.getText()),
					summaryEmail.getText(), CalculatePrice(), OrderStatusName.pending.name());

			String[] travelerName = summaryFullName.getText().split(" ");
			String travelerFirstName = travelerName[0];
			String travelerLastName = travelerName.length == 1 ? "" : travelerName[1];

			traveler = new Traveler(summaryID.getText(), travelerFirstName, travelerLastName, summaryEmail.getText(),
					summaryPhone.getText());

			if (OrderControl.addOrder(order, traveler)) {
				System.out.println("Order added successfuly ");
				recentOrder = OrderControl.getTravelerRecentOrder(traveler.getTravelerId());
				loadOrderConfirmation();

				/* NEED TO SEND EMAIL AND SEND MESSAGE */
			} else {
				loadRescheduleScreen(order);
			}

		}
	}

	/* This function returns the if of the selected park */
	private int getSelectedParkId() {
		Park park = ParkControl.getParkByName(summaryPark.getText());
		if (park != null)
			return park.getParkId();
		else
			return -1;
	}

	// Shlomi
	/* This function check if All the input is valid */
	private boolean isValidInput() {
		if (!checkIfFilledAllFields())
			new CustomAlerts(AlertType.WARNING, "Bad Input", "Bad Input", "Please fill all the fields").showAndWait();
		else if (summaryID.getText().length() != 9)
			new CustomAlerts(AlertType.WARNING, "Bad Input", "Bad ID Input", "Id length must be 9").showAndWait();
		else if (!checkIfVisitTimeIsValid())
			new CustomAlerts(AlertType.WARNING, "Bad Time", "Bad Time Input", "Time must be earlier than 18:00")
					.showAndWait();
		else if (Integer.parseInt(summaryVisitors.getText()) > 15
				&& summaryType.getText().equals(OrderType.GROUP.toString())) {
			new CustomAlerts(AlertType.WARNING, "Bad Input", "Invalid Visitor's Number",
					"Group order can be up to 15 travelers").showAndWait();

		} else if (subscriber != null && subscriber.getSubscriberType().equals("Family")
				&& Integer.parseInt(summaryVisitors.getText()) > subscriber.getNumberOfParticipants()) {
			new CustomAlerts(AlertType.ERROR, "Bad Time", "Invalid Visitor's Number",
					"Your family account has " + subscriber.getNumberOfParticipants()
							+ " members.\nThe number of visitors can not be higher than "
							+ subscriber.getNumberOfParticipants()).showAndWait();
		} else {
			return true;
		}
		return false;

	}

	// Shlomi
	private boolean checkIfFilledAllFields() {
		if (summaryID.getText().isEmpty() || summaryPark.getText().isEmpty() || summaryDate.getText().isEmpty()
				|| summaryType.getText().isEmpty() || summaryVisitors.getText().isEmpty()
				|| summaryEmail.getText().isEmpty() || summaryTime.getText().isEmpty()
				|| summaryFullName.getText().isEmpty() || summaryPhone.getText().isEmpty()
				|| summaryType.getText().equals("null") || summaryPark.getText().equals("null")
				|| summaryDate.getText().equals("null") || summaryTime.getText().equals("null")) {
			return false;
		} else if (summaryPayment.getText().equals("At The Park"))
			return true;
		else {
			if (cardHolderName.getText().isEmpty() || cardHolderLastName.getText().isEmpty() || CCV.getText().isEmpty()
					|| CardNumber.getText().isEmpty() || CardExpiryDate.valueProperty().getValue() == null)
				return false;
			else
				return true;
		}
	}

	// shlomi
	private Double CalculatePrice() {
		if (!summaryVisitors.getText().isEmpty() && !idInputOrderVisit.getText().isEmpty()
				&& !summaryVisitors.getText().isEmpty() && !summaryDate.getText().isEmpty()
				&& !summaryDate.getText().equals("null")) {

			CheckOut basic = new RegularCheckOut(Integer.parseInt(summaryVisitors.getText()), 1, summaryDate.getText());
			/* subscriber - Solo/family order */
			if ((permissionLabel.getText().equals("Solo") || permissionLabel.getText().equals("Family"))
					&& summaryType.getText().equals(OrderType.SOLO.toString())
					|| summaryType.getText().equals(OrderType.FAMILY.toString())) {

				SubscriberPreOrderCheckOut checkOut = new SubscriberPreOrderCheckOut(basic);
				return checkOut.getPrice();

				/* guest order */
			} else if (permissionLabel.getText().equals("Guest")) {
				RegularpreOrderCheckOut checkOut = new RegularpreOrderCheckOut(basic);
				return checkOut.getPrice();

				/* guide - group order - pay online */
			} else if (permissionLabel.getText().equals("Guide") && summaryPayment.getText().equals("Online")
					&& summaryType.getText().equals(OrderType.GROUP.toString())) {
				GuidePrePayCheckOut checkOut = new GuidePrePayCheckOut(basic);
				return checkOut.getPrice();

			}
			/* guide - group order - pay at the park */
			else if (permissionLabel.getText().equals("Guide") && summaryPayment.getText().equals("At The Park")
					&& summaryType.getText().equals(OrderType.GROUP.toString())) {
				GuidePayAtParkCheckOut checkOut = new GuidePayAtParkCheckOut(basic);
				return checkOut.getPrice();

			}

		}
		return 0.0;
	}

	// Shlomi
	private boolean checkIfVisitTimeIsValid() {
		int hour = Integer.parseInt(summaryTime.getText().split(":")[0]);
		for (int i : AllowedHours) {
			if (i == hour)
				return true;
		}
		return false;
	}

	private void initLabels() {// shlomi
		summaryPark.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("null"))
					summaryPark.setVisible(false);
				else
					summaryPark.setVisible(true);
			}
		});
		summaryDate.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("null"))
					summaryDate.setVisible(false);
				else
					summaryDate.setVisible(true);
			}
		});
		summaryTime.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("null"))
					summaryTime.setVisible(false);
				else
					summaryTime.setVisible(true);
			}
		});

		summaryType.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("null"))
					summaryType.setVisible(false);
				else {
					summaryType.setVisible(true);
					if (!summaryID.getText().isEmpty() && !summaryDate.getText().isEmpty()
							&& !summaryVisitors.getText().isEmpty() && !summaryType.getText().isEmpty()) {
						summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
					}
				}
			}
		});

		// shlomi
		idInputOrderVisit.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.length() == 9) {
					subscriber = TravelerControl.getSubscriber(arg2);
					if (subscriber == null)
						permissionLabel.setText("Guest");
					else
						permissionLabel.setText(subscriber.getSubscriberType());

					if (!summaryVisitors.getText().isEmpty())
						summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
				} else {
					permissionLabel.setText("Guest");
					summaryTotalPrice.setText("");
				}

				initComboBoxes();
			}

		});

		// shlomi
		summaryVisitors.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (!idInputOrderVisit.getText().isEmpty())
					summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
				else {
					summaryTotalPrice.setText("");
				}
			}
		});

		datePicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
			}
		});

		summaryID.textProperty().bind(Bindings.convert(idInputOrderVisit.textProperty()));
		summaryPark.textProperty().bind(Bindings.convert(parksComboBox.valueProperty()));
		summaryDate.textProperty().bind(Bindings.convert(datePicker.valueProperty()));
		summaryTime.textProperty().bind(Bindings.convert(timePicker.valueProperty()));
		summaryType.textProperty().bind(Bindings.convert(typeComboBox.valueProperty()));
		summaryVisitors.textProperty().bind(Bindings.convert(numOfVisitorsOrderVisit.textProperty()));
		summaryEmail.textProperty().bind(Bindings.convert(emailInputOrderVisit.textProperty()));
		summaryFullName.textProperty().bind(Bindings.convert(fullNameInput.textProperty())); // shlomi
		summaryPhone.textProperty().bind(Bindings.convert(phoneInput.textProperty())); // shlomi
	}

	/* Setup the date picker */
	private void initDatePicker() {
		timePicker.set24HourView(true);
		/* Disable the user from picking past dates */
		datePicker.setDayCellFactory(picker -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0);
			}
		});
	}

	private void initRadioBoxes() {
		summaryPayment.setText("Online");
		payNowRadioBox.setSelected(true);
		payLaterRadioBox.setSelected(false);
		paymentPane.setVisible(true);
		paymentPane.setDisable(false);
	}

	@FXML
	private void turnOffPayNow() {
		if (!payNowRadioBox.isSelected())
			payLaterRadioBox.setSelected(true);
		else {
			payNowRadioBox.setSelected(false);
			paymentPane.setVisible(false);
			paymentPane.setDisable(true);
			summaryPayment.setText("At The Park");
		}
	}

	@FXML
	private void turnOffPayLater() {
		if (!payLaterRadioBox.isSelected())
			payNowRadioBox.setSelected(true);
		else {
			payLaterRadioBox.setSelected(false);
			paymentPane.setVisible(true);
			paymentPane.setDisable(false);
			summaryPayment.setText("Online");
		}
	}

	private void initComboBoxes() {
		parksComboBox.getItems().clear();
		typeComboBox.getItems().clear();

		/* Set parks combobox to load dynamically from database */ // Shlomi
		ArrayList<String> parksNames = ParkControl.getParksNames();
		if (parksNames != null) {
			parksComboBox.getItems().addAll(parksNames);
		}
		/* Set up order type from OrderType Enum */ // Shlomi
		typeComboBox.getItems().addAll(Arrays.asList(OrderType.values()));
		if (!permissionLabel.getText().equals("Family")) {
			typeComboBox.getItems().remove(1);
		}

		/* Listener to order type ComboBox. activate on every item selected */
		typeComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (newItem == null) {
			} else {
				if (newItem.toString().equals("Solo Visit")) {
					numOfVisitorsOrderVisit.setText("1");
					numOfVisitorsOrderVisit.setDisable(true);

				} else {
					numOfVisitorsOrderVisit.setText("");
					numOfVisitorsOrderVisit.setPromptText("Visitor's Number");
					numOfVisitorsOrderVisit.setDisable(false);
				}
			}
		});
	}

	private void loadRescheduleScreen(Order order) {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Reschedule.fxml"));
			RescheduleController controller = new RescheduleController();
			loader.setController(controller);
			loader.load();
			controller.SetSelectedTimeLabel(summaryDate.getText() + ", " + summaryTime.getText());
			controller.setOrder(order);
			controller.setTraveler(traveler);
			Parent p = loader.getRoot();
			Stage newStage = new Stage();

			/* Block parent stage until child stage closes */
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(thisStage);

			newStage.setTitle("Reschedule");
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			System.out.println("faild to load form");
			e.printStackTrace();
		}

	}

	private void loadOrderConfirmation() {
		try {
			Stage thisStage = getStage();
			Stage newStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrderConfirmation.fxml"));
			OrderConfirmationController controller = new OrderConfirmationController();
			controller.setOrder(recentOrder);
			controller.setTraveler(traveler);
			controller.setSummaryPayment(summaryPayment.getText());
			controller.setStage(newStage);
			loader.setController(controller);
			loader.load();
			Parent p = loader.getRoot();

			newStage.setTitle("Order Confirmation");
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			if (isOrderFromMain)
				thisStage.close();
			newStage.show();
		} catch (IOException e) {
			System.out.println("faild to load form");
			e.printStackTrace();
		}

	}
	
	public void setOrderFromMain(boolean isOrderFromMain) {
		this.isOrderFromMain = isOrderFromMain;
	}

	private Stage getStage() {
		return (Stage) summaryID.getScene().getWindow();
	}
}
