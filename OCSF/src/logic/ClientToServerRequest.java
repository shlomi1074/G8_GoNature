package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientToServerRequest<T> implements Serializable {

	// Shlomi
	public enum Request {
		IS_CONNECTED, TRAVELER_LOGIN_ID, INSERT_TO_LOGGEDIN, SUBSCRIBER_LOGIN_SUBID, GET_PARK_BY_ID, GET_SUBSCRIBER,
		GET_ALL_PARKS, GET_MAX_DISCOUNT, GET_PARK_BY_NAME, GET_ORDERS_BETWEEN_DATES, ADD_ORDER, IS_TRAVELER_SUBSCRIBER,
		ADD_TRAVELER, GET_RECENT_ORDER

	}

	private Request requestType;
	private ArrayList<T> parameters = new ArrayList<>();
	private T obj;

	public ClientToServerRequest(Request requestType) {
		this.requestType = requestType;
	}

	public ClientToServerRequest(Request requestType, ArrayList<T> parameters) {
		this.requestType = requestType;
		this.parameters = parameters;
	}

	public ArrayList<?> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<T> parameters) {
		this.parameters = parameters;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public Request getRequestType() {
		return requestType;
	}

	public void setRequestType(Request requestType) {
		this.requestType = requestType;
	}

}
