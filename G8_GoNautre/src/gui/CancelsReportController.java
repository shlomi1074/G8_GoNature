package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.ReportsControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.report;
import logic.reportTb;

public class CancelsReportController implements Initializable{
	
    @FXML
    private Label headerLabel;

    @FXML
    private Label monthLabel;
    /*was arder, changed to ardent*/
    @FXML
    private Label ardentParkCancelsLabel;

    @FXML
    private Label pineParkCancelsLabel;

    @FXML
    private Label lindaParkCancelsLabel;

    @FXML
    private Label totalLabel;
	
    private int monthNumber; // the month number
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initLabels();
	}
	
	private void initLabels() {
		monthLabel.setText(DepartmentManagerReportsController.months[monthNumber]); // set the name of the month
		/*Lior*/
		int cancels=ReportsControl.getParkCancels(1,monthNumber).get(0);				//get cancels for ardent
		ardentParkCancelsLabel.setText(String.valueOf(cancels));			//set cancels for ardent
		int temp=ReportsControl.getParkCancels(2,monthNumber).get(0);					//get cancels for pine
		cancels+=temp;														//add to total number of cancels
		pineParkCancelsLabel.setText(String.valueOf(String.valueOf(temp)));	//set cancels for pine
		temp=ReportsControl.getParkCancels(3,monthNumber).get(0);						//get cancels for linda
		cancels+=temp;														//add to total number of cancels
		lindaParkCancelsLabel.setText(String.valueOf(String.valueOf(temp)));//set cancels for linda
		totalLabel.setText(String.valueOf(String.valueOf(cancels)));		//set total number of cancels
	}
	
	public void setMonthNumber(int month){
		this.monthNumber = month;	
	}

}