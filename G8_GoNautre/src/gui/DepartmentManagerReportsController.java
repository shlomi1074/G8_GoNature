package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ReportsControl;
import alerts.CustomAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.report;
import logic.reportTb;

public class DepartmentManagerReportsController implements Initializable {

	ObservableList<reportTb> observable = FXCollections.observableArrayList(); /*Lior*/

	@FXML
	private Label headerLabel;

    @FXML
    private TableView<reportTb> ReportsTableView;//Lior added id to table in fxml

	@FXML
	private TableColumn<reportTb, Integer> reportIDCol;

	@FXML
	private TableColumn<reportTb, String> reportTypeCol;

	@FXML
	private TableColumn<reportTb, Integer> parkIDCol;

	@FXML
	private TableColumn<reportTb, Integer> monthCol;

	@FXML
	private TableColumn<reportTb, String> commentCol;

	@FXML
	private JFXButton visitReportBtn;

	@FXML
	private JFXButton CancelsReportBtn;

	@FXML
	private JFXComboBox<String> monthCB;

	protected static String[] months = { "Month", "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };
	private String fxmlName;
	private String screenTitle;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTabelView();
		initComboBox();
	}

	@FXML
	private void visitReportBtn() {
		fxmlName = "/gui/VisitsReports.fxml";
		screenTitle = "Visits Report";
		if (monthCB.getSelectionModel().getSelectedIndex() != 0) {
			switchScenceWithController();
		}
		else {
			new CustomAlerts(AlertType.ERROR, "Error", "Month Error", "Plesae choose month.").showAndWait();
		}
	}

	@FXML
	private void cancelReportBtn() {
		fxmlName = "/gui/CancelsReport.fxml";
		screenTitle = "Cancels Report";
		if (monthCB.getSelectionModel().getSelectedIndex() != 0) {
			switchScenceWithController();
		}
		else {
			new CustomAlerts(AlertType.ERROR, "Error", "Month Error", "Plesae choose month.").showAndWait();
		}
	}
	
	/*Lior*/
	/* Here we need to fill the table view from database */
	public void loadTabelView() {
		ArrayList<report> reports= ReportsControl.getReports();
		ArrayList<reportTb> tbReports= convertreportsToTeportTb(reports);
		init(tbReports);
		ReportsTableView.setItems(getReports(tbReports));
	}
	/*Lior*/
	/*Here we convert reports from database To TeportTb*/
	public static ArrayList<reportTb> convertreportsToTeportTb(ArrayList<report> reports) {
		ArrayList<reportTb> tbReports = new ArrayList<reportTb>();
		for (report report : reports) {
			reportTb tbReport = new reportTb(report);
			tbReports.add(tbReport);
		}
		return tbReports;
	}
	
	/*Lior*/
	private void init(ArrayList<reportTb> tbReports) {
		reportIDCol.setCellValueFactory(new PropertyValueFactory<reportTb, Integer>("reportID"));
		reportTypeCol.setCellValueFactory(new PropertyValueFactory<reportTb, String>("reportType"));
		parkIDCol.setCellValueFactory(new PropertyValueFactory<reportTb, Integer>("parkID"));
		monthCol.setCellValueFactory(new PropertyValueFactory<reportTb, Integer>("month"));
		commentCol.setCellValueFactory(new PropertyValueFactory<reportTb, String>("comment"));
	}

	/*Lior*/
	private ObservableList<reportTb> getReports(ArrayList<reportTb> tbReports) {
		ReportsTableView.getItems().clear();
		for (reportTb report : tbReports) {
			observable.add(report);
		}
		return observable;
	}

	private void initComboBox() {
		monthCB.getItems().addAll(months);
		monthCB.getSelectionModel().select(0);
	}

	private Stage getStage() {
		return (Stage) monthCB.getScene().getWindow();
	}

	private void switchScenceWithController() {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
			if (screenTitle.equals("Cancels Report")) {
				CancelsReportController controller = new CancelsReportController();
				controller.setMonthNumber(monthCB.getSelectionModel().getSelectedIndex());
				loader.setController(controller);
			}
			else {
				VisitsReportController controller = new VisitsReportController();
				controller.setMonthNumber(monthCB.getSelectionModel().getSelectedIndex());
				loader.setController(controller);
				
			}
			loader.load();
			Parent p = loader.getRoot();
			Stage newStage = new Stage();

			/* Block parent stage until child stage closes */
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(thisStage);

			newStage.setTitle(screenTitle);
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			System.out.println("faild to load form");
			e.printStackTrace();
		}

	}

}
