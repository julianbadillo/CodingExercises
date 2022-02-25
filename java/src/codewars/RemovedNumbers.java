import java.util.LinkedList;
import java.util.List;

public class RemovedNumbers {
	public static List<long[]> removNb(long n) {
		long s = n * (n + 1) / 2, b;
		LinkedList<long[]> list = new LinkedList<>();
		for (long a = n / 2; a <= n; a++)
			if ((s - a) % (a + 1) == 0 && (b = (s - a) / (a + 1)) <= n)
				list.add(new long[] { a, b });

		return list;
	}
}