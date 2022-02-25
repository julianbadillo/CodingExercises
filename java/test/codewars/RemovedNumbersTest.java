import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class RemovedNumbersTest {
  @Test
	public void test12() {
	  	long n = 26;
		List<long[]> res = new ArrayList<long[]>();
		res.add(new long[] {15, 21});
		res.add(new long[] {21, 15});
		List<long[]> a = RemovedNumbers.removNb(26);
		long sum = n*(n+1)/2;
		a.stream().forEach(ab->System.out.println(Arrays.toString(ab)));
		
		for(long[] ab: res)
			assertEquals(ab[0] * ab[1], sum - ab[0] - ab[1]);
		
		assertArrayEquals(res.get(0), a.get(0));
		assertArrayEquals(res.get(1), a.get(1));
	}
	@Test
	public void test14() {
		List<long[]> res = new ArrayList<long[]>();
		List<long[]> a = RemovedNumbers.removNb(100);
		assertTrue(res.size() == a.size());
	}
	
	@Test
	public void test15(){
		long n = 1000003;
		//long n = 101;
		long sum = n*(n+1)/2;
		List<long[]> res = RemovedNumbers.removNb(n);
		assertFalse(res.isEmpty());
		System.out.println(res.size());
		for(long[] ab: res)
			assertEquals(ab[0] * ab[1], sum - ab[0] - ab[1]);
	}
}
