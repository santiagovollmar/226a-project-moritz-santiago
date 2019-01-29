package ch.nyp.noa.config.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import ch.nyp.noa.config.validation.annotation.Validation;
import ch.nyp.noa.config.validation.annotation.Validations;

@Component
public class ValidationClassesRegistrar implements ApplicationRunner {
	
	private static final String BASE_PACKAGE = "ch.nyp.noa.webContext";
	
	private ValidationRegistry validationRegistry;
	
	/**
	 * @param validationRegistry
	 */
	@Autowired
	public ValidationClassesRegistrar(ValidationRegistry validationRegistry) {
		super();
		this.validationRegistry = validationRegistry;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// find classes annotated with @Validation
		List<Class<?>> validationClasses = findClasses(BASE_PACKAGE, metadataReader -> {
			try {
				return metadataReader.getAnnotationMetadata().hasAnnotation(Validations.class.getName());
			} catch (Throwable e) {}
			
			return false;
		});
		
		// register found classes
		for (Class<?> validationClass : validationClasses) {
			Validations validations = validationClass.getAnnotation(Validations.class);
			if (validations != null) {
				for (Validation validation : validations.value()) {
					validationRegistry.register(validationClass, validation);
				}
			}
		}
	}
	
	/**
	 * This method returns a list of classes that are in the given base package and
	 * that are annotated with the given annotation
	 *
	 * @param basePackage
	 * @throws IOException 
	 */
	private List<Class<?>> findClasses(String basePackage, CandidateEvaluator candidateEvaluator) throws IOException
			{
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
			try {
				if (resource.isReadable()) {
					MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
					
					if (candidateEvaluator.isCandidate(metadataReader)) {
						candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
					}
				}
			} catch (Exception ignore) {} // meh, just skip the resource
		}
		return candidates;
	}
	
	/**
	 * Resolves the base package
	 *
	 * @param  basePackage The base package to resolve
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
	 * @param  metadataReader
	 * @return
	 */
	boolean isCandidate(MetadataReader metadataReader);
}