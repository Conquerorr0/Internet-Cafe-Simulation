package application;

public class User extends Users{
	
	private String phoneNumber;

	public User(int id, String userName, String password, String phoneNumber) {
		super(id, userName, password);
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
