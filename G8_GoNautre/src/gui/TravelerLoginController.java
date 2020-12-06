package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TravelerLoginController implements Initializable {

    @FXML
    private AnchorPane loginContainer;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Label forgotPasswordLabel1;

    @FXML
    private JFXButton loginButton;
    
	private Stage parentStage;
	
	public TravelerLoginController(Stage parentStage){
		this.parentStage = parentStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	switchScene();
            }
        });

	}
	
	private void switchScene() {
		try {
			Stage thisStage = getStage();
			Stage newStage = new Stage(); 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TravelerScreen.fxml"));
			TravelerScreenController controller = new TravelerScreenController("user", "password");
			loader.setController(controller);
			controller.setStage(newStage);
			controller.setMainScreenStage(parentStage);
			loader.load();
			Parent p = loader.getRoot(); 	
			newStage.setTitle("user screen");
			newStage.setScene(new Scene(p)); 
			newStage.setResizable(false);
			newStage.show();
			thisStage.close();
			parentStage.hide();
		} catch (Exception e) {
			System.out.println("faild to load form");
			e.printStackTrace();
		}
	}
	
	private Stage getStage() {
		return (Stage) loginButton.getScene().getWindow();
	}
	

}
