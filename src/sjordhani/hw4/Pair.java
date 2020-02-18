package sjordhani.hw4;

/**
 * Represents a (key, Value) pair.
 * 
 * You will use this when storing a factor 49 for a number as 7^2 where key=7 and value=2.
 * 
 * No need to modify this method.
 */
public class Pair<Key extends Comparable<Key>,Value> implements Comparable<Key> {
	final Key key;
	final Value value;
	
	public Pair (Key k, Value v) {
		this.key = k;
		this.value = v;
	}
	
	/** Comparable only needed on key. */
	@Override
	public int compareTo(Key other) {
		return key.compareTo(other);
	}
}
