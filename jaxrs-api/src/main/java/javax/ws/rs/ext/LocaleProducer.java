package javax.ws.rs.ext;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Priority;
import javax.ws.rs.core.JaxRsRequestScoped;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Producer class for the request locale of a jax rs request.
 * This iterates through the available LocaleResoler implementations sorted ascending by their priority and
 * returns the locale of the first LocaleResolver that produces a value. If no locale is produces by any producer
 * the systems default locale is returned.
 */
@ApplicationScoped
@Alternative
@Priority(LocaleProducer.DEFAULT_PRODUCER_PRIORITY)
public class LocaleProducer {
    static final int DEFAULT_PRODUCER_PRIORITY = 100;

    @Inject
    @Any
    private Instance<LocaleResolver> resolvers;

    private List<LocaleResolver> sortedResolvers;

    @PostConstruct
    private void initializeSortedResolvers() {
        sortedResolvers = resolvers.stream()
                .sorted(comparing(this::resolverPriority))
                .collect(toList());
    }

    @Produces @JaxRsRequestScoped @Named("RequestLocale") @Default
    public Locale produceLocale() {
        return sortedResolvers.stream()
                .map(LocaleResolver::resolve)
                .flatMap(this::optionalStream)
                .findFirst()
                .orElse(Locale.getDefault());
    }

    private Integer resolverPriority(LocaleResolver resolver) {
        Priority priority = resolver.getClass().getAnnotation(Priority.class);
        return priority != null ? priority.value() : LocaleResolver.DEFAULT_PRIORITY;
    }

    private Stream<Locale> optionalStream(Optional<Locale> o) {
        // TODO: for JDK9 replace with: o.stream()
        return o.isPresent() ? Stream.of(o.get()) : Stream.empty();
    }
}
