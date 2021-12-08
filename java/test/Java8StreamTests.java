import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class Java8StreamTests {

	@Test
	public void test() {
		
		int [][] mat = {{1, 2, 3},
						{3, 5, 1},
						{1, 0, 2}};
		
		int maxInc = Arrays.stream(mat).map(x -> Arrays.stream(x)).mapToInt(x -> x.max().getAsInt()).max().getAsInt();
		assertEquals(5, maxInc);
		
	}

}
