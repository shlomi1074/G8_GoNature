package gui;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	private JFXComboBox<String> typeComboBox;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		accordion.setExpandedPane(identificationTP);
		initComboBoxes();
		initRadioBoxes();
		initDatePicker();
		initSummaryLabelsBindings();
	}
	
	private void initSummaryLabelsBindings() {
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
		summaryID.textProperty().bind(Bindings.convert(idInputOrderVisit.textProperty()));
		summaryPark.textProperty().bind(Bindings.convert(parksComboBox.valueProperty()));
		summaryDate.textProperty().bind(Bindings.convert(datePicker.valueProperty()));
		summaryTime.textProperty().bind(Bindings.convert(timePicker.valueProperty()));
		summaryType.textProperty().bind(Bindings.convert(typeComboBox.valueProperty()));
		summaryVisitors.textProperty().bind(Bindings.convert(numOfVisitorsOrderVisit.textProperty()));
		summaryEmail.textProperty().bind(Bindings.convert(emailInputOrderVisit.textProperty()));
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

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });	
	}

	private void initRadioBoxes() {
		payNowRadioBox.setSelected(true);
		payLaterRadioBox.setSelected(false);
		paymentPane.setVisible(true);
		paymentPane.setDisable(false);
	}

	@FXML
	private void turnOffPayNow() {
		payNowRadioBox.setSelected(false);
		paymentPane.setVisible(false);
		paymentPane.setDisable(true);
		summaryPayment.setText("At The Park");
	}

	@FXML
	private void turnOffPayLater() {
		payLaterRadioBox.setSelected(false);
		paymentPane.setVisible(true);
		paymentPane.setDisable(false);
		summaryPayment.setText("Online");
	}

	private void initComboBoxes() {
		parksComboBox.getItems().clear();
		typeComboBox.getItems().clear();

		/* MAYBE NEED TO CHANGE TO DYNAMIC FROM THE DB IN THE FUTURE */
		parksComboBox.getItems().addAll("Ardent Park", "Pine Park", "Linda Park");
		typeComboBox.getItems().addAll("Solo Visit", "Family Visit", "Group Visit");

		/* Listener to order type ComboBox. activate on every item selected */
		typeComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (newItem == null) {
			} else {
				if (newItem.equals("Solo Visit")) {
					numOfVisitorsOrderVisit.setText("1");
					numOfVisitorsOrderVisit.setDisable(true);

				} else {
					numOfVisitorsOrderVisit.setPromptText("Visitor's Number");
					numOfVisitorsOrderVisit.setDisable(false);
				}
			}
		});

	}

	@FXML
	private void placeOrderButton() {
		/* Need to add alot of stuff */

		/* Test UI - need to delete */
		loadRescheduleScreen();
		/* End UI Test */
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
