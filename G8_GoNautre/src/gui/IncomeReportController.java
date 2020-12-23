package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import Controllers.ReportsControl;
import client.ChatClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class IncomeReportController implements Initializable{
	
	 @FXML
	    private Label headerLabel;

	    @FXML
	    private Label monthLabel;

	    @FXML
	    private Label individualLabel;

	    @FXML
	    private Label groupsLabel;

	    @FXML
	    private Label subscribersLabel;

	    @FXML
	    private Label totalLabel;
	    
	    @FXML
	    private JFXButton sendToManagerBtn;
	    
	    @FXML
	    private JFXTextArea commentTextArea;
	    
	    private ArrayList<String> newReportList;
		private static ArrayList<?> reportList;
	    
	    private int parkID;
	    private int monthNumber;
	    private String comment;
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			initLabels();
			commentTextArea.setText(comment);
			
			newReportList=new ArrayList<>();
			
			newReportList.add(String.valueOf(monthNumber));
			newReportList.add("Income Report");
			newReportList.add(String.valueOf(MemberLoginController.member.getParkId()));
			
			ReportsControl.showReport(newReportList);
			reportList=ChatClient.responseFromServer.getResultSet();
			
			monthLabel.setText(CreateReportsController.months[CreateReportsController.month]);
			individualLabel.setText(String.valueOf(reportList.get(0)));	
			groupsLabel.setText(String.valueOf(reportList.get(1)));	
			subscribersLabel.setText(String.valueOf(reportList.get(2)));	
			totalLabel.setText(String.valueOf(reportList.get(3)));	

			
			
			
		}
		
		/* HERE WE NEED TO HANDLE WHEN THE BUTTON IS CLICKED
		 * NEED TO ADD THE REPORT TO THE DATABASE */
		@FXML
		private void sendToManagerBtn() {
			
			/*ADD TO TABLE CODE HERE */
			
			/* NEED TO ADD SUCCESS ALERT (Information) */
			
			ArrayList<String> reportArrayToUpdateDB=new ArrayList<>();
		
			reportArrayToUpdateDB.add(String.valueOf(CreateReportsController.getMonth()));
			reportArrayToUpdateDB.add("Income Report");
			reportArrayToUpdateDB.add(String.valueOf(MemberLoginController.member.getParkId()));
			reportArrayToUpdateDB.add(commentTextArea.getText());
			ReportsControl.addNewReportToDB(reportArrayToUpdateDB);
			/* This line must be at the end of this function
			 * This line closes the window */
			getStage().close();
		}
		
		/* HERE WE NEED TO GET THE DATA AND SET THE LABELS */
		private void initLabels() {
			monthLabel.setText(CreateReportsController.months[monthNumber]); // set the name of the month
			
		}

		public void setMonthNumber(int month){
			this.monthNumber = month;	
		}

		public void setComment(String comment){
			this.comment = comment;	
		}
		
		private Stage getStage() {
			return (Stage) monthLabel.getScene().getWindow();
		}
		
		public void setParkID(int parkID){
			this.parkID = parkID;
		}

}