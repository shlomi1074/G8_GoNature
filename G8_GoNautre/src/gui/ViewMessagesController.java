package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewMessagesController implements Initializable {
	
    @FXML
    private TableView<?> messagesTableView;

    @FXML
    private TableColumn<?, ?> SubjectCol;

    @FXML
    private TableColumn<?, ?> DateCol;

    @FXML
    private Label headerLabel;

    @FXML
    private JFXTextArea messageTextArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
