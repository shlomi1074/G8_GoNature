package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import Controllers.ParkControl;
import Controllers.RequestControl;
import alerts.CustomAlerts;
import client.ChatClient;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import logic.Discount;
import logic.OrderTb;
import logic.Request;
import logic.RequestTb;

public class ViewRequestsForChangesController implements Initializable {
	@FXML
	private TableView<Request> parametersTable;

	@FXML
	private TableView<Discount> discountTable;

	
	
	@FXML
	private TableColumn<Request, Integer> parametersIdCol;

	@FXML
	private TableColumn<Request, String> typeCol;

	@FXML
	private TableColumn<Request, String> oldValueCol;

	@FXML
	private TableColumn<Request, String> newValueCol;

	@FXML
	private TableColumn<Request, String> parametersStatusCol;

	@FXML
	private TableColumn<Discount, Integer> discountIdCol;

	@FXML
	private TableColumn<Discount, String> discountCol;

	@FXML
	private TableColumn<Discount, String> startDateCol;

	@FXML
	private TableColumn<Discount, String> endDateCol;

	@FXML
	private TableColumn<Discount, String> discountStatusCol;

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

	//	int parkId = MemberLoginController.member.getParkId();
		loadChanges();
	}

	@FXML
	void confirmRequestBtn() {

		TableViewSelectionModel<Request> request=parametersTable.getSelectionModel();

		Request r=request.getSelectedItem();

		ArrayList<Integer> parameters=new ArrayList<>();
		
		
		
		
		ParkControl.changeParkParameters(parameters);
		
		RequestControl.changeRequestStatus(r.getRequestId(),true);
	
		
		loadChanges();
	
	
		new CustomAlerts(AlertType.CONFIRMATION, "Sent", "Sent", "Request"+r.getChangeName()+" was confirmed with value"+r.getNewValue()).showAndWait();

	}

	void loadChanges() {
		

		RequestControl.viewcurrentRequests();
		parametersIdCol.setCellValueFactory(new PropertyValueFactory<>("requestId"));
		typeCol.setCellValueFactory(new PropertyValueFactory<>("changeName"));
		parametersStatusCol.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
		newValueCol.setCellValueFactory(new PropertyValueFactory<>("newValue"));
	
		oldValueCol.setCellValueFactory(new PropertyValueFactory<>("oldValue"));


		ObservableList<Request> requests=FXCollections.observableArrayList();
		requests.addAll(ChatClient.requestsWaitingForApproval);
	
		
		parametersTable.setItems(requests);

	
		
		discountIdCol.setCellValueFactory(new PropertyValueFactory<>("discountId"));
		discountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		
		
		ObservableList<Discount> discounts=FXCollections.observableArrayList();
		
//		this.discountId = discountId;
//		this.amount = amount;
//		this.startDate = startDate;
//		this.endDate = endDate;
//		this.parkId = parkId;
//		this.status = status;
		
		
				
	}
	

	@FXML
	void cancelRequestBtn() {

		TableViewSelectionModel<Request> request=parametersTable.getSelectionModel();

		Request r=request.getSelectedItem();

		RequestControl.changeRequestStatus(r.getRequestId(),false);

		loadChanges();
		new CustomAlerts(AlertType.CONFIRMATION, "Sent", "Sent", "Request"+r.getChangeName()+" was declined").showAndWait();

	}









}