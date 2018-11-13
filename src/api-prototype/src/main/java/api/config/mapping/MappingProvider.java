package api.config.mapping;

import api.config.mapping.util.Mapping;

/**
 * This interface ensures that a Mapping can be provided by the implementing
 * class. All MapperPreset-Enums must implement this interface
 *
 * @author Santiago Gabriel Vollmar
 *
 */
public interface MappingProvider {
	/**
	 * This method returns the {@link Mapping} provided by this
	 * {@link MappingProvider}
	 *
	 * @return Mapping The {@link Mapping} provided by this {@link MappingProvider}
	 */
	Mapping getMapping();
}
