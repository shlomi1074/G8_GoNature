package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import client.ClientUI;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

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

	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}

	private void init() {
		setFirstYouTubeVideo();
		setDrawer();
		getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				ClientUI.chat.getClient().quit();
			}
		});

	}

	private Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
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

	/* Set up the menu in the main screen using drawer */
	private void setDrawer() {
		try {
			/* Connect the drawer with the menu layout */
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/MainScreenMenu.fxml"));
			VBox menu = loader.load();
			drawMenu.setSidePane(menu);

			// MainScreenMenuController controller = loader.getController();
		} catch (IOException e) {
			System.out.println("Could not load menu bar in mainScreen");
			e.printStackTrace();
		}

		/* Set how the drawer will function */
		HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
		task.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
			drawMenu.toggle();
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
