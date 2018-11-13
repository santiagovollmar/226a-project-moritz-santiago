package api.config.mapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * This class registers all classes in the given base package annotated
 * with @Mappable with the global mapper at application startup <br>
 * This class also registers all present mappings listed in enums annotated
 * with @MapperPresetsListing
 *
 * @author Santiago Gabriel Vollmar, Moritz Lauper
 *
 */
@Component
public class MappableRegisterRunner implements ApplicationRunner {
	private static final String BASE_PACKAGE = "ch.nyp.noa.webContext";

	/**
	 * Runs at application startup
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*
		 * register @Mappables
		 */
		// find classes annotated with @Mappable
		List<Class<?>> mappables = findClasses(BASE_PACKAGE, metadataReader -> {
			try {
				return Class.forName(metadataReader.getClassMetadata().getClassName())
						.isAnnotationPresent(Mappable.class);
			} catch (Throwable e) {
			}

			return false;
		});

		// register classes with the mapper instance
		Mapper.instance().registerClass(mappables);

		/*
		 * register @MapperPresetsListings
		 */
		// find classes annotated with @MapperPresetsListing
		List<Class<?>> mapperPresetListings = findClasses(BASE_PACKAGE, metadataReader -> {
			try {
				Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());

				boolean valid = true;

				valid &= clazz.isAnnotationPresent(MapperPresetsListing.class);
				valid &= MappingProvider.class.isAssignableFrom(clazz);
				valid &= clazz.isEnum();

				return valid;
			} catch (Throwable e) {
			}

			return false;
		});

		// register mappings
		for (Class<?> mapperPresetListing : mapperPresetListings) {
			@SuppressWarnings("unchecked")
			MappingProvider[] mappingProviders = ((Class<MappingProvider>) mapperPresetListing).getEnumConstants();
			Mapper.instance().registerMapping(Arrays.asList(mappingProviders));
		}
	}

	/**
	 * This method returns a list of classes that are in the given base package and
	 * that are annotated with the given annotation
	 *
	 * @param basePackage
	 */
	private List<Class<?>> findClasses(String basePackage, CandidateEvaluator candidateEvaluator)
			throws IOException, ClassNotFoundException {
		// create list for found candidates
		List<Class<?>> candidates = new ArrayList<>();

		// create classpath reader objects
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

		// construct base package search path
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage)
				+ "/" + "**/*.class";

		// search through resources
		for (Resource resource : resourcePatternResolver.getResources(packageSearchPath)) {
			if (resource.isReadable()) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);

				if (candidateEvaluator.isCandidate(metadataReader)) {
					candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
				}
			}
		}
		return candidates;
	}

	/**
	 * Resolves the base package
	 *
	 * @param basePackage The base package to resolve
	 * @return
	 */
	private String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}
}

interface CandidateEvaluator {
	/**
	 * checks if a resource is a candidate for the current search
	 *
	 * @param metadataReader
	 * @return
	 */
	boolean isCandidate(MetadataReader metadataReader);
}