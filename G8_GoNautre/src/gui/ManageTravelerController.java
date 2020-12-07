package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageTravelerController implements Initializable {

	@FXML
	private JFXTextField idTextField;

	@FXML
	private TableView<String> ordersTableView;

	@FXML
	private Button occVisitBtn;

	@FXML
	private Button confirmBtn;

	@FXML
	private Button searchBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void loadCasualVisit() {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CasualTravelerVisit.fxml"));
			CasualVisitController controller = new CasualVisitController();
			loader.setController(controller);
			loader.load();
			Parent p = loader.getRoot();
			Stage newStage = new Stage();

			/* Block parent stage until child stage closes */
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(thisStage);

			newStage.setTitle("Casul Visit");
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("faild to load form");
		}

	}

	private Stage getStage() {
		return (Stage) occVisitBtn.getScene().getWindow();
	}

}
