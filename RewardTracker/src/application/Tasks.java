package application;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tasks {

	private static Map<Task, Map<String, Double>> taskTypeMaps = new HashMap<>();

	private static Map<String, Double> timedTasks = new HashMap<>();
	private static Map<String, Double> oneTimeTasks = new HashMap<>();
	private static Map<String, Double> repeatableTasks = new HashMap<>();

	private static String timedTasksFile = "C:/Users/Autumn Herness/Desktop/RewardTrackerExcelFiles/TimedTasks.xlsx";
	private static String oneTimeTasksFile = "C:/Users/Autumn Herness/Desktop/RewardTrackerExcelFiles/OneTimeTasks.xlsx";
	private static String repeatableTasksFile = "C:/Users/Autumn Herness/Desktop/RewardTrackerExcelFiles/RepeatableTasks.xlsx";

	private static Map<Task, String> filenameMap = new HashMap<>();

	private static void fillFixedMaps() {
		taskTypeMaps.put(Task.TIMED, timedTasks);
		taskTypeMaps.put(Task.ONE_TIME, oneTimeTasks);
		taskTypeMaps.put(Task.REPEATABLE, repeatableTasks);

		filenameMap.put(Task.TIMED, timedTasksFile);
		filenameMap.put(Task.ONE_TIME, oneTimeTasksFile);
		filenameMap.put(Task.REPEATABLE, repeatableTasksFile);
	}

	/**
	 * Reads information from XLSX Files to initialize the Maps. Called in
	 * Main.java.
	 */
	static void initializeTaskLists() {
		fillFixedMaps();
		for (Task taskType : Task.values()) {
			try {
				FileInputStream file = new FileInputStream(new File(filenameMap.get(taskType)));
				
				try {
					XSSFWorkbook wb = new XSSFWorkbook(file);
					XSSFSheet sheet = wb.getSheetAt(0);

					Iterator<Row> rowIterator = sheet.iterator();
					while (rowIterator.hasNext()) {
						String taskName = "";
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();

						Cell cell = cellIterator.next();
						if (cell.getCellType() == CellType.STRING) {
							taskName = cell.getStringCellValue();
						} else {
							System.out.println("Task Name must be String.");
						}
						try {
							cell = cellIterator.next();
							try {
								double value = cell.getNumericCellValue();
								taskTypeMaps.get(taskType).put(taskName, value);
							} catch (Exception e) {
								System.out.println("Score values must be numeric. Value was " + cell.getStringCellValue());
							}
						} catch (Exception e) {
							System.out.println("Each Task Name requires an accompanying score");
						}
					}
					wb.close();
				} catch (Exception e) {
					System.out.println("Some issue with Excel");
				}
				
			} catch (Exception e) {
				System.out.println("Did not find file " + filenameMap.get(taskType));
			}
		}
	}

	/**
	 * Called before closing the program to update the Excel Files.
	 */
	protected static void writeToExcelFiles() {
		// TODO: implement
		for (Task taskType : Task.values()) {
			System.out.println(taskType);
		}
	}

	static void addTask(String taskName, double value, Task taskType) {
		taskTypeMaps.get(taskType).put(taskName, value);
	}

	static void removeTask(String taskName, Task taskType) {
		taskTypeMaps.get(taskType).remove(taskName);
	}

	public static ObservableList<String> getTaskList(Task taskType) {
		return FXCollections.observableArrayList(taskTypeMaps.get(taskType).keySet());
	}

}
