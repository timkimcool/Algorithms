import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Prob2 {
	Scanner scan;
	File input;
	Map<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	List<Edge> edges = new ArrayList<Edge>();
	Map<Integer, Integer> shortestPathLength = new HashMap<Integer, Integer>();
	Map<Integer, String> shortestPath = new HashMap<Integer, String>();

	
	public Prob2(String filePath) {
		try {
			input = new File(filePath);
			scan = new Scanner(input);
			// create Graph G with HashMap vertices and ArrayList edges
			String[] eleArray;
			while (scan.hasNextLine()) {
				String[] array = scan.nextLine().split("\t");
				Vertex source = getVertex(Integer.parseInt(array[0]), vertices);
				vertices.put(source.value, source);
				for (int i = 1; i < array.length; i++) {
					eleArray = array[i].split(",");
					Vertex target = getVertex(Integer.parseInt(eleArray[0]), vertices);
					Edge e = new Edge(source, target, Integer.parseInt(eleArray[1]));				
					source.addEdge(e);
					edges.add(e);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
	}
	
	public Integer Dijkstra(int source, int target) {
		Vertex vSource = vertices.get(source);
		Vertex vTarget = vertices.get(target);
		MinHeap heap = new MinHeap(vertices.size());
		shortestPath.put(vSource.value, vSource.value + "");
		shortestPathLength.put(vSource.value, 0);
		List<Integer> visitedV = new ArrayList<Integer>();
		for (Edge e : vertices.get(vSource.value).edges) {
			e.target().weight = e.weight;
			e.target().shortVertex = vSource.value;
			heap.insert(e.target());
		}
		//heap.print();
		
		while (vSource != vTarget) {
			vSource = heap.extractMin();
			visitedV.add(vSource.value);
			shortestPathLength.put(vSource.value, vSource.weight); 
			shortestPath.put(vSource.value, shortestPath.get(vSource.shortVertex + " -> " + vSource.value));
			for(Edge e : vSource.edges) {
				int index = heap.findNode(e.target().value);
				e.target().shortVertex = vSource.value;
				if (index == -1) {
					e.target().weight = shortestPathLength.get(vSource.value) + e.weight;
					heap.insert(e.target());
				} else {
					heap.replaceNode(index, shortestPathLength.get(vSource.value) + e.weight);
				}
			}
		}
		return shortestPathLength.get(target);
	}
	
	public Vertex getVertex(int n, Map<Integer, Vertex> vertices) {
		if (!vertices.containsKey(n)) {
			Vertex v = new Vertex(n);
			vertices.put(n, v);
			return v;
		} else {
			return vertices.get(n);
		}
	}
}

