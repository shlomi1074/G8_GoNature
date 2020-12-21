package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class CancelsReportController implements Initializable{
	
    @FXML
    private Label headerLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label arderParkCancelsLabel;

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
	
	/* HERE WE NEED TO GET THE DATA AND SET THE LABELS */
	private void initLabels() {
		monthLabel.setText(DepartmentManagerReportsController.months[monthNumber]); // set the name of the month
		
	}
	
	public void setMonthNumber(int month){
		this.monthNumber = month;	
	}

}
