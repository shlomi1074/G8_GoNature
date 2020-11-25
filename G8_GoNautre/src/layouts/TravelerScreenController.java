package layouts;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
	
	String ID;
	String password;
	
	public TravelerScreenController(String username, String password) {
		this.ID = username;
		this.password = password;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	private void loadOrderVisit() {
		FxmlUtil loader = new FxmlUtil();
		Pane view = loader.loadPaneWithController("/layouts/OrderVisit.fxml", "orderVisit");
		borderPane.setCenter(view);
	}
	
}
