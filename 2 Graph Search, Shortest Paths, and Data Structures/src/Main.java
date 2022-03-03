public class Main {
	public static void main(String[] args) {
		/*
		// Algo 1
		String file = "src/1_input.txt";
		Prob1 prob1 = new Prob1(file);
		System.out.println("WOKRING: " + prob1.findSCC());
		*/
	
		
		// Algo 2
		String file = "src/2_input.txt";
		Prob2 prob2 = new Prob2(file);
		int[] result = new int[] { 7,37,59,82,99,115,133,165,188,197 };
		String prt = "";
		for (int i = 0; i < result.length; i++) {
			prt += "," + prob2.Dijkstra(1, result[i]);
		}
		System.out.println(prt.substring(1));
		
		/*
		// Algo 3
		String file = "src/3_input.txt";
		Prob3 prob3 = new Prob3(file);
		*/
		
		/*		
		String file = "src/4_input.txt";
		Prob4 prob4 = new Prob4(file);
		System.out.println(prob4.twoSum());
		*/
	}
	
}
