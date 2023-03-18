package multitaks.annotations.directory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import multitaks.enums.FieldType;

/**
 *
 * @author dogi_
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Key{
    
    String value();
    FieldType type() default(FieldType.DEFAULT);
    
}
