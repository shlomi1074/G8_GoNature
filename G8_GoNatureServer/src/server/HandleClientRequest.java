package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import Util.sendToClient;
import controllers.EmailControl;
import controllers.sqlHandlers.EmployeeQueries;
import controllers.sqlHandlers.OrderQueries;
import controllers.sqlHandlers.ParkQueries;
import controllers.sqlHandlers.ReportsQueries;
import controllers.sqlHandlers.RequestsQueries;
import controllers.sqlHandlers.TravelersQueries;
import controllers.sqlHandlers.MysqlConnection;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import logic.Discount;
import logic.Employees;
import logic.Messages;
import logic.Order;
import logic.Park;
import logic.ServerToClientResponse;
import logic.ServerToClientResponse.Response;
import logic.Subscriber;
import logic.Traveler;
import logic.report;
import ocsf.server.ConnectionToClient;

public class HandleClientRequest implements Runnable {

	private ConnectionToClient client = null;
	private Object msg = null;

	private Connection mysqlconnection;
	private OrderQueries orderQueries;
	private ParkQueries parkQueries;
	private ReportsQueries reportsQueries;
	private TravelersQueries travelerQueriesl;
	private RequestsQueries requestsQueries;
	private EmployeeQueries employeeQueries;

	public HandleClientRequest(ConnectionToClient client, Object msg) {
		this.client = client;
		this.msg = msg;

		try {
			mysqlconnection = MysqlConnection.getInstance().getConnection();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			sendToClient.sendToClientMsg(client, "DB Error");
			e.printStackTrace();
		}
		employeeQueries = new EmployeeQueries(mysqlconnection);
		orderQueries = new OrderQueries(mysqlconnection);
		parkQueries = new ParkQueries(mysqlconnection);
		reportsQueries = new ReportsQueries(mysqlconnection);
		travelerQueriesl = new TravelersQueries(mysqlconnection);
		requestsQueries = new RequestsQueries(mysqlconnection);
	}

	/* HERE WE NEED TO HANDLE THE CLIENT MSG */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		ServerToClientResponse response;
		if (msg instanceof ClientToServerRequest)
			try {
				ClientToServerRequest<?> request = (ClientToServerRequest<?>) msg;
				if (request.getRequestType().equals(Request.IS_CONNECTED)) {
					boolean res = travelerQueriesl.checkIfConnected(request.getParameters());
					response = new ServerToClientResponse(Response.IS_CONNECTED_RESPONSE);
					response.setResult(res);
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.TRAVELER_LOGIN_ID)) {
					Traveler traveler = travelerQueriesl.isTravelerExist(request.getParameters());
					response = new ServerToClientResponse(Response.TRAVELER_LOGIN_ID_RESPONSE);
					response.setResultSet(new ArrayList<Traveler>(Arrays.asList(traveler)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.INSERT_TO_LOGGEDIN)) {
					travelerQueriesl.insertToLoggedInTable(request.getParameters());
					client.sendToClient("Finished Insert");
				}
				if (request.getRequestType().equals(Request.SUBSCRIBER_LOGIN_SUBID)) {
					Subscriber sub = travelerQueriesl.getSubscriberBySubId(request.getParameters());
					response = new ServerToClientResponse(Response.SUBSCRIBER_LOGIN_ID_RESPONSE);
					response.setResultSet(new ArrayList<Traveler>(Arrays.asList(sub)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_PARK_BY_ID)) {
					Park park = parkQueries.getParkById(request.getParameters());
					response = new ServerToClientResponse(Response.GET_PARK_BY_ID_RESPONSE);
					response.setResultSet(new ArrayList<Park>(Arrays.asList(park)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_PARK_BY_NAME)) {
					Park park = parkQueries.getParkByName(request.getParameters());
					response = new ServerToClientResponse(Response.GET_PARK_BY_NAME_RESPONSE);
					response.setResultSet(new ArrayList<Park>(Arrays.asList(park)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_SUBSCRIBER)) {
					Subscriber sub = travelerQueriesl.getSubscriberById(request.getParameters());
					response = new ServerToClientResponse(Response.GET_SUBSCRIBER_RESPONSE);
					response.setResultSet(new ArrayList<Subscriber>(Arrays.asList(sub)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_ALL_PARKS)) {
					ArrayList<Park> parks = parkQueries.getAllParks(request.getParameters());
					response = new ServerToClientResponse(Response.GET_ALL_PARKS_RESPONSE);
					response.setResultSet(parks);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_MAX_DISCOUNT)) {
					Discount discount = requestsQueries.getMaxDisount(request.getParameters());
					response = new ServerToClientResponse(Response.GET_MAX_DISCOUNT_RESPONSE);
					response.setResultSet(new ArrayList<Discount>(Arrays.asList(discount)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_ORDERS_BETWEEN_DATES)) {
					ArrayList<Order> orders = orderQueries.getOrderBetweenTimes(request.getParameters());
					response = new ServerToClientResponse(Response.GET_ORDERS_BETWEEN_DATES_RESPONSE);
					response.setResultSet(orders);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.ADD_ORDER)) {
					boolean result = orderQueries.addNewOrder(request.getObj());
					response = new ServerToClientResponse(Response.ADD_ORDER_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.ADD_TRAVELER)) {
					boolean result = travelerQueriesl.addTraveler(request.getObj());
					response = new ServerToClientResponse(Response.ADD_TRAVELER_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_RECENT_ORDER)) {
					Order order = orderQueries.getRecentOrder(request.getParameters());
					response = new ServerToClientResponse(Response.GET_RECENT_ORDER_RESPONSE);
					response.setResultSet(new ArrayList<Order>(Arrays.asList(order)));
					client.sendToClient(response);
				}

				/* Alon 12.12.2020 */
				if (request.getRequestType().equals(Request.MEMBER_LOGIN)) {
					Employees member = travelerQueriesl.isMemberExist(request.getParameters());// <-bug
					response = new ServerToClientResponse(Response.MEMBER_LOGIN_RESPONSE);
					response.setResultSet(new ArrayList<Employees>(Arrays.asList(member)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.LOGOUT)) {
					travelerQueriesl.removeFromLoggedInTable(request.getParameters());
					client.sendToClient("User logedout.");
				}
				/* End of Alon's 12.12.20 edit */

				/* Ofir */
				// Ofir Avraham
				// Imported Order
				if (request.getRequestType().equals(Request.GET_ALL_ORDER_FOR_ID)) {
					ArrayList<Order> orders = orderQueries.getAllOrdersForID(request.getParameters());
					response = new ServerToClientResponse<>(Response.GET_ALL_ORDER_FOR_ID_RESPONSE);
					response.setResultSet(orders);
					client.sendToClient(response);
				}
				// Ofir Avraham Vaknin
				if (request.getRequestType().equals(Request.CHANGE_ORDER_STATUS_BY_ID)) {
					boolean res = orderQueries.setOrderStatusWithIDandStatus(request.getParameters());
					response = new ServerToClientResponse<>(Response.CHANGE_ORDER_STATUS_BY_ID_RESPONSE);
					response.setResult(res);
					client.sendToClient(response);
				}

				// Ofir Avraham Vaknin
				if (request.getRequestType().equals(Request.GET_ORDERS_THAT_MATCH_AFTER_ORDER_CANCEL)) {
					ArrayList<Order> orders = orderQueries.findMatchingOrdersInWaitingList(request.getParameters());
					response = new ServerToClientResponse<>(Response.GET_ORDERS_THAT_MATCH_AFTER_ORDER_CANCEL_RESPONSE);
					response.setResultSet(orders);
					client.sendToClient(response);
				}

				// Ofir Avraham Vaknin
				if (request.getRequestType().equals(Request.SEND_MSG_TO_TRAVELER)) {
					boolean result = travelerQueriesl.sendMessageToTraveler(request.getParameters());
					response = new ServerToClientResponse<>(Response.SEND_MSG_TO_TRAVELER_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}

				// Ofir Avraham Vaknin
				if (request.getRequestType().equals(Request.GET_ALL_ORDERS)) {
					ArrayList<Order> orders = orderQueries.getAllOrders();
					response = new ServerToClientResponse<>(Response.GET_ALL_ORDERS_RESPONSE);
					response.setResultSet(orders);
					client.sendToClient(response);
				}

				/* End Ofir */
				/* Lior */
				if (request.getRequestType().equals(Request.DELETE_TRAVELER)) {
					travelerQueriesl.deleteFromTravelerTable(request.getParameters());
					client.sendToClient("Finished Insert");
					client.sendToClient("");
				}

				if (request.getRequestType().equals(Request.INSERT_TO_SUBSCRIBER)) {
					travelerQueriesl.insertSubscriberToSubscriberTable(request.getParameters());
					client.sendToClient("Finished Insert");
					client.sendToClient("");
				}
				if (request.getRequestType().equals(Request.INSERT_TO_CREDITCARD)) {
					travelerQueriesl.insertCardToCreditCardTable(request.getParameters());
					client.sendToClient("Finished Insert");
					client.sendToClient("");
				}

				if (request.getRequestType().equals(Request.MANAGER_REQUEST)) { // ofir n
					requestsQueries.insertAllNewRequestsFromParkManager(request.getParameters());
					response = new ServerToClientResponse(Response.MANAGER_REQUEST_RESPONSE);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.VIEW_MANAGER_REQUEST)) { // ofir n
					response = new ServerToClientResponse(Response.VIEW_MANAGER_REQUEST_RESPONSE);
					response.setResultSet(requestsQueries.GetRequestsFromDB());
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.SEND_EMAIL)) {
					boolean result = EmailControl.sendEmail((Messages) request.getObj());
					response = new ServerToClientResponse(Response.SEND_EMAIL_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.SEND_EMAIL_WITH_EMAIL)) {
					boolean result = EmailControl.sendEmailToWithEmailInput((Messages) request.getObj(),
							request.getInput());
					response = new ServerToClientResponse(Response.SEND_EMAIL_WITH_EMAIL_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_EMPLOYEE)) {
					Employees employee = employeeQueries.getEmployeeById(request.getParameters());
					response = new ServerToClientResponse(Response.GET_EMPLOYEE_RESPONSE);
					response.setResultSet(new ArrayList<Employees>(Arrays.asList(employee)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_EMPLOYEE_PASSWORD)) {
					Employees employee = employeeQueries.getEmployeeById(request.getParameters());
					String password;
					String email;
					if (employee == null) {
						password = "";
						email = "";
					} else {
						password = employeeQueries.getEmployeePasswordById(employee.getEmployeeId());
						email = employee.getEmail();
					}
					response = new ServerToClientResponse(Response.GET_EMPLOYEE_PASSWORD_RESPONSE);
					response.setResultSet(new ArrayList<String>(Arrays.asList(email, password)));
					client.sendToClient(response);
				}
				/* Lior */
				if (request.getRequestType().equals(Request.GET_MESSAGES_BY_ID)) {
					ArrayList<Messages> messages = travelerQueriesl.getMessages(request.getParameters());
					response = new ServerToClientResponse<>(Response.GET_MESSAGES_BY_ID_RESPONSE);
					response.setResultSet(messages);
					client.sendToClient(response);
				}

				// Ofir Avraham Vaknin v3.
				if (request.getRequestType().equals(Request.ADD_VISIT)) {
					response = new ServerToClientResponse(Response.ADD_VISIT_RESPONSE);
					boolean result = parkQueries.addVisit(request.getParameters());
					response.setResult(result);
					client.sendToClient(response);

				}

				// Ofir Avraham Vaknin v3.
				if (request.getRequestType().equals(Request.UPDATE_CURRENT_VISITORS_ID)) {
					response = new ServerToClientResponse(Response.UPDATE_CURRENT_VISITORS_ID_RESPONSE);
					boolean result = parkQueries.updateNumberOfVisitors(request.getParameters());
					response.setResult(result);
					client.sendToClient(response);
				}

				// Ofir Avraham Vaknin v3.
				// Using shlomi function - AddNewOrder
				if (request.getRequestType().equals(Request.ADD_CASUAL_ORDER)) {
					response = new ServerToClientResponse(Response.ADD_CASUAL_ORDER_RESPONSE);
					boolean result = orderQueries.addNewOrder(request.getObj());
					response.setResult(result);
					client.sendToClient(response);
				}

				// Ofir Avraham Vaknin v3.
				if (request.getRequestType().equals(Request.GET_ALL_ORDERS_FOR_PARK)) {
					response = new ServerToClientResponse(Response.GET_ALL_ORDERS_FOR_PARK_RESPONSE);
					ArrayList<Order> result = orderQueries.getOrdersForPark(request.getParameters());
					response.setResultSet(result);
					client.sendToClient(response);
				}

				// Ofir Avraham Vaknin v3.
				if (request.getRequestType().equals(Request.GET_ALL_ORDERS_FOR_PARK_WITH_TRAVLER)) {
					response = new ServerToClientResponse(Response.GET_ALL_ORDERS_FOR_PARK_WITH_TRAVLER_RESPONSE);
					ArrayList<Order> result = orderQueries.getOrderForTravelerInPark(request.getParameters());
					response.setResultSet(result);
					client.sendToClient(response);
				}
			
				if (request.getRequestType().equals(Request.VIEW_MANAGER_DISCOUNT)) { // ofir n

					response = new ServerToClientResponse(Response.VIEW_MANAGER_DISCOUNT_RESPONSE);

					response.setResultSet(requestsQueries.GetDiscountsFromDB());

					// System.out.println(response.getResultSet()+" **");
					client.sendToClient(response);

				}
				
				if (request.getRequestType().equals(Request.CONFIRM_REQUEST)) { // ofir n

					response = new ServerToClientResponse(Response.CONFIRM_REQUEST_RESPONSE);
					if ((int) request.getParameters().get(1) == 1)
						requestsQueries.changeStatusOfRequest(true, (int) request.getParameters().get(0));

					else {
						requestsQueries.changeStatusOfRequest(false, (int) request.getParameters().get(0));

					}

					client.sendToClient(response);

				}

				
				if (request.getRequestType().equals(Request.CONFIRM_DISCOUNT)) { // ofir n

					response = new ServerToClientResponse(Response.CONFIRM_DISCOUNT_RESPONSE);
					if ((int) request.getParameters().get(1) == 1)
						requestsQueries.changeStatusOfDiscount(true, (int) request.getParameters().get(0));

					else {
						requestsQueries.changeStatusOfDiscount(false, (int) request.getParameters().get(0));

					}

					client.sendToClient(response);

				}

				if (request.getRequestType().equals(Request.MANAGER_REPORT)) { // ofir n
					response = new ServerToClientResponse(Response.MANAGER_REPORT_RESPONSE);

					if (request.getParameters().get(1).equals("Total Visitors"))
						response.setResultSet(reportsQueries.createNumberOfVisitorsReport(
								Integer.parseInt((String) request.getParameters().get(0))));

					if (request.getParameters().get(1).equals("Income"))
						response.setResultSet(reportsQueries
								.createIncomeReport(Integer.parseInt((String) request.getParameters().get(0))));

					client.sendToClient(response);

				}

				if (request.getRequestType().equals(Request.ADD_REPORT_TO_DB)) {

					response = new ServerToClientResponse(Response.ADD_REPORT_TO_DB_RESPONSE);
					reportsQueries.createNewReportInDB(request.getParameters());

					client.sendToClient(response);

				}

				if (request.getRequestType().equals(Request.CHANGE_PARK_PARAMETERS)) { // ofir n
					response = new ServerToClientResponse(Response.CHANGE_PARK_PARAMETERS_RESPONSE);

					parkQueries.changeParkParametersInDB(request.getParameters());
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.CHECK_IF_PARK_FULL_AT_DATE)) {
					response = new ServerToClientResponse(Response.CHECK_IF_PARK_FULL_AT_DATE_RESPONSE);
					response.setResultSet(parkQueries.isParkIsFullAtDate(request.getParameters()));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.DELETE_REPORT)) {
					response = new ServerToClientResponse(Response.DELETE_REPORT_RESPONSE);
					reportsQueries.deleteReport(request.getParameters());
					client.sendToClient("Finished");
				}
				if (request.getRequestType().equals(Request.INSERT_REPORT)) {
					response = new ServerToClientResponse(Response.INSERT_REPORT_RESPONSE);
					response.setResult(reportsQueries.insertReport(request.getParameters()));
					client.sendToClient(response);
				}

				/* Alon */
				if (request.getRequestType().equals(Request.COUNT_ENTER_SOLO_VISITORS)) {

					response = new ServerToClientResponse(Response.COUNT_ENTER_SOLO_VISITORS_RESPONSE);
					response.setResultSet(reportsQueries.CountSolosEnterTime(request.getParameters()));
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.COUNT_ENTER_SUBS_VISITORS)) {

					response = new ServerToClientResponse(Response.COUNT_ENTER_SUBS_VISITORS_RESPONSE);
					response.setResultSet(reportsQueries.CountSubsEnterTime(request.getParameters()));
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.COUNT_ENTER_GROUP_VISITORS)) {

					response = new ServerToClientResponse(Response.COUNT_ENTER_GROUP_VISITORS_RESPONSE);
					response.setResultSet(reportsQueries.CountGroupsEnterTime(request.getParameters()));
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.COUNT_VISIT_SOLO_VISITORS)) {

					response = new ServerToClientResponse(Response.COUNT_VISIT_SOLO_VISITORS_RESPONSE);
					response.setResultSet(reportsQueries.CountSolosVisitTime(request.getParameters()));
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.COUNT_VISIT_SUBS_VISITORS)) {

					response = new ServerToClientResponse(Response.COUNT_VISIT_SUBS_VISITORS_RESPONSE);
					response.setResultSet(reportsQueries.CountSubsVisitTime(request.getParameters()));
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.COUNT_VISIT_GROUP_VISITORS)) {

					response = new ServerToClientResponse(Response.COUNT_VISIT_GROUP_VISITORS_RESPONSE);
					response.setResultSet(reportsQueries.CountGroupsVisitTime(request.getParameters()));
					client.sendToClient(response);

				}
				// ALON END

				if (request.getRequestType().equals(Request.INSERT_TO_FULL_PARK_DATE)) {
					response = new ServerToClientResponse(Response.INSERT_TO_FULL_PARK_DATE_RESPONSE);
					response.setResult(parkQueries.insertToFullParkDate(request.getParameters()));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.CHECK_WAITING_LIST)) {

					int orderId = (Integer) request.getParameters().get(0);

					Order order = orderQueries.getOrderByID(orderId);
					System.out.println("Order in check waiting list - handle client request = " + order.getOrderId());
					if (order != null) {
						NotifyWaitingList notifyWaitingList = new NotifyWaitingList(order);
						new Thread(notifyWaitingList).start();
					}
					client.sendToClient("Finished");
				}

				/* Lior */
				/* get reports */
				if (request.getRequestType().equals(Request.GET_REPORTS)) {
					ArrayList<report> reports = reportsQueries.getReports(request.getParameters());
					response = new ServerToClientResponse<>(Response.GET_REPORTS_RESPONSE);
					response.setResultSet(reports);
					client.sendToClient(response);
				}

				/* Lior */
				/* get cancels for park id */
				if (request.getRequestType().equals(Request.GET_CANCELS)) {
					ArrayList<Integer> cancels = reportsQueries.getParkCancels(request.getParameters());
					response = new ServerToClientResponse<>(Response.GET_CANCELS_RESPONSE);
					response.setResultSet(cancels);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_SIMULATOR_TRAVELERS_IDS)) {
					ArrayList<String> travelersID = parkQueries.getSimulatorTravelersId();
					response = new ServerToClientResponse<>(Response.GET_SIMULATOR_TRAVELERS_IDS_RESPONSE);
					response.setResultSet(travelersID);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_RELEVANT_ORDER_ENTRANCE)) {
					ArrayList<Order> result = orderQueries.getRelevantOrderForParkEntrance(request.getParameters());
					response = new ServerToClientResponse<>(Response.GET_RELEVANT_ORDER_ENTRANCE_RESPONSE);
					response.setResultSet(result);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_RELEVANT_ORDER_EXIT)) {
					ArrayList<Order> result = orderQueries.getRelevantOrderForParkExit(request.getParameters());
					response = new ServerToClientResponse<>(Response.GET_RELEVANT_ORDER_EXIT_RESPONSE);
					response.setResultSet(result);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.UPDATE_EXIT_TIME_SIMULATOR)) {
					parkQueries.updateVisitExitTimeSimulator((Order) request.getObj());
					client.sendToClient("Finished");
				}
				/* Lior */
				/* get pending after date passed for park id */
				if (request.getRequestType().equals(Request.GET_PENDING_AFTER_DATE_PASSED)) {
					ArrayList<Integer> pending = reportsQueries.getParkPendingDatePassed(request.getParameters());
					response = new ServerToClientResponse<>(Response.GET_PENDING_AFTER_DATE_PASSED_RESPONSE);
					response.setResultSet(pending);
					client.sendToClient(response);
				}
				
				/*
				 * Ofir Avraham Vaknin
				 * Orginzed Code
				 * Change number of participants in order
				 */
				if(request.getRequestType().equals(Request.CHANGE_ORDER_NUMBER_OF_VISITORS_BY_ID)) {
					boolean result = orderQueries.UpdateNumberOfVisitorsForOrder(request.getParameters());
					response = new ServerToClientResponse<>(Response.CHANGE_ORDER_NUMBER_OF_VISITORS_BY_ID_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}
				
				/*
				 * Ofir Avraham Vaknin
				 * Orginzed Code
				 * Update the price in a spesific order.
				 */
				if(request.getRequestType().equals(Request.CHANGE_ORDER_PRICE_BY_ID)) {
					boolean result = orderQueries.UpdatePriceForOrder(request.getParameters());
					response = new ServerToClientResponse<>(Response.CHANGE_ORDER_PRICE_BY_ID_RESPONSE);
					response.setResult(result);
					client.sendToClient(response);
				}

				// client.sendToClient("Finished ");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
