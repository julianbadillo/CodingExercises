
import org.junit.Test;
import static org.junit.Assert.*;

public class WeirdPrimeGenTest {
    @Test
    public void test1() {
        System.out.println("Fixed Tests: countOnes");    
        assertEquals(8, WeirdPrimeGen.countOnes(10));
        assertEquals(90, WeirdPrimeGen.countOnes(100));
    }
    @Test
    public void test2() {
        System.out.println("Fixed Tests: maxPn");    
        assertEquals(47, WeirdPrimeGen.maxPn(5));
        assertEquals(101, WeirdPrimeGen.maxPn(7));
    }
    @Test
    public void test3() {
        System.out.println("Fixed Tests: anOverAverage");    
        assertEquals(3, WeirdPrimeGen.anOverAverage(5));
    }
    
    @Test
	public void testGCD() throws Exception {
		assertEquals(12, WeirdPrimeGen.gcd(12, 24));
		assertEquals(12, WeirdPrimeGen.gcd(24, 12));
		assertEquals(8, WeirdPrimeGen.gcd(8*13, 8*144));
		assertEquals(8*13, WeirdPrimeGen.gcd(8*13*43, 8*13*48));
		assertEquals(1, WeirdPrimeGen.gcd(13, 166));
		assertEquals(1, WeirdPrimeGen.gcd(13, 57));
	}
    
    @Test
	public void testA() throws Exception {
		System.out.println(WeirdPrimeGen.anOver(30));
	}
}