package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.ReportsControl;
import client.ChatClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import logic.VisitReport;

public class VisitsReportController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private LineChart<Number, Number> stayTime_chart;

//	@FXML
//	private CategoryAxis stayX;
	
    @FXML
    private NumberAxis stayX2;

	@FXML
	private NumberAxis stayY;

	@FXML
	private LineChart<Number, Number> entranceTime_chart;

//	@FXML
//	private CategoryAxis enterX;
	
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
		lblMonth.setText(DepartmentManagerReportsController.months[monthNumber]); // set the name of the month
		fillEntranceTimeChart();
		fillVisitTimeChart();
	}

	public void setMonthNumber(int month) {
		this.monthNumber = month;
	}

	
	@SuppressWarnings("unchecked")
	public void fillEntranceTimeChart() {
		enterX2.setAutoRanging(false);
		enterX2.setLowerBound(8.0);
		enterX2.setUpperBound(18.0);
		enterX2.setMinorTickVisible(false);
		enterX2.setTickUnit(0.5);
		ReportsControl.countSolosEnterTime(monthNumber);
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep2 = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep3 = new ArrayList<VisitReport>();
		rep = (ArrayList<VisitReport>)ChatClient.responseFromServer.getResultSet();
		ReportsControl.countSubsEnterTime(monthNumber);
		rep2 = (ArrayList<VisitReport>)ChatClient.responseFromServer.getResultSet();
		ReportsControl.countGroupsEnterTime(monthNumber);
		rep3 = (ArrayList<VisitReport>)ChatClient.responseFromServer.getResultSet();
		XYChart.Series<Number,Number> series = new Series<Number, Number>();
		XYChart.Series<Number,Number> series2 = new Series<Number, Number>();
		XYChart.Series<Number,Number> series3 = new Series<Number, Number>();
		double hour,min,time;
		for (int i = 0; i < rep.size() ; i++) {
			hour = Double.parseDouble(rep.get(i).getData().substring(0,2));
			min = Double.parseDouble(rep.get(i).getData().substring(3,5))/60;
			time = hour+min;
			series.getData().add(new Data<Number, Number>(time,rep.get(i).getSum()));
		}
		for (int i = 0; i < rep2.size() ; i++) {
			hour = Double.parseDouble(rep2.get(i).getData().substring(0,2));
			min = Double.parseDouble(rep2.get(i).getData().substring(3,5))/60;
			time = hour+min;
			series2.getData().add(new Data<Number, Number>(time,rep2.get(i).getSum()));
		}
		for (int i = 0; i < rep3.size() ; i++) {
			hour = Double.parseDouble(rep3.get(i).getData().substring(0,2));
			min = Double.parseDouble(rep3.get(i).getData().substring(3,5))/60;
			time = hour+min;
			series3.getData().add(new Data<Number, Number>(time,rep3.get(i).getSum()));
		}
		series.setName("Solos      ");
		series2.setName("Subscribers");
		series3.setName("Groups     ");
		entranceTime_chart.getData().addAll(series,series3,series2);
		/*This is my solution to not show decimal value in y axis this is not perfect we should change it before presentation 
		enterY.setAutoRanging(false);
		enterY.setLowerBound(0);
		enterY.setUpperBound(15);
		enterY.setTickUnit(1);
		enterY.setMinorTickVisible(false);
		*/
		
	}
	
	@SuppressWarnings("unchecked")
	public void fillVisitTimeChart() {
		stayX2.setAutoRanging(false);
		stayX2.setLowerBound(0.0);
		stayX2.setUpperBound(10.0);
		stayX2.setMinorTickVisible(false);
		stayX2.setTickUnit(0.5);
		ReportsControl.countSolosVisitTime(monthNumber);
		ArrayList<VisitReport> rep = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep2 = new ArrayList<VisitReport>();
		ArrayList<VisitReport> rep3 = new ArrayList<VisitReport>();
		rep = (ArrayList<VisitReport>)ChatClient.responseFromServer.getResultSet();
		ReportsControl.countSubsVisitTime(monthNumber);
		rep2 = (ArrayList<VisitReport>)ChatClient.responseFromServer.getResultSet();
		ReportsControl.countGroupsVisitTime(monthNumber);
		rep3 = (ArrayList<VisitReport>)ChatClient.responseFromServer.getResultSet();
		
		XYChart.Series<Number,Number> series = new Series<Number, Number>();
		XYChart.Series<Number,Number> series2 = new Series<Number, Number>();
		XYChart.Series<Number,Number> series3 = new Series<Number, Number>();
		double hour,min,time;
		for (int i = 0; i < rep.size() ; i++) {
			hour = Double.parseDouble(rep.get(i).getData().substring(0,2));
			min = Double.parseDouble(rep.get(i).getData().substring(3,5))/60;
			time = hour+min;
			series.getData().add(new Data<Number, Number>(time,rep.get(i).getSum()));
		}
		for (int i = 0; i < rep2.size() ; i++) {
			hour = Double.parseDouble(rep2.get(i).getData().substring(0,2));
			min = Double.parseDouble(rep2.get(i).getData().substring(3,5))/60;
			time = hour+min;
			series2.getData().add(new Data<Number, Number>(time,rep2.get(i).getSum()));
		}
		for (int i = 0; i < rep3.size() ; i++) {
			hour = Double.parseDouble(rep3.get(i).getData().substring(0,2));
			min = Double.parseDouble(rep3.get(i).getData().substring(3,5))/60;
			time = hour+min;
			
			series3.getData().add(new Data<Number, Number>(time,rep3.get(i).getSum()));
		}
		series.setName("Solos      ");
		series2.setName("Subscribers");
		series3.setName("Groups     ");
		stayTime_chart.getData().addAll(series,series3,series2);
		/*This is my solution to not show decimal value in y axis this is not perfect we should change it before presentation 
		enterY.setAutoRanging(false);
		enterY.setLowerBound(0);
		enterY.setUpperBound(15);
		enterY.setTickUnit(1);
		enterY.setMinorTickVisible(false);
		*/
	}
}
