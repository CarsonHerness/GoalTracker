package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnterPointsController implements Initializable {

	@FXML private ComboBox<TaskType> submitPointsTaskTypeList;
	@FXML private ComboBox<String> submitPointsTaskNameList;
	@FXML private TextField submitPointsMinutes;
	@FXML private Label submitPointsErrorMessage;
	
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
		
	}

}
