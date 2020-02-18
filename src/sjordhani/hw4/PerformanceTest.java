package sjordhani.hw4;

/**
 * Used to validate the performance of Composite.multiply.
 * 
 * Once you have completed the implementation of Composite, this class 
 * can be used "AS IS" to validate performance of multiply for very large numbers.
 */
public class PerformanceTest {  
	public static void main(String[] args) {
		
		// start at 100!
		Composite fact = new Composite(2);  // n! for n=3
		int n;
		for (n = 3; n <= 100;  n++) { 
			fact = fact.multiply(new Composite(n));
		}
		
		Composite next = null;
		System.out.println("\nn\tTime");
		n = 100;
		while (n <= 102400) {
			long total = Long.MAX_VALUE;
			for (int t = 0; t < 80; t++) {

				System.gc();
				long now = System.nanoTime();
				for (int ct = 0; ct < 100; ct++) {
					next = fact.multiply(fact);       // Compute (n!)^2 a number of times
				}
				total = Math.min(total, System.nanoTime() - now);
			}
			System.out.println(n + "\t" + total + "\t" + next);
			
			// advance n
			int target = 2*n;
			while (n <= target) {
				fact = fact.multiply(new Composite(n));
				n++;
			}
			n = target;
		}
	}
}
