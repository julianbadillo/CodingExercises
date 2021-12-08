import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryShiftedSearchTest {

	@Test
	public void testBinarySearchNotFound() {
		BinaryShiftedSearch bs = new BinaryShiftedSearch();
		int[] arr = {1,2,3,4,5};
		assertEquals(-1, bs.findIndex(arr, 10));
	}
	
	@Test
	public void testBinarySearchNotFound2() {
		BinaryShiftedSearch bs = new BinaryShiftedSearch();
		int[] arr = {1,2,3,4,5};
		assertEquals(-1, bs.findIndex(arr, -3));
	}
	
	@Test
	public void testBinarySearchNotFound3() {
		BinaryShiftedSearch bs = new BinaryShiftedSearch();
		int[] arr = {1,2,4,5};
		assertEquals(-1, bs.findIndex(arr, 3));
	}
	

	@Test
	public void testBinarySearchFound1() {
		BinaryShiftedSearch bs = new BinaryShiftedSearch();
		int[] arr = {0,1,2,3,4,5};
		for (int i = 0; i < arr.length; i++) 
			assertEquals(i, bs.findIndex(arr, i));
	}
	
	@Test
	public void testBinarySearchFound2() {
		BinaryShiftedSearch bs = new BinaryShiftedSearch();
		int[] arr = {0,1,2,3,4,5, 6};
		for (int i = 0; i < arr.length; i++) 
			assertEquals(i, bs.findIndex(arr, i));
	}
	
	
	@Test
	public void testBinarySearchFoundShifted1() {
		BinaryShiftedSearch bs = new BinaryShiftedSearch();
		int[] arr = {7,8,9,0,1,2,3,4,5,6};
		for (int i = 0; i < arr.length; i++) 
			assertEquals( (i+3)%arr.length, bs.findIndex(arr, i));
	}

	@Test
	public void testBinarySearchFoundShifted2() {
		BinaryShiftedSearch bs = new BinaryShiftedSearch();
		int[] arr = {7,8,9,10,0,1,2,3,4,5,6};
		for (int i = 0; i < arr.length; i++) 
			assertEquals( (i+4)%arr.length, bs.findIndex(arr, i));
	}
}
