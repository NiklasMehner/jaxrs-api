package javax.ws.rs.ext;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * The default locale resolvers returns the highest ranked acceptable language (if available).
 *
 * It runs at DEFAULT_PRIORITY + 1 so custom implementations of the LocaleResolver interface without a @Priority
 * annotation take precedence of the default implementation.
 */
@ApplicationScoped
@Priority(value = LocaleResolver.DEFAULT_PRIORITY + 1)
public class DefaultLocaleResolver implements LocaleResolver {

    @Inject
    private HttpHeaders headers;

    @Override
    public Optional<Locale> resolve() {
        List<Locale> languages = headers.getAcceptableLanguages();
        return languages.isEmpty() ? Optional.empty() : Optional.of(languages.get(0));
    }
}
