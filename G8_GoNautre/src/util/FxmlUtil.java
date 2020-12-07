package util;

import java.io.IOException;

import gui.AddSubscriberController;
import gui.CreateReportsController;
import gui.ManageTravelerController;
import gui.OrderVisitController;
import gui.ParkParametersController;
import gui.ProfileController;
import gui.TravelerViewOrders;
import gui.UpdateParametersController;
import gui.ViewMessagesController;
import gui.ViewRequestsForChangesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlUtil {
	private Pane view;

	/**
	 * This function loads fxml and it's controller
	 * 
	 * @param fxmlUrl        - fxml to load
	 * @param controllerName - the fxml's controller
	 * @return Pane
	 */
	public Pane loadPaneToBorderPaneWithController(String fxmlUrl, String controllerName) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlUrl));
		if (controllerName.equals("orderVisit")) {
			OrderVisitController controller = new OrderVisitController();
			loader.setController(controller);
		} else if (controllerName.equals("profile")) {
			ProfileController controller = new ProfileController();
			loader.setController(controller);
		} else if (controllerName.equals("manageTraveler")) {
			ManageTravelerController controller = new ManageTravelerController();
			loader.setController(controller);
		} else if (controllerName.equals("parkParameters")) {
			ParkParametersController controller = new ParkParametersController();
			loader.setController(controller);
		} else if (controllerName.equals("travelerOrders")) {
			TravelerViewOrders controller = new TravelerViewOrders();
			loader.setController(controller);
		} else if (controllerName.equals("travelerMessages")) {
			ViewMessagesController controller = new ViewMessagesController();
			loader.setController(controller);
		} else if (controllerName.equals("viewRequests")) {
			ViewRequestsForChangesController controller = new ViewRequestsForChangesController();
			loader.setController(controller);
		} else if (controllerName.equals("addSubSubscriber")) {
			AddSubscriberController controller = new AddSubscriberController();
			loader.setController(controller);
		} else if (controllerName.equals("updateParameters")) {
			UpdateParametersController controller = new UpdateParametersController();
			loader.setController(controller);
		} else if (controllerName.equals("createReport")) {
			CreateReportsController controller = new CreateReportsController();
			loader.setController(controller);
		}
		try {
			loader.load();
			view = loader.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return view;

	}

}
