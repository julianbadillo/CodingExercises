import static org.junit.Assert.*;

import org.junit.Test;

public class GreatestNumberTest {

	@Test
	public void positivNoDot(){
		GreatestNumber gn = new GreatestNumber();
		char[] chars = "1 2 3 4 5 6 7 8 9".replace(" ", "").toCharArray();
		assertEquals("987654321", gn.greatest(chars));
	}
	
	@Test
	public void testNeg() {
		GreatestNumber gn = new GreatestNumber();
		char[] chars = "4 9 8 . 8 5 2 -".replace(" ", "").toCharArray();
		assertEquals("-2.45889", gn.greatest(chars));
	}
	
	@Test
	public void testPositif() {
		GreatestNumber gn = new GreatestNumber();
		char[] chars = "7 4 1 . 2 5 8 9 9".replace(" ", "").toCharArray();
		assertEquals("9987542.1", gn.greatest(chars));
	}
	
	@Test
	public void testNoDot(){
		GreatestNumber gn = new GreatestNumber();
		char[] chars = "- 7 7 8 8 6 5 1".replace(" ", "").toCharArray();
		assertEquals("-1567788", gn.greatest(chars));
	}

	//
	@Test
	public void leadingZeros(){
		GreatestNumber gn = new GreatestNumber();
		char[] chars = "- 4 0 0 5 9 8 . 2".replace(" ", "").toCharArray();
		assertEquals("-0.024589", gn.greatest(chars));
	}
	//
	@Test
	public void trailingZeros(){
		GreatestNumber gn = new GreatestNumber();
		char[] chars = "0 0 0 1 0 0 0 .".replace(" ", "").toCharArray();
		assertEquals("100000", gn.greatest(chars));
	}
	
	//
	@Test
	public void justZeros(){
		GreatestNumber gn = new GreatestNumber();
		char[] chars = "- 0 0 0 0 0 0 0 .".replace(" ", "").toCharArray();
		assertEquals("0", gn.greatest(chars));
	}
}
