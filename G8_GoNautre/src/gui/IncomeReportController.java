package gui;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import Controllers.ReportsControl;
import alerts.CustomAlerts;
import client.ChatClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.GoNatureFinals;
import logic.Order;
import logic.Report;

@SuppressWarnings("unchecked")
public class IncomeReportController implements Initializable {

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

	@FXML
	private BarChart<String, Number> barChart;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;

	private ArrayList<String> newReportList;
	private static ArrayList<Integer> reportList;

	private int parkID;
	private int monthNumber;
	private String comment;
	private boolean isDepManager = false;

	/* Orders distributed by order type */
	private ArrayList<Order> solosOrdersUnClean = new ArrayList<Order>();
	private ArrayList<Order> subscribesrOrdersUnClean = new ArrayList<Order>();
	private ArrayList<Order> groupsOrdersUnClean = new ArrayList<Order>();

	/*
	 * Numbers of visitors distributed by days
	 * 0 - Sunday
	 * 6 - Saturday
	 */
	private int[] daysSolosClean = new int[7];
	private int[] daysSubscribersClean = new int[7];
	private int[] daysGroupClean = new int[7];
	private int[] totalClean = new int[7];

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
		getData();
		cleanData();

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadBarChat();
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();
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

	private void loadBarChat() {
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(Arrays.stream(totalClean).max().getAsInt() + 20);
		xAxis.setCategories(FXCollections.<String>observableArrayList(
				Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")));

		barChart.setBarGap(2);

		loadDataToChart(daysSolosClean, "Solos      ");
		loadDataToChart(daysSubscribersClean, "Subscribers");
		loadDataToChart(daysGroupClean, "Groups     ");
		loadDataToChart(totalClean, "Total      ");
		setToolTip();
	}

	private void loadDataToChart(int[] data, String name) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName(name);
		series.getData().add(new XYChart.Data<>("Sunday", data[0]));
		series.getData().add(new XYChart.Data<>("Monday", data[1]));
		series.getData().add(new XYChart.Data<>("Tuesday", data[2]));
		series.getData().add(new XYChart.Data<>("Wednesday", data[3]));
		series.getData().add(new XYChart.Data<>("Thursday", data[4]));
		series.getData().add(new XYChart.Data<>("Friday", data[5]));
		series.getData().add(new XYChart.Data<>("Saturday", data[6]));
		barChart.getData().add(series);
	}

	private void setToolTip() {
		for (XYChart.Series<String, Number> s : barChart.getData()) {
			for (XYChart.Data<String, Number> d : s.getData()) {

				Tooltip.install(d.getNode(),
						new Tooltip(s.getName().trim() + " Income in " + d.getXValue() + ": " + d.getYValue()));

				d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
				d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
			}
		}
	}

	@FXML
	private void sendToManagerBtn() {
		Report r = new Report(0, "Income", parkID, monthNumber, commentTextArea.getText());
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
		newReportList.add("Income");
		newReportList.add(String.valueOf(parkID));
		ReportsControl.showReport(newReportList);
		reportList = ChatClient.responseFromServer.getResultSet();

		individualLabel.setText(String.valueOf(reportList.get(0)));
		groupsLabel.setText(String.valueOf(reportList.get(2)));
		subscribersLabel.setText(String.valueOf(reportList.get(1)));
		totalLabel.setText(String.valueOf(reportList.get(0) + reportList.get(1) + reportList.get(2)));

	}

	private void getData() {
		solosOrdersUnClean = ReportsControl.getSolosOrdersVisitorsReport(monthNumber, parkID);
		subscribesrOrdersUnClean = ReportsControl.getSubscribersOrdersVisitorsReport(monthNumber, parkID);
		groupsOrdersUnClean = ReportsControl.getGroupOrdersVisitorsReport(monthNumber, parkID);
	}

	private void cleanData() {
		for (Order order : solosOrdersUnClean) {
			String date = order.getOrderDate();
			int dayInWeek = getNumberInWeek(date);
			daysSolosClean[dayInWeek] += order.getPrice();
			totalClean[dayInWeek] += order.getPrice();
		}

		for (Order order : subscribesrOrdersUnClean) {
			String date = order.getOrderDate();
			int dayInWeek = getNumberInWeek(date);
			daysSubscribersClean[dayInWeek] += order.getPrice();
			totalClean[dayInWeek] += order.getPrice();
		}

		for (Order order : groupsOrdersUnClean) {
			String date = order.getOrderDate();
			int dayInWeek = getNumberInWeek(date);
			daysGroupClean[dayInWeek] += order.getPrice();
			totalClean[dayInWeek] += order.getPrice();
		}
	}

	private int getNumberInWeek(String dateStr) {
		int dayOfWeek = 0;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			return (dayOfWeek - 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;

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