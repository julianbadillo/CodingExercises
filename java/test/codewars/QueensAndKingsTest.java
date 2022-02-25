import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

public class QueensAndKingsTest {

	
	@Test
	public void testIsOptimalMatch1A() {
		//A: YXZ   B: ZYX   C: XZY   X: BAC   Y: CBA   Z: ACB
		//	0: 102   1: 210   2: 021   0: 102   1: 210   2: 021
		int[][] queens = {{1,0,2},
						{2,1,0},
						{0,2,1}};
		int[][] kings = {{1,0,2},
						{2,1,0},
						{0,2,1}};
		// priority to queens
		int[] match = new int[]{1,2,0};
		assertTrue(isOptimalMatch(queens, kings, match));
		// all get their second choice
		match = new int[]{0,1,2};
		assertTrue(isOptimalMatch(queens, kings, match));
		// priority to kings
		match = new int[]{2,0,1};
		assertTrue(isOptimalMatch(queens, kings, match));
			
	}
	@Test
	public void testIsOptimalMatch2(){
		int[][] queens = {{1, 0, 2, 3},//q0 -> k1 OK
						{2, 3, 0, 1},//q1 -> k2 OK
						{1, 0, 2, 3},//q2 -> k0 Could be with k1, but he prefers q0
						{1, 2, 0, 3},};//q3 -> k3 Could be with k1, but he prefers q0
										//		Could be with k2, but he prefers q1
										//		could be with k0 but he prefers q2
				
		int[][] kings = {{1, 2, 3, 0},//k0 -> q2
						{0, 3, 2, 1},//k1 -> q0 OK
						{0, 1, 3, 2},//k2 -> q1 Could be with q0, but q0 prefers k1 
						{0, 1, 2, 3},};//k3 -> q3 could be with q0, but she prefers k1
										//		could be with q1, but she prefers k2
										// 		could be with q2, but she prefers k0

		int match[] = {1, 2, 0, 3};
		assertTrue(isOptimalMatch(queens, kings, match));
		
	}
	
	@Test
	public void testIsOptimalMatchNegative() {
		//A: YXZ   B: ZYX   C: XZY   X: BAC   Y: CBA   Z: ACB
		//	0: 102   1: 210   2: 021   0: 102   1: 210   2: 021
		int[][] queens = {{1,0,2},
						{2,1,0},
						{0,2,1}};
		int[][] kings = {{1,0,2},
						{2,1,0},
						{0,2,1}};
		// 
		int[] match = new int[]{0,2,1};
		assertFalse(isOptimalMatch(queens, kings, match));
		
		match = new int[]{2,1,0};
		assertFalse(isOptimalMatch(queens, kings, match));
	}
	
	@Test
	public void testBestMatchSingleSolution() {
		int[][] queens = {{0,1,2},
						{1,2,0},
						{2,0,1}};
		int[][] kings = {{0,1,2},
						{1,2,0},
						{2,0,1}};
		int[] match = QueensAndKings.bestMatch(queens, kings);
		
		// only one solution
		assertArrayEquals(match, new int[]{0,1,2});
		assertTrue(isOptimalMatch(queens, kings, match));
	}
	
	@Test
	public void testBestMatchMultipleSolution() {
		//	0: 102   1: 210   2: 021
		//	0: 102   1: 210   2: 021
		int[][] queens = {{1,0,2},
						{2,1,0},
						{0,2,1}};
		int[][] kings = {{1,0,2},
						{2,1,0},
						{0,2,1}};
		
		int[] match = QueensAndKings.bestMatch(queens, kings);
		assertTrue(Arrays.equals(match, new int[] { 1, 2, 0 }) 
				|| Arrays.equals(match, new int[] { 2, 0, 1 })
				|| Arrays.equals(match, new int[] { 0, 1, 2 }));
		assertTrue(isOptimalMatch(queens, kings, match));
	}
	
	
	
	@Test
	public void testBestMatch4x4() {
		int[][] queens = {{0,1,2,3},
						{1,2,0,3},
						{1,3,0,2},
						{0,1,2,3}};
		
		int[][] kings = {{0,1,2,3},
						{1,2,0,3},
						{0,2,1,3},
						{0,1,3,2}};
		
		int[] match = QueensAndKings.bestMatch(queens, kings);
		assertTrue(isOptimalMatch(queens, kings, match));
	}
	
	
	
	@Test
	public void testBestMatch5x5() {
		int[][] queens = {{0,4,5,1,2,3},
						{1,5,2,4,0,3},
						{1,2,0,4,5,3},
						{0,1,2,3,5,4},
						{4,5,3,2,1,0},
						{5,3,4,1,0,2}};
		
		int[][] kings = {{0,4,5,1,2,3},
						{1,5,2,4,0,3},
						{1,2,0,4,5,3},
						{0,1,2,3,5,4},
						{4,5,3,2,1,0},
						{5,3,4,1,0,2}};
		
		int[] match = QueensAndKings.bestMatch(queens, kings);
		assertTrue(isOptimalMatch(queens, kings, match));
	}
	
	
	@Test
	public void testBestMatch100SingleSolution() {
		int n = 100;
		// Only one possible match
		int[][] queens = new int[n][n];
		for (int i = 0; i < n; i++) 
			queens[i] = IntStream.range(i, n+i).map(k-> k % n).toArray();
		int[][] kings = new int[n][n];
		for (int i = 0; i < n; i++) 
			kings[i] = IntStream.range(i, n+i).map(k -> k % n).toArray();
		int[] match = QueensAndKings.bestMatch(queens, kings);
		assertArrayEquals(match, IntStream.range(0, n).toArray());
		assertTrue(isOptimalMatch(queens, kings, match));
	}
	
	
	@Test
	public void testBestMatchBigSingleSolution() {
		int n = 3000;
		// stress test, Only one possible match
		int[][] queens = new int[n][n];
		for (int i = 0; i < n; i++) 
			queens[i] = IntStream.range(0, n).toArray();
		int[][] kings = new int[n][n];
		for (int i = 0; i < n; i++) 
			kings[i] = IntStream.range(0, n).toArray();
		int[] match = QueensAndKings.bestMatch(queens, kings);
		assertArrayEquals(match, IntStream.range(0, n).toArray());
		assertTrue(isOptimalMatch(queens, kings, match));
	}
	
	@Test
	public void testBestMatchBigRandom() {
		for (int test = 0; test < 10; test++) {
			int n = 100;
			int[][] queens = new int[n][n];
			for (int i = 0; i < n; i++) {
				queens[i] = IntStream.range(0, n).toArray();
				randomPermutation(queens[i]);
			}

			int[][] kings = new int[n][n];
			for (int i = 0; i < n; i++) {
				kings[i] = IntStream.range(0, n).toArray();
				randomPermutation(kings[i]);
			}

			int[] match = QueensAndKings.bestMatch(queens, kings);
			boolean res = isOptimalMatch(queens, kings, match);
			if(!res){
				Arrays.stream(queens).forEach(q -> System.out.println(Arrays.toString(q)));
				System.out.println("---");
				Arrays.stream(kings).forEach(k -> System.out.println(Arrays.toString(k)));
				System.out.println("---");
				System.out.println(Arrays.toString(match));
			}
			assertTrue(res);
			
		}
	}
	
	void randomPermutation(int [] arr){
		Random r = new Random();
		int t, j;
		for (int i = 0; i < arr.length; i++) {
			j = r.nextInt(arr.length);
			t = arr[i];
			arr[i] = arr[j];
			arr[j] = t;
		}
	}
	
	public static boolean isOptimalMatch(int[][] queens, int[][] kings, int[] match) {
		int n = queens.length;

		int[] match2 = new int[n];
		for (int q = 0; q < n; q++)
			match2[match[q]] = q;

		// inverted index for queens and kings
		int[][] queenIdx = new int[n][n];
		for (int q = 0; q < n; q++) 
			for (int idx = 0; idx < n; idx++) 
				queenIdx[q][queens[q][idx]] = idx;
		
		int[][] kingIdx = new int[n][n];
		for (int k = 0; k < n; k++)
			for (int idx = 0; idx < n; idx++) 
				kingIdx[k][kings[k][idx]] = idx;

		for (int q = 0; q < n; q++) {
			int currentQueensIdx = queenIdx[q][match[q]];
			for (int k = 0; k < n; k++) {
				int currentKingsIdx = kingIdx[k][match2[k]];
				// quadratic can work
				if (match[q] == k)
					continue;
				// potential indexes
				int newQueensIdx = queenIdx[q][k];
				int newKingsIdx = kingIdx[k][q];
				if (newQueensIdx < currentQueensIdx && newKingsIdx < currentKingsIdx) {
					System.err.println("There is a better match!");
					System.err.println("Queen: " + q + " Should be with King: " + k);
					return false;
				}
			}
		}
		return true;
	}

}
