package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifyTaskOrRewardController implements Initializable {
	
	private enum Modification {
		ADD, REMOVE, MODIFY;
		
		@Override
		  public String toString() {
		    switch(this) {
		      case ADD: return "Add Task or Reward";
		      case REMOVE: return "Remove Task or Reward";
		      case MODIFY: return "Modify Task or Reward";
		      default: throw new IllegalArgumentException();
		    }
		}
	}
	
	private enum TaskTypeOrReward {
		TIMED_TASK, ONE_TIME_TASK, REPEATABLE_TASK, REWARD;
		
		@Override
		  public String toString() {
		    switch(this) {
		      case TIMED_TASK: return "Timed Task";
		      case ONE_TIME_TASK: return "One Time Task";
		      case REPEATABLE_TASK: return "Repeatable Task";
		      case REWARD: return "Reward";
		      default: throw new IllegalArgumentException();
		    }
		}
	}
	
	@FXML private ComboBox<Modification> modifyChooseAction;
	@FXML private ComboBox<String> modifyChooseTaskOrReward;
	@FXML private ComboBox<TaskTypeOrReward> modifyChooseTaskTypeOrReward;
	@FXML private TextField nameOfTaskOrReward;
	@FXML private TextField pointsOfTaskOrReward;
	@FXML private Button submitModification;
	@FXML private Label modifyErrorMessage;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		modifyChooseAction.setItems(FXCollections.observableArrayList(Modification.values()));
		modifyChooseTaskTypeOrReward.setItems(FXCollections.observableArrayList(TaskTypeOrReward.values()));
		modifyChooseTaskOrReward.setVisible(false);
		nameOfTaskOrReward.setVisible(false);
		pointsOfTaskOrReward.setVisible(false);
	}
	
	public void chooseAction(ActionEvent event) {
		switch (modifyChooseAction.getValue()) {
		case ADD:
			nameOfTaskOrReward.setVisible(true);
			pointsOfTaskOrReward.setVisible(true);
			modifyChooseTaskOrReward.setVisible(false);
			nameOfTaskOrReward.setPromptText("Enter name");
			break;
		case REMOVE:
			nameOfTaskOrReward.setVisible(false);
			pointsOfTaskOrReward.setVisible(false);
			modifyChooseTaskOrReward.setVisible(true);
			break;
		case MODIFY:
			modifyChooseTaskOrReward.setVisible(true);
			nameOfTaskOrReward.setVisible(true);
			pointsOfTaskOrReward.setVisible(true);
			nameOfTaskOrReward.setPromptText("Enter new name");
			break;
		}
	}
	
	public void chooseTaskTypeOrReward(ActionEvent event) {
		switch (modifyChooseTaskTypeOrReward.getValue()) {
		case TIMED_TASK:
			pointsOfTaskOrReward.setPromptText("Enter points per minute");
			modifyChooseTaskOrReward.setItems(Tasks.getTaskList(TaskType.TIMED));
			break;
		case ONE_TIME_TASK:
			pointsOfTaskOrReward.setPromptText("Enter points");
			modifyChooseTaskOrReward.setItems(Tasks.getTaskList(TaskType.ONE_TIME));
			break;
		case REPEATABLE_TASK:
			pointsOfTaskOrReward.setPromptText("Enter points");
			modifyChooseTaskOrReward.setItems(Tasks.getTaskList(TaskType.REPEATABLE));
			break;
		case REWARD:
			pointsOfTaskOrReward.setPromptText("Enter cost");
			modifyChooseTaskOrReward.setItems(Bank.getRewardList());
			break;
		}
	}
	

}
