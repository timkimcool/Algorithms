import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import jdk.jfr.SettingControl;

public class Prob2 {
	public static void main(String[] args) {
		Prob2 prob2 = new Prob2("src/2_input.txt");
		
		// actual code works, but takes too long, so consolidated vertices based on plotting the points
		Prob2 prob2Optimized = new Prob2("src/2_inputOptimized.txt");
		double consolidatedAns = prob2Optimized.getTSP();	// Consolidated path: 6 11 12 15 19 17 20 14 13 9 
//		System.out.println("Consolidated path: " + (prob2.vertices.get(6).edges.get(10) + prob2.vertices.get(10).edges.get(12) + prob2.vertices.get(12).edges.get(15) + prob2.vertices.get(15).edges.get(17) 
//				+ prob2.vertices.get(17).edges.get(20) + prob2.vertices.get(20).edges.get(14) + prob2.vertices.get(14).edges.get(13) 
//				+ prob2.vertices.get(13).edges.get(9) + prob2.vertices.get(9).edges.get(6) 
//				));
		
//		// Full path
//		System.out.println("Real1: " + (prob2.vertices.get(1).edges.get(2) + prob2.vertices.get(2).edges.get(6) + prob2.vertices.get(6).edges.get(10) + prob2.vertices.get(10).edges.get(11) 
//				+ prob2.vertices.get(11).edges.get(12) + prob2.vertices.get(12).edges.get(15) + prob2.vertices.get(15).edges.get(19) + prob2.vertices.get(19).edges.get(18) 
//				+ prob2.vertices.get(18).edges.get(22) + prob2.vertices.get(22).edges.get(23) + prob2.vertices.get(23).edges.get(21) + prob2.vertices.get(21).edges.get(17)
//				+ prob2.vertices.get(17).edges.get(20) + prob2.vertices.get(20).edges.get(25) + prob2.vertices.get(25).edges.get(24) + prob2.vertices.get(24).edges.get(16) 
//				+ prob2.vertices.get(16).edges.get(14) + prob2.vertices.get(14).edges.get(13) + prob2.vertices.get(13).edges.get(9) + prob2.vertices.get(9).edges.get(7) 
//				+ prob2.vertices.get(7).edges.get(3) + prob2.vertices.get(3).edges.get(4) + prob2.vertices.get(4).edges.get(8) + prob2.vertices.get(8).edges.get(5) 
//				+ prob2.vertices.get(5).edges.get(1)
//				));
		// short cut for combining vertices
		System.out.println("Answer: " + (int) (consolidatedAns 
				// 9 -> 6 (add back consolidated vertices: 7 3 4 8 5 1 2)
				- prob2.vertices.get(9).edges.get(6) + prob2.vertices.get(9).edges.get(7) + prob2.vertices.get(7).edges.get(3) + prob2.vertices.get(3).edges.get(4)
				+ prob2.vertices.get(4).edges.get(8) + prob2.vertices.get(8).edges.get(5) + prob2.vertices.get(5).edges.get(1) + prob2.vertices.get(1).edges.get(2) + prob2.vertices.get(2).edges.get(6)
				// 6 -> 11 (add back vertices: 10)
				- prob2.vertices.get(6).edges.get(11) + prob2.vertices.get(6).edges.get(10) + prob2.vertices.get(10).edges.get(11)
				// 14 -> 20 (add back: 16 24 25)
				- prob2.vertices.get(14).edges.get(20) + prob2.vertices.get(14).edges.get(16) + prob2.vertices.get(16).edges.get(24) + prob2.vertices.get(24).edges.get(25) + prob2.vertices.get(25).edges.get(20)
				// 15 -> 17 (add back: 19 18 22 23 21
				- prob2.vertices.get(15).edges.get(17) + prob2.vertices.get(15).edges.get(19) + prob2.vertices.get(19).edges.get(18) + prob2.vertices.get(18).edges.get(22) 
				+ prob2.vertices.get(22).edges.get(23) + prob2.vertices.get(23).edges.get(21) + prob2.vertices.get(21).edges.get(17) // 19, 18, 22, 23, 21
				));
	}
	
	Map<Integer, Vertex> vertices = new HashMap<>();
	Map<String, Integer> letterToVertex = new HashMap<>();
	Map<Integer, String> vertexToLetter = new HashMap<>();
	int n;  // vertices
	
	public Prob2(String path) {
		// how many neighbors to consider;
		String str = "abcdefghijklmnopqrstuvwxy";
		for (int i = 1; i < str.length() + 1; i++) {
			letterToVertex.put(str.substring(i - 1, i), i);
		}
		for(String s : letterToVertex.keySet()) {
			vertexToLetter.put(letterToVertex.get(s), s);
		}
		
		try {
			File inputA = new File(path);
			Scanner scanA;
			scanA = new Scanner(inputA);
			n = Integer.parseInt(scanA.nextLine());
			int count = 1;
			while (scanA.hasNextLine()) {
				String[] line = scanA.nextLine().split(" ");
				double x = Double.parseDouble(line[0]);
				double y = Double.parseDouble(line[1]);
				Vertex vert1 = new Vertex(x, y, count);
				for(int i = 1; i < count; i++) {
					Vertex vert2 = vertices.get(i);
					vert1.addVertex(vert2);
					vert2.addVertex(vert1);
				}
				vertices.put(count++, vert1);
			}
			scanA.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getTSP() {
		Map<String, Map<Integer, Double>> numStore1= new HashMap<>();
		Map<String, Map<Integer, Double>> numStore2 = new HashMap<>();
		for (int m = 2; m <= n; m++) {
			Map<String, Map<Integer, Double>> numStoreA = numStore1.size() > 1 ? numStore1 : numStore2;
			Map<String, Map<Integer, Double>> numStoreB = numStore1.size() > 1 ? numStore2 : numStore1;
			List<String> arrays = new ArrayList<>();
			arrays = getSubsets(m);
			for (String str : arrays) {
				for (String c1 : str.split("")) {
					int j = letterToVertex.get(c1);
					if (j == 1) {
						continue;
					}
					double minVal = Double.MAX_VALUE;
					for (String c2 : str.split("")) {
						int k = letterToVertex.get(c2);
						if (k == j) {
							continue;
						}
						String setWithoutJ = removeFromSet(str, c1);
						
						if (k == 1) {
							if (!numStoreA.containsKey(setWithoutJ)) {
								numStoreA.put(setWithoutJ, new HashMap<>());
							}
							if (setWithoutJ.equals("a")) {
								numStoreA.get(setWithoutJ).put(k, (double) 0);
							} else {
								numStoreA.get(setWithoutJ).put(k, Double.MAX_VALUE);
							}
						}
						if (numStoreA.containsKey(setWithoutJ) && numStoreA.get(setWithoutJ).containsKey((k))) {
							
							double val = numStoreA.get(setWithoutJ).get(k) + vertices.get(k).edges.get(j);
							minVal = val < minVal ? val : minVal;
						}
					}
					if(!numStoreB.containsKey(str)) {
						numStoreB.put(str, new HashMap<>());
					}
					numStoreB.get(str).put(j, minVal);
				}
			}
			numStoreA.clear();
		}
		Map<String, Map<Integer, Double>> numStoreA = numStore1.size() > 1 ? numStore1 : numStore2;
		
		// final run
		double minVal = Double.MAX_VALUE;;
		for (String s : numStoreA.keySet()) {
			for (int j = 2; j <= n; j++) {
				double val = numStoreA.get(s).get(j) + vertices.get(j).edges.get(1);
				minVal = val < minVal ? val : minVal;
			}
		}
		return minVal;
	}
	
	private List<String> getSubsets(int m) {
		List<String> arrays = new ArrayList<>();
		Queue<String> queue = new LinkedList<>();
		queue.add("a");
		while (!queue.isEmpty()) {
			String str = queue.poll();
			if (str.length() == m) {
				arrays.add(str);
				continue;
			}
			for (int i : vertices.keySet()) {
				String s = vertexToLetter.get(i);
				if (!str.contains(s)) {
					queue.add(str + s);
				}
			}
		}
		return arrays;
	}
	
	private String removeFromSet(String str, String remove) {
		char[] charArray = str.toCharArray();
		Arrays.sort(charArray);
		return String.valueOf(charArray).replaceAll(remove, "");
	}
	
	class Vertex {
		double x;
		double y;
		int num;
		Map<Integer, Double> edges = new HashMap<>();
		
		public Vertex(double x, double y, int num) {
			this.x = x;
			this.y = y;
			this.num = num;
		}
		
		public void addVertex(Vertex a) {
			edges.put(a.num, Math.sqrt(Math.pow(this.x - a.x, 2) + Math.pow(this.y - a.y, 2)));
		}
	}
}

