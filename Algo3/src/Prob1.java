import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Prob1 {
	
	public static void main(String[] args) {
		Prob1 prob1 = new Prob1();
		System.out.println("Part 1: " + prob1.getPartASum(true));
		System.out.println("Part 2: " + prob1.getPartASum(false));
		System.out.println("Part 3: " + prob1.getPartBSum());
	}
	
	PriorityQueue<Job> heapPart1 = new PriorityQueue<>(new Comparator<Job>() {
	    @Override
	    public int compare(Job j1, Job j2) {
	    	return j1.getDiff() > j2.getDiff() || (j1.getDiff() == j2.getDiff() && j1.weight > j2.weight) ? -1 : 1;
	    }
	});
	PriorityQueue<Job> heapPart2 = new PriorityQueue<>(new Comparator<Job>() {
	    @Override
	    public int compare(Job j1, Job j2) {
	    	return j1.getRatio() > j2.getRatio() ? -1 : 1;
	    }
	});
	PriorityQueue<Edge> heapPart3 = new PriorityQueue<>(new Comparator<Edge>() {
	    @Override
	    public int compare(Edge e1, Edge e2) {
	    	return e1.weight > e2.weight ? 1 : -1;
	    }
	});
	Map<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>();
	List<Edge> edges = new ArrayList<Edge>();
	List<Vertex> vertices = new ArrayList<Vertex>();
	
	public Prob1() {
		try {
			File inputA = new File("src/1_inputa.txt");
			File inputB = new File("src/1_inputb.txt");
			Scanner scanA = new Scanner(inputA);
			Scanner scanB = new Scanner(inputB);
			scanA.nextLine();
			scanB.nextLine();
			while (scanA.hasNextLine()) {
				String nextLine = scanA.nextLine();
				String[] jobParam = nextLine.split(" ");
				heapPart1.add(new Job(Integer.parseInt(jobParam[0]), Integer.parseInt(jobParam[1])));
				heapPart2.add(new Job(Integer.parseInt(jobParam[0]), Integer.parseInt(jobParam[1])));
			}
			scanA.close();
			while (scanB.hasNextLine()) {
				String nextLine = scanB.nextLine();
				String[] nodes = nextLine.split(" ");
				int node0 = Integer.parseInt(nodes[0]);
				int node1 = Integer.parseInt(nodes[1]);
				if (!vertexMap.containsKey(node0)) {
					vertexMap.put(node0, new Vertex(node0));
				}
				if (!vertexMap.containsKey(node1)) {
					vertexMap.put(node1, new Vertex(node1));
				}
				Edge edge = new Edge(vertexMap.get(node0), vertexMap.get(node1), Integer.parseInt(nodes[2]));
				vertexMap.get(node0).addEdge(edge);
				vertexMap.get(node1).addEdge(edge);
			}
			scanB.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public long getPartASum(boolean part1) {
		long compTime = 0;
		long WCTSum = 0;
		PriorityQueue <Job> heap = part1 ? heapPart1 : heapPart2;
		while (!heap.isEmpty()) {
			Job job = heap.poll();
			compTime += job.length;
			WCTSum += compTime * job.weight;
		}
		return WCTSum;
	}
	
	public long getPartBSum() {
		int numOfVertices = vertexMap.values().size();
		long totalCost = 0;
		Vertex tmpVertex = (Vertex) vertexMap.values().toArray()[(int) (Math.random() * numOfVertices)];
		while(vertices.size() != numOfVertices) {
			int vertexValue = tmpVertex.value;
			vertices.add(tmpVertex);
			tmpVertex.edges.forEach(e -> {
				if (!vertices.contains(e.getVertex(vertexValue))) {
					heapPart3.add(e);
				}
			});
			Edge tmpEdge = null;
			while (vertices.contains(tmpVertex)) {
				if (heapPart3.isEmpty()) {
					return totalCost;
				}
				tmpEdge = heapPart3.poll();
				tmpVertex = vertices.contains(tmpEdge.v1) ? tmpEdge.v2 : tmpEdge.v1;
			}
			totalCost += tmpEdge.weight;		
		}
		return totalCost;
	}
	
	class Job {
		int weight;
		int length;
		
		public Job(Integer weight, Integer length) {
			this.weight = weight;
			this.length = length;
		}
		
		public int getDiff() {
			return this.weight - this.length;
		}
		
		public double getRatio() {
			return (double) this.weight / this.length;
		}
		
		public String toString() {
			return "diff|w|l|/: " + getDiff() + "|" + this.weight + "|" + this.length + "|" + getRatio();
		}
	}

	class Vertex {
		public int value;
		List<Edge> edges = new ArrayList<Edge>();
		public Vertex(int vertex) {
			this.value = vertex;
		}
		
		public void addEdge(Edge e) {
			edges.add(e);
		}
		
		public String toString() {
			return "Vertex value: " + this.value;
		}
	}

	class Edge {
		Vertex v1;
		Vertex v2;
		int weight;
		
		public Edge(Vertex v1, Vertex v2, int weight) {
			this.v1 = v1;
			this.v2 = v2;
			this.weight = weight;
		}
		
		public Vertex getVertex(int value) {
			return v1.value == value ? v2 : v1;
		}
		
		public String toString() {
			return this.v1.value + " - " + this.v2.value + "(" + this.weight + ")";
		}
	}
}

