package api.config.mapping.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

import api.config.mapping.Mapper;
import api.config.mapping.MapperPresetProvider;

/**
 * This class is a utility class, providing methods for easy creation of maps
 * and sets to be used in MapperPresets-Enums
 *
 * @author Santiago Gabriel Vollmar, Moritz Lauper
 *
 */
public abstract class PresetCreator {

	/**
	 * This method creates a new Function that maps its input to the given output
	 * class through {@link Mapper#map(Object, Class, MapperPresetContainer)}
	 *
	 * @param clazz           Class The class to convert the input of the newly
	 *                        created Function to
	 * @param presetContainer The mapping presets to use for the conversion
	 * @return Function A function converting its input to the desired output
	 */
	public static Function<Object, ?> mapConversion(Class<?> clazz, MapperPresetProvider presetContainer) {
		return (Object e) -> {
			try {
				return Mapper.instance().map(e, clazz, presetContainer);
			} catch (Exception e1) {
				return null;
			}
		};
	}

	/**
	 * This method creates a new MapEntry with the given key and value
	 *
	 * @param key   Key The key to assign to the new MapEntry
	 * @param value Value The value to assign to the new MapEntry
	 * @return MapEntry A new MapEntry with the given key and value
	 */
	public static <Key, Value> MapEntry<Key, Value> entry(Key key, Value value) {
		return new MapEntry<>(key, value);
	}

	/**
	 * This method creates a new HashMap containing the given MapEntries
	 *
	 * @param entries MapEntry The MapEntries to fill the newly created map with
	 * @return HashMap A new HashMap containing the given MapEntries
	 */
	@SafeVarargs
	public static <Key, Value> HashMap<Key, Value> map(MapEntry<Key, Value>... entries) {
		HashMap<Key, Value> map = new HashMap<Key, Value>((int) (entries.length * 1.5), 1f);

		for (MapEntry<Key, Value> entry : entries) {
			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}

	/**
	 * This method creates a new HashSet containing the given keys
	 *
	 * @param keys Key The key to fill the newly created set with
	 * @return HashSet A new HashSet containing the specified keys
	 */
	@SafeVarargs
	public static <Key> HashSet<Key> set(Key... keys) {
		return new HashSet<>(Arrays.asList(keys));
	}

	/**
	 * This method creates a new Mapping with the given classes
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static Mapping mapping(Class<?> from, Class<?> to) {
		return new Mapping(from, to);
	}
}
