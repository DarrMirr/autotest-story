package darr.mirr.utils.case_providers;

import static darr.mirr.data.SystemProperties.*;

/**
 * Created by Darr Mirr on 12.09.17.
 *
 * Class return Case Provider depend on setting system property
 */
public class CaseProviders {

    private CaseProviders() {
    }

    public static CaseProvider get() {
        if (isPresentTestRailProperties() && !isPresentCaseidProperty()) {
            return new MockTestManager();
        }
        if (isPresentCaseidProperty() && !isPresentTestRailProperties()) {
            return new SimpleCaseProvider();
        }
        throw new IllegalArgumentException("Test case provider doesn't determine. Please, check correction arguments filterWith -D prefix.");
    }

    private static boolean isPresentTestRailProperties() {
        return SUITE_ID != null && NEW_TESTRUN_NAME != null && PROJECT_ID != null;
    }

    private static boolean isPresentCaseidProperty() {
        return CASE_ID_PROPERTY != null;
    }
}
