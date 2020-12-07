package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewRequestsForChangesController implements Initializable {
	@FXML
	private TableView<?> parametersTable;

	@FXML
	private TableColumn<?, ?> parametersIdCol;

	@FXML
	private TableColumn<?, ?> typeCol;

	@FXML
	private TableColumn<?, ?> oldValueCol;

	@FXML
	private TableColumn<?, ?> newValueCol;

	@FXML
	private TableColumn<?, ?> parametersStatusCol;

	@FXML
	private TableColumn<?, ?> discountIdCol;

	@FXML
	private TableColumn<?, ?> discountCol;

	@FXML
	private TableColumn<?, ?> startDateCol;

	@FXML
	private TableColumn<?, ?> endDateCol;

	@FXML
	private TableColumn<?, ?> discountStatusCol;

	@FXML
	private Label headerLabel;

	@FXML
	private Button confirmRequestBtn;

	@FXML
	private Button cancelRequestBtn;

	@FXML
	private Label selectedRequestLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
