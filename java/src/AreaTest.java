import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.Arrays;

import org.junit.Test;

public class AreaTest {

	@Test
	/**
	 * Test side by side same size rectangle union
	 * Should become a new bigger rectangle
	 */
	public void testRectangleUnion1(){
		
		Area a1 = new Area(new Rectangle(10, 10, 10, 20));
		Area a2 = new Area(new Rectangle(20, 10, 10, 20));
		
		a1.add(a2);
		assertTrue(a1.isPolygonal());
		assertTrue(a1.isRectangular());
		PathIterator ite = a1.getPathIterator(null);
		int steps=0;
		
		float[] coords = new float[2];
		
		int t = ite.currentSegment(coords);
		assertTrue( Arrays.equals(new float[]{10.0f, 10.0f}, coords));
		while(!ite.isDone())
		{
			steps++;
			t = ite.currentSegment(coords);
			//System.out.println(t);
			//System.out.println(Arrays.toString(coords));
			ite.next();
		}
		// starting + moving to
		assertEquals(5, steps);
	}
	
	
	@Test
	/**
	 * Test side by side different size rectangle union
	 * Should become different shape
	 */
	public void testRectangleUnion2(){
		
		Area a1 = new Area(new Rectangle(10, 10, 10, 20));
		Area a2 = new Area(new Rectangle(20, 10, 10, 30));
		
		a1.add(a2);
		assertTrue(a1.isPolygonal());
		assertFalse(a1.isRectangular());
		PathIterator ite = a1.getPathIterator(null);
		int steps=0;
		
		float[] coords = new float[2];
		
		int t = ite.currentSegment(coords);
		assertTrue( Arrays.equals(new float[]{10.0f, 10.0f}, coords));
		while(!ite.isDone())
		{
			steps++;
			t = ite.currentSegment(coords);
			//System.out.println(t);
			//System.out.println(Arrays.toString(coords));
			ite.next();
		}
		// starting + moving to
		assertEquals(7, steps);
	}
	
	@Test
	/**
	 * Test two sepparate rectangles
	 */
	public void testRectangleUnion4(){
		
		Area a1 = new Area(new Rectangle(10, 10, 10, 20));
		Area a2 = new Area(new Rectangle(30, 10, 10, 30));
		
		a1.add(a2);
		assertTrue(a1.isPolygonal());
		assertFalse(a1.isRectangular());
		PathIterator ite = a1.getPathIterator(null);
		int steps=0;
		
		float[] coords = new float[2];
		
		int t;
		while(!ite.isDone())
		{
			steps++;
			t = ite.currentSegment(coords);
			System.out.println(t);
			System.out.println(Arrays.toString(coords));
			ite.next();
		}
		// starting + moving to
		assertEquals(10, steps);
	}
}
