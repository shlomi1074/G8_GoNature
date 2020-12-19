package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import alerts.CustomAlerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepartmentManagerReportsController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private TableColumn<?, ?> reportIDCol;

	@FXML
	private TableColumn<?, ?> reportTypeCol;

	@FXML
	private TableColumn<?, ?> parkIDCol;

	@FXML
	private TableColumn<?, ?> monthCol;

	@FXML
	private TableColumn<?, ?> commentCol;

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

	/* Here we need to fill the tabe view from database */
	public void loadTabelView() {

	}

	@FXML
	private void visitReportBtn() {
		fxmlName = ""; // NEED TO CREATE GUI
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
