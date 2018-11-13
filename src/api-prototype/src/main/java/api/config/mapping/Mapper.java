package api.config.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

import api.config.mapping.util.Mapping;

/**
 * This class is responsible for mapping different classes.
 *
 * See also: {@link #map(Object, Class, MapperPresetContainer)},
 * {@link #map(Object, Class, HashSet, HashMap, HashMap)},
 * {@link #register(Class)}
 *
 * @author Santiago Gabriel Vollmar, Moritz Lauper
 */
public class Mapper {
	/**
	 * This field is the globally used instance of this class
	 */
	private static Mapper instance;

	/**
	 * This field is a HashMap containing {@link CopyOnWriteArrayList}s of class
	 * fields. <br>
	 * <b>These lists are not to be edited!</b>
	 */
	private HashMap<String, CopyOnWriteArrayList<Field>> classFields = new HashMap<>();

	/**
	 * This field is a HashMap containing projections of registered classes (HashMap
	 * representations) <br>
	 * <b>These maps are not to be edited!</b>
	 */
	private HashMap<String, HashMap<String, Field>> classProjections = new HashMap<>();

	/**
	 * This field is a HashMap containing {@link Constructors}s of all registered
	 * classes <br>
	 * <b>These constructors are not to be edited!</b>
	 */
	private HashMap<String, Constructor<?>> classConstructors = new HashMap<>();

	/**
	 * This field contains mapper presets for conversions between registered
	 * classes. (from.getClass().getName() is key of outer map and
	 * to.getClass().getName() of inner) <br>
	 * <b>These maps are not to be edited!</b>
	 */
	private HashMap<String, HashMap<String, MapperPresetProvider>> classMappings = new HashMap<>();

	/**
	 * This method returns the globally used instance of this class
	 *
	 * @return Mapper The globally used instance of this class
	 */
	public static Mapper instance() {
		return instance != null ? instance : (instance = new Mapper());
	}

	/**
	 * This method maps all elements present in the given iterable to the given
	 * class using {@link #map(Object, Class, MapperPresetContainer)}. The mapped
	 * objects are returned in the collection supplied by the given collection
	 * supplier
	 *
	 * @param fromEntities      Iterable An iterable containing the objects to be
	 *                          mapped
	 * @param toClass           Class The class to map the given objects to
	 * @param presetContainer   The preset to use for the conversion
	 * @param collectionFactory A Supplier providing a collection to return the
	 *                          mapped objects in
	 * @return C A collection provided by the given collection factory containing
	 *         the mapped objects
	 */
	public <From, To, C extends Collection<To>> C mapAll(Iterable<From> fromEntities, Class<To> toClass,
			MapperPresetProvider presetContainer, Supplier<C> collectionFactory) {
		C toEntities = collectionFactory.get();

		for (From from : fromEntities) {
			toEntities.add(map(from, toClass, presetContainer));
		}

		return toEntities;
	}

	/**
	 * This method acts as a wrapper method for
	 * {@link #map(Object, Class, HashSet, HashMap, HashMap)} enabling the use of
	 * presets. This means that the arguments
	 * <code>ignore, aliases, conversions</code> will be fetched from the given
	 * {@link MapperPresetContainer}. The BiConsumer specified in the
	 * presetContainer's field <code>after</code> will be called after the mapping
	 * process with the given instance <code>from</code> as first argument and the
	 * newly created instance <code>to</code> as the second argument.
	 *
	 * @param from            From The instance to be passed forward to
	 *                        {@link #map(Object, Class, HashSet, HashMap, HashMap)}
	 * @param toClass         Class<To> The class to be passed forward to
	 *                        {@link #map(Object, Class, HashSet, HashMap, HashMap)}
	 * @param presetContainer MapperPresetContainer A preset container for mapping
	 *                        options
	 * @return To Forwarded return value of
	 *         {@link #map(Object, Class, HashSet, HashMap, HashMap)}
	 * @throws RuntimeException if an exception occurred during the mapping process
	 */
	public <From, To> To map(From from, Class<To> toClass, MapperPresetProvider presetContainer)
			throws RuntimeException {
		MapperPreset preset = presetContainer.getPreset();

		To to = map(from, toClass, preset.getIgnores(), preset.getAliases(), preset.getConversions());

		if (preset.getAfter() != null) {
			preset.getAfter().accept(from, to);
		}

		return to;
	}

	/**
	 * This method attempts to map the properties of <code>from</code> to a newly
	 * created instance of <code>toClass</code> and returns it. The classes of all
	 * objects to be mapped by this method must have previously been registered with
	 * {@link #register(Class)} or else an exception will be thrown. Additionally,
	 * all objects to be created by this method (that is of class
	 * <code>Class<To> toClass</code>) must have a default constructor. <br>
	 * <br>
	 * This method will map out the fields of both <code>From</code> and
	 * <code>To</code> and search every field of <code>To</code> in the fields of
	 * <code>From</code>. If a field of <code>To</code> was found in the fields of
	 * <code>From</code> it's value will be copied to the newly created object which
	 * is about to be returned. If a field was not found, it will keep it's default
	 * value. <br>
	 * <br>
	 * The behaviour of this method can be modified by the extra parameters as
	 * follows:
	 * <ul>
	 * <li><b>ignore:</b> Fields of <code>To</code> listed here will be ignored and
	 * not attempted to copy</li>
	 * <li><b>aliases:</b> Fields of <code>To</code> specified by a key in this map
	 * will be searched for by the respective value stored in the map instead of the
	 * fields value itself</li>
	 * <li><b>conversions:</b> Before copying a value from a field of
	 * <code>From</code> to a field of <code>To</code> listed as key in this map.
	 * It's value will be passed through the associated {@link Function} before
	 * performing said operation</li>
	 * </ul>
	 *
	 * @param from        From The instance to copy the properties from
	 * @param toClass     Class<To> The class of the desired output instance
	 * @param ignore      HashSet<String> A list of fields to be ignored (declared
	 *                    in <code>To</code>)
	 * @param aliases     HashMap<String, String> A map, mapping fields declared in
	 *                    <code>To</code> to differently named fields in
	 *                    <code>From</code>
	 * @param conversions HashMap<String, Function> A map containing type
	 *                    conversions for fields declared in <code>To</code>
	 * @return To A newly created instance of the specified type containing the
	 *         field-values of <code>from</code>
	 * @throws RuntimeException If an exception occurred during the mapping process
	 */
	public <From, To> To map(From from, Class<To> toClass, HashSet<String> ignores, HashMap<String, String> aliases,
			HashMap<String, Function<Object, ?>> conversions) throws RuntimeException {
		if (from == null) {
			return null;
		}
		
		try {
			// create new instance to be filled out
			To to = getStandardConstructor(toClass).newInstance();

			// get all fields of To to
			CopyOnWriteArrayList<Field> toFields = fetchFields(toClass);

			// project From from
			Class<From> fromClass = (Class<From>) from.getClass();
			HashMap<String, Field> fromProjection = getProjection(fromClass);

			// set fields of to
			for (Field toField : toFields) {
				// check if field should get ignored
				if (ignores != null && ignores.contains(toField.getName())) {
					continue;
				}

				// get value of toField stored in from object
				String fieldToGet = null;
				if (aliases != null) {
					fieldToGet = aliases.getOrDefault(toField.getName(), toField.getName());
				} else {
					fieldToGet = toField.getName();
				}

				Field fromField = fromProjection.get(fieldToGet);
				Object value = fromField != null ? fromField.get(from) : null;

				// apply conversion if set in conversions for current field
				if (toField.getType() != fromField.getType()) {
					Function<Object, ?> conversion = null;
					if (conversions != null) {
						conversion = conversions.get(toField.getName());
					}

					if (conversion != null) {
						value = conversion.apply(value);
					} else {
						// get presetContainer from classMappings
						MapperPresetProvider presetContainer = getMapping(fromField.getType(), toField.getType());

						// map value if preset was found
						if (presetContainer != null) {
							value = map(value, toField.getType(), presetContainer);
						} else {
							value = null;
						}
					}
				}

				// set value
				toField.set(to, value);
			}

			return to;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/**
	 * This method returns a HashMap representation of the given class if it was
	 * previously registered with {@link Mapper#register(Class)}. The field names of
	 * the class are stored as keys, pointing to their respective Field objects.
	 *
	 * @param clazz
	 * @return HashMap A HashMap representing the given class
	 */
	private <Instance> HashMap<String, Field> getProjection(Class<Instance> clazz) {
		return classProjections.get(clazz.getName());
	}

	/**
	 * This method returns the default constructor of the given class if it was
	 * previously registered with {@link Mapper#register(Class)}.
	 *
	 * @param clazz Class The class to return the default constructor of
	 * @return Constructor The default constructor of the given class
	 */
	private <T> Constructor<T> getStandardConstructor(Class<T> clazz) {
		return (Constructor<T>) classConstructors.get(clazz.getName());
	}

	/**
	 * This method returns a MapperPresetProvider if the expressed mapping was
	 * previously registered with {@link Mapper#registerMapping(Mapping)}
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	private MapperPresetProvider getMapping(Class<?> from, Class<?> to) {
		// get presetContainer from classMappings
		MapperPresetProvider presetProvider = null;
		{
			HashMap<String, MapperPresetProvider> subClassMappings = classMappings.get(from.getName());
			if (subClassMappings != null) {
				presetProvider = subClassMappings.get(to.getName());
			}
		}

		return presetProvider;
	}

	/**
	 * This method returns a {@link CopyOnWriteArrayList} containing all declared
	 * fields of the given class and all it's superclasses if the class was
	 * previously registered with {@link #register(Class)}. <br>
	 * <b>This list and the elements contained in it are not to be edited!</b>
	 *
	 * @param clazz Class<?> The Class which's fields should be returned
	 * @return CopyOnWriteArrayList<Field> A list containing all declared
	 */
	private CopyOnWriteArrayList<Field> fetchFields(Class<?> clazz) {
		return classFields.get(clazz.getName());
	}

	/**
	 * This method creates a map representation of the given instance and stores it
	 * in <code>classProjections</code>. All field names (including inherited) of
	 * the given class will be set as keys in a newly created HashMap. Each
	 * referencing the respective Field objects.
	 *
	 * @param clazz Class<Instance> The class to be used to analyze the fields
	 */
	private <Instance> void registerProjection(Class<Instance> clazz) {
		// get fields
		CopyOnWriteArrayList<Field> fields = fetchFields(clazz);

		// create projection map
		HashMap<String, Field> projection = new HashMap<>(fields.size(), 2f); // use number of fields as size and 2f as
																				// load factor to prevent resizing of
																				// the map
		// project fields
		try {
			for (Field field : fields) { // TODO maybe perform these operations on a thread pool to increase performance
											// (overhead worth?)
				projection.put(field.getName(), field);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		synchronized (classProjections) {
			classProjections.put(clazz.getName(), projection);
		}
	}

	/**
	 * This method registers the given class with this Mapper so that it can later
	 * be mapped by it.
	 *
	 * @param clazz Class<?> The class to register with this Mapper
	 * @throws Error If the given class doesn't have a standard constructor
	 */
	public void registerClass(Class<?> clazz) throws Error {
		/*
		 * register class fields
		 */
		// save class name for later use
		Class<?> key = clazz;

		// create new list
		CopyOnWriteArrayList<Field> fields = new CopyOnWriteArrayList<>();

		// add fields to fill while going up the inheritance tree
		while (clazz != null) {
			Collections.addAll(fields, clazz.getDeclaredFields());

			clazz = clazz.getSuperclass();
		}

		// make all fields accessible
		for (Field field : fields) {
			field.setAccessible(true);
		}

		synchronized (classFields) {
			classFields.put(key.getName(), fields);
		}

		/*
		 * register projection
		 */
		registerProjection(key);

		/*
		 * register constructor
		 */
		synchronized (classConstructors) {
			try {
				classConstructors.put(key.getName(), key.getDeclaredConstructor());
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error(String.format("Given class %s has no standard constructor", clazz.getName()));
			}
		}
	}

	/**
	 * This method registers a mapping for later automatic use in
	 * {@link #map(Object, Class, HashSet, HashMap, HashMap)}
	 *
	 * @param mapping The mapping to register
	 */
	public void registerMapping(MappingProvider mappingProvider) {
		Mapping mapping = mappingProvider.getMapping();

		Class<?> fromClass = mapping.getFrom();
		Class<?> toClass = mapping.getTo();

		HashMap<String, MapperPresetProvider> subClassMappings = classMappings.get(fromClass.getName());
		if (subClassMappings == null) {
			subClassMappings = new HashMap<>();

			synchronized (classMappings) {
				classMappings.put(fromClass.getName(), subClassMappings);
			}
		}

		synchronized (subClassMappings) {
			subClassMappings.put(toClass.getName(), mapping.getPreset());
		}
	}

	/**
	 * This method acts as a convenience method for {@link #registerClass(Class)} to
	 * register multiple classes at once
	 *
	 * @param classes Iterable<Class<?>> The classes to pass forward to
	 *                {@link #registerClass(Class)}
	 */
	public void registerClass(Iterable<Class<?>> classes) {
		for (Class<?> clazz : classes) {
			registerClass(clazz);
		}
	}

	/**
	 * This method acts as a convenience method for
	 * {@link #registerMapping(Mapping)} to register multiple mappings at once
	 *
	 * @param mappings Iterable<Mapping<?, ?> The mappings to pass forward to
	 *                 {@link #registerMapping(Mapping)}
	 */
	public void registerMapping(Iterable<MappingProvider> mappingProviders) {
		for (MappingProvider mappingProvider : mappingProviders) {
			registerMapping(mappingProvider);
		}
	}
}
