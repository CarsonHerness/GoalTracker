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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RedeemPointsController implements Initializable{
	@FXML private ComboBox<String> rewardsAbleToRedeem;
	@FXML private Label points;
	@FXML private TableView<Reward> rewardTable;
	@FXML private TableColumn<Reward, String> rewardNameColumn;
	@FXML private TableColumn<Reward, Double> rewardCostColumn;
	@FXML private Button redeemBackToMenuButton;
	@FXML private Label redeemErrorMessage;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		resetRewardData();
	}
	
	public void resetRewardData() {
		DecimalFormat truncatedPoints = new DecimalFormat("#.##");
		truncatedPoints.setRoundingMode(RoundingMode.DOWN);
		String pointsMessage = "Bank: $" + truncatedPoints.format(Bank.getPoints());
		points.setText(pointsMessage);
		
		redeemErrorMessage.setVisible(false);
		if (Bank.getAffordableRewardList().size() > 0) {
			rewardsAbleToRedeem.setItems(Bank.getAffordableRewardList());
		}
		if (Bank.getRewardSet().size() > 0) {
			rewardNameColumn.setCellValueFactory(new PropertyValueFactory<Reward, String>("name"));
			rewardCostColumn.setCellValueFactory(new PropertyValueFactory<Reward, Double>("cost"));
			
			rewardTable.setItems(Bank.getRewardSet());
		}
	}
	
	public void submitRewardChoice(ActionEvent event) {
		String name = rewardsAbleToRedeem.getValue();
		if (name != null) {
			double cost = Bank.getCost(name);
			Bank.spendPoints(cost);
			Bank.removeReward(name);
			resetRewardData();
			String message = "Successfully redeemed " + name + " for $" + Double.toString(cost);
			redeemErrorMessage.setText(message);
			redeemErrorMessage.setTextFill(Color.GREEN);
			redeemErrorMessage.setVisible(true);
		} else {
			redeemErrorMessage.setText("Please choose a reward");
			redeemErrorMessage.setTextFill(Color.RED);
			redeemErrorMessage.setVisible(true);
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
