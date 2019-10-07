package application;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController implements Initializable {
	@FXML private Label pointsLabel;
	@FXML private Button enterPointsButton;
	@FXML private Button modifyButton;
	@FXML private Button redeemPointsButton;
	
	public void enterPoints(ActionEvent event) throws IOException {
		Stage oldStage = (Stage) enterPointsButton.getScene().getWindow();
		oldStage.close();
		
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/EnterPoints.fxml"));			
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void redeemPoints(ActionEvent event) throws IOException {
		Stage oldStage = (Stage) redeemPointsButton.getScene().getWindow();
		oldStage.close();
		
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/RedeemPoints.fxml"));			
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void modify(ActionEvent event) throws IOException {
		Stage oldStage = (Stage) modifyButton.getScene().getWindow();
		oldStage.close();
		
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/ModifyTaskOrReward.fxml"));			
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		pointsLabel.setText(Bank.getReadablePoints());
	}

}
