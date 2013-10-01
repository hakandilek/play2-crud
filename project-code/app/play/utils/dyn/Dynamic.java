package play.utils.dyn;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * indicates a dynamic controller or DAO class.
 * 
 * @author Hakan Dilek
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Dynamic {

}
