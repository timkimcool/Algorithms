import java.util.ArrayList;
import java.util.List;

public class Graph {

}
//Vertex with list of edges
class Vertex {
	public int value;
	public boolean explored;
	public int fTime; // finishing time of vertex
	public int weight;
	public int shortVertex;
	List<Edge> edges = new ArrayList<Edge>();
	public Vertex(int vertex) {
		this.value = vertex;
		this.explored = false;
	}
	
	public Vertex(int vertex, int weight) {
		this.value = vertex;
		this.weight = weight;
		this.explored = false;
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
	
	public String toString() {
		return "Vertex value: " + this.value;
	}
	
	public void explored() {
		this.explored = true;
	}
}

//Edge with the end point vertices
class Edge {
	Vertex v1;	// source
	Vertex v2;	// target
	Boolean rev;
	int weight;
	List<Vertex> vertices;
	public Edge(Vertex v1, Vertex v2) {
		this.vertices = new ArrayList<Vertex>();
		this.vertices.add(v1);
		this.vertices.add(v2);
		this.v1 = v1;
		this.v2 = v2;
		rev = false;
		this.weight = 0;
	}
	
	public Edge(Vertex v1, Vertex v2, int weight) {
		this.vertices = new ArrayList<Vertex>();
		this.vertices.add(v1);
		this.vertices.add(v2);
		this.v1 = v1;
		this.v2 = v2;
		rev = false;
		this.weight = weight;
	}
	
	public Vertex source() {
		return !rev ? v1 : v2;
	}
	
	public Vertex target() {
		return rev ? v1 : v2;
	}
	
	public void rev() {
		rev = !rev;
	}
	
	public String toString() {
		return this.source().value + " - " + this.target().value + "(" + this.weight + ")";
	}
}