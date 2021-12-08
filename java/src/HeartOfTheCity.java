import java.math.BigInteger;
import java.util.Scanner;
import java.util.TreeSet;

import static java.math.BigInteger.*;
public class HeartOfTheCity {

	/**
	 * Killer case
	 * 10000 - 60803664
	 * 100000
	 * 999999
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		long n = in.nextInt();
		HeartOfTheCity h = new HeartOfTheCity();
		
		System.out.println(h.solve(n));
		in.close();
	}
	
	
	public BigInteger solve(long n){
		// I only need one quadrant, everything else by 4
		// n is odd, then I care about  (n -1) / 2
		n /= 2;
		// I can see it if there is no building in front -> cannot be simplified
		// symmertric
		// lets do brute force
		// generate all i / j where j > i (n^2)
		
		
		/*BigInteger buildings = BigInteger.valueOf(2*n-2);
		for (long i = 2; i <= n; i++) {
			// count all the relative primes of i that are smaller == count all the relative primes that are larger
			// optimization - if its prime -> add i
			for (long j = 2; j < i-1; j++) {
				if(gcd(i,j) == 1)
					buildings = buildings.add(BigInteger.ONE);
			}
		}*/
		
		BigInteger buildings = ZERO;
		for (long i = 1; i <= n; i++) {
			buildings = buildings.add(valueOf(relPrimes(i, n)));
		}
	
		buildings = buildings.multiply(valueOf(8));
		return buildings;
	}
	
	public long relPrimes(long i, long n){
		long t = 0;
		for (long j = 1; j <= i; j++) {
			if(gcd(i, j) == 1)
				t++;
		}
		return t;
	}
	
	public long relPrimes2(long i, long n){
		// break i in its prime factors
		TreeSet<Long> l = new TreeSet<>();
		long i2 = i;
		long j = 2;
		while(i2 > 1){
			while(i2 % j == 0)
			{
				l.add(j);
				i2 /= j;
			}
			j++;
		}
		long t = i+1;
		for(long k : l)
			t -= (i/k);
		return t;
	}
	
	static long gcd(long a, long b){
		if(a % b == 0)
			return b;
		if(b % a == 0)
			return a;
		
		if(a == 1 || b == 1)
			return 1;
		if(a > b)
			return gcd(a%b, b);
		return gcd(b%a, a);
	}

}

class Solution{
	public static void main(String[] args) {
		HeartOfTheCity.main(args);
	}
}