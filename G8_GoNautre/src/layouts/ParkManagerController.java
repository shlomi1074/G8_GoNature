package layouts;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.FxmlUtil;

public class ParkManagerController implements Initializable {

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
	private JFXButton createReportsButton;

	@FXML
	private JFXButton updateParametersButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadProfile();
	}

	@FXML
	private void loadUpdateParameters() {
		FxmlUtil loader = new FxmlUtil();
		Pane view = loader.loadPaneWithOutController("/layouts/UpdateParameters.fxml");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadCreateReports() {
		FxmlUtil loader = new FxmlUtil();
		Pane view = loader.loadPaneWithOutController("/layouts/CreateReports.fxml");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadProfile() {
		FxmlUtil loader = new FxmlUtil();
		Pane view = loader.loadPaneWithController("/layouts/Profile.fxml", "profile");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadManageTraveler() {
		FxmlUtil loader = new FxmlUtil();
		Pane view = loader.loadPaneWithController("/layouts/ManageTraveler.fxml", "manageTraveler");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadParkParameters() {
		FxmlUtil loader = new FxmlUtil();
		Pane view = loader.loadPaneWithController("/layouts/ParkParameters.fxml", "parkParameters");
		borderPane.setCenter(view);
	}

}
