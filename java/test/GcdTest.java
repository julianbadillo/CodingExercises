import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GcdTest {

	@Test
	public void test() {
		
		assertEquals(2, HeartOfTheCity.gcd(10, 8));
		assertEquals(8, HeartOfTheCity.gcd(48, 8));
		assertEquals(12, HeartOfTheCity.gcd(24, 36));
		assertEquals(1, HeartOfTheCity.gcd(35, 24));
		assertEquals(12, HeartOfTheCity.gcd(60, 144));
	}

}
