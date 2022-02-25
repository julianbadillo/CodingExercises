import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class GcdTest {

	@Test
	public void test() {
		
		Assert.assertEquals(2, HeartOfTheCity.gcd(10, 8));
		Assert.assertEquals(8, HeartOfTheCity.gcd(48, 8));
		Assert.assertEquals(12, HeartOfTheCity.gcd(24, 36));
		Assert.assertEquals(1, HeartOfTheCity.gcd(35, 24));
		Assert.assertEquals(12, HeartOfTheCity.gcd(60, 144));
	}

}
