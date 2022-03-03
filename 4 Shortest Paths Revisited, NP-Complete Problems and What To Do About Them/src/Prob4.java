import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

public class Prob4 {
	public static void main (String args[]) {
		// int[] a = new int[] {1, 2, 3, 4, 5, 6};
		int [] a = new int[] {1};
		for (int i : a) {
			Prob4 prob4 = new Prob4("src/4_input" + i + ".txt");
			System.out.println("answer " + i + ": " + prob4.passFalseTest());
			prob4.checkSolution();
		}
	}
	
	Map<Integer, List<Integer>> map = new TreeMap<>(); // contains abs (smaller, bigger) clauses
	Map<Integer, Boolean> truthMap = new TreeMap<>(); // contains both
	Map<Integer, Integer> input = new TreeMap<>();
	Stack<Integer> stack = new Stack<>();
	
	public Prob4(String path) {
		File file = new File(path);
		try {
			Scanner scan = new Scanner(file);
			int line = Integer.parseInt(scan.nextLine());
			while (scan.hasNext()) {
				String[] lines = scan.nextLine().split(" ");
				int x = Integer.parseInt(lines[0]);
				int y = Integer.parseInt(lines[1]);
				input.put(x, y);
				int small = Math.abs(x) > Math.abs(y) ? y : x;
				int big = Math.abs(x) > Math.abs(y) ? x : y;
				if (!map.containsKey(small)) {
					map.put(small, new ArrayList<>());
					if (y == -17378) {
						System.out.println("ADDED");
					}
				}
				map.get(small).add(big);
				if (small == -17378) {
					System.out.println( x + "|" + y +"|" + map.get(y).toString());
				}
			}
			
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void checkSolution() {
		for (int i : input.keySet()) {
			System.out.println(i + "|" + input.get(i));
			System.out.println(truthMap.get(i));
			System.out.println(truthMap.get(input.get(i)));
			if (!(truthMap.get(i) || truthMap.get(input.get(i)))) {
				System.out.println("i: " + i + "|" + input.get(i));
				System.out.println("map:" + truthMap.get(i) + "|" + truthMap.get(input.get(i)));
			}
		}
	}
	
	// Check for false seems to work
	public boolean passFalseTest() {
		for (int i : map.keySet()) {
			int small = i <= 0 ? i : -i;
			if (truthMap.containsKey(i)) {
				if (truthMap.get(i) && truthMap.get(-i)) {
					// part 2 is flipped
					truthMap.put(-small, true);
					truthMap.put(small, false);
				} else {
					continue;
				}
			} else {
				// i <= 0, start true;
				truthMap.put(small, true);
				truthMap.put(-small, false);
			}
			stack.add(small);
			if (!passTrueTest(-small)) {	// pass false value; all the associated values of not i has to be true
				// if false value fails
				// if it's already false
				if(!truthMap.get(small)) {
					stack.pop();
					i = loopStack();
					if (i == Integer.MAX_VALUE) {
						return false;
					}
				} else {
					// otherwise set it to false
					stack.pop();
					stack.add(-small);
					truthMap.put(-small, true);
					truthMap.put(small, false);
					if(!passTrueTest(small)) {
						i = loopStack();
						if (i == Integer.MAX_VALUE) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	// Check for true
	private boolean passTrueTest() {
		for (int i : map.keySet()) {
			if (truthMap.containsKey(i)) {
				for (int j : map.get(i)) {
					
				}
			}
		}
		
		return true;
	}
	
	private int loopStack() {
		while (!stack.isEmpty()) {
			int j = stack.pop();
			if (j <= 0 && truthMap.get(j)) {
				truthMap.put(j, true);
				truthMap.put(-j, true);
				return j;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	// k is false -> everything else in list has to be true
	// Side-effect: adds new values to truthMap
	private boolean passTrueTest(int k) {
		List<Integer> list = map.get(k);
		if (list == null) {
			return true;
		}
		List<Integer> tmpList = new ArrayList<>();
		for (int i : list) {
			if (truthMap.containsKey(i)) {
				if (!truthMap.get(i)) {
					clearMap(tmpList);
					return false;
				}
			} else {
				// only option
				truthMap.put(i, true);
				truthMap.put(-i, false);
				tmpList.add(i);
				if(!passTrueTest(-i)) {
					clearMap(tmpList);
					return false;
				}
			}
		}
		
		return true;
	}
	
	// backtrack truthMap
	private void clearMap(List<Integer> list) {
		for (int i : list) {
			truthMap.remove(i);
			truthMap.remove(-i);
		}
	}
}
