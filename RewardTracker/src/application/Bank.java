package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Bank {
	private static String rewardTrackerBankFile = "C:/Users/Autumn Herness/Desktop/RewardTrackerExcelFiles/RewardTrackerBank.xlsx";

	private static String pointsSheetName = "Points";
	private static String rewardListSheetName = "Reward List";

	private static Set<Reward> rewardSet = new HashSet<>();
	private static Map<String, Reward> rewardMap = new HashMap<>();
	private static Map<String, Double> rewardList = new HashMap<>();
	private static Set<String> affordableRewardList = new HashSet<>();
	private static double points;

	static void initializeRewardListAndPoints() {
		try {
			FileInputStream file = new FileInputStream(new File(rewardTrackerBankFile));
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet rewardSheet = workbook.getSheet(rewardListSheetName);
			Iterator<Row> rowIterator = rewardSheet.iterator();
			while (rowIterator.hasNext()) {
				String rewardName = "";
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				Cell cell = cellIterator.next();
				if (cell.getCellType() == CellType.STRING) {
					rewardName = cell.getStringCellValue();
				} else {
					System.out.println("Task Name must be String.");
				}
				try {
					cell = cellIterator.next();
					try {
						double cost = cell.getNumericCellValue();
						addReward(rewardName, cost);
					} catch (Exception e) {
						System.out.println("Score values must be numeric. Value was " + cell.getStringCellValue());
					}
				} catch (Exception e) {
					System.out.println("Each Task Name requires an accompanying score");
				}

				XSSFSheet pointsSheet = workbook.getSheet(pointsSheetName);
				points = pointsSheet.getRow(0).getCell(0).getNumericCellValue();

				workbook.close();
			}
		} catch (Exception e) {
			System.out.println("Was not able to open file " + rewardTrackerBankFile);
		}
	}

	static void writeRewardListAndPointsFile() {
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet rewardSheet = workbook.createSheet(rewardListSheetName);

		int rownum = 0;
		for (String key : rewardList.keySet()) {
			Row row = rewardSheet.createRow(rownum++);
			Double value = rewardList.get(key);

			// | <Name of task> | <Point value> |
			row.createCell(0).setCellValue(key);
			row.createCell(1).setCellValue(value);
		}

		XSSFSheet pointsSheet = workbook.createSheet(pointsSheetName);
		Row row = pointsSheet.createRow(0);
		row.createCell(0).setCellValue(points);

		try {
			FileOutputStream out = new FileOutputStream(new File(rewardTrackerBankFile));
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (IOException e) {
			System.out.println("Was not able to save file " + rewardTrackerBankFile);
		}
	}

	public static ObservableList<String> getRewardList() {
		return FXCollections.observableArrayList(rewardList.keySet());
	}
	
	public static ObservableList<Reward> getRewardSet() {
		return FXCollections.observableArrayList(rewardSet);
	}
	
	public static ObservableList<String> getAffordableRewardList() {
		affordableRewardList = new HashSet<>();
		for (String reward : rewardList.keySet()) {
			if (rewardList.get(reward) < points) {
				affordableRewardList.add(reward);
			}
		}
		return FXCollections.observableArrayList(affordableRewardList);
	}

	static Double getPoints() {
		return points;
	}
	
	static Double getCost(String name) {
		return rewardList.get(name);
	}
	
	static void addReward(String name, double cost) {
		rewardList.put(name, cost);
		Reward reward = new Reward(name, cost);
		rewardSet.add(reward);
		rewardMap.put(name, reward);	
	}
	
	static void removeReward(String name) {
		rewardList.remove(name);
		rewardSet.remove(rewardMap.get(name));
		affordableRewardList.remove(name);
	}
	
	static void addPoints(double amount) {
		points += amount;
	}
	
	static void spendPoints(double amount) {
		if (points > amount) {
			points -= amount;
		} else {
			System.out.println("Cannot have negative points!");
		}
	}
}
