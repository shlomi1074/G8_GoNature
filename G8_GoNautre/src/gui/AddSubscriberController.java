package gui;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AddSubscriberController implements Initializable{
	
    @FXML
    private AnchorPane registerRootPane;

    @FXML
    private Label orderVisitHeaderLabel;

    @FXML
    private JFXTextField emailInputOrderVisit;

    @FXML
    private JFXTextField firstNameInputRegister;

    @FXML
    private JFXTextField lastNameInputRegister;

    @FXML
    private JFXTextField lastNameInputRegister1;

    @FXML
    private JFXTextField lastNameInputRegister11;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initComboBoxes();
	}

	private void initComboBoxes() {
		typeComboBox.getItems().addAll("Solo Account", "Family Account", "Guide Account");
	}
	
	
}
