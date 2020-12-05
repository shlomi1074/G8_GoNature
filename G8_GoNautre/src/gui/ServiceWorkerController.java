package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.FxmlUtil;

public class ServiceWorkerController implements Initializable {
	
    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane topPane;

    @FXML
    private Label userLabel;

    @FXML
    private VBox vbox;

    @FXML
    private JFXButton profileButton;

    @FXML
    private JFXButton currentVisitorsButton;

    @FXML
    private JFXButton enterVisitorIDButton;

    @FXML
    private JFXButton registerButton;

	FxmlUtil loader = new FxmlUtil();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadProfile();

	}
	
	@FXML
	private void loadRegister() {
		Pane view = loader.loadPaneWithOutController("/gui/AddSubscriber.fxml");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadProfile() {
		Pane view = loader.loadPaneWithController("/gui/Profile.fxml", "profile");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadManageTraveler() {
		Pane view = loader.loadPaneWithController("/gui/ManageTraveler.fxml", "manageTraveler");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadParkParameters() {
		Pane view = loader.loadPaneWithController("/gui/ParkParameters.fxml", "parkParameters");
		borderPane.setCenter(view);
	}

}
