package sjordhani.hw4;

import java.util.Iterator;

public class SamplePairUsage {
	public static void main(String[] args) {
		AVL<Integer,String> sample = new AVL<Integer,String>();
		
		sample.put(10, "ten");
		sample.put(20, "twenty");
		sample.put(30, "thirty");  // note this will force a rotation
		
		System.out.println(sample.rotations + " should be 1.");
		
		System.out.println("The keys (with their values) in order are:");
		for (Pair<Integer,String> pair : sample.pairs()) {
			System.out.println(pair.key + "=" + pair.value);
		}
		
		// also can do this with an iterator
		for (Iterator<Pair<Integer,String>> it = sample.pairs().iterator(); it.hasNext(); ) {
			Pair<Integer,String> pair = it.next();
			System.out.println(pair.key + "=" + pair.value);
		}
	}
}
