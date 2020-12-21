package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.event.DocumentEvent.EventType;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import Controllers.ParkControl;
import Controllers.ReportsControl;
import alerts.CustomAlerts;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.report;

public class UsageReportController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private JFXDatePicker datePicker;

	@FXML
	private JFXButton sendToManagerBtn;

	@FXML
	private JFXTextArea commentTextArea;

	@FXML
	private Label monthLabel;

	@FXML
	private AnchorPane root;

	@FXML
	private ProgressIndicator pb;

	private int parkID;
	private int monthNumber;
	private int year = Calendar.getInstance().get(Calendar.YEAR);;
	private String comment;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Locale.setDefault(Locale.ENGLISH);
		initLabels();
		showDatePicker();
	}

	private void showDatePicker() {
		LocalDate date = LocalDate.of(year, monthNumber, 1);

		DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(date));
		Node popupContent = datePickerSkin.getPopupContent();

		popupContent.applyCss();
		popupContent.lookup(".month-year-pane").setVisible(false);

		EventHandler<MouseEvent> handler = MouseEvent::consume;

		popupContent.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
		popupContent.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
		popupContent.addEventFilter(MouseEvent.MOUSE_RELEASED, handler);
		root.getChildren().add(popupContent);
		AnchorPane.setTopAnchor(popupContent, 200.0);
		AnchorPane.setRightAnchor(popupContent, 182.0);
		AnchorPane.setLeftAnchor(popupContent, 182.0);
		AnchorPane.setBottomAnchor(popupContent, 410.0);

		DatePickerContent pop = (DatePickerContent) datePickerSkin.getPopupContent();
		List<DateCell> dateCells = getAllDateCells(pop);

		Task<Boolean> task = new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				setDateCellColor(dateCells);
				return true;
			}
		};
		pb.setVisible(true);
		root.setDisable(true);
		new Thread(task).start();

		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				pb.setVisible(false);
				root.setDisable(false);
			}
		});

	}

	private void setDateCellColor(List<DateCell> dateCells) {
		for (DateCell cell : dateCells) {
			cell.setEditable(false);

			ArrayList<String> result = isParkIsFullAtDate(year, monthNumber, Integer.parseInt(cell.getText()));
			if (result.get(0).equals("notFull"))
				cell.setStyle("-fx-background-color: #8cf55f;");
			else {
				cell.setStyle("-fx-background-color: #ffc0cb;");
				String comment = "";
				for (String str : result) {
					comment += str + "\n";
				}
				cell.setTooltip(new Tooltip(comment));
			}
		}
	}

	private ArrayList<String> isParkIsFullAtDate(int year, int monthNumber, int day) {
		String date = year + "-" + monthNumber + "-" + day;
		return ParkControl.isParkIsFullAtDate(date, String.valueOf(parkID));
	}

	/*
	 * HERE WE NEED TO HANDLE WHEN THE BUTTON IS CLICKED NEED TO ADD THE REPORT TO
	 * THE DATABASE
	 */
	@FXML
	private void sendToManagerBtn() {

		report r = new report(0, "Usage", parkID, monthNumber, commentTextArea.getText());
		if (ReportsControl.addReport(r)) {
			new CustomAlerts(AlertType.INFORMATION, "Success", "Success",
					"Usage report has been sent to department manager.").showAndWait();
			getStage().close();
		} else {
			new CustomAlerts(AlertType.ERROR, "Faild", "Faild",
					"Something went wrong. Please try again late.").showAndWait();
		}
	
	}

	private void initLabels() {
		monthLabel.setText(CreateReportsController.months[monthNumber]); // set the name of the month
		commentTextArea.setText(comment);
	}

	private static List<DateCell> getAllDateCells(DatePickerContent content) {
		List<DateCell> result = new ArrayList<>();
		int rowNum = 0;
		int flag = 0;
		for (Node n : content.getChildren()) {
			if (n instanceof GridPane) {
				GridPane grid = (GridPane) n;
				for (Node gChild : grid.getChildren()) {
					if (rowNum < 7 || flag == 0) {
						if (((DateCell) gChild).getText().equals("1")) {
							flag = 1;
						}
						rowNum++;
					}
					if (((DateCell) gChild).getText().equals("1") && rowNum >= 25) {
						break;
					}
					if (gChild instanceof DateCell && flag == 1 && rowNum >= 7) {
						result.add((DateCell) gChild);
						rowNum++;
					}
				}
			}
		}

		return result;
	}

	public void setMonthNumber(int month) {
		this.monthNumber = month;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	private Stage getStage() {
		return (Stage) monthLabel.getScene().getWindow();
	}

	public void setParkID(int parkID) {
		this.parkID = parkID;
	}

}
