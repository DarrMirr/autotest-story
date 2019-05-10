package pol.mirr.utils.case_providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pol.mirr.utils.testcase_manager.TestCaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pol Mirr on 15.09.17.
 *
 * This class mock interaction filterWith Test Management System
 */
public class MockTestManager implements CaseProvider, TestCaseManager {
    private static final Logger logger = LoggerFactory.getLogger(MockTestManager.class);

    @Override
    public List<String> getCaseID() {
        List<String> caseIDs = new ArrayList<>();
        caseIDs.add("2");
        caseIDs.add("3");
        caseIDs.add("5");
        return caseIDs;
    }

    @Override
    public String addResult(Object result) {
        logger.info("------- TestCaseManager is adding result to queue");
        logger.info("------- Added item queue: {}", result.toString());
        return "Ok";
    }
}
