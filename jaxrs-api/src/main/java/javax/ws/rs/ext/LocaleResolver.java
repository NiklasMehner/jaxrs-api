package javax.ws.rs.ext;

import java.util.Locale;
import java.util.Optional;

/**
 * The LocaleResolver interface allows customizing the determination of the request locale for a jax rs request.
 *
 * If multiple resolvers are found they are evaluated in the ascending order of their @javax.inject.Priority
 * annotations. LocaleResolvers should be implemented in the application scope.
 */
public interface LocaleResolver {
    int DEFAULT_PRIORITY = 0;

    Optional<Locale> resolve();
}
