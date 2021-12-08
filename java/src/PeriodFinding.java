import static org.junit.Assert.*;

import static java.math.BigInteger.*;
import java.util.Random;

import org.junit.Test;

public class PeriodFinding {

	@Test
	public void factorize1() {
		// Example of factorizing when a period is found
		long p = 977, q = 983;
		long N = p * q;

		Random rand = new Random();
		long p2, q2;
		long r = 1;
		long a;
		do {
			a = (rand.nextLong() % N + N) % N;
			if (N % a == 0) {
				assertTrue(a == p || a == q);
				// FACTORIZED! trivial case
				return;
			} else {

				// find period
				long t = a;
				while (t != 1) {
					r++;
					t = a * t % N;
				}

				assertEquals(1, t);
			}
			// if odd period, retry
		} while (r % 2 != 0);
		// (a ^(r/2) +- 1)
		long x = valueOf(a).modPow(valueOf(r/2), valueOf(N)).longValue() - 1;
		long y = valueOf(a).modPow(valueOf(r/2), valueOf(N)).longValue() + 1;

		p2 = valueOf(x).gcd(valueOf(N)).longValue();
		q2 = valueOf(y).gcd(valueOf(N)).longValue();
		System.out.println("Factors are: "+ p2+", "+ q2);
		// check factors
		assertTrue(p2 == p || p2 == q);
		assertTrue(q2 == p || q2 == q);
	}

}
