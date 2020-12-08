package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class UpdateParametersController implements Initializable {
	

    @FXML
    private AnchorPane updateParametersRootPane;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane maxVisitorsTP;

    @FXML
    private AnchorPane identificationAP;

    @FXML
    private JFXTextField newMaxVisitorsTextField;

    @FXML
    private TitledPane estimatedTimeTP;

    @FXML
    private AnchorPane informationAP;

    @FXML
    private JFXTextField newEsitimatedTIme;

    @FXML
    private TitledPane gapTP;

    @FXML
    private AnchorPane paymentAP;

    @FXML
    private JFXTextField gapTextField;

    @FXML
    private TitledPane discountTP;

    @FXML
    private AnchorPane discountAP;

    @FXML
    private JFXTextField discountPercentage;

    @FXML
    private JFXDatePicker discountStartDate;

    @FXML
    private JFXDatePicker discountEndDate;

    @FXML
    private Label headerLabel;

    @FXML
    private JFXButton sendForApprovealButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
