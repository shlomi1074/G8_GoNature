package layouts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class MainScreenController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private JFXDrawer drawMenu;

	@FXML
	private AnchorPane rootAnchorPane;

	@FXML
	private WebView youTube;

	@FXML
	private ImageView firstParkImage;

	@FXML
	private ImageView secondParkImage;

	@FXML
	private ImageView thirdParkImage;

	@FXML
	private JFXHamburger hamburger;
	
	@FXML
	private Label menuLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setFirstYouTubeVideo();
		setDrawer();

	}

	@FXML
	private void setFirstYouTubeVideo() {
		youTube.getEngine().load("https://www.youtube.com/embed/QM5wVpOj9iI");

	}
	@FXML
	private void setSecondYouTubeVideo() {
		youTube.getEngine().load("https://www.youtube.com/embed/WWorX7kqC9g");
	}
	@FXML
	private void setThirdYouTubeVideo() {
		youTube.getEngine().load("https://www.youtube.com/embed/o8XbBa2Fhrg");
	}

	private void setDrawer() {
		try {
			/* Connect the drawer with the menu layout*/
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/MainScreenMenu.fxml"));
			VBox menu = loader.load();
			drawMenu.setSidePane(menu);
			
			// MainScreenMenuController controller = loader.getController();
		} catch (IOException ex) {
			System.out.println("Could not load menu bar in mainScreen");
		}
		
		/* Set how the drawer will function */
		HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
		task.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
			drawMenu.toggle();
			System.out.println("clicked on hamburger"); // ToRemove
		});
		drawMenu.setOnDrawerOpening((event) -> {
			AnchorPane.setRightAnchor(hamburger, 170.0);
			AnchorPane.setRightAnchor(menuLabel, 167.0);
			drawMenu.toFront();
			task.setRate(task.getRate() * -1);
			task.play();
		});
		drawMenu.setOnDrawerClosed((event) -> {
			AnchorPane.setRightAnchor(hamburger, 20.0);
			AnchorPane.setRightAnchor(menuLabel, 17.0);
			drawMenu.toBack();
			task.setRate(task.getRate() * -1);
			task.play();
		});
	}

}
