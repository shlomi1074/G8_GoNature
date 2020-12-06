package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.ClientUI;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import util.FxmlUtil;

public class TravelerScreenController implements Initializable{
	
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private AnchorPane topPane;

    @FXML
    private Label travelerLabel;

    @FXML
    private VBox vbox;

    @FXML
    private JFXButton profileTravelerButton;

    @FXML
    private JFXButton orderTravelerButton;

    @FXML
    private JFXButton viewOrdersButton;

    @FXML
    private JFXButton viewMessagesButton;

    @FXML
    private Pane midPane;
    
    private Stage stage;
    private Stage mainScreenStage;
    
	FxmlUtil loader = new FxmlUtil();
	
	String ID;
	String password;
	
	public TravelerScreenController(String username, String password) {
		this.ID = username;
		this.password = password;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();

	}
	
	private void init() {		
		getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				mainScreenStage.close();
				ClientUI.chat.getClient().quit();	
			}
		});
		loadProfile();
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
	private void loadOrderVisit() {
		Pane view = loader.loadPaneWithController("/gui/OrderVisit.fxml", "orderVisit");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadProfile() {
		Pane view = loader.loadPaneWithController("/gui/Profile.fxml", "profile");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadOrders() {
		Pane view = loader.loadPaneWithController("/gui/TravelerViewOrders.fxml", "travelerOrders");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void loadMessages() {
		Pane view = loader.loadPaneWithController("/gui/ViewMessages.fxml", "travelerMessages");
		borderPane.setCenter(view);
	}
	
	@FXML
	private void logOut() {
		getStage().close();
		mainScreenStage.show();
	}
	
	
	
}
