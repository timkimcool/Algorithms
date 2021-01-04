import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Prob3 {

	public Prob3(String filePath) {
		try {
			File input = new File(filePath);
			Scanner scan = new Scanner(input);
			ArrayList<Integer> inputNum = new ArrayList<Integer>();
			while (scan.hasNextLine()) {
				inputNum.add(Integer.parseInt(scan.nextLine()));
			}
			
			Heap maxHeap = new Heap(inputNum.size(), false);
			Heap minHeap = new Heap(inputNum.size(), true);
			
			int sum = 0;
			int median = 0;
			for (Integer n : inputNum) {
				// add n
				if (n < median) {
					maxHeap.insert(n);
				} else { minHeap.insert(n); }
				
				// rebalance if needed
				if (maxHeap.getSize() - minHeap.getSize() > 1) {
					minHeap.insert(maxHeap.extractRoot());	
				} else if (minHeap.getSize() - maxHeap.getSize() > 1) {
					maxHeap.insert(minHeap.extractRoot());
				}
				
				// get median
				if (minHeap.getSize() > maxHeap.getSize()) {
					median = minHeap.getRoot();
				} else { median = maxHeap.getRoot(); }
				
				/*
 				System.out.println(n + "Median: " + median);
 				System.out.println("MIN");
 				minHeap.print();
 				System.out.println("MAX");
 				maxHeap.print();
 				*/
				sum += median;
			}
			System.out.println(sum % 10000);

		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
}
