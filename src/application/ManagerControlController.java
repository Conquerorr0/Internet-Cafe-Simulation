package application;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ManagerControlController {

	private Parent root;

	@FXML
	private ImageView table1;
	@FXML
	private ImageView table2;
	@FXML
	private ImageView table3;
	@FXML
	private ImageView table4;
	@FXML
	private ImageView table5;
	@FXML
	private ImageView table6;
	@FXML
	private ImageView table7;
	@FXML
	private ImageView table8;
	@FXML
	private ImageView table9;
	@FXML
	private ImageView table10;
	@FXML
	private ImageView table11;
	@FXML
	private ImageView table12;
	@FXML
	private ImageView table13;
	@FXML
	private ImageView table14;
	@FXML
	private ImageView table15;
	@FXML
	private ImageView table16;
	@FXML
	private ImageView table17;
	@FXML
	private ImageView table18;
	@FXML
	private ImageView table19;
	@FXML
	private ImageView table20;
	@FXML
	private ImageView table21;
	@FXML
	private ImageView table22;
	@FXML
	private ImageView table23;
	@FXML
	private ImageView table24;
	@FXML
	private ImageView table25;
	@FXML
	private ImageView table26;
	@FXML
	private ImageView table27;
	@FXML
	private ImageView table28;

	private Image newImage;

	public static ControlPanelController[] controllers = new ControlPanelController[28];

	public ManagerControlController() {
		for (int i = 0; i < controllers.length; i++) {
			controllers[i] = new ControlPanelController();
		}
	}

	@FXML
	private ImageView wifi_Image;

	@FXML
	private Rectangle rectChoosenTable;
	@FXML
	private Label choosenTable;

	@FXML
	private Label lblHourTime;
	@FXML
	private Label lblHourTime1;
	@FXML
	private Label lblStartTime;
	@FXML
	private Label lblStartTime1;
	@FXML
	private Label lblRemainingTime;
	@FXML
	private Label lblRemainingTime1;
	@FXML
	private Label lblOrders;
	@FXML
	private Label lblOrders1;
	@FXML
	private Label lblGeneralUsage;
	@FXML
	private Label lblGeneralUsage1;
	@FXML
	private Label lblTotalPrice;
	@FXML
	private Label lblTotalPrice1;

	private DateTimeFormatter formatter;
	private LocalTime now;

	private static final int PRICE = 20;
	public static ImageView clickedTable;
	private String tableId;
	public static String time;
	public static int index;
	public static String openingTime = "";
	public static int orders = 0;
	private DatabaseHelper db = new DatabaseHelper();

	@FXML
	private void tableClick(MouseEvent event) {

		if ((Node) event.getSource() instanceof ImageView) {
			Node clickedNode = (Node) event.getSource();

			if (clickedNode instanceof ImageView) {
				clickedTable = (ImageView) clickedNode;
				tableId = clickedTable.getId();
				index = Integer.parseInt(tableId.replaceAll("table", "")) - 1;
				ControlPanelController.event = event;
				choosenTable.setText(tableId.replaceAll("table", "Masa-"));
				lblStartTime1.setText(controllers[index].time);
				if (lblStartTime1.getText().equals("")) {
					lblRemainingTime1.setText("");
				} else {
					lblRemainingTime1.setText(
							(int) Double.parseDouble(controllers[index].hourTime) - Integer.parseInt(duration()) + "");
				}

				if (!lblStartTime1.getText().equals("")) {
					lblOrders1.setText(controllers[index].orders);
					lblGeneralUsage1.setText(((Double.parseDouble(controllers[index].hourTime) / 60) * PRICE) + "");
					lblTotalPrice1.setText(Double.parseDouble(lblOrders1.getText())
							+ (Double.parseDouble(controllers[index].hourTime) / 60 * PRICE) + "");
				} else {
					lblOrders1.setText("");
					lblGeneralUsage1.setText("");
					lblTotalPrice1.setText("");
				}

				if (!lblRemainingTime1.getText().equals("") && Integer.parseInt(lblRemainingTime1.getText()) == 0) {
					ControlPanelController controller = ManagerControlController.controllers[ManagerControlController.index];
					controller.time = "";
					controller.duration = "";
					controller.hourTime = "0";
					controller.orders = "0";
					changeImageGreen(event);
					cleanScreen();
					controller.setDrinks();
					if (db.readComputerStatus("computers", index + 1).equals("false"))
						db.updateComputerStatus("computers", index + 1, "true");
				} else if (!lblRemainingTime1.getText().equals("")
						&& Integer.parseInt(lblRemainingTime1.getText()) <= 30) {
					changeImageYellow(event);
				} else if (!lblRemainingTime1.getText().equals("")
						&& Integer.parseInt(lblRemainingTime1.getText()) >= 30) {
					changeImageRed(event);
				}

				clickedTable.setOnMouseClicked(mouseEvent -> {
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {

						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlPanel.fxml"));
							root = loader.load();
							Stage newStage = new Stage();
							newStage.setScene(new Scene(root));
							newStage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			} else {
				cleanScreen();
			}
		}
	}

	private void cleanScreen() {
		lblStartTime1.setText("");
		lblRemainingTime1.setText("");
		lblOrders1.setText("");
		lblGeneralUsage1.setText("");
		lblTotalPrice1.setText("");
	}

	private String currentTime() {

		now = LocalTime.now();
		// Saati istediğiniz formatta biçimlendir
		formatter = DateTimeFormatter.ofPattern("HH:mm");
		return now.format(formatter);
	}

	private String duration() {
		if (!lblStartTime1.getText().equals("")) {
			Duration duration = Duration.between(LocalTime.parse(lblStartTime1.getText()),
					LocalTime.parse(currentTime()));
			long minutes = duration.toMinutesPart();
			return minutes + "";
		}
		return "0";
	}

	private void changeImageYellow(MouseEvent event) {
		Node clickedNode = (Node) event.getSource();
		if (clickedNode instanceof ImageView) {
			ImageView clickedTable = (ImageView) clickedNode;
			newImage = new Image(getClass().getResource("/icons/orange-computer.png").toExternalForm());
			clickedTable.setImage(newImage);
		}
	}

	private void changeImageRed(MouseEvent event) {
		Node clickedNode = (Node) event.getSource();
		if (clickedNode instanceof ImageView) {
			ImageView clickedTable = (ImageView) clickedNode;
			newImage = new Image(getClass().getResource("/icons/red-computer.png").toExternalForm());
			clickedTable.setImage(newImage);
		}
	}

	private void changeImageGreen(MouseEvent event) {
		Node clickedNode = (Node) event.getSource();
		if (clickedNode instanceof ImageView) {
			ImageView clickedTable = (ImageView) clickedNode;
			newImage = new Image(getClass().getResource("/icons/green-computer.png").toExternalForm());
			clickedTable.setImage(newImage);
		}
	}
}