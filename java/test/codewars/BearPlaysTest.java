import org.junit.Test;

import static org.junit.Assert.*;

public class BearPlaysTest {

    @Test
    public void pileSize() {
        BearPlays bp = new BearPlays();
        int r = bp.pileSize(1,1,1);
        assertEquals(0,r);
    }
}