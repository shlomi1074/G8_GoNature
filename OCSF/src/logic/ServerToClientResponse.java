package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerToClientResponse <T> implements Serializable{
	public enum Response {
		IS_CONNECTED_RESPONSE, TRAVELER_LOGIN_ID_RESPONSE, SUBSCRIBER_LOGIN_ID_RESPONSE

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
