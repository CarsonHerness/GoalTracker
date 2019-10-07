package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RedeemPointsController implements Initializable{
	@FXML private ComboBox<String> rewardsAbleToRedeem;
	@FXML private Label points;
	@FXML private TableView<Reward> rewardTable;
	@FXML private TableColumn<Reward, String> rewardNameColumn;
	@FXML private TableColumn<Reward, Double> rewardCostColumn;
	

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
}
