package logic;

public class Employees {
	private int employeeId;
	private String role;
	private int parkId;
	private String firstName;
	private String lastName;
	private String email;

	public Employees(int employeeId, String role, int parkId, String firstName, String lastName, String email) {
		this.employeeId = employeeId;
		this.role = role;
		this.parkId = parkId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
