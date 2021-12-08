import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class QueensTest {

	/*@Test
	public void testPermsR() {
		ArrayList<Integer> a = new ArrayList<>();
		IntStream.range(0, 3)
					.forEach(i -> a.add(i));
		
		Queens.a = a;
		ArrayList<String> perms = Queens.permR();
		
		perms.stream().forEach(System.out::println);
		
		assertEquals(6, perms.size());
		assertTrue(perms.contains("012"));
		assertTrue(perms.contains("102"));
		assertTrue(perms.contains("021"));
		
	}
	*/
	@Test
	public void testPermsR2() {
		ArrayList<Integer> a = new ArrayList<>();
		IntStream.range(0, 4)
					.forEach(i -> a.add(i));
		
		Queens.a = a;
		ArrayList<String> perms = Queens.permR();
		
		perms.stream().forEach(System.out::println);
		
		assertEquals(perms.size(), 24);
		assertTrue(perms.contains("0123"));
		assertTrue(perms.contains("1023"));
		assertTrue(perms.contains("3021"));
		
	}

	@Test
	public void testPartialSort(){
		ArrayList<Integer> l = (ArrayList<Integer>)IntStream.range(0, 10).mapToObj(i->i).collect(Collectors.toList());
		
		Collections.sort(l.subList(5, l.size()), (x, y) -> y - x);
		assertEquals("[0, 1, 2, 3, 4, 9, 8, 7, 6, 5]", l.toString());
		
	}
	
	
	@Test
	public void testPermsIte(){
		Queens.q = new ArrayList<>();
		IntStream.range(0, 4).forEach(i -> Queens.q.add(i));
		
		int i = 0;
		for(; Queens.permutate() && i < 50; i++)
			System.out.println(Queens.q);
		assertEquals(23, i);
	}
	
	@Test
	public void testCollision(){
		boolean[][] b = new boolean[][]{
			{true, false, false},
			{false, true, false},
			{false, false, true},
		};
		assertTrue(Queens.collision(b));
		
		b = new boolean[][]{
			{false, true, false},
			{false, false, true},
			{false, false, false},
		};
		assertTrue(Queens.collision(b));
		
		b = new boolean[][]{
			{false, false, false},
			{true, false, false},
			{false, true, false},
		};
		assertTrue(Queens.collision(b));
		b = new boolean[][]{
			{true, false, false},
			{false, false, false},
			{false, false, true},
		};
		assertTrue(Queens.collision(b));
		b = new boolean[][]{
			{false, true, false},
			{true, false, false},
			{false, false, false},
		};
		assertTrue(Queens.collision(b));
		b = new boolean[][]{
			{false, false, true},
			{false, false, false},
			{true, false, false},
		};
		assertTrue(Queens.collision(b));
		b = new boolean[][]{
			{false, false, false},
			{false, false, true},
			{false, true, false},
		};
		b = new boolean[][] {
			{ false, false, false, true, false, false, }, 
			{ false, false, false, false, false, true, }, 
			{ true, false, false, false, false, false, },
			{ false, false, true, false, false, false, }, 
			{ false, false, false, false, true, false, }, 
			{ false, true, false, false, false, false, },};
		assertTrue(Queens.collision(b));
		
	}
	@Test
	public void testCollision2() {
		boolean[][] b = new boolean[][] { 
			{ false, true, false }, 
			{ false, false, false }, 
			{ false, false, true }, };
		assertFalse(Queens.collision(b));
		
		b = new boolean[][] { 
			{ false, true, false }, 
			{ false, false, false }, 
			{ true, false, false }, };
		assertFalse(Queens.collision(b));
		
		b = new boolean[][] {
			{ true, false, false }, 
			{ false, false, false }, 
			{ false, true, false }, };
		assertFalse(Queens.collision(b));
		
		b = new boolean[][] {
			{ false, true, false, false, false, false, }, 
			{ false, false, false, true, false, false, }, 
			{ false, false, false, false, false, true, },
			{ true, false, false, false, false, false, }, 
			{ false, false, true, false, false, false, }, 
			{ false, false, false, false, true, false, },};
		assertFalse(Queens.collision(b));
		
		b = new boolean[][] {
			{ false, false, false, false, true, false, }, 
			{ false, false, true, false, false, false, }, 
			{ true, false, false, false, false, false, },
			{ false, false, false, false, false, true, }, 
			{ false, false, false, true, false, false, }, 
			{ false, true, false, false, false, false, },};
		assertFalse(Queens.collision(b));
		
		b = new boolean[][] {
			{ false, false, false, false, false, false, }, 
			{ false, false, false, false, false, false, }, 
			{ false, false, false, false, false, false, },
			{ false, false, false, false, false, false, }, 
			{ false, false, false, false, false, false, }, 
			{ false, false, false, false, false, false, },};
		assertFalse(Queens.collision(b));
		
	}
}
