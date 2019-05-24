package darr.mirr.utils.case_providers;

import darr.mirr.utils.testcase_manager.TestCaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darr Mirr on 15.09.17.
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
    public void addResult(Object result, String attachmentPath) {
        logger.info("------- TestCaseManager is adding result to queue");
        logger.info("------- Added item queue: {}", result.toString());
    }
}
