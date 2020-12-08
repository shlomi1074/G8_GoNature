package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class CreateReportsController implements Initializable {

    @FXML
    private AnchorPane createReportsRootPane;

    @FXML
    private Label headerLabel;

    @FXML
    private TitledPane monthTP;

    @FXML
    private AnchorPane chooseMonthAP;

    @FXML
    private JFXComboBox<String> monthCB;

    @FXML
    private TitledPane reportTP;

    @FXML
    private AnchorPane chooseReportAP;

    @FXML
    private JFXRadioButton totalVisitorsRB;

    @FXML
    private JFXRadioButton useageRB;

    @FXML
    private JFXRadioButton IncomeRB;

    @FXML
    private TitledPane commentTP;

    @FXML
    private AnchorPane addCommentAP;

    @FXML
    private JFXTextArea commentTextArea;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
