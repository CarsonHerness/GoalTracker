package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RedeemPointsController implements Initializable{
	@FXML private ComboBox<String> rewardsAbleToRedeem;
	@FXML private Label points;
	@FXML private TableView<Reward> rewardTable;
	@FXML private TableColumn<Reward, String> rewardNameColumn;
	@FXML private TableColumn<Reward, Double> rewardCostColumn;
	@FXML private Button redeemBackToMenuButton;
	

	public void submitRewardChoice(ActionEvent event) {
		
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		points.setText(Double.toString(Bank.getPoints()));
		if (Bank.getAffordableRewardList().size() > 0) {
			rewardsAbleToRedeem.setItems(Bank.getAffordableRewardList());
		}
		if (Bank.getRewardSet().size() > 0) {
			rewardNameColumn.setCellValueFactory(new PropertyValueFactory<Reward, String>("name"));
			rewardCostColumn.setCellValueFactory(new PropertyValueFactory<Reward, Double>("cost"));
			
			rewardTable.setItems(Bank.getRewardSet());
		}
	}
	
	public void backToMainMenu(ActionEvent event) throws IOException {
		Stage stage = (Stage) redeemBackToMenuButton.getScene().getWindow();
		stage.close();
		
		Stage newStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));			
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		newStage.setScene(scene);
		newStage.show();
	}
}
