package pol.mirr.utils.testrunner_helpers.filters;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import pol.mirr.utils.testrunner_helpers.JunitHelper;

import java.util.List;

/**
 * Created by Pol Mirr on 15.09.17.
 *
 * Check test caseID against caseIDs from test run list
 * If match then return true, and test going to run.
 */
public class TestCaseFilter extends Filter {

    private List<String> testRunCaseIDs;

    public TestCaseFilter(List<String> testRunCaseIDs) {
        this.testRunCaseIDs = testRunCaseIDs;
    }

    @Override
    public boolean shouldRun(Description description) {
        if (description != null) {
            if (description.isSuite()) {
                return true;
            }
            if (description.isTest()) {
                String methodCaseID = JunitHelper.getMethodCaseID(description);
                return testRunCaseIDs.contains(methodCaseID);
            }
        }
        return false;
    }

    @Override
    public String describe() {
        return "TestCase Filter";
    }
}