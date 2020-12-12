package gui;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import Controllers.ParkControl;
import Controllers.TravelerControl;
import Controllers.calculatePrice.CheckOutDecorator;
import alerts.CustomAlerts;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import logic.OrderType;
import logic.Park;
import logic.Subscriber;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import java.time.DayOfWeek;
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
	private Label summaryTime;

	private Subscriber traveler;
	private int[] AllowedHours = { 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		accordion.setExpandedPane(identificationTP);
		initComboBoxes();
		initRadioBoxes();
		initDatePicker();
		initLabels(); // shlomi

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
				else
					summaryType.setVisible(true);
			}
		});

		// shlomi
		idInputOrderVisit.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.length() == 9) {
					traveler = TravelerControl.getSubscriber(arg2);
					if (traveler == null)
						permissionLabel.setText("Guest");
					else
						permissionLabel.setText(traveler.getSubscriberType());

					if (!summaryVisitors.getText().isEmpty())
						summaryTotalPrice.setText(CalculatePrice());
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
					summaryTotalPrice.setText(CalculatePrice());
				else {
					summaryTotalPrice.setText("");
				}
			}
		});

		summaryID.textProperty().bind(Bindings.convert(idInputOrderVisit.textProperty()));
		summaryPark.textProperty().bind(Bindings.convert(parksComboBox.valueProperty()));
		summaryDate.textProperty().bind(Bindings.convert(datePicker.valueProperty()));
		summaryTime.textProperty().bind(Bindings.convert(timePicker.valueProperty()));
		summaryType.textProperty().bind(Bindings.convert(typeComboBox.valueProperty()));
		summaryVisitors.textProperty().bind(Bindings.convert(numOfVisitorsOrderVisit.textProperty()));
		summaryEmail.textProperty().bind(Bindings.convert(emailInputOrderVisit.textProperty()));
	}

	// shlomi
	private String CalculatePrice() {
		if (!summaryVisitors.getText().isEmpty() && !idInputOrderVisit.getText().isEmpty()
				&& !summaryVisitors.getText().isEmpty()) {
			if ((permissionLabel.getText().equals("Solo") || permissionLabel.getText().equals("Family")) && 
					summaryType.getText().equals("Solo Visit") || summaryType.getText().equals("Family Visit")) {
				CheckOutDecorator t = new CheckOutDecorator(Integer.parseInt(summaryVisitors.getText()), 1, "2020-12-21");
				return String.valueOf(t.getPrice());
			}
		}
		return "0";
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

	@FXML
	private void placeOrderButton() {
		/* Need to add alot of stuff */
		// Shlomi
		if (isValidInput()) {

		} else {

		}

		/* Test UI - need to delete */
		// loadRescheduleScreen();
		/* End UI Test */
	}

	// Shlomi
	private boolean isValidInput() {
		if (!checkIfFilledAllFields())
			new CustomAlerts(AlertType.WARNING, "Bad Input", "Bad Input", "Please fill all the fields").showAndWait();
		else if (summaryID.getText().length() != 9)
			new CustomAlerts(AlertType.WARNING, "Bad Input", "Bad ID Input", "Id length must be 9").showAndWait();
		else if (!checkIfVisitTimeIsValid())
			new CustomAlerts(AlertType.WARNING, "Bad Time", "Bad Time Input", "Time must be earlier than 18:00")
					.showAndWait();
		else
			return true;
		return false;

	}

	// Shlomi
	private boolean checkIfFilledAllFields() {
		if ((summaryID.getText().isEmpty() || summaryPark.getText().isEmpty() || summaryDate.getText().isEmpty()
				|| summaryType.getText().isEmpty() || summaryVisitors.getText().isEmpty()
				|| summaryEmail.getText().isEmpty() || summaryTime.getText().isEmpty()))
			return false;
		else if (summaryPayment.getText().equals("At The Park"))
			return true;
		else {
			if (cardHolderName.getText().isEmpty() || cardHolderLastName.getText().isEmpty() || CCV.getText().isEmpty()
					|| CardNumber.getText().isEmpty() || CardExpiryDate.valueProperty().getValue() == null)
				return false;
			else
				return true;
		}
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

	private void loadRescheduleScreen() {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Reschedule.fxml"));
			RescheduleController controller = new RescheduleController();
			loader.setController(controller);
			loader.load();
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

	private Stage getStage() {
		return (Stage) summaryID.getScene().getWindow();
	}
}
