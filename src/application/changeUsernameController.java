package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class changeUsernameController {

	@FXML
	Label lblMessage;
	@FXML
	TextField txtOldUsername;
	@FXML
	TextField txtNewUsername;
	@FXML
	Button btnUpdate;

	private DatabaseHelper db = null;
	private User user = null;
	private String oldUsername;
	private String newUsername;

	@FXML
	private void changeUsername() {
		db = new DatabaseHelper();
		oldUsername = txtOldUsername.getText();
		newUsername = txtNewUsername.getText();
		user = db.readDataFromUser("users", oldUsername);
		if (user != null) {
			if (db.readDataFromUser("users", newUsername) == null) {
				db.updateUsername("users", oldUsername, newUsername);
				db.closeConnection();
				lblMessage.setText("Başarılı!");
				Stage stage = (Stage) btnUpdate.getScene().getWindow();
				stage.close();
			} else {
				lblMessage.setText("Bu kullanıcı adı daha önce alınmış!");
			}

		} else {
			lblMessage.setText("Kayıtlı kullanıcı bulunamadı!");
		}
	}
}
