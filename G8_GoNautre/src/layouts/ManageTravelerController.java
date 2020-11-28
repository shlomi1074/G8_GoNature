package layouts;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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

}
