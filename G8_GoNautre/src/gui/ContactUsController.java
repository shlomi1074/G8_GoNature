package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ContactUsController implements Initializable {
    @FXML
    private AnchorPane ourConcactsPane;

    @FXML
    private AnchorPane emailPane;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXButton sendButton;

    @FXML
    private JFXTextField nameTextField;

    @FXML
    private JFXTextField phoneTextField;

    @FXML
    private JFXTextField emailTextField;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
