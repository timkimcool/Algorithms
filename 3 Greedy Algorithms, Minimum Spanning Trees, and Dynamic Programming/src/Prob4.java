import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


// recursive and dynamic implementation of knapsack problem
public class Prob4 {
	public static void main(String[] args) {
		Prob4 prob4a = new Prob4("src/4_inputa.txt");
		System.out.println("Part 1: " + prob4a.getMaxValue());
		Prob4 prob4b = new Prob4("src/4_inputb.txt");
		System.out.println("Part 2: " + prob4b.getMaxValue());
	}
	
	Map<String, Long> valueStore = new HashMap<>();
	Map<Integer, Item> items = new HashMap<>();
	Map<Integer, Set<Integer>> weightsToCalc = new HashMap<>();
	int totalCapacity;
	int totalItems;
	
	public Prob4(String path) {
		File input = new File(path);
		try {
			Scanner scan = new Scanner(input);
			String[] line = scan.nextLine().split(" ");
			totalCapacity = Integer.parseInt(line[0]);	
			totalItems = Integer.parseInt(line[1]);		
			int count = 0;
			while(scan.hasNext()) {
				line = scan.nextLine().split(" ");
				count++;
				items.put(count, new Item(Integer.parseInt(line[0]), Integer.parseInt(line[1])));	
			}
			getValuesToStore(totalCapacity, totalItems);
			calcValues(totalItems);
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// get the exact item and weight combination to calculate
	private void getValuesToStore(int capacity, int index) {
		if (index == 0 || capacity < 0) {
			return;
		}
		if (items.get(index).calcSet.contains(capacity)) {;
			return;
		}
		items.get(index).calcSet.add(capacity);
		getValuesToStore(capacity - items.get(index).weight, index - 1);
		getValuesToStore(capacity, index - 1);
	}
	
	// only calculate the necessary values
	private void calcValues(int index) {
		long val = 0;
		for (int j : items.get(1).calcSet) {
			val = (items.get(1).weight <= j) ? items.get(1).value : 0;
			valueStore.put("1," + j, val);
		}
		for (int i = 2; i <= index; i++) {
			for (int j : items.get(i).calcSet) {
				long prevWeight = valueStore.get((i - 1) + "," + j);
				if (j < items.get(i).weight) {
					valueStore.put(i + "," + j, prevWeight);
				} else {
					val = Math.max(prevWeight, valueStore.get((i - 1) + "," + (j - items.get(i).weight)) + items.get(i).value);
					valueStore.put(i + "," + j, val);
				}
			}
		}
	}
	
	public long getMaxValue() {
		return valueStore.get(totalItems + "," + totalCapacity);
	}
	
	class Item {
		int weight;
		int value;
		Set<Integer> calcSet;
		
		public Item(int value, int weight) {
			this.weight = weight;
			this.value = value;
			calcSet = new HashSet<>();
		}
		
		public String toString() {
			return " ITEM (v|w): " + value + "|" + weight;
		}
	}
}
