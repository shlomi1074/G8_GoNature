package gui;

import java.net.URL;
import java.util.ResourceBundle;
import Controllers.ReportsControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.GoNatureFinals;

/**
 * counts pending orders with passed date and cancelled orders for each park
 * presents it in report and shows in total how many orders were cancelled
 */
public class CancelsReportController implements Initializable{
	
    @FXML
    private Label headerLabel;

    @FXML
    private Label monthLabel;

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
	/*does the counting and setting of each label*/
	private void initLabels() {
		/*sets label of month from user's choice from last screen */
		monthLabel.setText(GoNatureFinals.MONTHS[monthNumber]); // set the name of the month

		int cancels=0,cancelsPark1=0,cancelsPark2=0,cancelsPark3=0;
		/*Ardent*/
		cancelsPark1=ReportsControl.getParkCancels(1,monthNumber).get(0);			//get cancels for Ardent
		cancelsPark1+=ReportsControl.getParkPendingDatePassed(1,monthNumber).get(0);//get pending after date has passed for Ardent
		ardentParkCancelsLabel.setText(String.valueOf(cancelsPark1));				//set number for Ardent
		/*Pine*/
		cancelsPark2=ReportsControl.getParkCancels(2,monthNumber).get(0);			//get cancels for Pine
		cancelsPark2+=ReportsControl.getParkPendingDatePassed(2,monthNumber).get(0);//get pending after date has passed for Pine
		pineParkCancelsLabel.setText(String.valueOf(String.valueOf(cancelsPark2)));	//set cancels for Pine
		/*Linda*/
		cancelsPark3=ReportsControl.getParkCancels(3,monthNumber).get(0);			//get cancels for Linda
		cancelsPark3+=ReportsControl.getParkPendingDatePassed(3,monthNumber).get(0);//get pending after date has passed for Linda
		lindaParkCancelsLabel.setText(String.valueOf(String.valueOf(cancelsPark3)));//set cancels for Linda
		
		cancels=cancelsPark1+cancelsPark2+cancelsPark3;								//calculate total
		totalLabel.setText(String.valueOf(String.valueOf(cancels)));				//set total number of cancels
	}
	
	/**
	 * Setter for class variable monthNumber
	 * 
	 * @param month The mounth number
	 */
	public void setMonthNumber(int month){
		this.monthNumber = month;	
	}

}