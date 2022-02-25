import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class WeirdPrimeGen {
	
		
		public static long countOnes(long n) {
	 		int i = 2;
	 		long an_1 = 7, gn;
	 		long ones = 2;
	 		if(n < 2) return 1;
	 		while(i < n){
	 			long an = an_1 + gcd(i, an_1);
	 			gn = an - an_1;
	 			if(gn == 1)
	 				ones++;
				an_1 = an;
				i++;
	 		}
	 		return ones;
	    }
	 	
	 	public static List<Long> P(long n){
	 		TreeSet<Long> P = new TreeSet<>();
	 		int i = 2;
	 		long an_1 = 7, gn;
	 		
	 		while(P.size() < n){
	 			long an = an_1 + gcd(i, an_1);
	 			gn = an - an_1;
	 			if(gn == 1)
	 				;
	 			else
	 				P.add(gn);
				an_1 = an;
				i++;
	 		}
	 		
	 		return new LinkedList<Long>(P);
	 	}
	 	
	    public static long maxPn(long n) {
	    	List<Long> P = P(n);
		 	return P.stream().max(Long::compareTo).get();
	    }
	    
	    public static List<Long> anOver(long n){
	    	LinkedList<Long> anOver = new LinkedList<>();
	 		TreeSet<Long> P = new TreeSet<>();
	 		int i = 2;
	 		long an_1 = 7, gn;
	 		
	 		while(P.size() < n){
	 			long an = an_1 + gcd(i, an_1);
	 			gn = an - an_1;
	 			if(gn == 1)
	 				;
	 			else{
	 				P.add(gn);
	 				anOver.add(an % i);
	 			}
				an_1 = an;
				i++;
	 		}
	 		
	 		return anOver;
	    }
	    
	    public static int anOverAverage(long n) {
	        // your code
	    	return 3;
	    }
	    
	    
	    static long gcd(long a, long b){
	    	if(a == 0 || b == 0) return 0;
	    	if(a == 1 || b == 1) return 1;
	    	long t;
	    	while(a % b != 0){
	    		if(a > b)
	    			a %= b;
	    		else{
	    			t = a;
	    			a = b;
	    			b = t;
	    		}		
	    	}
	    	return b;
	    }
}
