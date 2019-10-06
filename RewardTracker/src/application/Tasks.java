package application;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tasks {
	
	private static Map<String, Float> timedTasks = new HashMap<>();
	private static Map<String, Float> oneTimeTasks = new HashMap<>();
	private static Map<String, Float> repeatableTasks = new HashMap<>();
	
	private static String timedTasksFile = "/application/Data/TimedTasks.xlsx";
	private static String oneTimeTasksFile = "/application/Data/OneTimeTasks.xlsx";
	private static String repeatableTasksFile = "/application/Data/RepeatableTasks.xlsx";
	
	/** 
	 * Reads information from XCEL Files to initialize the Maps. Called in Main.java.
	 */
	protected static void initializeTaskLists() {
		// TODO: Implement
	}
	
	/**
	 * Called before closing the program to update the Excel Files.
	 */
	protected static void writeToExcelFiles() {	
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
	}
	
	private static void addTask(String taskName, float value, Task taskType) {
		switch (taskType) {
		case TIMED:
			timedTasks.put(taskName, value);
			break;
		case ONE_TIME:
			oneTimeTasks.put(taskName, value);
			break;
		case REPEATABLE:
			repeatableTasks.put(taskName, value);
			break;
		}
	}
	
	private static void removeTask(String taskName, Task taskType) {
		switch (taskType) {
		case TIMED:
			timedTasks.remove(taskName);
			break;
		case ONE_TIME:
			oneTimeTasks.remove(taskName);
			break;
		case REPEATABLE:
			repeatableTasks.remove(taskName);
			break;
		}
	}
	
	public static ObservableList<String> getTaskList(Task taskType) {
		Set<String> keySet = Collections.EMPTY_SET;
		switch (taskType) {
		case TIMED:
			keySet = timedTasks.keySet();
		case ONE_TIME:
			keySet = oneTimeTasks.keySet();
		case REPEATABLE:
			keySet = repeatableTasks.keySet();
		}
		return FXCollections.observableArrayList(keySet);
	}

}
