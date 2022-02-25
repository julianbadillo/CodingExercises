import static org.junit.Assert.*;

import org.junit.Test;

public class SafeCombinationTest {

	@Test
	public void testDecryp1() {
		assertEquals("64643", (new SafeCombination()).decrypt("Aol zhml jvtipuhapvu pz: zpe-mvby-zpe-mvby-aoyll"));
		assertEquals("35162", (new SafeCombination()).decrypt("Wkh vdih frpelqdwlrq lv: wkuhh-ilyh-rqh-vla-wzr"));
		assertEquals("4918202163", (new SafeCombination()).decrypt("Dro ckpo mywlsxkdsyx sc: pyeb-xsxo-yxo-osqrd-dgy-joby-dgy-yxo-csh-drboo"));
		assertEquals("400440877", (new SafeCombination()).decrypt("Ymj xfkj htrgnsfynts nx: ktzw-ejwt-ejwt-ktzw-ktzw-ejwt-jnlmy-xjajs-xjajs"));
	}

}
