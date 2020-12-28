package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Controllers.ReportsControl;
import client.ChatClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import logic.GoNatureFinals;
import logic.VisitReport;

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

	private int monthNumber; // the month number

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}

	private void init() {
		lblMonth.setText(GoNatureFinals.MONTHS[monthNumber]); // set the name of the month
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fillEntranceTimeChart();
				fillVisitTimeChart();
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
	private void fillEntranceTimeChart() {
		enterX2.setAutoRanging(false);
		enterX2.setLowerBound(8.0);
		enterX2.setUpperBound(18.0);
		enterX2.setMinorTickVisible(false);
		enterX2.setTickUnit(0.5);
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep2 = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep3 = new ArrayList<VisitReport>();
		ReportsControl.countSolosEnterTime(monthNumber);
		rep = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		ReportsControl.countSubsEnterTime(monthNumber);
		rep2 = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
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
	private void fillVisitTimeChart() {
		stayX2.setAutoRanging(false);
		stayX2.setLowerBound(0.0);
		stayX2.setUpperBound(10.0);
		stayX2.setMinorTickVisible(false);
		stayX2.setTickUnit(0.5);
		ReportsControl.countSolosVisitTime(monthNumber);
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep2 = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep3 = new ArrayList<VisitReport>();
		rep = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		ReportsControl.countSubsVisitTime(monthNumber);
		rep2 = (ArrayList<VisitReport>) ChatClient.responseFromServer.getResultSet();
		ReportsControl.countGroupsVisitTime(monthNumber);
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
		stayTime_chart.getData().addAll(series, series3, series2);
		maxNumOfVisitors++;
		if(maxNumOfVisitors%2!=0)
			maxNumOfVisitors++;
		/* Y axis parameters setters */
		stayY.setAutoRanging(false);
		stayY.setLowerBound(0);
		stayY.setUpperBound(maxNumOfVisitors);
		stayY.setTickUnit(Math.ceil(maxNumOfVisitors*0.1));
		stayY.setMinorTickVisible(false);
	}
}
