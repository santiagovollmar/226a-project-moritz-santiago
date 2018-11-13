package api.config.mapping.util;

/**
 * This class represents an Entry of a HashMap
 *
 * @author Santiago Gabriel Vollmar, Moritz Lauper
 *
 * @param <Key> The Type of this entry's key
 * @param <Value> The Type of this entry's value
 */
public class MapEntry<Key, Value> {
	private Key key;
	private Value value;

	/**
	 *
	 */
	public MapEntry() {
	}

	/**
	 * @param key
	 * @param value
	 */
	public MapEntry(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Value value) {
		this.value = value;
	}
}
