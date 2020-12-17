package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;



public class TotalVisitorsReportController implements Initializable{
	
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
    
    private int parkID = MemberLoginController.member.getParkId();
    private int monthNumber;
    private String comment;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initLabels();
		commentTextArea.setText(comment);
	}
	
	/* HERE WE NEED TO HANDLE WHEN THE BUTTON IS CLICKED
	 * NEED TO ADD THE REPORT TO THE DATABASE */
	@FXML
	private void sendToManagerBtn() {
		
		/*ADD TO TABLE CODE HERE */
		
		/* NEED TO ADD SUCCESS ALERT (Information) */
		
		
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
}
