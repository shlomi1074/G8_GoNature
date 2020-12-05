package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainScreenMenuController implements Initializable {

	@FXML
	private JFXButton orderVisit;

	@FXML
	private JFXButton travelerLogin;

	@FXML
	private JFXButton memberLogin;

	@FXML
	private JFXButton showPrices;

	@FXML
	private JFXButton contactUs;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void loadContactUs() {
		switchScence("ContactUs.fxml", "GoNature8 - Contact Us");
	}

	@FXML
	private void loadMemberLogin() {
		switchScenceWithController("MemberLogin.fxml", "GoNature8 - Member Login", 2);
	}

	@FXML
	private void loadTravelerLogin() {
		switchScenceWithController("TravelerLogin.fxml", "GoNature8 - Traveler Login", 1);
	}

	@FXML
	private void loadOrderVisit() {
		switchScenceWithController("OrderVisit.fxml", "GoNature8 - Order A Visit", 3);
	}

	private void switchScence(String fxmlName, String screenTitle) {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
			loader.load();
			Parent p = loader.getRoot();
			Stage newStage = new Stage();

			/* Block parent stage until child stage closes */
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(thisStage);

			newStage.setTitle(screenTitle);
			// stage.getIcons().add(new Image("url"));
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			System.out.println("faild to load form");
		}

	}

	private void switchScenceWithController(String fxmlName, String screenTitle, int id) {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
			if (id == 1) {
				TravelerLoginController controller = new TravelerLoginController(thisStage);
				loader.setController(controller);
			} else if (id == 2) {
				MemberLoginController controller = new MemberLoginController(thisStage);
				loader.setController(controller);
			} else if (id == 3) {
				OrderVisitController controller = new OrderVisitController();
				loader.setController(controller);
			}
			loader.load();
			Parent p = loader.getRoot();
			Stage newStage = new Stage();

			/* Block parent stage until child stage closes */
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(thisStage);

			newStage.setTitle(screenTitle);
			// stage.getIcons().add(new Image("url"));
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
			// thisStage.close();
		} catch (IOException e) {
			System.out.println("faild to load form");
			e.printStackTrace();
		}

	}

	private Stage getStage() {
		return (Stage) orderVisit.getScene().getWindow();
	}
}
