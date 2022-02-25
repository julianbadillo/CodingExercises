import org.junit.Test;
import static org.junit.Assert.*;

public class InfiniteDigitalStringTest {

    private static Object[][] DATA = new Object[][] {
            new Object[] {"58257860625",24674951477L," ... 25786062[58 257860625]9 ... "},
            new Object[] {"455",        98L,         "...545556..."},
            new Object[] {"910",        8L,          "...7891011..."},
            new Object[] {"456",        3L,          "...3456..."},
            new Object[] {"454",        79L,         "...444546..."},
            new Object[] {"9100",       188L,        "...9899100..."},
            new Object[] {"99100",      187L,        "...9899100..."},
            new Object[] {"01",         10L,         ""},
            new Object[] {"00101",      190L,        "...9899100..."},
            new Object[] {"001",        190L,        "...9899100..."},
            new Object[] {"00",         190L,        "...9899100..."},
            new Object[] {"123456789",  0L,          ""},
            new Object[] {"1234567891", 0L,          ""},
            new Object[] {"123456798",  1000000071L, ""},
            new Object[] {"10",         9L,          ""},
            new Object[] {"53635",      13034L,      "...5363 5364..."},
            new Object[] {"040",        1091L,       "...400 401"},
            new Object[] {"11",         11L,         ""},
            new Object[] {"99",         168L,        ""},
            new Object[] {"667",        122L,        ""},
            new Object[] {"0404",       15050L,      ""},
            new Object[] {"949225100",  382689688L,  ""},
            new Object[] {"3999589058124"  , 6957586376885L   , ".... 58905812[3999 589058124]000 ..."},
            //new Object[] {"555899959741198", 1686722738828503L, ""},
            new Object[] {"091",        170L,        ""},
            new Object[] {"0910",       2927L,       ""},
            new Object[] {"0991",       2617L,       ""},
            new Object[] {"09910",      2617L,       ""},
            new Object[] {"09991",      35286L,      ""}
            };

    @Test
    public void sampleTests() {
        for (Object[] t: DATA) {
            String s      = (String) t[0],
                    msg    = (String) t[2];
            long expected = (long)   t[1];
            assertEquals(s+" in "+ msg, expected, InfiniteDigitalString.findPosition(s));
        }
        assertNotEquals(-1L, InfiniteDigitalString.findPosition("040"));
    }

    @Test
    public void positionOfNumber() {

        assertEquals(0L, InfiniteDigitalString.positionOfNumber(1L));
        assertEquals(8L, InfiniteDigitalString.positionOfNumber(9L));
        assertEquals(9L, InfiniteDigitalString.positionOfNumber(10L));
        assertEquals(11L, InfiniteDigitalString.positionOfNumber(11L));
        assertEquals(13L, InfiniteDigitalString.positionOfNumber(12L));
        assertEquals(9L+2*89L, InfiniteDigitalString.positionOfNumber(99L));
        assertEquals(9L+2*90L, InfiniteDigitalString.positionOfNumber(100L));
        assertEquals(9L+2*90L + 3*900L, InfiniteDigitalString.positionOfNumber(1000L));
        assertEquals(9L+2*90L + 3*899L, InfiniteDigitalString.positionOfNumber(999L));
        assertEquals(1089L, InfiniteDigitalString.positionOfNumber(400L));
        assertEquals(2925L, InfiniteDigitalString.positionOfNumber(1009L));
    }
}
