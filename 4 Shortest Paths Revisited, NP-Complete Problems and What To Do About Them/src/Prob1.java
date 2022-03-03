import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Prob1 {
	
	public static void main(String[] args) {
//		Prob1 prob1a = new Prob1("src/test.txt");
		Prob1 prob1a = new Prob1("src/1_inputa.txt");
		Prob1 prob1b = new Prob1("src/1_inputb.txt");
		Prob1 prob1c = new Prob1("src/1_inputc.txt");
		System.out.println(prob1a.returnShortestPath());
//		System.out.println(prob1b.returnShortestPath());
//		System.out.println(prob1a.returnShortestPath());
//		Prob1 prob1a = new Prob1("src/test.txt");
//		Map<Integer, Long> pathLengths = prob1a.runDijkstra(1);
//		System.out.println(prob1a.getShortestPath());
	}
	
	Map<Integer, Vertex> vertices = new HashMap<>();
	long[][] vArray;
	List<Edge> edges = new ArrayList<>();
	
	public Prob1(String path) {
		
		try {
			File inputA = new File(path);
			Scanner scanA;
			scanA = new Scanner(inputA);
			scanA.nextLine();
			while (scanA.hasNextLine()) {
				String[] line = scanA.nextLine().split(" ");
				int head = Integer.parseInt(line[0]);
				int tail = Integer.parseInt(line[1]);
				int length = Integer.parseInt(line[2]);
				if (!vertices.containsKey(head)) {
					vertices.put(head, new Vertex(head));
				}
				if (!vertices.containsKey(tail)) {
					vertices.put(tail, new Vertex(tail));
				}
				Vertex headV = vertices.get(head);
				Vertex tailV = vertices.get(tail);
				Edge e = new Edge(headV, tailV, length);
				edges.add(e);
				headV.addEdge(e);
//				tailV.addEdge(new Edge(tailV, headV, length));
				tailV.addInEdge(e);
			}
			scanA.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String returnShortestPath() {
		if (createNewGraph()) {
			return "" + getShortestPath();
		} else {
			return "negative cycle detected";
		}
	}
	
	private boolean createNewGraph() {
		System.out.println("Create New Graph");
		int newNum = 0;
		boolean foundNum = false;
		while(!foundNum) {
			if (vertices.containsKey(newNum)) {
				newNum++;
			} else {
				foundNum = true;
			}
		}
		// create new graph
		Vertex source = new Vertex(newNum);
		vertices.put(newNum, source);
		for(int vNum : vertices.keySet()) {
			Vertex v = vertices.get(vNum);
			Edge e = new Edge(source, v, 0);
			source.addEdge(e);
			v.addInEdge(e);
		}
		// run Bellman-Ford
		long[][] array = runBellFord(newNum);
		if (array == null) {
			return false;
		}
		vertices.remove(newNum);
		// set vertex weight
		for (int vNum : vertices.keySet()) {
			vertices.get(vNum).weight = array[array.length - 1][vNum];
		}

		// get new edge values
		for (Edge e : edges) {
			e.trueLength = e.length;
			e.length = e.length + e.head.weight - e.tail.weight;
			if (e.length < 0) {
				System.out.println("neg value: " + e.length + "|" + e.trueLength +"|" + e.head.weight + "|" + e.tail.weight);
			}
		}
		return true;
	}
	
	private long getShortestPath() {
		System.out.println("getShortestPath");
		// run n Dijkstra
		long shortestPath = Integer.MAX_VALUE;
		for(int vNum : vertices.keySet()) {
			Map<Integer, Long> pathLengths = runDijkstra(vNum);
			long test = Integer.MAX_VALUE;
			for (int i : pathLengths.keySet()) {
				if (pathLengths.get(i) < shortestPath) {
					shortestPath = pathLengths.get(i);
				}
				if (pathLengths.get(i) < test) {
					test = pathLengths.get(i);
				}
			}
		}
		return shortestPath;
	}
	
	public Map<Integer, Long> runDijkstra(int source) {
		Vertex vSource = vertices.get(source);
		PriorityQueue<Edge> heap = new PriorityQueue<Edge>();
		Map<Integer, Long> pathLengths = new HashMap<Integer, Long>();
		Map<Integer, Long> pathLengths2 = new HashMap<Integer, Long>();
		List<Vertex> visitedV = new ArrayList<Vertex>();
		visitedV.add(vSource);
		pathLengths.put(source, (long) 0);
		pathLengths2.put(source, (long) 0);
		for (Edge e : vSource.outEdges) {
			e.fullLength = e.length;
			e.trueFullLength = e.trueLength;
			heap.add(e);
		}
		while(visitedV.size() != vertices.size()) {
			if(heap.isEmpty()) {
				return pathLengths2;
			}
			Edge shortEdge = heap.remove();
			vSource = shortEdge.tail;
			if (visitedV.contains(vSource)) {
				continue;
			}
			visitedV.add(vSource);
			pathLengths.put(vSource.number, shortEdge.fullLength);
			pathLengths2.put(vSource.number, shortEdge.trueFullLength);
			for (Edge e: vSource.outEdges) {
				e.fullLength = e.length;
				e.trueFullLength = e.trueLength;
				if(!visitedV.contains(e.tail) || visitedV.contains(e.tail) && pathLengths.get(e.tail.number) > pathLengths.get(e.head.number) + e.length) {
					e.fullLength = pathLengths.get(e.head.number) + e.length;
					e.trueFullLength = pathLengths2.get(e.head.number) + e.trueLength;
					heap.add(e);
				}
			}
		}
		pathLengths.remove(source);
		pathLengths2.remove(source);
		return pathLengths2;
	}
	
	public long runDijkstra(int source, int target) {
		Vertex vSource = vertices.get(source);
		Vertex vTarget = vertices.get(target);
		PriorityQueue<Edge> heap = new PriorityQueue<Edge>();
		Map<Integer, Long> pathLengths = new HashMap<Integer, Long>();
		Map<Integer, String> pathLengths2 = new HashMap<Integer, String>();
		List<Vertex> visitedV = new ArrayList<Vertex>();
		visitedV.add(vSource);
		pathLengths.put(source, (long) 0);
		pathLengths2.put(source, source + "");
		for (Edge e : vSource.outEdges) {
			heap.add(e);
		}
		while(vSource.number != vTarget.number) {
			if(heap.isEmpty()) {
				return 1000000;
			}
			Edge shortEdge = heap.remove();
			vSource = shortEdge.tail;
			if (visitedV.contains(vSource)) {
				continue;
			}
			visitedV.add(vSource);
			pathLengths.put(vSource.number, shortEdge.fullLength);
			pathLengths2.put(vSource.number, pathLengths2.get(shortEdge.head.number) + "," + vSource.number);
			
			for (Edge e: vSource.outEdges) { 
				if(!visitedV.contains(e.tail) || visitedV.contains(e.tail) && pathLengths.get(e.tail.number) > pathLengths.get(e.head.number) + e.length) {
					e.fullLength = pathLengths.get(e.head.number) + e.length;
					heap.add(e);
				}
			}
		}
		return pathLengths.get(target);
	}
	
	private long[][] runBellFord(int source) {
		vArray = new long[vertices.size() + 1][vertices.size() + 1];
		for (int i = 0; i < vertices.size() + 1; i++) {
			vArray[0][i] = i == source ? 0 : Integer.MAX_VALUE;
		}
		
		//Bell-Ford loop
		for (int i = 1; i < vertices.size(); i++) {
			// early termination if no values change
			iterateBellFord(vArray, i);
//			if (iterateBellFord(vArray, i)) {	
//				System.out.println("early");
//				return vArray;
//			}
		}
		
		// negative cycle check
		if (!iterateBellFord(vArray, vertices.size())) {
			System.out.println("NEGATIVE CYCLE");
			return null;
		}
		
//		int count = 0;
//		for (String i : pathLengths2.get(target).split(",")) {
//			count++;
//			System.out.println(i + "|" + vArray[count - 1][Integer.parseInt(i)]);
//		}
		return vArray;
	}
	
	// returns false if values in vArray change; otherwise true;
	private Boolean iterateBellFord(long[][] vArray, int i) {
		Boolean changed = true;
		for(int vNum : vertices.keySet()) {
			// find minimum w_v
			long minTargetEdge = Integer.MAX_VALUE;
			for (Edge e : vertices.get(vNum).inEdges) {
				long tmpMin = vArray[i - 1][e.head.number] + e.length;
				minTargetEdge = minTargetEdge > tmpMin ? tmpMin : minTargetEdge;
			}
			// check if quitting early
//			if (vArray[i - 1][vNum] > minTargetEdge) {
//				System.out.println(vArray[i][vNum] + "|" + vArray[i - 1][vNum] +"|" + minTargetEdge + "|" + vArray[i - 1][vNum] + "|" + minTargetEdge);
//			}
			vArray[i][vNum] = Math.min(vArray[i - 1][vNum], minTargetEdge);
			if (vArray[i - 1][vNum] != vArray[i][vNum]) {
				System.out.println("changed: " + vNum + "|" + vArray[i - 1][vNum] + "|" + vArray[i][vNum]);
				changed = false;
			}
		}
		return changed;
	}
	
	class Vertex {
		int number;
		long weight;
		List<Edge> outEdges = new ArrayList<>();
		List<Edge> inEdges = new ArrayList<>();
		
		public Vertex(int number) {
			this.number = number;
		}
		
		public Vertex() {
			this.number = 0;
		}
		
		public void addEdge(Edge e) {
			outEdges.add(e);
		}
		
		public void addInEdge(Edge e) {
			inEdges.add(e);
		}
		
		public String toString() {
			return "v: " + number;
		}
		
		public Edge getEdge(Vertex v) {
			for (Edge e : outEdges) {
				if (e.tail == v) {
					return e;
				}
			}
			return null;
		}
	}
	
	class Edge implements Comparable<Edge>{
		Vertex head;
		Vertex tail;
		long length;
		long fullLength;
		long trueLength;
		long trueFullLength;
		
		public Edge(Vertex head, Vertex tail, int length) {
			this.head = head;
			this.tail = tail;
			this.length = length;
			this.fullLength = length;
			this.trueFullLength = length;
		}
		
		@Override
		public int compareTo(Edge e) {
			return (int) (this.fullLength - e.fullLength);
		}
		
		public String toString() {
			return "EDGE: " + head.number + "|" + tail.number + "|" + length + "|" + fullLength;
		}
	}
	
}
