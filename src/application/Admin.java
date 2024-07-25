package application;

public class Admin extends Users {
	
	private String phoneNumber;

	public Admin(int id, String userName, String password, String phoneNumber) {
		super(id, userName, password);
		this.phoneNumber = phoneNumber;
		// TODO Auto-generated constructor stub
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

}
