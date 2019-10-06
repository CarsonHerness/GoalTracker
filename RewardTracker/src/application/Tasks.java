package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	private static String rewardTrackerTasksFile = "C:/Users/Autumn Herness/Desktop/RewardTrackerExcelFiles/RewardTrackerTasks.xlsx";

	private static String timedTasksSheet = "TimedTasks";
	private static String oneTimeTasksSheet = "OneTimeTasks";
	private static String repeatableTasksSheet = "RepeatableTasks";

	private static Map<Task, String> sheetNameMap = new HashMap<>();

	private static void fillFixedMaps() {
		taskTypeMaps.put(Task.TIMED, timedTasks);
		taskTypeMaps.put(Task.ONE_TIME, oneTimeTasks);
		taskTypeMaps.put(Task.REPEATABLE, repeatableTasks);

		sheetNameMap.put(Task.TIMED, timedTasksSheet);
		sheetNameMap.put(Task.ONE_TIME, oneTimeTasksSheet);
		sheetNameMap.put(Task.REPEATABLE, repeatableTasksSheet);
	}

	/**
	 * Reads information from XLSX Files to initialize the Maps. Called in
	 * Main.java.
	 */
	static void initializeTaskLists() {
		fillFixedMaps();
		try {
			FileInputStream file = new FileInputStream(new File(rewardTrackerTasksFile));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			for (Task taskType : Task.values()) {
				XSSFSheet sheet = workbook.getSheet(sheetNameMap.get(taskType));
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
			}
			workbook.close();
		} catch (Exception e) {
			System.out.println("Was not able to find file " + rewardTrackerTasksFile);
		}
	}

	/**
	 * Called before closing the program to update the Excel Files.
	 */
	static void writeTasksToExcelFiles() {
        XSSFWorkbook workbook = new XSSFWorkbook();
		for (Task taskType : Task.values()) {
	        XSSFSheet sheet = workbook.createSheet(sheetNameMap.get(taskType));
	        
	        int rownum = 0;
	        for (String key : taskTypeMaps.get(taskType).keySet()) {
	            Row row = sheet.createRow(rownum++);
	            Double value = taskTypeMaps.get(taskType).get(key);
	            
	            // | <Name of task> | <Point value> |
	            row.createCell(0).setCellValue(key);
	            row.createCell(1).setCellValue(value);
	        }
		}
        try {
        	FileOutputStream out = new FileOutputStream(new File(rewardTrackerTasksFile));
            workbook.write(out);
            out.close();
            workbook.close();
		} catch (IOException e) {
			System.out.println("Was not able to save file " + rewardTrackerTasksFile);
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
