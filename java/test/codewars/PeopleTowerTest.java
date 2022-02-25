import static org.junit.Assert.*;

import org.junit.Test;

public class PeopleTowerTest {

	@Test
	public void test1() {
		PeopleTower pt = new PeopleTower();
		Person[] p = new Person[5];
		int i = 0;
		p[i++] = new Person(150,50);
		p[i++] = new Person(150,40);
		p[i++] = new Person(160,50);
		p[i++] = new Person(150,55);
		p[i++] = new Person(170,60);
		
		int r = pt.getMaxPeopleTower(p);
		assertEquals(3, r);
	}
	
	@Test
	public void test2() {
		PeopleTower pt = new PeopleTower();
		Person[] p = new Person[5];
		int i = 0;
		p[i++] = new Person(140,40);
		p[i++] = new Person(130,30);
		p[i++] = new Person(160,60);
		p[i++] = new Person(155,55);
		p[i++] = new Person(170,70);
		
		int r = pt.getMaxPeopleTower(p);
		assertEquals(5, r);
	}
	
	@Test
	public void test3() {
		PeopleTower pt = new PeopleTower();
		Person[] p = new Person[5];
		int i = 0;
		p[i++] = new Person(140,40);
		p[i++] = new Person(130,40);
		p[i++] = new Person(130,60);
		p[i++] = new Person(155,40);
		p[i++] = new Person(170,40);
		
		int r = pt.getMaxPeopleTower(p);
		assertEquals(1, r);
	}

	@Test
	public void testOne() {
		PeopleTower pt = new PeopleTower();
		Person[] p = new Person[1];
		int i = 0;
		p[i++] = new Person(140,40);
		int r = pt.getMaxPeopleTower(p);
		assertEquals(1, r);
	}

	@Test
	public void testZero() {
		PeopleTower pt = new PeopleTower();
		Person[] p = new Person[0];
		int r = pt.getMaxPeopleTower(p);
		assertEquals(0, r);
	}

}
