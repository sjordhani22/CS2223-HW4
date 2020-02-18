package sjordhani.hw4;

import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 * All you have to do for Question 1 is rearrange the values in line 17.
 * 
 */

public class Question1 {

	public static void main(String[] args) {

		AVL<Integer, Boolean> tree = new AVL<Integer, Boolean>();

		AVL<Integer, Boolean> avl = new AVL<Integer, Boolean>();

		int[] values = { 8, 10, 4, 11, 9, 6, 2, 12, 7, 5, 3, 1 };
		int maxHt = 0;
		int maxRt = 0;

		for (int i : values) {
			tree.put(i, true); 
		}

		StdOut.println("Number of rotations:" + tree.rotations);
		StdOut.println("Height of tree:" + tree.height());
		StdRandom.shuffle(values);

		StdOut.println("N\tMaxHt.\tMaxRot.");
		int n = 1;
		for (int i = 1; i <= 4095; n++, i = (int) Math.pow(2, n) - 1) {
			int[] arr = new int[i];
			for (int j = 0; j < i; j++) {
				arr[j] = j + 1;
			}
			for (int k = 0; k <= 3000; k++) {
				avl = new AVL<Integer, Boolean>();
				StdRandom.shuffle(arr);
				for (int h : arr) {
					avl.put(h, true);
				}
				if (maxHt < avl.height()) {
					maxHt = avl.height();
				}
				if (maxRt < avl.rotations) {
					maxRt = avl.rotations;
				}
			}
			arr = null;
			StdOut.println(i + "\t" + maxHt + "\t" + maxRt);
			maxHt = 0;
			maxRt = 0;
		}
		for (int i = 1; i <= 12; i++) {
			if (!tree.contains(i)) {
				StdOut.println("Error:  doesn't contain " + i);
			}
		}
	}
}