package darr.mirr.utils.testrunner_helpers;


import org.junit.runner.Description;
import darr.mirr.utils.CaseID;

/**
 * Created by Pol Mir on 27.09.17.
 *
 */
public class JunitHelper {

    private JunitHelper() {
    }

    public static String getMethodCaseID(Description description) {
        if (isAnnotationPresent(CaseID.class, description)) {
            return description.getAnnotation(CaseID.class).value();
        }
        return "";
    }

    private static boolean isAnnotationPresent(Class klass, Description description) {
        return description.getAnnotation(klass) != null;
    }
}
