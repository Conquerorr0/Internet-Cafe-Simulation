package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
 
public class UserLoginController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private Button btnBackMainWindow;
    @FXML
    private Button btnUserLogin;
    @FXML
    private Button btnUserRegister;
    
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField passUserPassword = new PasswordField();
    @FXML
    private CheckBox cbShowPassword;
    @FXML
    private Label shownPassword;
    @FXML 
    private Label lblMessage;
    
    
    private String userUsername;
    private String userPassword;
    
    private DatabaseHelper dbHelper;
    private User user = null;
    
    //Ana sayfaya dönme
    @FXML
    private void backMainWindowActionPerformed(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    
    //Kullanıcı giriş kontrolü
    @FXML
    private void userLoginActionPerformed(ActionEvent event) throws IOException {
    	dbHelper = new DatabaseHelper();
    	user = dbHelper.readDataFromUser("users", txtUsername.getText());
    	try {
			if(user != null) {
				userUsername = user.getUserName();
				userPassword = user.getPassword();
				if(txtUsername.getText().equals(userUsername) && passUserPassword.getText().equals(userPassword)) {
					root = FXMLLoader.load(getClass().getResource("UserPage.fxml"));
			    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    	scene = new Scene(root);
			    	stage.setScene(scene);
			    	stage.show();
		    	}
		    	else {
		    		lblMessage.setText("Kullanıcı adı veya parola hatalı!");
		    	}
			} else {
				lblMessage.setText("Kayıtlı kullanıcı bulunamadı!");
			}
		} catch (NullPointerException e) {
			System.out.println("Error Message: " + e.getMessage());
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
    
    @FXML
    private void userRegisterActionPerformed(ActionEvent event) throws IOException {
    	btnUserRegister.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 1) {

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("UserRegister.fxml"));
					root = loader.load();
					Stage newStage = new Stage();
					newStage.setScene(new Scene(root));
					newStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
    }
}
