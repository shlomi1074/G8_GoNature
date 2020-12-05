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
	private Label summaryTotalPrice;

	@FXML
	private Label summaryTime;

	@FXML
	private Label summaryDate1;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		accordion.setExpandedPane(identificationTP);
		initComboBoxes();
		initRadioBoxes();
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
	private void updateSummary() {
		summaryID.setText(idInputOrderVisit.getText());
		if (parksComboBox.getSelectionModel().getSelectedItem() != null)
			summaryPark.setText(parksComboBox.getSelectionModel().getSelectedItem().toString());
		if (datePicker.getValue() != null)
			summaryDate.setText(datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		if (timePicker.getValue() != null)
			summaryTime.setText(timePicker.getValue().format(DateTimeFormatter.ofPattern("H:m")));
		if (typeComboBox.getSelectionModel().getSelectedItem() != null)
			summaryType.setText(typeComboBox.getSelectionModel().getSelectedItem().toString());
		summaryVisitors.setText(numOfVisitorsOrderVisit.getText());
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
