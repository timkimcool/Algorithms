import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Prob1 {
	Scanner scan;
	File input;
	Map<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	List<Edge> edges = new ArrayList<Edge>();
	
	int t = 0; // for finishing time in 1st pass; # of nodes processed
	Vertex s = null; //for leaders in second pass; current source vertex
	int sccSize = 0;
	Map<Integer, Integer> fTimeVert = new HashMap<Integer, Integer>();
	Map<Integer, Integer> result = new HashMap<Integer, Integer>();
	Stack<Vertex> stack = new Stack<Vertex>();
	
	public Prob1(String filePath) {
		try {
			input = new File(filePath);
			scan = new Scanner(input);
			// create Graph G with HashMap vertices and ArrayList edges
			while (scan.hasNextLine()) {
				String[] array = scan.nextLine().split(" ");
				Vertex source = getVertex(Integer.parseInt(array[0]), vertices);
				Vertex target = getVertex(Integer.parseInt(array[1]), vertices);
				Edge e = new Edge(source, target);				
				source.addEdge(e);
				edges.add(e);
				
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
	}
	
	public String findSCC() {
		// DFS Rev
		revGraph();
		DFSLoop();
		// DFS
		revGraph();
		DFSLoop2();
		List<Integer> resultSort = new ArrayList<Integer>(result.values());
		Collections.sort(resultSort, Collections.reverseOrder());
		return resultSort.get(0) + "," + resultSort.get(1) + "," + resultSort.get(2) + "," + resultSort.get(3) + "," + resultSort.get(4);	// return 5 biggest scc's
	}
	
	public void revGraph() {
		// reset edges list + explored
		for (Vertex v : vertices.values()) {
			v.edges.clear();
			v.explored = false;
		}
		for (Edge e : edges) {
			e.rev();
			e.source().addEdge(e);
		}
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
	
	// Not using recursion because of potential stack overflow error due to # of recursion calls	
	public void DFSLoop() {
		// Randomize starting vertices
		// ArrayList<Vertex> a = vertices.values().stream().collect(Collectors.toCollection(ArrayList::new));
		// Collections.shuffle(a);
		for (Vertex v : vertices.values()) {	// vertices.values() > a for random starting points
			if (!v.explored) {
				stack.push(v);
				while (!stack.isEmpty()) {
					Vertex v2 = stack.peek();
					boolean done = true;
					for (Edge e : v2.edges) {
						if (!e.target().explored) {
							done = false;
							e.target().explored();
							stack.push(e.target());		// keep track of vertices being processed
						}
					}
					if (done && !stack.isEmpty() ) {	//process pack of vertices that are done
						v2 = stack.pop();
						t++;
						v2.fTime = t;
						fTimeVert.put(v2.fTime, v2.value);
					}
				}
			}
		}
	}
	
	public void DFSLoop2() {
		List<Integer> fTimeList = new ArrayList<Integer>(fTimeVert.keySet());
		Collections.sort(fTimeList, Collections.reverseOrder());
		stack.clear();
		for (Integer n : fTimeList) {
			Vertex v = vertices.get(fTimeVert.get(n));
			if (!v.explored) {
				stack.push(v);
				while (!stack.isEmpty()) {
					DFS2(stack.pop());
				}	
				result.put(v.value, sccSize);
				sccSize = 0;
			}
		}
	}
	
	public void DFS2(Vertex v) {
		if (!v.explored) {
			v.explored();
			if (s == null) {
				s = v;
			} else if (s.fTime < v.fTime) { s = v; }
			sccSize++;
		}
		for (Edge e : v.edges) {
			if (!e.target().explored) {
				stack.push(e.target());
			}
		}
	}
}