package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Bank { 
	private static Map<String, Float> rewardList = new HashMap<>();
	private static Set<String> affordableRewardList = new HashSet<>();
	private static float points;
	
	// find way to quickly update affordable rewards, so don't have to search through 
	// entire HashMap to find the prices every time points change
}
