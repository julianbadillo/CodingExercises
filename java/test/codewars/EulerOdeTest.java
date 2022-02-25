
import org.junit.Test;
import static org.junit.Assert.*;

public class EulerOdeTest {
    
    @Test
    public void test1() {
        System.out.println("Fixed Tests: exEuler");         
        assertEquals(0.5, EulerOde.exEuler(1), 1e6);
        assertEquals(0.026314, EulerOde.exEuler(10), 1e-6);
        assertEquals(0.015193, EulerOde.exEuler(17), 1e-6);
        assertEquals(0.005073, EulerOde.exEuler(50), 1e-6);
        assertEquals(0.002524, EulerOde.exEuler(100), 1e-6);
    }
}