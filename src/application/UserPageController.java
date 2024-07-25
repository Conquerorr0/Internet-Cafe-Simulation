package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UserPageController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
    private Button btnBackMainWindow;
	@FXML
    private Button btnChangeUsername;
	@FXML
    private Button btnChangePassword;
	@FXML
    private Button btnChangePhoneNumber;
	
	
	
	@FXML
    private void backMainWindowActionPerformed(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("UserLogin.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
	
	@FXML
    private void changeUsername(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("changeUsername.fxml"));
			root = loader.load();
			Stage newStage = new Stage();
			newStage.setScene(new Scene(root));
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@FXML
    private void changePassword(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("changePassword.fxml"));
			root = loader.load();
			Stage newStage = new Stage();
			newStage.setScene(new Scene(root));
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@FXML
    private void changePhoneNumber(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("changePhoneNumber.fxml"));
			root = loader.load();
			Stage newStage = new Stage();
			newStage.setScene(new Scene(root));
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
