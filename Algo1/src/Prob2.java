

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prob2 {
	private Scanner scan;
	private File file;
	private long inversion;
	
	public Prob2(String filePath) throws FileNotFoundException {
		file = new File(filePath);
		scan = new Scanner(file);
	}
	
	public String nextLine() {
		return scan.nextLine();
	}
	
	public boolean hasNextLine() {
		return scan.hasNextLine();
	}
	
	public List<Integer> sort(List<Integer> a) {
		if (a.size() == 1) {
			return a;
		} else {
			return this.merge(this.sort(a.subList(0, a.size()/2)), this.sort(a.subList(a.size()/2, a.size())));
		}
	}
	
	public List<Integer> merge(List<Integer> a, List<Integer> b) {
		if (a.isEmpty()) {
			return b;
		} else if (b.isEmpty()) {
			return a;
		}
		ArrayList<Integer> mergedArray = new ArrayList<Integer>();
		int i = 0, j = 0;
		while (true) {
			if (a.get(i) > b.get(j)) {
				mergedArray.add(b.get(j));
				this.inversion = this.inversion + a.size() - i;
				j++;
				if (j == (b.size())) {
					mergedArray.addAll(a.subList(i, a.size()));
					break;
				}
			} else {
				mergedArray.add(a.get(i));
				i++;
				if (i == (a.size())) {
					mergedArray.addAll(b.subList(j, b.size()));
					break;
				}
			}
		}
		return mergedArray;
	}
	
	public Long getInversions() {
		return this.inversion;
	}
}
