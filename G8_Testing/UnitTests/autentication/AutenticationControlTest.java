package autentication;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.AutenticationControl;
import Controllers.IAutenticationManager;
import Controllers.IDataBaseManager;
import logic.Employees;
import logic.Subscriber;
import logic.WorkerType;

class AutenticationControlTest {

	class stubAutenticationManager implements IAutenticationManager {

		@Override
		public boolean isConnected(String id) {
			return connectedCondition;
		}

		@Override
		public boolean isTravelerExist(String id) {
			return travelerExisitCondition;
		}

		@Override
		public void insertTologgedinTable(String id) {
			System.out.println(id + " is updated in database");
		}

	}

	class stubDataBaseManager implements IDataBaseManager {

		@Override
		public Subscriber getSubBySubId(String subId) {
			return sub;
		}

		@Override
		public boolean isMemberExist(String id, String pass) {
			return memberExistCondition;
		}

	}

	public AutenticationControl ac;
	public IAutenticationManager acm;
	public IDataBaseManager dbc;
	public static Subscriber sub;
	public static Employees employee;
	public static boolean connectedCondition;
	public static boolean travelerExisitCondition;
	public static boolean memberExistCondition;

	@BeforeEach
	void setUp() throws Exception {
		connectedCondition = false;
		travelerExisitCondition = false;
		memberExistCondition = false;

		sub = new Subscriber(0, "308438084", "Shlomi", "Amar", "s@gmail.com", "0544411005", null, "Solo", 1);
		employee = new Employees(0, WorkerType.SERVICE, 0, "Shlomi", "Amar", "Serice@gmail.com");

		acm = new stubAutenticationManager();
		dbc = new stubDataBaseManager();
		ac = new AutenticationControl(acm, dbc);
	}

	@Test
	void failedLoginByIdTravelerAlreadyConnectedTest() {
		AutenticationControlTest.connectedCondition = true;
		int actualValue = AutenticationControl.loginById("205843899");
		int expetedValue = 1;
		assertEquals(expetedValue, actualValue);
	}

	@Test
	void failedLoginByIdTravelerDoesNotExistTest() {
		AutenticationControlTest.connectedCondition = false;
		AutenticationControlTest.travelerExisitCondition = false;
		int actualValue = AutenticationControl.loginById("308438084");
		int expetedValue = 2;
		assertEquals(expetedValue, actualValue);
	}

	@Test
	void successLoginByIdTravelerExistTest() {
		AutenticationControlTest.connectedCondition = false;
		AutenticationControlTest.travelerExisitCondition = true;
		int actualValue = AutenticationControl.loginById("205843899");
		int expetedValue = 0;
		assertEquals(expetedValue, actualValue);
	}

	@Test
	void failedLoginBySubIdTravelerDoesNotExistTest() {
		AutenticationControlTest.sub = null;
		int actualValue = AutenticationControl.loginBySubId("8383838333323");
		int expetedValue = 2;
		assertEquals(expetedValue, actualValue);
	}

	@Test
	void failedLoginBySubIdTravelerExistButConnectedTest() {
		AutenticationControlTest.connectedCondition = true;
		AutenticationControlTest.sub = new Subscriber(0, "308438084", "Shlomi", "Amar", "s@gmail.com", "0544411005",
				null, "Solo", 1);
		int actualValue = AutenticationControl.loginBySubId("308438084");
		int expetedValue = 1;
		assertEquals(expetedValue, actualValue);
	}

	@Test
	void successLoginBySubIdTravelerExistTest() {
		AutenticationControlTest.connectedCondition = false;
		AutenticationControlTest.sub = new Subscriber(0, "308438084", "Shlomi", "Amar", "s@gmail.com", "0544411005",
				null, "Solo", 1);
		int actualValue = AutenticationControl.loginBySubId("308438084");
		int expetedValue = 0;
		assertEquals(expetedValue, actualValue);
	}

	@Test
	void failedWorkerLoginWorkerConnectedTest() {
		AutenticationControlTest.connectedCondition = true;
		AutenticationControlTest.memberExistCondition = true;
		int actualValue = AutenticationControl.memberLoginHandler("308438084", "123");
		int expetedValue = 1;
		assertEquals(expetedValue, actualValue);
	}

	@Test
	void failedWorkerLoginWorkerDoesNotExistTest() {
		AutenticationControlTest.connectedCondition = false;
		AutenticationControlTest.memberExistCondition = false;
		int actualValue = AutenticationControl.memberLoginHandler("308438084", "123");
		int expetedValue = 2;
		assertEquals(expetedValue, actualValue);
	}

	@Test
	void successWorkerLoginWorkerExistTest() {
		AutenticationControlTest.connectedCondition = false;
		AutenticationControlTest.memberExistCondition = true;

		int actualValue = AutenticationControl.memberLoginHandler("308438084", "123");
		int expetedValue = 0;
		assertEquals(expetedValue, actualValue);
	}

}
