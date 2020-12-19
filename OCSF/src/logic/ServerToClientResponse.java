package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerToClientResponse<T> implements Serializable {

	// Shlomi
	public enum Response {
		IS_CONNECTED_RESPONSE, TRAVELER_LOGIN_ID_RESPONSE, SUBSCRIBER_LOGIN_ID_RESPONSE, GET_SUBSCRIBER_RESPONSE,
		GET_ALL_PARKS_RESPONSE, GET_MAX_DISCOUNT_RESPONSE, GET_PARK_BY_NAME_RESPONSE, GET_ORDERS_BETWEEN_DATES_RESPONSE,
		ADD_ORDER_RESPONSE, IS_TRAVELER_SUBSCRIBER_RESPONSE, ADD_TRAVELER_RESPONSE, GET_RECENT_ORDER_RESPONSE,
		MEMBER_LOGIN_RESPONSE, GET_ALL_ORDER_FOR_ID_RESPONSE, GET_ALL_ORDERS_RESPONSE, GET_MESSAGES_BY_ID_RESPONSE,
		CHANGE_ORDER_STATUS_BY_ID_RESPONSE, GET_PARK_BY_ID_RESPONSE, GET_ORDERS_THAT_MATCH_AFTER_ORDER_CANCEL_RESPONSE,
		SEND_MSG_TO_TRAVELER_RESPONSE, MANAGER_REQUEST_RESPONSE, VIEW_MANAGER_REQUEST_RESPONSE, SEND_EMAIL_RESPONSE,
		GET_EMPLOYEE_RESPONSE, GET_EMPLOYEE_PASSWORD_RESPONSE, SEND_EMAIL_WITH_EMAIL_RESPONSE, ADD_VISIT_RESPONSE,
		UPDATE_CURRENT_VISITORS_ID_RESPONSE, ADD_CASUAL_ORDER_RESPONSE, GET_ALL_ORDERS_FOR_PARK_RESPONSE,
		GET_ALL_ORDERS_FOR_PARK_WITH_TRAVLER_RESPONSE

	}

	private Response responseType;
	private boolean result;
	private int rowsAffected;
	private ArrayList<T> resultSet = new ArrayList<T>();
	private int arrayListSize;

	public ServerToClientResponse(Response type) {
		this.responseType = type;
	}

	public Response getResponseType() {
		return responseType;
	}

	public void setResponseType(Response responseType) {
		this.responseType = responseType;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getRowsAffected() {
		return rowsAffected;
	}

	public void setRowsAffected(int rowsAffected) {
		this.rowsAffected = rowsAffected;
	}

	public ArrayList<T> getResultSet() {
		return resultSet;
	}

	public void setResultSet(ArrayList<T> resultSet) {
		this.resultSet = resultSet;
	}

	public int getArrayListSize() {
		return arrayListSize;
	}

	public void setArrayListSize(int arrayListSize) {
		this.arrayListSize = arrayListSize;
	}

}
