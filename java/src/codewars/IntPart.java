import static java.util.Arrays.fill;
import static java.util.stream.IntStream.of;
import static java.lang.String.format;
import static java.math.BigInteger.valueOf;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class IntPart {

	public static String part(long n) {
		List<Integer> list = allPartitions((int)n);		
		
		int range = list.get(list.size()-1) - 1;
		int sum = list.stream().mapToInt(i->i).sum();
		float avg = (float)sum / list.size();
		float median = list.get(list.size()/2);
		if(list.size() % 2 == 0){
			median += list.get(list.size()/2 - 1);
			median /= 2;
		}
		//System.out.println(list);
		return format("Range: %d Average: %.2f Median: %.2f", range, avg, median);
	}
	
	static boolean isPrime(int i){
		return valueOf(i).isProbablePrime(100);
	}

	public static List<Integer> allPartitions(int n) {
		// generate all partitions and keep max
		int[] part;
		TreeSet<Integer> set = new TreeSet<>();
		int prod;
		set.add(1);
		set.add(n);
		for (int p = 2; p < n; p++) {
			part = new int[p];
			// 1's in everything but the first
			fill(part, 1);
			part[0] = n - p + 1;
			
			while (true) {
				prod = of(part).reduce(1, (i, j) -> i * j);
				set.add(prod);
				//System.out.println(Arrays.toString(part));
				// move from left to right
				// fin the leftmost position in which part[i] > part[i+1]+1
				int i, k = 1;
				while (k < p) {
					for (i = 0; i < p - k && part[i] <= part[i + k] + 1; i++);
					if (i < p - k) {
						part[i]--;
						part[i + k]++;
						break;
					}
					k++;
				}
				if(k == p)
					break;
			}
		}
		return new ArrayList<Integer>(set);
	}
}
