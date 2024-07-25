package application;

public class Users {
	
	private String userName;
	private String password;
	
	public Users(int id, String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

}
