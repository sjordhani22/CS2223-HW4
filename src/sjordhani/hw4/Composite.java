package sjordhani.hw4;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Homework 4: Data Type Exercise
 * 
 * Copy this class into your USERID.hw4 package and complete.
 * 
 * Each composite number is to be represented as a binary tree of prime factors
 * (with value as power).
 * 
 * Yes, the Composite Problem is Back!
 */
public class Composite {

	/*
	 * 
	 * // I used this main class to test out my functions individually public static
	 * void main(String[] args) { Composite a = new Composite(20); Composite b = new
	 * Composite(10);
	 * 
	 * System.out.println(a.multiply(b)); System.out.print(a.value());
	 * 
	 * }
	 * 
	 */
	/**
	 * Keep track of the AVL tree of factor/exponents based at this root.
	 * 
	 * Each key is a BigInteger factor; each value is a power of that factor
	 */
	AVL<BigInteger, Integer> tree = new AVL<BigInteger, Integer>();

	/**
	 * Constructs a Composite with the specified value, which may not be one, zero
	 * or negative.
	 */
	public Composite(long val) {
		this(BigInteger.valueOf(val));
	}

	Composite() {

	}

	/**
	 * Constructs a Composite from the given Pair<BigInteger,Integer> powers.
	 * 
	 * Useful to be able to create a Composite object from pre-existing factors and
	 * exponents
	 */
	public Composite(Iterable<Pair<BigInteger, Integer>> factors) {
		for (Pair<BigInteger, Integer> pair : factors) {
			tree.put(pair.key, pair.value);
		}
	}

	/** Helper value for factorize. */
	public final static BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);

	/**
	 * Constructs a Composite with the specified value, which may not be zero, one
	 * or negative.
	 * 
	 */
	public Composite(BigInteger val) {
		if (val.compareTo(BigInteger.ZERO) <= 0) {
			throw new IllegalArgumentException("Composite must be a non-negative value.");
		}
		if (val.compareTo(BigInteger.ONE) == 0) {
			throw new IllegalArgumentException("Composite cannot be one.");
		}

		BigInteger tempVal = val;
		BigInteger divider = TWO;

		boolean beenDivisible = false;

		while (divider.multiply(divider).compareTo(val) <= 0) {
			if (tempVal.mod(divider).compareTo(BigInteger.ZERO) == 0) {
				int power = 0;

				while (tempVal.mod(divider).compareTo(BigInteger.ZERO) == 0) {
					power++;
					tempVal = tempVal.divide(divider);
					beenDivisible = true;
				}
				tree.put(divider, power);
			}
			divider = divider.add(BigInteger.ONE);
		}

		if (!beenDivisible) {
			tree.put(val, 1);
		}
		tree.put(tempVal, 1);

	}

	/**
	 * Returns string representation as a sequence of factors with primes
	 * 
	 * 125 returns "5^3" 36 returns "2^2 * 3^2"
	 * 
	 * Note that spaces appear between the * and the other factors
	 */
	public String toString() {
		String line = "";
		boolean afterFirst = false;

		for (Pair<BigInteger, Integer> pair : tree.pairs()) {
			if (pair.key.compareTo(BigInteger.ONE) > 0) {
				if (pair.key.compareTo(BigInteger.ZERO) < 0) {

				} else if (pair.value == 0) {

				} else if (pair.value == 1) {
					if (!afterFirst)
						afterFirst = true;
					else
						line += " * ";
					line += pair.key.toString();
				} else {
					if (!afterFirst)
						afterFirst = true;
					else
						line += " * ";
					line += pair.key.toString() + "^" + pair.value;
				}
			}

		}
		return line;
	}

	/**
	 * Determine if two composite values are equal to each other.
	 * 
	 * Hint: Since you can't know the structure of the respective AVL trees, you
	 * should get their respective pairs as an iterator
	 */
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}
		if (o instanceof Composite) {
			Composite other = (Composite) o;

			if (this.value().equals(other.value())) {
				return true;
			} else
				return false;
		}
		return false;
	}

	/**
	 * Return value of Composite as a single BigInteger.
	 * 
	 * Useful for testing.
	 * 
	 * @return  a single BigInteger value representing actual value of Composite.
	 */
	public BigInteger value() {
		BigInteger key = BigInteger.ONE;
		int val = 0;
		BigInteger pow = BigInteger.valueOf(1);
		BigInteger tot = BigInteger.valueOf(1);

		for (Pair<BigInteger, Integer> a : tree.pairs()) {
			if (a == null) {
				return tot;
			}
			key = a.key;
			val = a.value;
			pow = key.pow(val);
			tot = tot.multiply(pow);
		}
		return tot;
	}

	/**
	 * Determine if Composite represents a prime number.
	 * 
	 * See https://en.wikipedia.org/wiki/Prime_number#Primality_of_one
	 */
	public boolean isPrime() {
		BigInteger val = this.value();

		if (!val.isProbablePrime(1))
			return false;

		BigInteger two = new BigInteger("2");

		if (!two.equals(val) && BigInteger.ZERO.equals(val.remainder(two)))
			return false;

		return true;
	}

	/**
	 * Determine whether composite is simply divisible by prime number factor.
	 * 
	 * You can assume factor is a prime number. If it is not, then this method is
	 * not responsible for the result.
	 */
	public boolean divisibleBy(long factor) {
		return divisibleBy(BigInteger.valueOf(factor));
	}

	/**
	 * Determine whether composite is simply divisible by prime number factor.
	 * 
	 * You can assume factor is a prime number. If it is not, then this method is
	 * not responsible for the result.
	 */
	public boolean divisibleBy(BigInteger factor) {

		if (!factor.isProbablePrime(25)) {
			throw new IllegalArgumentException("Factor is not prime:" + factor);
		}

		BigInteger val = this.value();

		if (val.remainder(factor).equals(BigInteger.ZERO)) {
			return true;
		}
		return false;
	}

	/**
	 * Computes product of two composite numbers.
	 * 
	 * @param comp
	 * @return
	 */
	public Composite multiply(Composite comp) {

		AVL<BigInteger, Integer> other = new AVL<BigInteger, Integer>();

		for (Pair<BigInteger, Integer> a : tree.pairs()) {
			other.put(a.key, a.value);
		}
		for (Pair<BigInteger, Integer> b : comp.tree.pairs()) {
			if (other.get(b.key) != null) {
				other.put(b.key, other.get(b.key) + b.value);
			} else {
				other.put(b.key, b.value);
			}
		}
		Composite prod = new Composite(2);
		prod.tree = other;
		return prod;
	}

	/**
	 * Computes greatest common divisor with given composite number.
	 * 
	 * https://en.wikipedia.org/wiki/Greatest_common_divisor
	 * 
	 * When there is no common divisor (other than the value 1) this method returns
	 * null
	 * 
	 * @param comp    other number to compute gcd
	 * @return the greatest common divisor between this and comp.
	 */
	public Composite gcd(Composite comp) {

		BigInteger S = BigInteger.ZERO;
		BigInteger thisVal = this.value();
		BigInteger compVal = comp.value();

		while (thisVal.remainder(compVal).compareTo(BigInteger.ZERO) > 0) {
			S = thisVal.remainder(compVal);
			thisVal = compVal;
			compVal = S;
		}
		if (!compVal.equals(BigInteger.ZERO) && !compVal.equals(BigInteger.ONE)) {
			Composite newComp = new Composite(compVal);
			return newComp;
		}
		return null;
	}

	/**
	 * Computes least common multiple with given composite number.
	 * 
	 * https://en.wikipedia.org/wiki/Least_common_multiple
	 * 
	 * @param comp    other number to compute gcd
	 * @return the greatest common divisor between this and comp.
	 */
	public Composite lcm(Composite comp) {

		if (comp.tree.isEmpty() || tree.isEmpty()) {
			return null;
		}
		Composite lcm = new Composite();

		for (Pair<BigInteger, Integer> n : tree.pairs()) {
			for (Pair<BigInteger, Integer> compN : comp.tree.pairs()) {
				if (compN.key.equals(n.key)) {
					lcm.tree.put(compN.key, Math.max(compN.value, n.value));
				} else if (compN.key.compareTo(n.key) > 0) {
					lcm.tree.put(compN.key, compN.value);
				} else {
					lcm.tree.put(n.key, n.value);
				}
			}
		}
		return lcm;
	}

	/**
	 * Return Composite number with linked list of factors in ascending order.   
	 * 
	 * @param num
	 */
	public static Composite factorize(BigInteger num) {
		return new Composite(num);
	}
}
