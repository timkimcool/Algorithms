import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Prob3 {
	public static void main(String[] args) {
		Prob3 prob3 = new Prob3();
		String indices = "1,2,3,4,17,117,517,997";
		System.out.println("Part 1: " + prob3.getBiggestCode());
		System.out.println("Part 2: " + prob3.getSmallestCode());
		System.out.println("Part 3: " + prob3.isIndexIncluded(indices));
	}
	List<Node> nodes = new ArrayList<>();
	Node treeHead;
	
	Map<Integer, DynamicStore> memo = new HashMap<>();
	List<Long> weights = new ArrayList<>();
	List<Integer> solutionIndices = new ArrayList<>();
	
	
	public Prob3() {
		try {
			// Part 1 and 2
			File inputA = new File("src/3_inputa.txt");
			Scanner scanA = new Scanner(inputA);
			scanA.nextLine();
			while (scanA.hasNextLine()) {
				String nextLine = scanA.nextLine();
				nodes.add(new Node(Integer.parseInt(nextLine), 0));
			}
			nodes.sort(Comparator.comparing(Node::getWeight));
			treeHead = createHoffmanCode();
			scanA.close();
			
			// Part 3
			File inputB = new File("src/3_inputb.txt");
			Scanner scanB = new Scanner(inputB);
			int index = Integer.parseInt(scanB.nextLine());
			weights.add((long) 0);
			while (scanB.hasNextLine()) {
				String nextLine = scanB.nextLine();
				weights.add(Long.parseLong(nextLine));
			}
			scanB.close();
			initializeArray();
			populateMap(index);
			solutionIndices = createSolutionIndexArray(index);			
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	private Node createHoffmanCode() {
		while (nodes.size() > 1) {
			Node n1 = nodes.remove(0);
			Node n2 = nodes.remove(0);
			int depth = n1.depth > n2.depth ? n1.depth : n2.depth;
			depth++;
			nodes.add(new Node(n1, n2, n1.weight + n2.weight, depth));
			nodes.sort(Comparator.comparing(Node::getWeight));
		}
		return nodes.get(0);
	}
	
	public int getBiggestCode() {
		return treeHead.depth;
	}
	
	public int getSmallestCode() {
		List<Node> searchNodes = new ArrayList<>();
		searchNodes.add(treeHead);
		int smallest = -1;
		int count = 0;
		while (smallest == -1) {
			int size = searchNodes.size();
			for (int i = 0; i < size; i++) {
				Node n = searchNodes.remove(0);
				if (n.depth == 0 && smallest == -1) return count;
				if (n.right != null) searchNodes.add(n.right);
				if (n.left != null) searchNodes.add(n.left);
			}
			count++;
		}
		return smallest;
	}
	
	private void initializeArray() {
		memo.put(0, new DynamicStore(0, weights.get(0)));
		memo.put(1, new DynamicStore(1, weights.get(1)));
	}
	
	private void populateMap(int index) {
		DynamicStore tmpDS;
		for(int i = 2; i <= index; i++) {
			long weight1 = memo.get(i - 1).weight;
			long weight2 = memo.get(i - 2).weight + weights.get(i);
			if (weight1 == weight2) {
				tmpDS = new DynamicStore(i, weight1, memo.get(i - 1));
				tmpDS.addPointer(memo.get(i - 2));
			} else if(weight1 > weight2) {
				tmpDS = new DynamicStore(i, weight1, memo.get(i - 1));
			} else {
				tmpDS = new DynamicStore(i, weight2, memo.get(i - 2));
			}
			memo.put(i, tmpDS);	
		}
	}
	
	private List<Integer> createSolutionIndexArray(int index) {
		List<Integer> indices = new ArrayList<>();
		DynamicStore solDS = memo.get(index);
		populateIndices(solDS.pointers, indices);
		return indices;
	}
	
	private void populateIndices(List<DynamicStore> pointers, List<Integer> indices) {
		for(DynamicStore pointer : pointers) {
			indices.add(pointer.index);
			populateIndices(pointer.pointers, indices);
		}
	}
	
	public String isIndexIncluded(String indices) {
		String[] indexArray = indices.split(",");
		String retStr = "";
		for (int i = 0; i < indexArray.length; i++) {
			int index = Integer.parseInt(indexArray[i]);
			retStr += solutionIndices.contains(index) ? 1 : 0;
		}
		return retStr;
	}
	
	class Node{
		int weight;
		Node right;
		Node left;
		int depth;

		
		public Node(int weight, int depth) {
			this.weight = weight;
			this.right = null;
			this.left = null;
			this.depth = depth;
		}
		
		public Node(Node right, Node left, int weight, int depth) {
			this.weight = weight;
			this.right = right;
			this.left = left;
			this.depth = depth;
		}
		
		public int getWeight() {
			return this.weight;
		}
		public int compareTo(Node n1) {
			return n1.weight - this.weight;
		}
	}
	
	class DynamicStore {
		int index;
		List<DynamicStore> pointers = new ArrayList<>();
		long weight;
		
		public DynamicStore(int index, long weight) {
			this.index = index;
			this.weight = weight;
		}
		
		public DynamicStore(int index, long weight, DynamicStore pointer) {
			this.index = index;
			this.weight = weight;
			pointers.add(pointer);
		}
		
		public void addPointer(DynamicStore pointer) {
			pointers.add(pointer);
		}
		
		public String toString() {
			return index + "|" + weight;
		}
	}
}
