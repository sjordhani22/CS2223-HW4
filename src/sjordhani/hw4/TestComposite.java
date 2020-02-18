package sjordhani.hw4;

import java.math.BigInteger;
import java.util.Random;

import edu.princeton.cs.algs4.StdRandom;
import junit.framework.TestCase;

public class TestComposite extends TestCase {
	// test a prime
	public void testConstruct() {
		Composite c = new Composite(23);
		assertEquals ("23", c.toString());
	}
	
	// test a single prime to a single power.
	public void testPower() {
		Composite c = new Composite(32);
		assertEquals ("2^5", c.toString());
	}
	
	// test a number of primes to a single power
	public void testPrimes() {
		Composite c = new Composite(2*3*5*7*11);
		assertEquals ("2 * 3 * 5 * 7 * 11", c.toString());
		assertEquals (BigInteger.valueOf(2310), c.value());
	}
	
	// test a number of primes to a different power
	public void testPrimesMultiples() {
		Composite c = new Composite(2*2*2*3*3*5*5*5*7*11);
		assertEquals ("2^3 * 3^2 * 5^3 * 7 * 11", c.toString());
	}
	
	// Try to random large primes and validate they add together and provide result.
	public void testStressTest() {
		BigInteger prime1 = new BigInteger(32, 64, new Random());
		BigInteger prime2 = new BigInteger(32, 64, new Random());
		Composite c1 = new Composite(prime1);
		Composite c2 = new Composite(prime2);
		
		assertEquals (prime1.toString(), c1.toString());
		assertEquals (prime2.toString(), c2.toString());
	}
	
	// Try to random large even numbers to compute gcd
	public void testStressTest2() {
		BigInteger prime1 = new BigInteger(32, 64, new Random());
		BigInteger even1 = prime1.add(BigInteger.ONE);
		BigInteger prime2 = new BigInteger(32, 64, new Random());
		BigInteger even2 = prime2.add(BigInteger.ONE);
		
		Composite c1 = new Composite(even1);
		Composite c2 = new Composite(even2);
		Composite gcd = c1.gcd(c2);
		
		
	}
		
	// lots of special cases when unit values are in play.
	public void testUnit() {
		Composite c1 = new Composite(2       *5*         11);
		Composite c2 = new Composite(    3        * 7        * 13 * 17);
		
		// nothing in common? return null
		assertTrue (c1.gcd(c2) == null);
		assertTrue (c2.gcd(c1) == null);
	}  
	
	// lots of special cases for equality
	public void testEquals() {
		Composite c1 = new Composite(2 * 3 * 11);
		Composite c2 = new Composite(2 * 5 * 7 * 11);
		assertFalse (c1.equals(c2));
		assertFalse (c2.equals(c1));
		
		
		
		Composite comp = new Composite(2*5*11);
		assertFalse (comp.equals(null));
		assertFalse (comp.equals(new Integer(55)));

		System.out.println(comp.toString());
		assertTrue (comp.equals(comp));
		
		assertFalse (comp.equals(new Composite(5*11)));       // missing factor
		assertFalse (comp.equals(new Composite(2*2*5*11)));   // different power
		assertFalse (comp.equals(new Composite(2*5*11*13)));  // extra factor
		
		// switch sides
		assertFalse (new Composite(5*11).equals(comp));       // missing factor
		assertFalse (new Composite(2*2*5*11).equals(comp));   // different power
		assertFalse (new Composite(2*5*11*13).equals(comp));  // extra factor
		
		// missing factors
		assertFalse (new Composite(2*5*11).equals(new Composite(2*11)));
		assertFalse (new Composite(2*11).equals(new Composite(2*7*11)));
		
		// wrong internal
		assertFalse (new Composite(2*5*5*5*5*11).equals(new Composite(2*11)));
		assertFalse (new Composite(2*5*5*5*5*11).equals(new Composite(2*7*7*7*7*11)));
		
	}  
	
	// greatest common divisor tests
	public void testGCD() {
		Composite one = new Composite(2*2*2      *5*         11);
		Composite two = new Composite(2*2   * 3  *5*5  * 7        * 13 * 17);
		
		// gcd(a,b) == gcd(b,a)
		assertTrue (one.gcd(two).equals (two.gcd(one)));
		 
		assertEquals (new Composite(2*2*5), one.gcd(two));
	}
	
	// least common multiple tests
	public void testLCM() {
		Composite one = new Composite(2*2*2      *5*         11);
		Composite two = new Composite(2*2   * 3  *5*5  * 7        * 13 * 17);
		 
		// lcm(a,b) == lcm(b,a)
		assertTrue (one.lcm(two).equals (two.lcm(one))); 
		
		assertEquals (new Composite(2*2*2*3*5*5*7*11*13*17), one.lcm(two));
	}
	
	// test for primality
	public void testPrime() {
		Composite two = new Composite(2);
		assertTrue (two.isPrime());
		two = two.multiply(two);
		assertFalse (two.isPrime());
		
		Composite four = new Composite(4);
		assertFalse (four.isPrime()); 
		
		// two children of root
		assertFalse (new Composite(2*3*5).isPrime());
		
		//one child of root (both sides)
		assertFalse (new Composite(2*3).isPrime());
		assertFalse (new Composite(2).multiply(new Composite(3)).isPrime());
		assertFalse (new Composite(3).multiply(new Composite(2)).isPrime());
	}
	
	// test for primality
	public void testMultiply() {
		Composite val1 = new Composite(2);
		Composite val2 = new Composite(2);
		
		int [] ar = new int[50];
		int [] copy = new int[ar.length];
		for (int i = 0; i < ar.length; i++) {
			ar[i] = i+3;
			copy[i] = i+3;
		}
		
		// randomize order in one. Because of commutativity property of multiplication,
		// we will arrive at same result.
		StdRandom.shuffle(ar);
		
		for (int i = 0; i < ar.length; i++) {
			val1 = val1.multiply(new Composite(ar[i]));
			val2 = val2.multiply(new Composite(copy[i]));
		}
		
		assertEquals(val1, val2);
	}
	
	// test for large equality
	public void testLargeEquals() {
		Composite val1 = new Composite(14519 );
		Composite val2 = new Composite(14519 );
		
		for (int i = 0; i < 40; i++) {
			int rn = 3+(int)(Math.random()*1000);
			Composite c2 = new Composite(rn);
			val1 = val1.multiply(c2);
			
			rn = 3+(int)(Math.random()*1000);
			c2 = new Composite(rn);
			val2 = val2.multiply(c2);
		}
		
		val1 = val1.multiply(new Composite(Composite.TWO));  // make 2 a factor of both
		val2 = val2.multiply(new Composite(Composite.TWO));  // make 2 a factor of both
		BigInteger v1 = val1.value();
		BigInteger v2 = val2.value();
		if (v1.equals(v2)) {
			assertTrue (val1.equals(val2));   // truly unlikely
			assertTrue (val1.equals(val2));
		} else {
			assertFalse (val1.equals(val2));
			assertFalse (val1.equals(val2));	
		}
	}
	
	public void testDivideBy() {
		Composite val1 = new Composite(17);
		assertFalse (val1.divisibleBy(2));
		assertTrue (val1.divisibleBy(17));
		
		val1 = val1.multiply(new Composite(17));
		assertFalse (val1.divisibleBy(2));
		assertTrue (val1.divisibleBy(17));
	}
	
	// factorize
	public void testFactorize() {
		Composite c1 = new Composite(129837);
		Composite c2 = Composite.factorize(BigInteger.valueOf(129837));
		assertEquals(c1, c2);
	}
	
	// sanity checks
	public void testSanity() {
		try {
			new Composite(-1);
			fail ("shouldn't be able to construct a negative composite number.");
		} catch (Exception e) { }
		
		try {
			new Composite(0);
			fail ("shouldn't be able to construct a composite from zero.");
		} catch (Exception e) { }
		
		try {
			new Composite(1);
			fail ("shouldn't be able to construct a composite from one any more.");
		} catch (Exception e) { }
	}
}
