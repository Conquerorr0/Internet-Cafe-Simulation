package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ManagerLoginController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private Button btnBackMainWindow;
    @FXML
    private Button btnAdminLogin;
    
    @FXML
    private TextField txtAdminUsername;
    
    @FXML
    private PasswordField passAdminPassword = new PasswordField();
    @FXML
    private CheckBox cbShowPassword;
    @FXML 
    private Label shownPassword;
    
    private String adminUsername;
    private String adminPassword;
    
    private DatabaseHelper dbHelper;
    
    private Admin admin = null;
    
    
    //Ana sayfaya dönme
    @FXML
    private void backMainWindowActionPerformed(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void showPassword() {
    	if (cbShowPassword.isSelected()) {
            // CheckBox seçili ise şifreyi görünür yap
			shownPassword.setText(passAdminPassword.getText());
			cbShowPassword.setText("Gizle");
        } else{
            // CheckBox seçili değilse şifreyi gizle
        	shownPassword.setText("");
        	cbShowPassword.setText("Göster");
        }
    }
    
    //Admin giriş kontrolü
    @FXML
    private void adminLoginActionPerformed(ActionEvent event) throws IOException {
    	dbHelper = new DatabaseHelper();
    	admin = dbHelper.readDataFromAdmin("admin");
    	try {
			if(admin != null) {
				adminUsername = admin.getUserName();
				adminPassword = admin.getPassword();
			}
		} catch (NullPointerException e) {
			System.out.println("Error Message: " + e.getMessage());
		}
    	
    	if(txtAdminUsername.getText().equals(adminUsername) && passAdminPassword.getText().equals(adminPassword)) {
    		root = FXMLLoader.load(getClass().getResource("ManagerControlPage.fxml"));
        	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        	scene = new Scene(root);
        	stage.setScene(scene);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;
            stage.setX(centerX);
            stage.setY(centerY);
        	stage.show();
    	}
    	else {
    		System.out.println("Kullanıcı adı veya parola hatalı!");
    	}
    }
}
