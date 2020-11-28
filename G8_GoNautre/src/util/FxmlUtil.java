package util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import layouts.OrderVisitController;
import layouts.ParkParametersController;
import layouts.ProfileController;


public class FxmlUtil {
	private Pane view;
	
	
	/* this function loads fxml into a Pane and returns the pane
	 * it is used to load fxml files to BorderPane */
	public Pane loadPaneWithController(String fxmlUrl, String controllerName) {
			
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlUrl));
		if (controllerName.equals("orderVisit")) {
			OrderVisitController controller = new OrderVisitController();
			loader.setController(controller);
		}
		else if(controllerName.equals("profile")) {
			ProfileController controller = new ProfileController();
			loader.setController(controller);
		}
		else if(controllerName.equals("manageTraveler")) {
			ProfileController controller = new ProfileController();
			loader.setController(controller);
		}	
		else if(controllerName.equals("parkParameters")) {
			ParkParametersController controller = new ParkParametersController();
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
	
	public Pane loadPaneWithOutController(String fxmlUr) {	
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlUr));
		try {
			loader.load();
			view = loader.getRoot(); 
		} catch (IOException e) {
			e.printStackTrace();
		}

		return view;
		
		
	}
	
	
}
