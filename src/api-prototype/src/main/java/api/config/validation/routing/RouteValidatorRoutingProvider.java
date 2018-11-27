package ch.nyp.noa.config.validation.routing;

/**
 * This interface is intended to be used with a {@link RouteValidator}, to
 * provide it's necessary routings.
 *
 * @author Santiago Gabriel Vollmar
 */
@Deprecated
public interface RouteValidatorRoutingProvider {
	
	/**
	 * This method provides the routings to be used by a {@link RouteValidator}.
	 *
	 * @return Routings to be used by a {@link RouteValidator}
	 */
	Iterable<ValidationRouting<?>> provideRoutings();
}
