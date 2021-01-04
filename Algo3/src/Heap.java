
public class Heap {
	private Job[] Heap;
	private int size;
	private int maxsize;
	private boolean isMin;
	
	// true == min; false == max
	public Heap(int maxsize, Boolean isMin) {
		this.maxsize = maxsize;
		this.size = 0;
		Heap = new Job[this.maxsize + 1];
		this.isMin = isMin;
		if (isMin) {
			Heap[0] = new Job(0, Integer.MAX_VALUE);
		} else {
			Heap[0] = new Job(0, Integer.MIN_VALUE);
		}
	}
	public Job getRoot() {
		return Heap[1];
	}
	
	public Job getLastElement() {
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

        if (Heap[pos].isGreater(Heap[leftChild(pos)]) ||  
            Heap[pos].isGreater(Heap[rightChild(pos)])) { 
  
            if (Heap[rightChild(pos)].isGreater(Heap[leftChild(pos)])) { 
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
        if (!Heap[pos].isGreater(Heap[leftChild(pos)]) ||  
            !Heap[pos].isGreater(Heap[rightChild(pos)])) { 
  
            if (Heap[leftChild(pos)].isGreater(Heap[rightChild(pos)])) { 
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
        Job tmp; 
        tmp = Heap[fpos]; 
        Heap[fpos] = Heap[spos]; 
        Heap[spos] = tmp; 
    } 
    
    public void insert (Job n) {
    	Heap[++size] = n;
    	
    	int current = size;
    	if (isMin) {
        	while(!Heap[current].isGreater(Heap[parent(current)])) {
        		swap(current, parent(current));
        		current = parent(current);
        	}
    	} else {
        	while(Heap[current].isGreater(Heap[parent(current)])) {
        		swap(current, parent(current));
        		current = parent(current);
        	}
    	}

    }
    
    
    public Job extractRoot() { 
        Job popped = Heap[1]; 
        Heap[1] = Heap[size--];
        if (isMin) { minHeapify(1); } 
        else { maxHeapify(1); }
        return popped; 
    } 
    
    public void print() { 
    	/*
    	String prt = "";
    	for (int i = 1; i <= size; i++) {
    		prt += Heap[i] + ", ";
    	}
    	System.out.println(prt);
    	*/
    	asd
        for (int i = 1; i <= size / 2; i++) { 
        	String str = "";
        	if (2 * i + 1 <= size) str += " PARENT: " + Heap[i]; 
    		if (2 * i + 1 <= size) str += " LEFT CHILD: " + Heap[2 * i];
    		if (2 * i + 1 <= size) str += " RIGHT CHILD:" + Heap[2 * i + 1];
            System.out.print(str); 
            System.out.println(); 
        } 
        
    } 
    
}
