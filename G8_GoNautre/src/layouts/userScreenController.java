package layouts;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class userScreenController implements Initializable{
	
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
	
	String username;
	String password;
	
	public userScreenController(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
}
