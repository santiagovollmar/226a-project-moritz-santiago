package api.config.mapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class holds configuration data for
 * {@link Mapper#map(Object, Class, MapperPresetContainer)}
 *
 * @author Santiago Gabriel Vollmar, Moritz Lauper
 *
 */
public class MapperPreset implements MapperPresetProvider {
	private HashSet<String> ignores;
	private HashMap<String, String> aliases;
	private HashMap<String, Function<Object, ?>> conversions;
	private BiConsumer<Object, Object> after;

	/**
	 *
	 */
	public MapperPreset() {
	}

	/**
	 * @param ignore
	 * @param aliases
	 * @param conversions
	 * @param after
	 */
	public MapperPreset(HashSet<String> ignores, HashMap<String, String> aliases,
			HashMap<String, Function<Object, ?>> conversions, BiConsumer<Object, Object> after) {
		this.ignores = ignores;
		this.aliases = aliases;
		this.conversions = conversions;
		this.after = after;
	}

	/**
	 * @return the ignore
	 */
	public HashSet<String> getIgnores() {
		return ignores;
	}

	/**
	 * @param ignore the ignore to set
	 */
	public void setIgnores(HashSet<String> ignores) {
		this.ignores = ignores;
	}

	/**
	 * @return the aliases
	 */
	public HashMap<String, String> getAliases() {
		return aliases;
	}

	/**
	 * @param aliases the aliases to set
	 */
	public void setAliases(HashMap<String, String> aliases) {
		this.aliases = aliases;
	}

	/**
	 * @return the conversions
	 */
	public HashMap<String, Function<Object, ?>> getConversions() {
		return conversions;
	}

	/**
	 * @param conversions the conversions to set
	 */
	public void setConversions(HashMap<String, Function<Object, ?>> conversions) {
		this.conversions = conversions;
	}

	/**
	 * @return the after
	 */
	public BiConsumer<Object, Object> getAfter() {
		return after;
	}

	/**
	 * @param after the after to set
	 */
	public void setAfter(BiConsumer<Object, Object> after) {
		this.after = after;
	}

	@Override
	public MapperPreset getPreset() {
		return this;
	}
}
