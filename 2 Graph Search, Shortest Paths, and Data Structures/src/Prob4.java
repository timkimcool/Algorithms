import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Prob4 {
	// Map<Integer, List<Long>> hash;
	Map<Long, Long> hash;
	
	public Prob4(String filePath) {
		try {
			File input = new File(filePath);
			Scanner scan = new Scanner(input);
			//hash = new HashMap<Integer, List<Long>>();
			hash = new HashMap<Long, Long>();
			while (scan.hasNextLine()) {
				long num = Long.parseLong(scan.nextLine());
				if (hash.containsKey(num)) {
					hash.put(num, hash.get(num) + 1);
				}
				hash.put(num, (long) 1);
			}
			scan.close();
			System.out.println("DONE");
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public Integer twoSum() {
		long startTime = System.currentTimeMillis();
		List<Long> answers = new ArrayList<Long>();
		for (long i = -10000; i <= 10000; i++) {
			if (addNum(i)) answers.add(i);
			if (i % 500 == 0) {
				System.out.println(i + ": " + (System.currentTimeMillis() - startTime)/1000 + "seconds");
			}
		}
		return answers.size();
	}
	
	public Boolean addNum(long i) {
		for (long num : hash.keySet()) {
			long diff = i - num;
			if (!hash.containsKey(diff) || (num == diff && hash.get(num) == 1)) continue;
			// System.out.println(i + "|" + num + "|" + diff);
			return true;
		}
		return false;
	}
	
	public Integer hashFunction(Long num) {
		long prime = 1000003;
		return (int) (num % prime); 
	}
}