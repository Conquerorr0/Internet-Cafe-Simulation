package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
	private static final String URL = "jdbc:mysql://localhost:3306/internetcafe"; // internetcafe eğer veritabanınızın ismi farklı ise bunun yerine girin
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";	//Şifrenizi girin

	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	public DatabaseHelper() {
		connect();
	}

	public boolean connect() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database connection established.");
			return true;
		} catch (SQLException e) {
			System.err.println("Error connecting to the database: " + e.getMessage());
			return false;
		}
	}

	public void closeConnection() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
				System.out.println("Database connection closed.");
			}
		} catch (SQLException e) {
			System.err.println("Error closing the database connection: " + e.getMessage());
		}
	}

	public Admin readDataFromAdmin(String tableName) {
		String query = "SELECT * FROM " + tableName + ";";
		Admin admin = null;

		try {
			if (connection == null || connection.isClosed()) {
				System.err.println("Database connection is not established.");
				return null;
			}

			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("idadmin");
				String userName = resultSet.getString("userName");
				String password = resultSet.getString("password");
				String phoneNumber = resultSet.getString("phoneNumber");
				admin = new Admin(id, userName, password, phoneNumber);
			}
		} catch (SQLException e) {
			System.err.println("Error executing query: " + e.getMessage());
		} finally {
			closeConnection();
		}

		return admin;
	}

	public User readDataFromUser(String tableName, String userName1) {
		String query = "SELECT * FROM " + tableName + ";";
		User user = null;

		try {
			if (connection == null || connection.isClosed()) {
				System.err.println("Database connection is not established. w");
				return null;
			}

			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("iduser");
				String userName = resultSet.getString("userName");
				String password = resultSet.getString("password");
				String phoneNumber = resultSet.getString("phoneNumber");
				if (userName.equals(userName1)) {
					user = new User(id, userName, password, phoneNumber);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error executing query: " + e.getMessage());
		}

		return user;
	}

	public void updateUsername(String tableName, String userName, String newUsername) {
		String sql = "UPDATE " + tableName + " SET userName = ? WHERE userName = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, newUsername);
			statement.setString(2, userName);

			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Veri güncellenirken hata oluştu: s" + e.getMessage());
		}
	}
	
	public void updateUserPassword(String tableName, String userName, String newPassword) {
		String sql = "UPDATE " + tableName + " SET password = ? WHERE userName = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, newPassword);
			statement.setString(2, userName);

			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Veri güncellenirken hata oluştu: s" + e.getMessage());
		}
	}

	public void updateUserPhoneNumber(String tableName, String userName, String newPhoneNumber) {
		String sql = "UPDATE " + tableName + " SET phoneNumber = ? WHERE userName = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, newPhoneNumber);
			statement.setString(2, userName);

			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Veri güncellenirken hata oluştu: s" + e.getMessage());
		}
	}
	
	public void updateComputerStatus(String tableName, int computerID, String newStatus) {
		String sql = "UPDATE " + tableName + " SET status = ? WHERE idComputer = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, newStatus);
			statement.setInt(2, computerID);

			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Veri güncellenirken hata oluştu: " + e.getMessage());
		}
	}

	public String readComputerStatus(String tableName, int computerID) {
		String sql = "SELECT status FROM " + tableName + " WHERE idComputer = ?";
		String status = null;

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, computerID);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					status = resultSet.getString("status");
				} else {
					System.out.println("Belirtilen masa bulunamadı.");
				}
			}
		} catch (SQLException e) {
			System.err.println("Veri okunurken hata oluştu: " + e.getMessage());
		}

		return status;
	}

	public boolean insertUser(String tableName, String name, String password, String phoneNumber) {
		// Önce veritabanında aynı kullanıcı adına sahip kaydın olup olmadığını kontrol
		// edelim
		if (userExists(tableName, name)) {
			System.out.println("Bu kullanıcı adı zaten mevcut. Yeni kullanıcı eklenemedi.");
			return false;
		}

		// Kullanıcı yoksa yeni kullanıcıyı ekleyelim
		String sql = "INSERT INTO " + tableName + " (userName, password, phoneNumber) VALUES (?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, name);
			statement.setString(2, password);
			statement.setString(3, phoneNumber);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				return true;
			}
		} catch (SQLException e) {
			System.err.println("Veri eklenirken hata oluştu: " + e.getMessage());
		}
		return false;
	}

	// Veritabanında aynı isimde kullanıcı var mı yok mu kontrol eden metot
	private boolean userExists(String tableName, String userName) {
		String query = "SELECT COUNT(*) FROM " + tableName + " WHERE userName = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				return count > 0; // Eğer count 0'dan büyükse kullanıcı mevcut demektir
			}
		} catch (SQLException e) {
			System.err.println("Kullanıcı var mı kontrolü yapılırken hata oluştu: " + e.getMessage());
		}

		return false; // Hata durumunda veya kullanıcı bulunamazsa false döndür
	}

}