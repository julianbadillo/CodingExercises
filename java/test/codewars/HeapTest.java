import static org.junit.Assert.*;

import org.junit.Test;

public class HeapTest {

	@Test
	public void testPushOnly1() {
		int[] a = {0,1,2,3,4,5,6,7,8,9};
		Heap h = new Heap();
		for (int i = 0; i < a.length; i++) {
			h.push(a[i]);
			assertEquals(0, h.peekMin());
		}
	}

	@Test
	public void testPushOnly2() {
		int[] a = {6,7,8,9,0,1,2,3,4,5,11};
		Heap h = new Heap();
		for (int i = 0; i < a.length; i++) {
			h.push(a[i]);
		}
		assertEquals(0, h.peekMin());
	}
	
	@Test
	public void testPushOnly3() {
		int[] a = {11,10,9,8,7,5,3,2,1,0};
		Heap h = new Heap();
		for (int i = 0; i < a.length; i++) {
			h.push(a[i]);
		}
		assertEquals(0, h.peekMin());
	}
	
	@Test
	public void testPop() {
		int[] a = {0,1,2,3,4,5,6,7,8,9};
		Heap h = new Heap();
		for (int i = 0; i < a.length; i++)
			h.push(a[i]);
		for (int i = 0; i < a.length; i++)
			assertEquals(i, h.pop());
	}
	
	@Test
	public void testPop2() {
		int[] a = {6,7,8,9,0,1,2,3,4,5};
		Heap h = new Heap();
		for (int i = 0; i < a.length; i++)
			h.push(a[i]);
		for (int i = 0; i < a.length; i++)
			assertEquals(i, h.pop());
	}
	
	@Test
	public void testPop4() {
		int[] a = {5,8,3,6,2,1,0,7,9,4};
		Heap h = new Heap();
		for (int i = 0; i < a.length; i++)
			h.push(a[i]);
		for (int i = 0; i < a.length; i++)
			assertEquals(i, h.pop());
	}
}
