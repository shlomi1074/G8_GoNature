package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import Controllers.ReportsControl;
import alerts.CustomAlerts;
import client.ChatClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.GoNatureFinals;
import logic.report;

public class TotalVisitorsReportController implements Initializable {

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
	private boolean isDepManager = false;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();

	}

	private void init() {
		initLabels();
		commentTextArea.setText(comment);

		if (isDepManager) {
			commentTextArea.setEditable(false);
			commentTextArea.setPromptText("Park manager comment:");
			sendToManagerBtn.setDisable(true);
			sendToManagerBtn.setVisible(false);
		}

	}

	@FXML
	private void sendToManagerBtn() {

		report r = new report(0, "Total Visitors", parkID, monthNumber, commentTextArea.getText());
		if (ReportsControl.addReport(r)) {
			new CustomAlerts(AlertType.INFORMATION, "Success", "Success",
					"Total Visitors report has been sent to department manager.").showAndWait();
			getStage().close();
		} else {
			new CustomAlerts(AlertType.ERROR, "Faild", "Faild", "Something went wrong. Please try again late.")
					.showAndWait();
		}

		getStage().close();
	}

	private void initLabels() {
		monthLabel.setText(GoNatureFinals.MONTHS[monthNumber]); // set the name of the month

		newReportList = new ArrayList<>();

		newReportList.add(String.valueOf(monthNumber));
		newReportList.add("Total Visitors");
		newReportList.add(String.valueOf(parkID));

		ReportsControl.showReport(newReportList);
		reportList = ChatClient.responseFromServer.getResultSet();

		individualLabel.setText(String.valueOf(reportList.get(0)));
		groupsLabel.setText(String.valueOf(reportList.get(1)));
		subscribersLabel.setText(String.valueOf(reportList.get(2)));
		totalLabel.setText(String.valueOf(reportList.get(3)));

	}

	/**
	 * Setter for class variable monthNumber
	 * 
	 * @param month
	 */
	public void setMonthNumber(int month) {
		this.monthNumber = month;
	}

	/**
	 * Setter for class variable comment
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	private Stage getStage() {
		return (Stage) monthLabel.getScene().getWindow();
	}

	/**
	 * Setter for class variable parkID
	 * 
	 * @param parkID
	 */
	public void setParkID(int parkID) {
		this.parkID = parkID;
	}

	/**
	 * Setter for class variable isDepManager
	 * 
	 * @param b
	 */
	public void setIsDepManager(boolean b) {
		this.isDepManager = b;

	}

}