package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.ClientUI;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

	private Stage stage;
	private Stage mainScreenStage;
	
	FxmlUtil loader = new FxmlUtil();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}

	private void init() {
		loadProfile();
		getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				mainScreenStage.close();
				ClientUI.chat.getClient().quit();
			}
		});
	}

	private Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setMainScreenStage(Stage stage) {
		this.mainScreenStage = stage;
	}

	@FXML
	private void loadUpdateParameters() {
		FxmlUtil loader = new FxmlUtil();
		Pane view = loader.loadPaneWithOutController("/gui/UpdateParameters.fxml");
		borderPane.setCenter(view);
	}

	@FXML
	private void loadCreateReports() {
		Pane view = loader.loadPaneWithOutController("/gui/CreateReports.fxml");
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
	
	@FXML
	private void logOut() {
		getStage().close();
		mainScreenStage.show();
	}

}
