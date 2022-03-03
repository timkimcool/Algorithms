
public class MinHeap {
	private Vertex[] Heap;
	private int size;
	private int maxsize;
	
	public MinHeap(int maxsize) {
		this.maxsize = maxsize;
		this.size = 0;
		Heap = new Vertex[this.maxsize + 1];
		Heap[0] = new Vertex(0);
		Heap[0].weight = 0;
		for (int i = 1; i < Heap.length; i++) {
			Heap[i] = new Vertex(999999, 999999);
		}
	}
	
	private int parent(int pos) {
		return pos / 2;
	}
	
	private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
    private int rightChild(int pos) 
    { 
        return (2 * pos) + 1; 
    } 

    private boolean isLeaf(int pos) 
    { 
        if (pos >= (size / 2) && pos <= size && size != 2) { 
            return true; 
        } 
        return false; 
    }
    
    private void minHeapify(int pos) 
    { 
    	try {
        if (isLeaf(pos)) 
            return; 

        if (Heap[pos].weight > Heap[leftChild(pos)].weight ||  
            Heap[pos].weight > Heap[rightChild(pos)].weight) { 
  
            if (Heap[leftChild(pos)].weight < Heap[rightChild(pos)].weight) { 
                swap(pos, leftChild(pos)); 
                minHeapify(leftChild(pos)); 
            } 
            else { 
                swap(pos, rightChild(pos)); 
                minHeapify(rightChild(pos)); 
            } 
        }
    	} catch (ArrayIndexOutOfBoundsException  e){
    		System.out.println(pos + "|" + Heap.length);
    		System.exit(1);
    	}
    } 
    
    private void swap(int fpos, int spos) 
    { 
        Vertex tmp; 
        tmp = Heap[fpos]; 
        Heap[fpos] = Heap[spos]; 
        Heap[spos] = tmp; 
    } 
    
    public void insert (Vertex v) {
    	Heap[++size] = v;
    	
    	int current = size;
    	while(Heap[current].weight < Heap[parent(current)].weight) {
    		swap(current, parent(current));
    		current = parent(current);
    	}
    }
    
    public void replaceNode(int i, int comp) {
    	if (Heap[i].weight < comp) return;
    	Heap[i].weight = comp;
    	
    	int current = i;
    	while(Heap[current].weight < Heap[parent(current)].weight) {
    		swap(current, parent(current));
    		current = parent(current);
    	}
    }
    
    public int findNode(int vValue) {
    	for (int i = 1; i < Heap.length; i++) {
    		if (Heap[i].value == vValue) { 
    			return i;
    		}
    	}
    	return -1;
    }
    
    public Vertex extractMin() { 
        Vertex popped = Heap[1]; 
        Heap[1] = Heap[size--]; 
        minHeapify(1); 
        return popped; 
    } 
    
    public void print() { 
        for (int i = 1; i <= size / 2; i++) { 
            if (i <= size) System.out.print(" PARENT: " + Heap[i].value + " (" + Heap[i].weight + ")");
    		if (2 * i <= size) System.out.print(" LEFT CHILD: " + Heap[2 * i].value  + " (" + Heap[2 * i].weight + ")");
			if (2 * i + 1 <= size) System.out.print(" RIGHT CHILD: " + Heap[2 * i + 1].value + " (" + Heap[2 * i + 1].weight + ")"); 
            System.out.println(); 
        } 
    } 
    
}
