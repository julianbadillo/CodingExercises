import org.junit.Test;

import static org.junit.Assert.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class BinaryRegexpTest {

    private static Pattern regex = Pattern.compile(BinaryRegexp.multipleOf7());

    @Test
    public void EdgeCases() {
        System.out.println("Testing for: empty string");
        assertEquals(false, regex.matcher("").matches());
        System.out.println("Testing for: 0");
        assertEquals(true, regex.matcher("0").matches());
    }

    @Test
    public void FixedTests100() {
        for(int i=1; i<1000; i++) {
            System.out.println("Testing for: "+i);
            assertEquals("Failed on :"+i+" "+Integer.toBinaryString(i),
                    i%7 == 0, regex.matcher(Integer.toBinaryString(i)).matches());
        }
    }

    @Test
    public void testMultipleOf2() {
        Pattern regex = Pattern.compile(BinaryRegexp.multipleOf2());
        assertFalse(regex.matcher("").matches());
        for(int i=1; i<1000; i++) {
            System.out.println("Testing for: "+i);
            assertEquals("Failed on :" + i, i % 2 == 0, regex.matcher(Integer.toBinaryString(i)).matches());
        }
    }

    @Test
    public void testMultipleOf3() {
        Pattern regex = Pattern.compile(BinaryRegexp.multipleOf3());
        assertFalse(regex.matcher("").matches());
        for(int i=1; i<1000; i++) {
            System.out.println("Testing for: " + i);
            assertEquals("Failed on :" + i, i % 3 == 0, regex.matcher(Integer.toBinaryString(i)).matches());
        }
    }

    @Test
    public void testMultipleOf4() {
        Pattern regex = Pattern.compile(BinaryRegexp.multipleOf4());
        assertFalse(regex.matcher("").matches());
        for(int i=1; i<1000; i++) {
            System.out.println("Testing for: " + i);
            assertEquals("Failed on :" + i, i % 4 == 0, regex.matcher(Integer.toBinaryString(i)).matches());
        }
    }
}