package darr.mirr.utils.testrunner_helpers.listeners;

import com.codepine.api.testrail.model.Result;
import darr.mirr.utils.testcase_manager.TestCaseManager;
import darr.mirr.utils.testrunner_helpers.JunitHelper;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Darr Mirr on 27.09.17.
 * <p>
 * Слушатель, который собирает результаты прохождения теста и отправляет в Тест кейс менеджер
 */
public class CollectResultListener extends RunListener {
    private static final int SUCCESS = 1;
    private static final int RETEST = 4;
    private static final int FAILED = 5;
    private static final Logger logger = LoggerFactory.getLogger(CollectResultListener.class);
    private TestCaseManager<Result> testCaseManager;
    private ConcurrentHashMap<String, Result> testResultMap;

    public CollectResultListener(TestCaseManager<Result> testCaseManager) {
        this.testCaseManager = testCaseManager;
        testResultMap = new ConcurrentHashMap<>();
    }

    @Override
    public void testStarted(Description description) throws Exception {
        var result = getDefaultTestResult(description);
        if (result.getCaseId() != null) {
            testResultMap.put(description.getMethodName(), result);
        }
    }

    @Override
    public void testFinished(Description description) throws Exception {
        if (testResultMap.containsKey(description.getMethodName())) {
            Result testResult = testResultMap.remove(description.getMethodName());
            logger.info("Send to testcaseManager: {}", testResult);
            testCaseManager.addResult(testResult, getAttachment(description));
        } else {
            logger.warn("Test result is absent for method {}", description.getMethodName());
        }
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        if (testResultMap.containsKey(failure.getDescription().getMethodName())) {
            Result result = testResultMap.get(failure.getDescription().getMethodName());
            var status = getFailStatus(failure.getException());
            result.setStatusId(status);
            result.setComment(failure.getMessage());
        }
    }

    private int getFailStatus(Throwable e) {
        if (e instanceof AssertionError) {
            return FAILED;
        }
        return RETEST;
    }

    private Result getDefaultTestResult(Description description) {
        var caseID = JunitHelper.getMethodCaseID(description);
        var result = new Result();
        result.setCaseId(caseID.equals("") ? null : Integer.valueOf(caseID));
        result.setStatusId(SUCCESS);
        return result;
    }

    private String getAttachment(Description description) {
        return !description.getChildren().isEmpty() ? description.getChildren().get(0).getDisplayName() : null;
    }
}
