package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Controllers.ParkControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Order;
import logic.Traveler;

public class OrderConfirmationController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private Label summaryID;

	@FXML
	private Label summaryEmail;

	@FXML
	private Label summaryFullName;

	@FXML
	private Label summaryPhone;

	@FXML
	private Label summaryPark;

	@FXML
	private Label summaryDate;

	@FXML
	private Label summaryTime;

	@FXML
	private Label summaryType;

	@FXML
	private Label summaryVisitors;

	@FXML
	private Label summaryPayment;

	@FXML
	private Label totalPriceLabel;

	@FXML
	private JFXButton finishBtn;

	private Order order;
	private Traveler traveler;
	private String paymentMethod;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setOrderInfo();
	}

	public void setOrderInfo() {
		if (order != null && traveler != null) {
			summaryID.setText(order.getTravelerId());
			summaryFullName.setText(traveler.getFirstName() + " " + traveler.getLastName());
			summaryPhone.setText(traveler.getPhoneNumber());
			summaryEmail.setText(traveler.getEmail());
			summaryPark.setText(ParkControl.getParkName(order.getParkId() + ""));
			summaryDate.setText(order.getOrderDate());
			summaryTime.setText(order.getOrderTime());
			summaryType.setText(order.getOrderType());
			summaryVisitors.setText(order.getNumberOfParticipants() + "");
			summaryPayment.setText(paymentMethod);

			totalPriceLabel.setText(order.getPrice() + "₪");
		}

	}

	/* On OK button click */
	@FXML
	private void closeStage() {
		getStage().close();
	}

	public Stage getStage() {
		return (Stage) totalPriceLabel.getScene().getWindow();
	}

	public void setOrder(Order recentOrder) {
		this.order = recentOrder;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public void setSummaryPayment(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
