import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Prob2 {
	List<Edge> edgeList = new ArrayList<>();
	UnionFind ufA;
	UnionFind ufB;
	int K = 4;
	
	public Prob2() {
		try {
			// Part 1
			File inputA = new File("src/2_inputa.txt");
			Scanner scanA = new Scanner(inputA);
			ufA = new UnionFind();
			scanA.nextLine();
			while (scanA.hasNextLine()) {
				String nextLine = scanA.nextLine();
				String[] edges = nextLine.split(" ");
				processEdge(edges);
			}
			scanA.close();
			
			// Part 2			
			File inputB = new File("src/2_inputb.txt");
			Scanner scanB = new Scanner(inputB);
			ufB = new UnionFind();
			scanB.nextLine();
			while (scanB.hasNextLine()) {
				String nextLine = scanB.nextLine().replaceAll(" ", "");
				ufB.processVertex(nextLine, Integer.parseInt(nextLine, 2));
			}
			scanB.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	private void processEdge(String[] input) {
		int vertex1 = Integer.parseInt(input[0]);
		int vertex2 = Integer.parseInt(input[1]);
		int weight = Integer.parseInt(input[2]);
		ufA.processVertex(input[0], vertex1);
		ufA.processVertex(input[1], vertex2);
		edgeList.add(new Edge(vertex1, vertex2, weight));
	}
	
	private void mergeEdge(Edge edge) {
		ufA.Union(edge.getVertex1(), edge.getVertex2());
	}
	
	public int getMaxSpacing() {
		Collections.sort(edgeList, new Comparator<Edge>() {
		    @Override
		    public int compare(Edge e1, Edge e2) {
		    	return e1.getWeight() - e2.getWeight();
		    };
		});
		while (ufA.getClusterCount() > 4) {
			mergeEdge(edgeList.remove(0));
		}

		return getCrossingEdge().getWeight();
	}
	
	private Edge getCrossingEdge() {
		Edge edge = null;
		while (edge == null) {
			edge = edgeList.remove(0);
			if (ufA.isEdgeInCluster(edge)) {
				edge = null;
			}
		}
		return edge;
	}
	
	public int getNumOfClusters() {
		Iterator<String> vertices = ufB.getVertices();
		while (vertices.hasNext()) {
			String vertex = vertices.next();
			for (int i = 0; i < vertex.length(); i++) {
				String newVertex = getNewVertex(vertex, i);
				mergeVertex(vertex, newVertex);
				for (int j = i + 1; j < vertex.length(); j++) {
					String newVertex2 = getNewVertex(newVertex, j);
					mergeVertex(vertex, newVertex2);
				}
			}
		}
		return ufB.getClusterCount();
	}
	
	private void mergeVertex(String vertex, String newVertex) {
		if (ufB.doesVertexExist(newVertex)) {
			ufB.Union(vertex, newVertex);
		}
	}
	
	private String getNewVertex(String vertex, int index) {
		return vertex.substring(0, index) + (vertex.charAt(index) == '0' ? "1" : "0") + vertex.substring(index + 1);
	}
	
	public static void main(String[] args) {
		Prob2 prob2 = new Prob2();
		System.out.println("Part 1: " + prob2.getMaxSpacing());
		System.out.println("Part 2: " + prob2.getNumOfClusters());
	}
	
	class Edge {
		private int weight;
		int[] vertices;
		
		public Edge(int vertexA, int vertexB, int weight) {
			vertices = new int[]{ vertexA, vertexB };
			this.weight = weight;
		}
		
		public String toString() {
			return vertices[0] + "|" + vertices[1] + "|" + weight;
		}
		
		public int getWeight() {
			return this.weight;
		}
		
		public String getVertex1() {
			return String.valueOf(this.vertices[0]);
		}
		
		public String getVertex2() {
			return String.valueOf(this.vertices[1]);
		}
	}
	
	class UnionFind {
		private Map<String, Integer> vertexToCluster = new HashMap<>();
		private Map<Integer, List<String>> clusterToVertices = new HashMap<>();
		
		public UnionFind() {
			vertexToCluster = new HashMap<>();
			clusterToVertices = new HashMap<>();
		}
		
		public void Union(String vertex1, String vertex2) {
			int group1 = vertexToCluster.get(vertex1);
			int group2 = vertexToCluster.get(vertex2);
			if (group1 == group2) {
				return;
			}
			if (clusterToVertices.get(group1).size() > clusterToVertices.get(group2).size()) {
				mergeGroups(group2, group1);
			} else {
				mergeGroups(group1, group2);
			}
		}
		
		private void mergeGroups(int source, int target) {
			for (String vertex : clusterToVertices.get(source)) {
				vertexToCluster.replace(vertex, target);
			}
			clusterToVertices.get(target).addAll(clusterToVertices.get(source));
			clusterToVertices.remove(source);
		}

		public void processVertex(String input, int clusterNum) {
			if (!vertexToCluster.containsKey(input)) {
				vertexToCluster.put(input, clusterNum);
			}
			if (!clusterToVertices.containsKey(clusterNum)) {
				clusterToVertices.put(clusterNum, new ArrayList<String>());
				clusterToVertices.get(clusterNum).add(input);
			}
		}
		
		public int getClusterCount() {
			return clusterToVertices.keySet().size();
		}
		
		public boolean isEdgeInCluster(Edge edge) {
			return vertexToCluster.get(edge.getVertex1()).equals(ufA.vertexToCluster.get(edge.getVertex2()));
		}
		
		public boolean doesVertexExist(String vertex) {
			return vertexToCluster.containsKey(vertex);
		}
		
		public Iterator<String> getVertices() {
			return vertexToCluster.keySet().iterator();
		}
	}
}

