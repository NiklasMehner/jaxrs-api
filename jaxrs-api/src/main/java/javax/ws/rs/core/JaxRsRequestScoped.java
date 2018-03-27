package javax.ws.rs.core;

import javax.enterprise.context.NormalScope;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The JaxRsRequestScoped is the CDI scope for jax rs specifix objects.
 *
 * The JaxRsRequestScoped is activated before the invocation of a method handling a jax rs request and closed after the
 * method execution is finished.
 */
@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@NormalScope
@Inherited
public @interface JaxRsRequestScoped {
}
