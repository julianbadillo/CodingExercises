import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class RunesTest {

    @Test
    public void solveExpression() {
        assertEquals( "Answer for expression '1+1=?' " , 2 , Runes.solveExpression("1+1=?") );
        assertEquals( "Answer for expression '123*45?=5?088' " , 6 , Runes.solveExpression("123*45?=5?088") );
        assertEquals( "Answer for expression '-5?*-1=5?' " , 0 , Runes.solveExpression("-5?*-1=5?") );
        assertEquals( "Answer for expression '19--45=5?' " , -1 , Runes.solveExpression("19--45=5?") );
        assertEquals( "Answer for expression '??*??=302?' " , 5 , Runes.solveExpression("??*??=302?") );
        assertEquals( "Answer for expression '?*11=??' " , 2 , Runes.solveExpression("?*11=??") );
        assertEquals( "Answer for expression '??*1=??' " , 2 , Runes.solveExpression("??*1=??") );
    }


    @Test
    public void parse() {
        String [] v = Runes.parse("1234+456=790");
        assertArrayEquals(new String[]{"1234","+","456","790"}, v);

        v = Runes.parse("1234-456=790");
        assertArrayEquals(new String[]{"1234","-","456","790"}, v);

        v = Runes.parse("1234*456=790");
        assertArrayEquals(new String[]{"1234","*","456","790"}, v);

        v = Runes.parse("1234*-456=790");
        assertArrayEquals(new String[]{"1234","*","-456","790"}, v);

        v = Runes.parse("1234--456=790");
        assertArrayEquals(new String[]{"1234","-","-456","790"}, v);

        v = Runes.parse("-1234-456=-790");
        assertArrayEquals(new String[]{"-1234","-","456","-790"}, v);
    }

    @Test
    public void testPattern(){
        boolean r = Pattern.matches("(-?[0-9\\?]+)", "12?345");
        assertTrue(r);
        r = Pattern.matches("(-?[0-9\\?]+)", "-12?345");
        assertTrue(r);
        r = Pattern.matches("(-?[0-9\\?]+)", "12?34?5");
        assertTrue(r);

        r = Pattern.matches("([\\+\\-\\*])", "*");
        assertTrue(r);
        r = Pattern.matches("([\\+\\-\\*])", "+");
        assertTrue(r);
        r = Pattern.matches("([\\+\\-\\*])", "-");
        assertTrue(r);
        r = Pattern.matches("(-?[0-9\\?]+)\\=", "-123=");
        assertTrue(r);
        r = Pattern.matches("(-?[0-9\\?]+)\\=", "1?23=");
        assertTrue(r);

        r = Pattern.matches(Runes.EXP, "-1234-456=-790");
        assertTrue(r);

        Pattern p = Pattern.compile(Runes.EXP);
        Matcher m = p.matcher("1234+456=790");
        assertTrue(m.matches());
        assertEquals("1234", m.group(1));
        assertEquals("+", m.group(2));
        assertEquals("456", m.group(3));
        assertEquals("790", m.group(4));
    }
}