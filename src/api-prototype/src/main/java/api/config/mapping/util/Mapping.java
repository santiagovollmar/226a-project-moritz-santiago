package api.config.mapping.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

import api.config.mapping.MapperPreset;
import api.config.mapping.MappingProvider;

/**
 * This class represents a mapping from one class to another with a specific
 * mapper preset
 * 
 * @author Santiago Gabriel Vollmar
 *
 */
public class Mapping implements MappingProvider {
	private Class<?> from;
	private Class<?> to;
	private MapperPreset preset;

	/**
	 *
	 */
	public Mapping() {
	}

	/**
	 * @param from
	 * @param to
	 * @param preset
	 */
	public Mapping(Class<?> from, Class<?> to) {
		this.from = from;
		this.to = to;
		preset = new MapperPreset();
	}

	/**
	 *
	 * @param after
	 * @return
	 */
	public Mapping withAfter(BiConsumer<Object, Object> after) {
		preset.setAfter(after);
		return this;
	}

	/**
	 *
	 * @param aliases
	 * @return
	 */
	public Mapping withAliases(HashMap<String, String> aliases) {
		preset.setAliases(aliases);
		return this;
	}

	/**
	 *
	 * @param conversions
	 * @return
	 */
	public Mapping withConversions(HashMap<String, Function<Object, ?>> conversions) {
		preset.setConversions(conversions);
		return this;
	}

	/**
	 *
	 * @param ignores
	 * @return
	 */
	public Mapping withIgnores(HashSet<String> ignores) {
		preset.setIgnores(ignores);
		return this;
	}

	/**
	 * @return the from
	 */
	public Class<?> getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public Class<?> getTo() {
		return to;
	}

	/**
	 * @return the preset
	 */
	public MapperPreset getPreset() {
		return preset;
	}

	@Override
	public Mapping getMapping() {
		return this;
	}
}
