package ch.nyp.noa.config.validation.util;


public class Pair<T, S> {
	
	public final T left;
	
	public final S right;
	
	/**
	 * @param left
	 * @param right
	 */
	public Pair(T left, S right) {
		super();
		this.left = left;
		this.right = right;
	}
	
}
