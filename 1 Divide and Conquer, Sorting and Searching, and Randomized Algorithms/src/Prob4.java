import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Prob4 {
	Scanner scan;
	File input;
	HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	public Prob4(String filePath) {
		try {
			// create two arrays
			input = new File(filePath);
			scan = new Scanner(input);
			while (scan.hasNextLine()) {
				String[] array = scan.nextLine().split("\t");
				int vertex = Integer.parseInt(array[0]);
				Vertex a;
				if (!vertices.containsKey(vertex)) {
					a = new Vertex(vertex);
					vertices.put(vertex, a);
				} else {
					a = vertices.get(vertex);
				}
				
				for (int i = 1; i < array.length; i++) {
					int endPointVertex = Integer.parseInt(array[i]);
					Vertex b;
					if (!vertices.containsKey(endPointVertex)) {
						b = new Vertex(endPointVertex);
						vertices.put(endPointVertex, b);
					} else {
						b = vertices.get(endPointVertex);
					}
					if (a.value < b.value)
					{
						Edge e = new Edge(a, b);
						edges.add(e);
						a.addEdge(e);
						b.addEdge(e);
					}
			
				}
			}
			scan.close();

			
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public int randomContraction() {
		while (vertices.size() > 2) {
			edges.get((int) Math.floor(Math.random() * edges.size())).merge(vertices, edges);  //pick random edge
		}
		ArrayList<Edge> removeEdges = new ArrayList<Edge>();
		for (Edge e : edges) {
			if (e.selfLoop()) {
				removeEdges.add(e);
			}
		}
		for (Edge e : removeEdges) {
			edges.remove(e);
		}
		return edges.size();
		// System.out.println("Vertices: " + vertices);
		// System.out.println("Edges: " + edges);
		// System.out.println("Edge size: " + edges.size());
		
	}
	
	public void test(HashMap<Integer, Vertex> vertices, Integer n) {
		vertices.remove(n);
	}
}

class Vertex {
	public int value;
	ArrayList<Edge> edges = new ArrayList<Edge>();
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
	ArrayList<Vertex> vertices;
	public Edge(Vertex v1, Vertex v2) {
		this.vertices = new ArrayList<Vertex>();
		this.vertices.add(v1);
		this.vertices.add(v2);
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public void merge(HashMap<Integer, Vertex> vertices, ArrayList<Edge> edges) {
		
		// remove vertex and edge
		if (this.selfLoop()) {
			edges.remove(this);
			return;
		}
		if (!vertices.containsKey(this.v2.value)) {
			System.out.println("edge: " + this);
			System.out.println("FAIL");
			System.exit(0);
		}
		vertices.remove(this.v2.value);
		edges.remove(this);
		
		// merge edges
		// v2
		Iterator<Edge> iter = this.v2.edges.iterator();
		while (iter.hasNext()) {
			Edge e = iter.next();
			if(e != this) {
				e.replace(this.v2, this.v1);
				if (!e.selfLoop()) {
					this.v1.addEdge(e);
				}
			}
		}
		this.replace(this.v2, this.v1);
		// v1
		ArrayList<Edge> selfLoopEdges = new ArrayList<Edge>();
		for (Edge e : this.v1.edges) {
			e.replace(this.v2, this.v1);
			if (e.selfLoop()) {
				selfLoopEdges.add(e);
			}
		}
		for (Edge e : selfLoopEdges) {
			this.v1.edges.remove(e);
		}
	}
	
	public void replace(Vertex target, Vertex source) {
		if (this.vertices.contains(target)) {
			this.vertices.remove(target);
			this.vertices.add(source);
			if (this.v1 == target) {
				this.v1 = source;
			}
			if (this.v2 == target) {
				this.v2 = source;
			}
		}
	}
	
	public boolean selfLoop() {
		return this.v1.value == this.v2.value;
	}
	
	public String toString() {
		return "E: " + v1.value + " " + v2.value;
	}
}
