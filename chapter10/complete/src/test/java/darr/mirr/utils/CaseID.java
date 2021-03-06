package darr.mirr.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Pol Mir on 30.08.17.
 *
 * Annotation caseID link test case and method which represent automation script of it
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CaseID {

    String value();
}
