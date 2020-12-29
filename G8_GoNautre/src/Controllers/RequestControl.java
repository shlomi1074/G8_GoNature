package Controllers;

import java.util.ArrayList;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;

/**
 * RequestControl class handles all the request related functionalities
 */
public class RequestControl {
	/**
	 * this function sends array containing Request parameters
	 * 
	 * @param arrayOfRequests
	 */
	public static void addNewRequest(ArrayList<?> arrayOfRequests) {
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.MANAGER_REQUEST, arrayOfRequests);
		ClientUI.chat.accept(request);
	}

	/**
	 * this function sends a request to view Request table
	 */
	public static void viewcurrentRequests() {
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.VIEW_MANAGER_REQUEST, new ArrayList<>());
		ClientUI.chat.accept(request);
	}

	/**
	 * this function sends a request to view Discount table
	 */
	public static void viewcurrentDiscounts() {
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.VIEW_MANAGER_DISCOUNT,
				new ArrayList<>());
		ClientUI.chat.accept(request);
	}

	/**
	 * 
	 * @param requestID
	 * @param bool      'true' to confirm, 'false' to cancel.
	 */
	public static void changeRequestStatus(Integer requestID, boolean bool) { // confirm if true, decline if false.
		ArrayList<Integer> requestidList = new ArrayList<>();
		requestidList.add(requestID);
		if (bool)
			requestidList.add(1);
		else
			requestidList.add(0);
		ClientToServerRequest<?> requestConfirm = new ClientToServerRequest<>(Request.CONFIRM_REQUEST, requestidList);
		ClientUI.chat.accept(requestConfirm);
	}

	/**
	 * 
	 * @param discountId
	 * @param bool       'true' to confirm, 'false' to cancel.
	 */
	public static void changeDiscountStatus(int discountId, boolean bool) {
		ArrayList<Integer> requestidList = new ArrayList<>();
		requestidList.add(discountId);

		if (bool)
			requestidList.add(1);
		else
			requestidList.add(0);

		ClientToServerRequest<?> requestConfirm = new ClientToServerRequest<>(Request.CONFIRM_DISCOUNT, requestidList);

		ClientUI.chat.accept(requestConfirm);

	}

}
