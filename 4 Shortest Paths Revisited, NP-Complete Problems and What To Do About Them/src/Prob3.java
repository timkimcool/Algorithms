import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Prob3 {
	public static void main(String[] arg) {
		Prob3 prob3 = new Prob3("src/3_input.txt");
		System.out.println("Answer: " + (int) prob3.getTSPSolution());
	}
	
	int n;
	Map<Integer, Vertex> numToVertex = new TreeMap<>();
	
	public Prob3(String path) {
		try {
			File file = new File(path);
			Scanner scan = new Scanner(file);
			n = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String[] lines = scan.nextLine().split(" ");
				int num = Integer.parseInt(lines[0]);
				Vertex vertex = new Vertex(Double.parseDouble(lines[1]), Double.parseDouble(lines[2]), num);
				numToVertex.put(num, vertex);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getTSPSolution() {
		List<Integer> notVisited = new ArrayList<>();
		for (int i : numToVertex.keySet()) {
			notVisited.add(i);
		}
		Double sum = 0.0;
		Vertex vertex = numToVertex.get(1);
		notVisited.remove(Integer.valueOf(1));
		while(!notVisited.isEmpty()) {
			double distance = Double.MAX_VALUE;
			Vertex newV = null;
			for(int i : notVisited) {
				Vertex tmpV = numToVertex.get(i);
				double tmpDistance = vertex.getDistance(tmpV);
				if (tmpDistance < distance) {
					distance = tmpDistance;
					newV = tmpV;
				}
			}
			vertex = newV;
			sum += distance;
			notVisited.remove(Integer.valueOf(vertex.num));
		}
		return sum + vertex.getDistance(numToVertex.get(1));
	}
	
	class Vertex {
		double x;
		double y;
		int num;
		Map<Double, List<Integer>> distances = new TreeMap<>();
		
		public Vertex(double x, double y, int num) {
			this.x = x;
			this.y = y;
			this.num = num;
		}
		public double getDistance(Vertex a) {
			return Math.sqrt(Math.pow(this.x - a.x, 2) + Math.pow(this.y - a.y, 2));
		}
	}
}
