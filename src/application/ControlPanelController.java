package application;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlPanelController implements Initializable {
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		ControlPanelController controller = ManagerControlController.controllers[ManagerControlController.index];
        lblCola.setText(controller.cola+"");
        lblFanta.setText(controller.fanta+"");
        lblGazoz.setText(controller.gazoz+"");
        lblTurkishCoffee.setText(controller.turkishCoffee+"");
        lblDidi.setText(controller.didi+"");
        lblWater.setText(controller.water+"");
        lblTea.setText(controller.tea+"");
        lblOralet.setText(controller.oralet+"");
        lblFuseTea.setText(controller.fuseTea+"");
    }
 
	@FXML 
	private TextField txtTime; 
	@FXML
	private Label lblMessage;

	// Bilgisayar işlemi butonları
	@FXML
	private Button btnOpenTable;
	@FXML
	private Button btnAddTime;
	@FXML
	private Button btnCloseTable;

	// İçecekler butonları
	@FXML
	private Button btnCola;
	@FXML
	private Button btnFanta;
	@FXML
	private Button btnGazoz;
	@FXML
	private Button btnDidi;
	@FXML
	private Button btnTurkishCoffee;
	@FXML
	private Button btnWater;
	@FXML
	private Button btnTea;
	@FXML
	private Button btnOralet;
	@FXML
	private Button btnFuseTea;
	
	//İçeceklerin sayıları
	@FXML
	private Label lblCola;
	@FXML
	private Label lblFanta;
	@FXML
	private Label lblGazoz;
	@FXML
	private Label lblDidi;
	@FXML
	private Label lblTurkishCoffee;
	@FXML
	private Label lblWater;
	@FXML
	private Label lblTea;
	@FXML
	private Label lblOralet;
	@FXML
	private Label lblFuseTea;
	
	//İçeceklerin adetleri
	@FXML
	private int cola = 0;
	@FXML
	private int fanta = 0;
	@FXML
	private int gazoz = 0;
	@FXML
	private int didi = 0;
	@FXML
	private int turkishCoffee = 0;
	@FXML
	private int water = 0;
	@FXML
	private int tea = 0;
	@FXML
	private int oralet = 0;
	@FXML
	private int fuseTea = 0;

	private String timeText;
	private Image newImage;
	private LocalTime now;
	private DateTimeFormatter formatter;
	public static MouseEvent event;

	private final String COLA = "20";
	private final String FANTA = "20";
	private final String GAZOZ = "20";
	private final String DIDI = "20";
	private final String TURKISH_COFFE = "35";
	private final String WATER = "10";
	private final String TEA = "10";
	private final String ORALET = "15";
	private final String FUSETEA = "20";

	public String hourTime = "0";
	public String time = "";
	public String duration = "";
	public String orders = "0";
	public String computerID;
	DatabaseHelper db = new DatabaseHelper();
	@FXML
	public void btnOpenActionPerformed() {
		if (getEvent() != null) { 
			timeText = txtTime.getText();
			if (!timeText.isBlank()) {
				if(Integer.parseInt(txtTime.getText()) > 30) {
					changeImageRed();
				} else {
					changeImageYellow();
				}
				ControlPanelController controller = ManagerControlController.controllers[ManagerControlController.index];
				controller.time = currentTime();
				controller.duration = duration();
				controller.hourTime = txtTime.getText();
				db.updateComputerStatus("computers", ManagerControlController.index+1, "false");
				Stage stage = (Stage) btnOpenTable.getScene().getWindow();
				stage.close();
			} else {
				lblMessage.setText("Lütfen süre girin!");
			}
		} else {
			System.out.println("event null");
		}
	}

	@FXML
	private void btnCloseActionPerformed() {
		changeImageGreen();
		ControlPanelController controller = ManagerControlController.controllers[ManagerControlController.index];
		controller.time = "";
		controller.duration = "";
		controller.hourTime = "0";
		controller.orders = "0";

		controller.cola = 0;
		controller.fanta = 0;
		controller.gazoz = 0;
		controller.didi = 0;
		controller.turkishCoffee = 0;
		controller.water = 0;
		controller.tea = 0;
		controller.oralet = 0;
		controller.fuseTea = 0;
		db.updateComputerStatus("computers", ManagerControlController.index+1, "true");
		Stage stage = (Stage) btnOpenTable.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnAddTimeActionPerformed() {
		if (getEvent() != null) {
			timeText = txtTime.getText();
			if (!timeText.isBlank()) {
				ControlPanelController controller = ManagerControlController.controllers[ManagerControlController.index];
				controller.hourTime = Double.parseDouble(controller.hourTime) + Double.parseDouble(txtTime.getText())
						+ "";
				Stage stage = (Stage) btnOpenTable.getScene().getWindow();
				stage.close();
			} else {
				lblMessage.setText("Lütfen süre girin!");
			}
			
			
		} else {
			System.out.println("event null");
		}
		
		
	}

	@FXML
	private void btnDrinksActionPerformed(ActionEvent e) {
		ControlPanelController controller = ManagerControlController.controllers[ManagerControlController.index];
		if (!controller.time.equals("")) {
			String order = controller.orders;
			controller.orders = Double.parseDouble(order)
					+ Double.parseDouble(orders(e)) + "";
		}

	}

	private void changeImageRed() {
		Node clickedNode = (Node) getEvent().getSource();
		if (clickedNode instanceof ImageView) {
			ImageView clickedTable = (ImageView) clickedNode;
			newImage = new Image(getClass().getResource("/icons/red-computer.png").toExternalForm());
			clickedTable.setImage(newImage);
		}
	}
	
	private void changeImageYellow() {
		Node clickedNode = (Node) event.getSource();
		if (clickedNode instanceof ImageView) {
			ImageView clickedTable = (ImageView) clickedNode;
			newImage = new Image(getClass().getResource("/icons/orange-computer.png").toExternalForm());
			clickedTable.setImage(newImage);
		}
	}
 
	private void changeImageGreen() {
		Node clickedNode = (Node) getEvent().getSource();
		if (clickedNode instanceof ImageView) {
			ImageView clickedTable = (ImageView) clickedNode;
			newImage = new Image(getClass().getResource("/icons/green-computer.png").toExternalForm());
			clickedTable.setImage(newImage);
		}
	}

	private String currentTime() {

		now = LocalTime.now();
		// Saati istediğiniz formatta biçimlendir
		formatter = DateTimeFormatter.ofPattern("HH:mm");
		return now.format(formatter);
	}

	private String duration() {
		Duration duration = Duration.between(
				LocalTime.parse(ManagerControlController.controllers[ManagerControlController.index].time),
				LocalTime.parse(currentTime()));
		long minutes = duration.toMinutesPart();

		return minutes+"";
	}

	private String orders(ActionEvent e) {
		ControlPanelController controller = ManagerControlController.controllers[ManagerControlController.index];
		Object source = e.getSource();
		Button clickbtn = (Button) source;
		switch (clickbtn.getId()) {
		case "btnCola": {
			controller.cola++;
			lblCola.setText(controller.cola+"");
			return COLA;
		}
		case "btnFanta": {
			controller.fanta++;
			lblFanta.setText(controller.fanta+"");
			return FANTA;
		}
		case "btnGazoz": {
			controller.gazoz++;
			lblGazoz.setText(controller.gazoz+"");
			return GAZOZ;
		}
		case "btnDidi": {
			controller.didi++;
			lblDidi.setText(controller.didi+"");
			return DIDI;
		}
		case "btnTurkishCoffee": {
			controller.turkishCoffee++;
			lblTurkishCoffee.setText(controller.turkishCoffee+"");
			return TURKISH_COFFE;
		}
		case "btnWater": {
			controller.water++;
			lblWater.setText(controller.water+"");
			return WATER;
		}
		case "btnOralet": {
			controller.oralet++;
			lblOralet.setText(controller.oralet+"");
			return ORALET;
		}
		case "btnFuseTea": {
			controller.fuseTea++;
			lblFuseTea.setText(controller.fuseTea+"");
			return FUSETEA;
		}
		case "btnTea": {
			controller.tea++;
			lblTea.setText(controller.tea+++"");
			return TEA;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + clickbtn.getId());
		}
	}

	public MouseEvent getEvent() {
		return event;
	}
	
	public void setDrinks() {
		ControlPanelController controller = ManagerControlController.controllers[ManagerControlController.index];
		controller.cola = 0;
		controller.fanta = 0;
		controller.gazoz = 0;
		controller.didi = 0;
		controller.turkishCoffee = 0;
		controller.water = 0;
		controller.tea = 0;
		controller.oralet = 0;
		controller.fuseTea = 0;
	}

}
