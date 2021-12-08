import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.junit.Test;

public class PriorityQueueTest {

	@Test
	public void test() {
		PriorityQueue<Integer> queue = new PriorityQueue<>((tx1, tx2) -> tx1 > tx2 ? -1 : (tx1 < tx2 ? 1 : 0));
		for (int i = 0; i < 10; i++)
			queue.add(i);
		for (int i = 0; i < 10; i++)
			assertEquals(9 - i, (int)queue.remove());
	}

	
	@Test
	public void testName() throws Exception {
		HashMap<String, Integer> map = new HashMap<>();
		
		map.put("A", 1);
		assertTrue(map.containsKey("A"));
		map.remove("A");
		assertFalse(map.containsKey("A"));
		assertNull(map.get("A"));
		
	}
}
