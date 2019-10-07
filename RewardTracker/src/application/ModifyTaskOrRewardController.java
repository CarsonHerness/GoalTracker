package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModifyTaskOrRewardController implements Initializable {

	private enum Modification {
		ADD, REMOVE, MODIFY;

		@Override
		public String toString() {
			switch (this) {
			case ADD:
				return "Add Task or Reward";
			case REMOVE:
				return "Remove Task or Reward";
			case MODIFY:
				return "Modify Task or Reward";
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	private enum TaskTypeOrReward {
		TIMED_TASK, ONE_TIME_TASK, REPEATABLE_TASK, REWARD;

		@Override
		public String toString() {
			switch (this) {
			case TIMED_TASK:
				return "Timed Task";
			case ONE_TIME_TASK:
				return "One Time Task";
			case REPEATABLE_TASK:
				return "Repeatable Task";
			case REWARD:
				return "Reward";
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	private Map<TaskType, TaskTypeOrReward> taskTypeToTaskTypeOrReward = new HashMap<>();
	private Map<TaskTypeOrReward, TaskType> taskTypeOrRewardToTaskType = new HashMap<>();

	@FXML
	private ComboBox<Modification> modifyChooseAction;
	@FXML
	private ComboBox<String> modifyChooseTaskOrReward;
	@FXML
	private ComboBox<TaskTypeOrReward> modifyChooseTaskTypeOrReward;
	@FXML
	private TextField nameOfTaskOrReward;
	@FXML
	private TextField pointsOfTaskOrReward;
	@FXML
	private Label modifyErrorMessage;
	@FXML
	private Button modifyBackToMenuButton;
	@FXML
	private Label oldNameLabel;
	@FXML
	private Label oldPointsLabel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		modifyChooseAction.setItems(FXCollections.observableArrayList(Modification.values()));
		modifyChooseTaskTypeOrReward.setItems(FXCollections.observableArrayList(TaskTypeOrReward.values()));
		modifyChooseTaskOrReward.setVisible(false);
		nameOfTaskOrReward.setVisible(false);
		pointsOfTaskOrReward.setVisible(false);

		taskTypeToTaskTypeOrReward.put(TaskType.TIMED, TaskTypeOrReward.TIMED_TASK);
		taskTypeToTaskTypeOrReward.put(TaskType.ONE_TIME, TaskTypeOrReward.ONE_TIME_TASK);
		taskTypeToTaskTypeOrReward.put(TaskType.REPEATABLE, TaskTypeOrReward.REPEATABLE_TASK);

		taskTypeOrRewardToTaskType.put(TaskTypeOrReward.TIMED_TASK, TaskType.TIMED);
		taskTypeOrRewardToTaskType.put(TaskTypeOrReward.ONE_TIME_TASK, TaskType.ONE_TIME);
		taskTypeOrRewardToTaskType.put(TaskTypeOrReward.REPEATABLE_TASK, TaskType.REPEATABLE);
	}

	public void chooseAction(ActionEvent event) {
		modifyErrorMessage.setVisible(false);
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
		modifyErrorMessage.setVisible(false);
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

	public void chooseTaskOrReward(ActionEvent event) {
		// Show previous values for chosen task/reward if Action is Modify
		if (modifyChooseAction.getValue() == Modification.MODIFY && modifyChooseTaskOrReward.getValue() != null) {
			String oldName = modifyChooseTaskOrReward.getValue();
			double oldPoints = 0;
			oldNameLabel.setText("Old name: " + oldName);
			oldNameLabel.setVisible(true);
			if (modifyChooseTaskTypeOrReward.getValue() == TaskTypeOrReward.REWARD) {
				oldPoints = Bank.getCost(oldName);
			} else {
				TaskTypeOrReward type = modifyChooseTaskTypeOrReward.getValue();
				TaskType taskType = taskTypeOrRewardToTaskType.get(type);
				oldPoints = Tasks.getScore(taskType, oldName);
			}
			oldPointsLabel.setText("Old points value: " + Double.toString(oldPoints));
			oldPointsLabel.setVisible(true);
		} else {
			oldNameLabel.setVisible(false);
			oldPointsLabel.setVisible(false);
		}
	}

	public void successAddMessage(String name, double points, TaskTypeOrReward type) {
		StringBuilder message = new StringBuilder();
		message.append("Successfully added ");
		message.append(type.toString());
		message.append(" ");
		message.append(name);
		message.append(" for ");
		message.append(points);
		message.append(" points");
		if (type == TaskTypeOrReward.TIMED_TASK) {
			message.append(" per minute");
		}
		modifyErrorMessage.setText(message.toString());
		modifyErrorMessage.setTextFill(Color.GREEN);
		modifyErrorMessage.setVisible(true);
	}

	public void successRemoveMessage(String name, TaskTypeOrReward type) {
		StringBuilder message = new StringBuilder();
		message.append("Successfully removed ");
		message.append(type.toString());
		message.append(" ");
		message.append(name);
		modifyErrorMessage.setText(message.toString());
		modifyErrorMessage.setTextFill(Color.GREEN);
		modifyErrorMessage.setVisible(true);
	}

	public void successModifyMessage(String oldName, double oldPoints, String newName, double newPoints,
			TaskTypeOrReward type) {
		StringBuilder message = new StringBuilder();
		message.append("Successfully modified ");
		message.append(type.toString());
		message.append(" from ");
		message.append(oldName);
		message.append(", ");
		message.append(oldPoints);
		message.append(" points ");
		if (type == TaskTypeOrReward.TIMED_TASK) {
			message.append("per minute ");
		}
		message.append("to ");
		message.append(newName);
		message.append(", ");
		message.append(newPoints);
		message.append(" points");
		if (type == TaskTypeOrReward.TIMED_TASK) {
			message.append(" per minute");
		}
		modifyErrorMessage.setText(message.toString());
		modifyErrorMessage.setTextFill(Color.GREEN);
		modifyErrorMessage.setVisible(true);
	}

	public void errorMessage(String message) {
		modifyErrorMessage.setText(message);
		modifyErrorMessage.setTextFill(Color.RED);
		modifyErrorMessage.setVisible(true);
	}

	public void checkSubmitAddTask(TaskType type) {
		if (nameOfTaskOrReward.getText() != "") {
			String name = nameOfTaskOrReward.getText();
			String pointsString = pointsOfTaskOrReward.getText();
			if (pointsString != null) {
				try {
					double points = Double.parseDouble(pointsString);
					Tasks.addTask(name, points, type);
					modifyChooseTaskOrReward.setItems(Tasks.getTaskList(type));
					successAddMessage(name, points, taskTypeToTaskTypeOrReward.get(type));
				} catch (Exception e) {
					errorMessage("Please enter a valid number for points");
				}
			} else {
				errorMessage("Please enter a points value");
			}
		} else {
			errorMessage("Please enter a name");
		}
	}

	public void checkSubmitAddReward() {
		if (nameOfTaskOrReward.getText() != null) {
			String name = nameOfTaskOrReward.getText();
			String pointsString = pointsOfTaskOrReward.getText();
			if (pointsString != null) {
				try {
					double points = Double.parseDouble(pointsString);
					Bank.addReward(name, points);
					modifyChooseTaskOrReward.setItems(Bank.getAffordableRewardList());
					successAddMessage(name, points, TaskTypeOrReward.REWARD);
				} catch (Exception e) {
					errorMessage("Please enter a valid number for points");
				}
			} else {
				errorMessage("Please enter a points value");
			}
		} else {
			errorMessage("Please enter a name");
		}
	}

	public void checkSubmitModifyTask(TaskType type) {
		String oldName = modifyChooseTaskOrReward.getValue();
		if (oldName != null) {
			double oldPoints = Tasks.getScore(type, oldName);

			String newNameString = nameOfTaskOrReward.getText();
			String newPointsString = pointsOfTaskOrReward.getText();

			String newName = null;
			double newPoints = -1;

			// no new name given
			if ((newNameString == null || newNameString.trim().isEmpty())) {
				if (newPointsString == null || newPointsString.trim().isEmpty()) {
					// old name, old points
					errorMessage("Please enter some change, for the name, points, or both");
				} else {
					// old name, new points
					newName = oldName;
					try {
						newPoints = Double.parseDouble(newPointsString);
						Tasks.removeTask(oldName, type);
						Tasks.addTask(newName, newPoints, type);
						modifyChooseTaskOrReward.setItems(Tasks.getTaskList(type));
						successModifyMessage(oldName, oldPoints, newName, newPoints,
								taskTypeToTaskTypeOrReward.get(type));
					} catch (Exception e) {
						errorMessage("Please enter a valid number for points");
					}
				}

			} else /* New name was given */ {
				// no new Points were given
				if (newPointsString == null || newPointsString.trim().isEmpty()) {
					// new name, old points
					newName = newNameString;
					newPoints = oldPoints;
					Tasks.removeTask(oldName, type);
					Tasks.addTask(newName, newPoints, type);
					modifyChooseTaskOrReward.setItems(Tasks.getTaskList(type));
					successModifyMessage(oldName, oldPoints, newName, newPoints, taskTypeToTaskTypeOrReward.get(type));
				} else {
					// new name, new points
					newName = newNameString;
					try {
						newPoints = Double.parseDouble(newPointsString);
						Tasks.removeTask(oldName, type);
						Tasks.addTask(newName, newPoints, type);
						modifyChooseTaskOrReward.setItems(Tasks.getTaskList(type));
						successModifyMessage(oldName, oldPoints, newName, newPoints,
								taskTypeToTaskTypeOrReward.get(type));
					} catch (Exception e) {
						errorMessage("Please enter a valid number for points");
					}
				}
			}
		} else {
			errorMessage("Please choose a reward");
		}
	}

	public void checkSubmitModifyReward() {
		String oldName = modifyChooseTaskOrReward.getValue();
		if (oldName != null) {
			double oldPoints = Bank.getCost(oldName);

			String newNameString = nameOfTaskOrReward.getText();
			String newPointsString = pointsOfTaskOrReward.getText();

			String newName = null;
			double newPoints = -1;

			// no new name given
			if ((newNameString == null || newNameString.trim().isEmpty())) {
				if (newPointsString == null || newPointsString.trim().isEmpty()) {
					// old name, old points
					errorMessage("Please enter some change, for the name, points, or both");
				} else {
					// old name, new points
					newName = oldName;
					try {
						newPoints = Double.parseDouble(newPointsString);
						Bank.removeReward(oldName);
						Bank.addReward(newName, newPoints);
						modifyChooseTaskOrReward.setItems(Bank.getRewardList());
						successModifyMessage(oldName, oldPoints, newName, newPoints, TaskTypeOrReward.REWARD);
					} catch (Exception e) {
						errorMessage("Please enter a valid number for points");
					}
				}

			} else /* New name was given */ {
				// no new Points were given
				if (newPointsString == null || newPointsString.trim().isEmpty()) {
					// new name, old points
					newName = newNameString;
					newPoints = oldPoints;
					Bank.removeReward(oldName);
					Bank.addReward(newName, newPoints);
					modifyChooseTaskOrReward.setItems(Bank.getRewardList());
					successModifyMessage(oldName, oldPoints, newName, newPoints, TaskTypeOrReward.REWARD);
				} else {
					// new name, new points
					newName = newNameString;
					try {
						newPoints = Double.parseDouble(newPointsString);
						Bank.removeReward(oldName);
						Bank.addReward(newName, newPoints);
						modifyChooseTaskOrReward.setItems(Bank.getRewardList());
						successModifyMessage(oldName, oldPoints, newName, newPoints, TaskTypeOrReward.REWARD);
					} catch (Exception e) {
						errorMessage("Please enter a valid number for points");
					}
				}
			}
		} else {
			errorMessage("Please choose a reward");
		}
	}

	public void submitModification(ActionEvent event) {
		if (modifyChooseAction.getValue() != null) {
			if (modifyChooseTaskTypeOrReward.getValue() != null) {
				switch (modifyChooseAction.getValue()) {
				case ADD:
					switch (modifyChooseTaskTypeOrReward.getValue()) {
					case TIMED_TASK:
						checkSubmitAddTask(TaskType.TIMED);
						break;
					case ONE_TIME_TASK:
						checkSubmitAddTask(TaskType.ONE_TIME);
						break;
					case REPEATABLE_TASK:
						checkSubmitAddTask(TaskType.REPEATABLE);
						break;
					case REWARD:
						checkSubmitAddReward();
						break;
					}
					break;
				case REMOVE:
					if (modifyChooseTaskOrReward.getValue() != null) {
						String name = modifyChooseTaskOrReward.getValue();
						if (modifyChooseTaskTypeOrReward.getValue() == TaskTypeOrReward.REWARD) {
							Bank.removeReward(name);
							successRemoveMessage(name, TaskTypeOrReward.REWARD);
						} else {
							Tasks.removeTask(name,
									taskTypeOrRewardToTaskType.get(modifyChooseTaskTypeOrReward.getValue()));
							successRemoveMessage(name, modifyChooseTaskTypeOrReward.getValue());
						}
					} else {
						errorMessage("Please choose a task or reward");
					}
					break;
				case MODIFY:
					switch (modifyChooseTaskTypeOrReward.getValue()) {
					case TIMED_TASK:
						checkSubmitModifyTask(TaskType.TIMED);
						break;
					case ONE_TIME_TASK:
						checkSubmitModifyTask(TaskType.ONE_TIME);
						break;
					case REPEATABLE_TASK:
						checkSubmitModifyTask(TaskType.REPEATABLE);
						break;
					case REWARD:
						checkSubmitModifyReward();
						break;
					}
					break;
				}
			} else {
				errorMessage("Please choose a task type or reward");
			}
		} else {
			errorMessage("Please choose an action");
		}
	}

	public void backToMainMenu(ActionEvent event) throws IOException {
		Stage stage = (Stage) modifyBackToMenuButton.getScene().getWindow();
		stage.close();

		Stage newStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		newStage.setScene(scene);
		newStage.show();
	}
}
