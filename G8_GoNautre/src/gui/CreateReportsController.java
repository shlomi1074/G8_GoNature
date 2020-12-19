package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;

import alerts.CustomAlerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateReportsController implements Initializable {

	@FXML
	private AnchorPane createReportsRootPane;

	@FXML
	private Label headerLabel;

	@FXML
	private TitledPane monthTP;

	@FXML
	private AnchorPane chooseMonthAP;

	@FXML
	private JFXComboBox<String> monthCB;

	@FXML
	private TitledPane reportTP;

	@FXML
	private AnchorPane chooseReportAP;

	@FXML
	private JFXRadioButton totalVisitorsRB;

	@FXML
	private JFXRadioButton useageRB;

	@FXML
	private JFXRadioButton IncomeRB;

	@FXML
	private TitledPane commentTP;

	@FXML
	private AnchorPane addCommentAP;

	@FXML
	private JFXTextArea commentTextArea;

	@FXML
	private JFXButton createButton;

	@FXML
	private Accordion accordion;

	protected static String[] months = { "Month", "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };
	private String fxmlName;
	private String screenTitle;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}

	private void init() {
		accordion.setExpandedPane(monthTP);
		initComboBox();
		/* Default settings */
		totalVisitorsRB.setSelected(true);
		this.fxmlName = "/gui/TotalVisitorsReport.fxml";
		screenTitle = "Total Visitors Report";
	}

	@FXML
	private void createReportButton() {
		if (monthCB.getSelectionModel().getSelectedIndex() == 0) {
			new CustomAlerts(AlertType.ERROR, "Error", "Month Error", "Plesae choose month.").showAndWait();
		} else {
			switchScenceWithController();
		}
	}

	@FXML
	private void turnON_totalVisitorsRB() {
		useageRB.setSelected(false);
		IncomeRB.setSelected(false);
		this.fxmlName = "/gui/TotalVisitorsReport.fxml";
		screenTitle = "Total Visitors Report";
	}

	@FXML
	private void turnON_useageRB() {
		totalVisitorsRB.setSelected(false);
		IncomeRB.setSelected(false);
		this.fxmlName = "/gui/UseageReport.fxml";
		screenTitle = "Useage Report";
	}

	@FXML
	private void turnON_IncomeRB() {
		totalVisitorsRB.setSelected(false);
		useageRB.setSelected(false);
		this.fxmlName = "/gui/IncomeReport.fxml";
		screenTitle = "Income Report";
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
			if (totalVisitorsRB.isSelected()) {
				TotalVisitorsReportController controller = new TotalVisitorsReportController();
				controller.setComment(commentTextArea.getText());
				controller.setMonthNumber(monthCB.getSelectionModel().getSelectedIndex());
				controller.setParkID(MemberLoginController.member.getParkId());
				loader.setController(controller);
			} else if (useageRB.isSelected()) {

			} else if (IncomeRB.isSelected()) {
				IncomeReportController controller = new IncomeReportController();
				controller.setComment(commentTextArea.getText());
				controller.setMonthNumber(monthCB.getSelectionModel().getSelectedIndex());
				controller.setParkID(MemberLoginController.member.getParkId());
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
