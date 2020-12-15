package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.RequestControl;
import client.ChatClient;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
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
		
		int parkId = MemberLoginController.member.getParkId();

		System.out.println("CHECK");
		
		
		
        
		//RequestControl.viewcurrentRequests(); // send request to server to call SELECT query, respond to client with array containing each request
		
		
		for(int i=0;i<ChatClient.requestsWaitingForApproval.size();i++) {
			
		//	parametersIdCol = new TableColumn<>("Name");
			
		//	parametersIdCol = new TableColumn<>("Name");

			//parametersIdCol.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
			
	        
			
		//	parametersIdCol.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
	
	      //  parametersIdCol.(tableColumn);
		}
		
		
		
		
		
		
	}


	@FXML
	void confirmRequestBtn(ActionEvent event) {

		
		
	}
	
	
	
	
	@FXML
	void cancelRequestBtn(ActionEvent event) {

	}

	







}