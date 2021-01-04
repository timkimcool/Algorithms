import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Prob1 {
	Heap heap;
	public Prob1(String filePath) {
		try {
			File input = new File(filePath);
			Scanner scan = new Scanner(input);
			heap = new Heap(Integer.parseInt(scan.nextLine()), false); // remove number of jobs
			while (scan.hasNextLine()) {
				String nextLine = scan.nextLine();
				String[] jobParam = nextLine.split(" ", 2);
				heap.insert(new Job(Integer.parseInt(jobParam[0]), Integer.parseInt(jobParam[1])));
			}
			scan.close();
			System.out.println("DONE");
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		heap.print();
	}
}

class Job {
	int weight;
	int length;
	
	public Job(Integer weight, Integer length) {
		this.weight = weight;
		this.length = length;
	}
	
	public int getDiff() {
		return this.weight - this.length;
	}
	
	public boolean isGreater(Job j) {
		if(this.getDiff() > j.getDiff()) {
			return true;
		} else if (this.getDiff() == j.getDiff() && this.weight > j.weight) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "diff|w|l: " + getDiff() + "|" + this.weight + "|" + this.length;
	}
}