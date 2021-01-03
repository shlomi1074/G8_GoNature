package gui;

import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ReportsControl;
import client.ChatClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import logic.GoNatureFinals;
import logic.VisitReport;

/**
 * Gets month that picked from previous page.
 * Loads all visitors stay time and entrance time into a line chart.
 */
public class VisitsReportController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private LineChart<Number, Number> stayTime_chart;

	@FXML
	private NumberAxis stayX2;

	@FXML
	private NumberAxis stayY;

	@FXML
	private LineChart<Number, Number> entranceTime_chart;

	@FXML
	private NumberAxis enterX2;

	@FXML
	private NumberAxis enterY;

	@FXML
	private Label lblMonth;
	
	@FXML
	private JFXComboBox<String> comboBox;
   

	private int monthNumber; // the month number

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}

	private void init() {
		lblMonth.setText(GoNatureFinals.MONTHS[monthNumber]); // set the name of the month
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fillEntranceTimeChart("Show whole month");
				fillVisitTimeChart("Show whole month");
				initComboBox();
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();
	}

	/**
	 * Setter for class variable monthNumber
	 * 
	 * @param month
	 */
	public void setMonthNumber(int month) {
		this.monthNumber = month;
	}

	@SuppressWarnings("unchecked")
	private void fillEntranceTimeChart(String option) {
		enterX2.setAutoRanging(false);
		enterX2.setLowerBound(7.5);
		enterX2.setUpperBound(18.0);
		enterX2.setMinorTickVisible(false);
		enterX2.setTickUnit(0.5);
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep2 = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep3 = new ArrayList<VisitReport>();
		if(!option.equals("Show whole month"))
			ReportsControl.countSolosEnterTimeWithDays(monthNumber,option);
		else
		ReportsControl.countSolosEnterTime(monthNumber);
		rep = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		if(!option.equals("Show whole month"))
			ReportsControl.countSubsEnterTimeWithDays(monthNumber,option);
		else
		ReportsControl.countSubsEnterTime(monthNumber);
		rep2 = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		if(!option.equals("Show whole month"))
			ReportsControl.countGroupsEnterTimeWithDays(monthNumber,option);
		else
		ReportsControl.countGroupsEnterTime(monthNumber);
		rep3 = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		XYChart.Series<Number, Number> series = new Series<Number, Number>();
		XYChart.Series<Number, Number> series2 = new Series<Number, Number>();
		XYChart.Series<Number, Number> series3 = new Series<Number, Number>();
		double hour, min, time;
		int maxNumOfVisitors = 0, sum;
		for (int i = 0; i < rep.size(); i++) {
			sum = rep.get(i).getSum();
			if (maxNumOfVisitors < sum) {
				maxNumOfVisitors = sum;
			}
			hour = Double.parseDouble(rep.get(i).getData().substring(0, 2));
			min = Double.parseDouble(rep.get(i).getData().substring(3, 5)) / 60;
			time = hour + min;
			series.getData().add(new Data<Number, Number>(time, sum));
		}
		for (int i = 0; i < rep2.size(); i++) {
			sum = rep2.get(i).getSum();
			if (maxNumOfVisitors < sum) {
				maxNumOfVisitors = sum;
			}
			hour = Double.parseDouble(rep2.get(i).getData().substring(0, 2));
			min = Double.parseDouble(rep2.get(i).getData().substring(3, 5)) / 60;
			time = hour + min;
			series2.getData().add(new Data<Number, Number>(time, sum));
		}
		for (int i = 0; i < rep3.size(); i++) {
			sum = rep3.get(i).getSum();
			if (maxNumOfVisitors < sum) {
				maxNumOfVisitors = sum;
			}
			hour = Double.parseDouble(rep3.get(i).getData().substring(0, 2));
			min = Double.parseDouble(rep3.get(i).getData().substring(3, 5)) / 60;
			time = hour + min;
			series3.getData().add(new Data<Number, Number>(time, sum));
		}
		series.setName("Solos      ");
		series2.setName("Subscribers");
		series3.setName("Groups     ");
		entranceTime_chart.getData().addAll(series, series3, series2);
		setToolTip();
		maxNumOfVisitors++;
		if(maxNumOfVisitors%2!=0)
			maxNumOfVisitors++;
		/* Y axis parameters setters */
		enterY.setAutoRanging(false);
		enterY.setLowerBound(0);
		enterY.setUpperBound(maxNumOfVisitors);
		enterY.setTickUnit(Math.ceil(maxNumOfVisitors*0.1));
		enterY.setMinorTickVisible(false);

	}

	@SuppressWarnings("unchecked")
	private void fillVisitTimeChart(String option) {
		stayX2.setAutoRanging(false);
		stayX2.setLowerBound(0.0);
		stayX2.setUpperBound(10.0);
		stayX2.setMinorTickVisible(false);
		stayX2.setTickUnit(0.5);
		if(!option.equals("Show whole month"))
			ReportsControl.countSolosVisitTimeWithDay(monthNumber,option);
		else
		ReportsControl.countSolosVisitTime(monthNumber);
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep2 = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep3 = new ArrayList<VisitReport>();
		rep = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		if(!option.equals("Show whole month"))
			ReportsControl.countSubsVisitTimeWithDay(monthNumber,option);
		else
		ReportsControl.countSubsVisitTime(monthNumber);
		rep2 = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		if(!option.equals("Show whole month"))
			ReportsControl.countGroupsVisitTimeWithDay(monthNumber,option);
		else
		ReportsControl.countGroupsVisitTime(monthNumber);
		rep3 = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		double totalNumOfVisitors=0;
		/*Sum total visitors at this date*/
		for(int i = 0; i<rep.size();i++)
			totalNumOfVisitors+=rep.get(i).getSum();
		for(int i = 0; i<rep2.size();i++)
			totalNumOfVisitors+=rep2.get(i).getSum();
		for(int i = 0; i<rep3.size();i++)
			totalNumOfVisitors+=rep3.get(i).getSum();

		XYChart.Series<Number, Number> series = new Series<Number, Number>();
		XYChart.Series<Number, Number> series2 = new Series<Number, Number>();
		XYChart.Series<Number, Number> series3 = new Series<Number, Number>();
		double hour, min, time;
		double maxNumOfVisitors = 0, sum=0;
		for (int i = 0; i < rep.size(); i++) {
			sum = rep.get(i).getSum();
			if (maxNumOfVisitors < sum) {
				maxNumOfVisitors = sum;
			}
			hour = Double.parseDouble(rep.get(i).getData().substring(0, 2));
			min = Double.parseDouble(rep.get(i).getData().substring(3, 5)) / 60;
			time = hour + min;
			series.getData().add(new Data<Number, Number>(time, sum/totalNumOfVisitors*100));
		}
		for (int i = 0; i < rep2.size(); i++) {
			sum = rep2.get(i).getSum();
			if (maxNumOfVisitors < sum) {
				maxNumOfVisitors = sum;
			}
			hour = Double.parseDouble(rep2.get(i).getData().substring(0, 2));
			min = Double.parseDouble(rep2.get(i).getData().substring(3, 5)) / 60;
			time = hour + min;
			series2.getData().add(new Data<Number, Number>(time, sum/totalNumOfVisitors*100));
		}
		for (int i = 0; i < rep3.size(); i++) {
			sum = rep3.get(i).getSum();
			if (maxNumOfVisitors < sum) {
				maxNumOfVisitors = sum;
			}
			hour = Double.parseDouble(rep3.get(i).getData().substring(0, 2));
			min = Double.parseDouble(rep3.get(i).getData().substring(3, 5)) / 60;
			time = hour + min;

			series3.getData().add(new Data<Number, Number>(time, sum/totalNumOfVisitors*100));
		}
		series.setName("Solos      ");
		series2.setName("Subscribers");
		series3.setName("Groups     ");
		stayTime_chart.getData().addAll(series, series3, series2);
		setToolTip();
		/* Y axis parameters setters */
		stayY.setAutoRanging(false);
		stayY.setLowerBound(0);
		if(totalNumOfVisitors==0)
			totalNumOfVisitors++;
		stayY.setUpperBound(maxNumOfVisitors/totalNumOfVisitors*100 + 5 > 100 ? 100: maxNumOfVisitors/totalNumOfVisitors*100 + 5);
		stayY.setTickUnit(1);
		stayY.setMinorTickVisible(false);
	}
	
	private void setToolTip() {
		for (XYChart.Series<Number, Number> s : stayTime_chart.getData()) {
			for (XYChart.Data<Number, Number> d : s.getData()) {

				Tooltip.install(d.getNode(), new Tooltip(d.getYValue()+"%"));
				
				d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
				d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
			}
		}
		for (XYChart.Series<Number, Number> s : entranceTime_chart.getData()) {
			for (XYChart.Data<Number, Number> d : s.getData()) {

				Tooltip.install(d.getNode(), new Tooltip(d.getYValue()+" Visitors"));
				
				d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
				d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
			}
		}
	}
	
	private void initComboBox() {
		int days = findNumOfDays();
		ObservableList<String> month_days = FXCollections.observableArrayList();
		month_days.add("Show whole month");
		for(int i=1;i<=days;i++) {
			month_days.add(String.valueOf(i));
		}
		comboBox.getItems().addAll(month_days);
		comboBox.getSelectionModel().select(0);
		comboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (newItem == null) {
			} else {
				entranceTime_chart.getData().clear();
				fillEntranceTimeChart(comboBox.getSelectionModel().getSelectedItem());
				stayTime_chart.getData().clear();
				fillVisitTimeChart(comboBox.getSelectionModel().getSelectedItem());
			}
		});
	}
	private int findNumOfDays() {
		String month = null;
		if(monthNumber<10)
			month="0"+monthNumber;
		else
			month = String.valueOf(monthNumber);
		YearMonth ym = YearMonth.parse(Calendar.getInstance().get(Calendar.YEAR)+"-"+month);
		return ym.lengthOfMonth();
	}
}