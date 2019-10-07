package application;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;

public class EnterPointsController implements Initializable {

	@FXML private ComboBox<TaskType> submitPointsTaskTypeList;
	@FXML private ComboBox<String> submitPointsTaskNameList;
	@FXML private TextField submitPointsMinutes;
	@FXML private Label submitPointsErrorMessage;
	@FXML private Button enterPointsMenuButton;

	private TaskType taskType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		submitPointsTaskTypeList.setItems(FXCollections.observableArrayList(TaskType.values()));
		submitPointsMinutes.setVisible(false);
	}

	public void chooseTaskType(ActionEvent event) {
		taskType = submitPointsTaskTypeList.getValue();
		submitPointsTaskNameList.setItems(Tasks.getTaskList(taskType));
		if (taskType == TaskType.TIMED) {
			submitPointsMinutes.setVisible(true);
		} else {
			submitPointsMinutes.setVisible(false);
		}
	}

	public void submitPoints(ActionEvent event) {
		if (submitPointsTaskNameList.getValue() != null) {
			submitPointsErrorMessage.setVisible(false);
			switch (submitPointsTaskTypeList.getValue()) {
			// TODO: update Main page and Reward Page: add menu button and close window each time
			case TIMED:
				String minutes = submitPointsMinutes.getText();
				try {
					double minutesNum = Double.parseDouble(minutes);
					double addedPoints = minutesNum
							* Tasks.getScore(TaskType.TIMED, submitPointsTaskNameList.getValue());
					Bank.addPoints(addedPoints);
				} catch (Exception e) {
					submitPointsErrorMessage.setText("Please enter a valid number of minutes");
					submitPointsErrorMessage.setVisible(true);
				}
				break;
			case ONE_TIME:
				Bank.addPoints(Tasks.getScore(TaskType.ONE_TIME, submitPointsTaskNameList.getValue()));
				break;
			case REPEATABLE:
				Bank.addPoints(Tasks.getScore(TaskType.REPEATABLE, submitPointsTaskNameList.getValue()));
				break;
			}

		} else {
			submitPointsErrorMessage.setText("Please select Task");
			submitPointsErrorMessage.setVisible(true);
		}
	}
	
	public void backToMainMenu(ActionEvent event) throws IOException {
		Stage stage = (Stage) enterPointsMenuButton.getScene().getWindow();
		stage.close();
		
		Stage newStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));			
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		newStage.setScene(scene);
		newStage.show();
	}

}
