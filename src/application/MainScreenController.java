package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MainScreenController {
    
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private Button btnManager;
    @FXML
    private Button btnUser;
    @FXML
    private Button btnBackMainWindow;
    @FXML
    private Button btnAdminLogin;
    @FXML
    private Button btnUserLogin;
    @FXML
    private Button btnUserRegister;
    
    @FXML
    private TextField txtAdminUsername;
    @FXML
    private TextField txtAdminPassword;
    
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtUserPassword;
    
    
    //Admin giriş sayfasına yönlendirme
    @FXML
    private void managerActionPerformed(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("ManagerLogin.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    //Kullanıcı giriş sayfasına yönlendirme
    @FXML
    private void userActionPerformed(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("UserLogin.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
}
