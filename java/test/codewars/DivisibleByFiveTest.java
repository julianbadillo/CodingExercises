import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DivisibleByFiveTest {

    private static Object[][] testArr = new Object[][] {
        new Object[] {false, "" },
        new Object[] {false, "abc"},
        new Object[] {true,  "000"},
        new Object[] {true,  "101"},
        new Object[] {true,  "1010"},
        new Object[] {true,  "10100"},
        new Object[] {true,  "1010101"},
        new Object[] {true,  Integer.toBinaryString(65020)},
        
        new Object[] {false, "10110101"},
        new Object[] {false, "1110001"},
        
        new Object[] {false,  Integer.toBinaryString(21)},
        new Object[] {false,  Integer.toBinaryString(15392)},
        new Object[] {false,  Integer.toBinaryString(23573)},
        new Object[] {false,  Integer.toBinaryString(19344)},
        
        new Object[] {false,  Integer.toBinaryString(43936)},
        new Object[] {false,  Integer.toBinaryString(32977)},
        new Object[] {false,  Integer.toBinaryString(328)},
        new Object[] {false,  Integer.toBinaryString(5729)}
    };

  @Test
  public void exampleTests() {
      for (Object[] arr: testArr) {
          boolean exp    = (boolean) arr[0];
          String  toTest = (String) arr[1];
          assertEquals(String.format("Should work with '%s':", toTest), exp, DivisibleByFive.pattern().matcher(toTest).matches());
      }
  }
}