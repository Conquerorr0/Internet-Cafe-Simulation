package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class changePasswordController {
	@FXML
	Label lblMessage;
	@FXML
	TextField txtUsername;
	@FXML
	TextField txtNewPassword;
	@FXML
	Button btnUpdate;

	private DatabaseHelper db = null;
	private User user = null;
	private String oldPassword;
	private String newPassword;

	@FXML
	private void changePassword() {
		db = new DatabaseHelper();
		oldPassword = txtUsername.getText();
		newPassword = txtNewPassword.getText();
		user = db.readDataFromUser("users", oldPassword);
		if (user != null) {
			if (db.readDataFromUser("users", newPassword) == null) {
				db.updateUserPassword("users", oldPassword, newPassword);
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
