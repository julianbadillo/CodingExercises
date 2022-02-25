import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleFieldTest {

    private static int[][] battleField = {
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    @Test
    public void SampleTest() {
        assertEquals(true, BattleField.fieldValidator(battleField));
    }

    @Test
    public void TouchValidateTest(){
        var b1 = new BattleField.Ship(0,0, 4, 1);
        var b2 = new BattleField.Ship(0,1, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
        b2 = new BattleField.Ship(1,1, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
        b2 = new BattleField.Ship(2,1, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
        b2 = new BattleField.Ship(3,1, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
        b2 = new BattleField.Ship(4,1, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));

        b2 = new BattleField.Ship(5,1, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));

        b2 = new BattleField.Ship(0,2, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
        b2 = new BattleField.Ship(1,2, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
        b2 = new BattleField.Ship(2,2, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
        b2 = new BattleField.Ship(3,2, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
    }

    @Test
    public void TouchValidateTest2(){
        var b1 = new BattleField.Ship(0,0, 1, 4);
        var b2 = new BattleField.Ship(1,0, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
        b2 = new BattleField.Ship(1,1, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
        b2 = new BattleField.Ship(1,2, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
        b2 = new BattleField.Ship(1,3, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
        b2 = new BattleField.Ship(1,4, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));

        b2 = new BattleField.Ship(1,5, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
        b2 = new BattleField.Ship(2,0, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
        b2 = new BattleField.Ship(2,2, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
        b2 = new BattleField.Ship(2,2, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
        b2 = new BattleField.Ship(2,2, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
    }

    @Test
    public void TouchValidateTestDiagonal(){
        var b1 = new BattleField.Ship(0,0, 1, 1);
        var b2 = new BattleField.Ship(1,1, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));
    }

    @Test
    public void TouchValidateDiagonal2(){
        var b1 = new BattleField.Ship(0,1, 1, 1);
        var b2 = new BattleField.Ship(1,0, 1, 1);
        assertTrue(b1.touches(b2));
        assertTrue(b2.touches(b1));

        b1 = new BattleField.Ship(0,2, 1, 1);
        b2 = new BattleField.Ship(1,0, 1, 1);
        assertFalse(b1.touches(b2));
        assertFalse(b2.touches(b1));
    }
}