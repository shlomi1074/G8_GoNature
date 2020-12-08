package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class CasualVisitController implements Initializable {

   @FXML
    private JFXTextField idInputCasualVisit;

    @FXML
    private JFXTextField emailInputCasualVisit;

    @FXML
    private JFXComboBox<?> typeComboBox;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
