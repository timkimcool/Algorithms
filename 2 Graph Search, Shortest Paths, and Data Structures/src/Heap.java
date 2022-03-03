
public class Heap {
	private Integer[] Heap;
	private int size;
	private int maxsize;
	private boolean isMin;
	
	// true == min; false == max
	public Heap(int maxsize, Boolean isMin) {
		this.maxsize = maxsize;
		this.size = 0;
		Heap = new Integer[this.maxsize + 1];
		this.isMin = isMin;
		if (isMin) {
			Heap[0] = Integer.MIN_VALUE;
		} else {
			Heap[0] = Integer.MAX_VALUE;
		}
	}
	public int getRoot() {
		return Heap[1];
	}
	
	public int getLastElement() {
		return Heap[size];
	}
	private int parent(int pos) {
		return pos / 2;
	}
	
	private int leftChild(int pos) { 
        return (2 * pos); 
    } 
    private int rightChild(int pos) { 
        return (2 * pos) + 1; 
    } 

    private boolean isLeaf(int pos) { 
        if (pos > (size / 2) && pos <= size) { 
            return true; 
        } 
        return false; 
    }
    
    public int getSize() {
    	return size;
    }
    
    private void minHeapify(int pos) { 
        if (isLeaf(pos)) 
            return; 

        if (Heap[pos] > Heap[leftChild(pos)] ||  
            Heap[pos] > Heap[rightChild(pos)]) { 
  
            if (Heap[leftChild(pos)] < Heap[rightChild(pos)]) { 
                swap(pos, leftChild(pos)); 
                minHeapify(leftChild(pos)); 
            } 
            else { 
                swap(pos, rightChild(pos)); 
                minHeapify(rightChild(pos)); 
            } 
        }
    } 
    
    private void maxHeapify(int pos) { 
        if (isLeaf(pos)) 
            return; 
        if (Heap[pos] < Heap[leftChild(pos)] ||  
            Heap[pos] < Heap[rightChild(pos)]) { 
  
            if (Heap[leftChild(pos)] > Heap[rightChild(pos)]) { 
                swap(pos, leftChild(pos)); 
                maxHeapify(leftChild(pos)); 
            } 
            else { 
                swap(pos, rightChild(pos)); 
                maxHeapify(rightChild(pos)); 
            } 
        }
    } 
    
    private void swap(int fpos, int spos) 
    { 
        Integer tmp; 
        tmp = Heap[fpos]; 
        Heap[fpos] = Heap[spos]; 
        Heap[spos] = tmp; 
    } 
    
    public void insert (Integer n) {
    	Heap[++size] = n;
    	
    	int current = size;
    	if (isMin) {
        	while(Heap[current] < Heap[parent(current)]) {
        		swap(current, parent(current));
        		current = parent(current);
        	}
    	} else {
        	while(Heap[current] > Heap[parent(current)]) {
        		swap(current, parent(current));
        		current = parent(current);
        	}
    	}

    }
    
    
    public Integer extractRoot() { 
        Integer popped = Heap[1]; 
        Heap[1] = Heap[size--];
        if (isMin) { minHeapify(1); } 
        else { maxHeapify(1); }
        return popped; 
    } 
    
    public void print() { 
    	String prt = "";
    	for (int i = 1; i <= size; i++) {
    		prt += Heap[i] + ", ";
    	}
    	System.out.println(prt);
    	/*
        for (int i = 1; i <= size / 2; i++) { 
            System.out.print(" PARENT: " + Heap[i] 
                             + " LEFT CHILD: " + Heap[2 * i] 
                             + " RIGHT CHILD:" + Heap[2 * i + 1]); 
            System.out.println(); 
        } 
        */
    } 
    
}
