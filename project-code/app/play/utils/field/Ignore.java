package play.utils.field;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates to ignore this field in the list of fields in the metamodel.
 * 
 * @author Ludovic Chane Won In
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface Ignore {
}
