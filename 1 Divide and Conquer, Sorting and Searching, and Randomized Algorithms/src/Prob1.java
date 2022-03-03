
import java.math.BigInteger;

public class Prob1 {
	public String answer(BigInteger a, BigInteger b) {
		
		return a.multiply(b).toString();
	}
	
	public BigInteger Karatsuba(BigInteger x, BigInteger y) {
		int xLength = x.toString().length();
		int yLength = y.toString().length();
		if (xLength == 1 || yLength == 1) {
			return x.multiply(y);
		}
		int length = Math.max(xLength, yLength);
		int n = (int) Math.ceil(length/2);
		
		BigInteger a = new BigInteger(x.toString().substring(0, xLength - n));
		BigInteger b = new BigInteger(x.toString().substring(xLength - n));
		BigInteger c = new BigInteger(y.toString().substring(0, yLength - n));
		BigInteger d = new BigInteger(y.toString().substring(yLength - n));
		BigInteger ac = Karatsuba(a, c);
		//System.out.println(a.toString() + " x " + c.toString() + " = " + ac.toString());
		BigInteger bd = Karatsuba(b, d);
		//System.out.println(b.toString() + " x " + d.toString() + " = " + bd.toString());
		BigInteger mid = Karatsuba(a.add(b), c.add(d)).subtract(ac).subtract(bd);
		//System.out.println("ab: " + a.add(b) + " cd: " + c.add(d) + " kara: " + Karatsuba(a.add(b), c.add(d)) + " mid = " + mid.toString());
		return ac.multiply(BigInteger.valueOf((long) Math.pow(10, n*2))).add(mid.multiply(BigInteger.valueOf((long) Math.pow(10, n)))).add(bd);
	}

}
