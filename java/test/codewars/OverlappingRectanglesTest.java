import static org.junit.Assert.*;

import org.junit.Test;

public class OverlappingRectanglesTest {

	@Test
	public void testZero() {
		OverlappingRectangles or = new OverlappingRectangles();
		assertEquals(0, or.maxOverlapping(new Rectangle[0]));
	}

	@Test
	public void testAll() {
		OverlappingRectangles or = new OverlappingRectangles();
		Rectangle[] recs = new Rectangle[]{
				new Rectangle(0, 0, 2, 2),
				new Rectangle(0, 0, 2, 2),
				new Rectangle(0, 0, 2, 2),
				new Rectangle(0, 0, 2, 2),
		}; 
		assertEquals(4, or.maxOverlapping(recs));
	}
	
	@Test
	public void testAll2() {
		OverlappingRectangles or = new OverlappingRectangles();
		Rectangle[] recs = new Rectangle[]{
				new Rectangle(0, 0, 2, 2),
				new Rectangle(0, 0, 3, 3),
				new Rectangle(0, 0, 4, 4),
				new Rectangle(0, 0, 5, 5),
		}; 
		assertEquals(4, or.maxOverlapping(recs));
	}
	
	@Test
	public void testAll3() {
		OverlappingRectangles or = new OverlappingRectangles();
		Rectangle[] recs = new Rectangle[]{
				new Rectangle(0, 0, 2, 2),
				new Rectangle(1, 1, 3, 3),
				new Rectangle(1, 0, 3, 4),
				new Rectangle(0, 1, 7, 5),
		}; 
		assertEquals(4, or.maxOverlapping(recs));
	}
	
	@Test
	public void testStarway() {
		OverlappingRectangles or = new OverlappingRectangles();
		Rectangle[] recs = new Rectangle[]{
				new Rectangle(0, 0, 10, 10),
				new Rectangle(5, 5, 10, 10),
				new Rectangle(10, 10, 10, 10),
				new Rectangle(15, 15, 10, 10),
				new Rectangle(20, 20, 10, 10),
		}; 
		assertEquals(2, or.maxOverlapping(recs));
	}
	@Test
	public void testStarway2() {
		OverlappingRectangles or = new OverlappingRectangles();
		Rectangle[] recs = new Rectangle[]{
				new Rectangle(30, 0, 10, 10),
				new Rectangle(25, 5, 10, 10),
				new Rectangle(20, 10, 10, 10),
				new Rectangle(15, 15, 10, 10),
				new Rectangle(10, 20, 10, 10),
		}; 
		assertEquals(2, or.maxOverlapping(recs));
	}
	
	
	
	@Test
	public void testMixed() {
		OverlappingRectangles or = new OverlappingRectangles();
		Rectangle[] recs = new Rectangle[]{
				new Rectangle(10, 0, 20, 20),
				new Rectangle(2, 2, 34, 30),
				new Rectangle(1, 0, 13, 45),
				new Rectangle(10, 12, 20, 25),
				new Rectangle(0, 3, 17, 35),
				new Rectangle(1, 1, 10, 50),
				new Rectangle(33, 21, 10, 15),
				new Rectangle(30, 11, 10, 15),
				new Rectangle(10, 31, 10, 15),
				new Rectangle(15, 11, 10, 25),
				new Rectangle(10, 51, 10, 25),
				new Rectangle(25, 11, 10, 25),
				new Rectangle(20, 44, 10, 15),
				new Rectangle(30, 12, 10, 15),
				new Rectangle(35, 23, 15, 25),
				new Rectangle(10, 21, 15, 25),
				new Rectangle(65, 31, 15, 35),
				new Rectangle(40, 21, 15, 25),
				new Rectangle(65, 41, 15, 15),
				new Rectangle(30, 51, 15, 25),
				new Rectangle(70, 23, 15, 35),
				new Rectangle(10, 15, 15, 15),
		}; 
		assertEquals(7, or.maxOverlapping(recs));
	}
	
}
