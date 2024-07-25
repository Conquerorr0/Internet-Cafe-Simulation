package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class changePhoneNumberController {
	@FXML
	Label lblMessage;
	@FXML
	TextField txtUsername;
	@FXML
	TextField txtNewPhoneNumber;
	@FXML
	Button btnUpdate;

	private DatabaseHelper db = null;
	private User user = null;
	private String username;
	private String newPhoneNumber;

	@FXML
	private void changePahoneNumber() {
		db = new DatabaseHelper();
		username = txtUsername.getText();
		newPhoneNumber = txtNewPhoneNumber.getText();
		user = db.readDataFromUser("users", username);
		if (user != null) {
			if (db.readDataFromUser("users", newPhoneNumber) == null) {
				db.updateUserPhoneNumber("users", username, newPhoneNumber);
				db.closeConnection();
				lblMessage.setText("Başarılı!");
				Stage stage = (Stage) btnUpdate.getScene().getWindow();
				stage.close();
			} else {
				lblMessage.setText("Kayıtlı kullanıcı bulunamadı!");
			}

		} else {
			lblMessage.setText("Kayıtlı kullanıcı bulunamadı!");
		}
	}
}
