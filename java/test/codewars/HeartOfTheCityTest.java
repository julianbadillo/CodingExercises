import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

public class HeartOfTheCityTest {

	@Test
	public void test1() {
		HeartOfTheCity h = new HeartOfTheCity();
		BigInteger b = h.solve(10000);
		
		Assert.assertEquals(new BigInteger("60803664"), b);
	}
	/*
	@Test
	public void test1b() {
		HeartOfTheCity h = new HeartOfTheCity();
		BigInteger b = h.solve(20000);
		
		Assert.assertEquals(new BigInteger("243179888"), b);
	}*/
	
	@Test
	public void test2() {
		HeartOfTheCity h = new HeartOfTheCity();
		BigInteger b = h.solve(33);
		Assert.assertEquals(BigInteger.valueOf(640), b);
	}
	
	@Test
	public void test3() {
		HeartOfTheCity h = new HeartOfTheCity();
		BigInteger b = h.solve(7);
		Assert.assertEquals(BigInteger.valueOf(32), b);
	}
	
	@Test
	public void test4() {
		HeartOfTheCity h = new HeartOfTheCity();
		BigInteger b = h.solve(21);
		Assert.assertEquals(BigInteger.valueOf(256), b);
	}
	
	@Test
	public void testRelPrimes(){
		HeartOfTheCity h = new HeartOfTheCity();
		Assert.assertEquals(h.relPrimes(12, 20), h.relPrimes2(12, 20));
	}
	
	@Test
	public void testRelPrimes2(){
		HeartOfTheCity h = new HeartOfTheCity();
		Assert.assertEquals(h.relPrimes(48, 300), h.relPrimes2(48, 300));
	}

}
