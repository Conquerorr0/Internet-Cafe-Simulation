package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class UserRegisterController {
	
	@FXML private Label lblMessage;
	
	@FXML private TextField txtUserName;
	@FXML private TextField txtUserPhoneNumber;
	
	@FXML private Button btnRegister;
	
	@FXML
    private PasswordField passUserPassword = new PasswordField();
    @FXML
    private CheckBox cbShowPassword;
    @FXML 
    private Label shownPassword;
	
	private String message;
	DatabaseHelper db = null;
	@FXML private void btnRegisterActionPerformed() {
		db = new DatabaseHelper();
		try {
			boolean isAdded = db.insertUser("users", txtUserName.getText(), passUserPassword.getText(), txtUserPhoneNumber.getText());
			if(isAdded) {
				Stage stage = (Stage) btnRegister.getScene().getWindow();
				stage.close();
			}
			else {
				message = "Kullanıcı mevcut!";
				lblMessage.setText(message);
			}
		} catch (Exception e) {
			message = "Kayıt başarısız!";
			lblMessage.setText(message);
			System.out.println(e.getMessage());
		}
		
	}
	
	@FXML
    private void showPassword() {
    	if (cbShowPassword.isSelected()) {
            // CheckBox seçili ise şifreyi görünür yap
			shownPassword.setText(passUserPassword.getText());
			cbShowPassword.setText("Gizle");
        } else{
            // CheckBox seçili değilse şifreyi gizle
        	shownPassword.setText("");
        	cbShowPassword.setText("Göster");
        }
    }
}
