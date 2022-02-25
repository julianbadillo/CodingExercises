import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class RangeExtractTest {
    @Test
    public void test_BasicTests() {
        assertEquals("-6,-3-1,3-5,7-11,14,15,17-20", RangeExtract.rangeExtraction(new int[]{-6, -3, -2, -1, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20}));

        assertEquals("-3--1,2,10,15,16,18-20", RangeExtract.rangeExtraction(new int[]{-3, -2, -1, 2, 10, 15, 16, 18, 19, 20}));
    }

    @Test
    public void anotherExtractTest() {
        var seq = new int[]{-1000,-999,-998,-500,-3,-1,0,1,2,3,100,101,103,105};
        String s = "-1000--998,-500,-3,-1-3,100,101,103,105";
        assertEquals(s, RangeExtract.rangeExtraction(seq));

        seq = new int[]{-3,-2,-1,0,1,2,3};
        s = "-3-3";
        assertEquals(s, RangeExtract.rangeExtraction(seq));

        seq = new int[]{1,3,5,7,9};
        s = "1,3,5,7,9";
        assertEquals(s, RangeExtract.rangeExtraction(seq));

    }

}