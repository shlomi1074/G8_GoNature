package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;//Lior added for btn
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import Controllers.AutenticationControl;
import Controllers.ParkControl;
import Controllers.TravelerControl;
import alerts.CustomAlerts;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DateCell;
import javafx.scene.layout.AnchorPane;
import logic.ClientToServerRequest;
import logic.OrderType;
import logic.ClientToServerRequest.Request;

public class AddSubscriberController implements Initializable {

	
	/*Lior*/
    @FXML
    private AnchorPane registerRootPane;

    @FXML
    private Label headerLabel;

    @FXML
    private JFXTextField emailInputRegister;

    @FXML
    private JFXTextField fullNameInputRegister;

    @FXML
    private JFXTextField phoneNumberInputRegister;

    @FXML
    private JFXTextField idInputRegister;

    @FXML
    private JFXTextField familySize;

    @FXML
    private JFXComboBox<String> typeComboBox;

    @FXML
    private Accordion accordion;

    @FXML
    private AnchorPane creditCardPane;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    private JFXTextField CardNumber;

    @FXML
    private JFXTextField CCV;

    @FXML
    private JFXDatePicker CardExpiryDate;

    @FXML
    private Label requiredFieldsLabel1;

    @FXML
    private JFXButton AddAcountBTN;

    @FXML
    private Label requiredFieldsLabel11;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initComboBoxes();
		dateSetter();
	}

	private void initComboBoxes() {
		typeComboBox.getItems().addAll("Solo Account", "Family Account", "Guide Account");
		
		
		/* Listener to type ComboBox. activate on every item selected */
		typeComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (newItem == null) {
			} else {
				if (newItem.toString().equals("Solo Account")) {
					familySize.setText("1");
					familySize.setDisable(true);
				} else {
					familySize.setText("");
					familySize.setPromptText("Family Size");
					familySize.setDisable(false);
				}
			}
		});
	}

	// Lior added method for clicking on add account btn
	@FXML 
	private void AddAcountBTN() {

		/* Lior : getting all information from user input */
		String fullName = fullNameInputRegister.getText();
		String[] temp = fullName.split(" ",2);
		String firstName = temp[0];
		String lastName = temp.length==1 ? "" : temp[1];
		String phoneNumber = phoneNumberInputRegister.getText();
		String id = idInputRegister.getText();
		String email = emailInputRegister.getText();
		String type = typeComboBox.getValue()==null ? "" :  typeComboBox.getValue();
		String cardNumber = CardNumber.getText();
		String cardExpiryDate = CardExpiryDate.getValue()==null ? "" : CardExpiryDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String numberOfParticipants = familySize.getText();
		String ccv = CCV.getText();
		
		/* if user did not fill all required fields */
		if (firstName.isEmpty() || lastName.isEmpty() || id.isEmpty() || email.isEmpty() || type.isEmpty()) {
			if( lastName.isEmpty() && (!(firstName.isEmpty() || id.isEmpty() || email.isEmpty() || type.isEmpty())))
				new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please enter full name")
				.showAndWait();
			else {
				new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please fill in all the fields")
				.showAndWait();
			}	
		}

		/* if user wants to put card detail but didnt fill all card required fields */
		else if (!(cardNumber.isEmpty() && cardExpiryDate.isEmpty() && ccv.isEmpty()) && (cardNumber.isEmpty() || cardExpiryDate.isEmpty() || ccv.isEmpty())) {// filled at least one of the fields
				new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error",
						"Please fill in all the fields regarding the card or leav them blank").showAndWait();
		}
		/*if subscriber tries to subscribe again with same id*/		
		else if (TravelerControl.getSubscriber(id) != null)
				new CustomAlerts(AlertType.ERROR, "Register Error", "Register Error", "You are already Registerd")
						.showAndWait();
		else {
			// if user that wants to be subscriber is a traveller , we delete him from traveller table
			if(AutenticationControl.isTravelerExist(id)) 
				TravelerControl.deleteFromTravelerTable(id);
			
			TravelerControl.insertSubscriberToSubscriberTable(id, firstName, lastName, email, phoneNumber, cardNumber, type, numberOfParticipants);
			//if user entered credit card info we enter it to credit card table
			if(!cardNumber.isEmpty()) {
				TravelerControl.insertCardToCreditCardTable(id,cardNumber, cardExpiryDate, ccv);
			}
			new CustomAlerts(AlertType.INFORMATION, "Subscriber Added", "Subscriber Added",
					"Subscriber was added successfully").showAndWait();
			
		}
	}
	/* Disable the user from picking past dates */
	private void dateSetter() {
		CardExpiryDate.setDayCellFactory(picker -> new DateCell() {
	            @Override
	            public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                LocalDate today = LocalDate.now();
	                setDisable(empty || date.compareTo(today) < 0);
	            }
	        });
	}
	

}
