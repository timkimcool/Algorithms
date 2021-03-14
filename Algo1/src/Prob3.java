

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prob3 {
	private Scanner scan;
	private File file;
	private long count = 0;
	
	public Prob3(String filePath) throws FileNotFoundException {
		file = new File(filePath);
		scan = new Scanner(file);
	}
	
	
	public String nextLine() {
		return scan.nextLine();
	}
	
	public boolean hasNextLine() {
		return scan.hasNextLine();
	}
	
	public List<Integer> quickSort(List<Integer> a) {
		if (a.size() == 1) {
			return a;
		}
		
		int pivot = choosePivot(a);
		if (pivot != 0) {
			int temp = a.get(0);
			a.set(0, a.get(pivot));
			a.set(pivot, temp);
		}
		a = this.partition(a, 0, a.size());
		ArrayList<Integer> retArray = new ArrayList<Integer>();
		int pivotLoc = a.remove(a.size() - 1);
		if (a.subList(0, pivotLoc).size() > 0) {
			retArray.addAll(this.quickSort(a.subList(0, pivotLoc)));
		}
		retArray.add(a.get(pivotLoc));
		if (a.subList(pivotLoc + 1, a.size()).size() > 0) {
			retArray.addAll(this.quickSort(a.subList(pivotLoc + 1, a.size())));
		}
		
		return retArray;
	}
	
	
	public Integer choosePivot(List<Integer> a) {
		int first = a.get(0);
		int second = a.get((int) Math.floor((a.size() - 1)/2));
		int third = a.get(a.size() - 1);
		if (((first > second) && (first < third)) || ((first < second) && (first > third))) {
			return 0;
		} else if (((second > first) && (second < third)) || ((second < first) && (second > third))) {
			return (int) Math.floor((a.size() - 1)/2);
		} else {
			return a.size() - 1;
		}
		// return 0;
		// return a.size() - 1;
	}
	
	public List<Integer> partition(List<Integer> a, int start, int end) {
		int pivot = a.get(start);
		int i = start + 1;
		for (int j = start + 1; j < start + end; j++) {
			if (a.get(j) < pivot) {
				int temp = a.get(j);
				a.set(j, a.get(i));
				a.set(i, temp);
				i++;
			}
		}
		int temp = a.get(i - 1);
		a.set(i - 1, pivot);
		a.set(0, temp);
		this.count = this.count + a.size() - 1;
		
		a.add(i - 1);
		return a;
	}
	
	public Long getCount() {
		return this.count;
	}
	
	
}
