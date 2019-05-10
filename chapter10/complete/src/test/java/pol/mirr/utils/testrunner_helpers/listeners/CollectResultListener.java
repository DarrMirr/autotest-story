package pol.mirr.utils.testrunner_helpers.listeners;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import pol.mirr.utils.LogService;
import pol.mirr.utils.testcase_manager.TestCaseManager;
import pol.mirr.utils.testcase_manager.TestResult;
import pol.mirr.utils.testrunner_helpers.JunitHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pol Mir on 27.09.17.
 * <p>
 * Слушатель, который собирает результаты прохождения теста и отправляет в Тест кейс менеджер
 */
public class CollectResultListener extends RunListener {
    private TestCaseManager testCaseManager;
    private LogService logService;
    private Map<String, TestResult> testResultMap;

    public CollectResultListener(TestCaseManager testCaseManager, LogService logService) {
        this.testCaseManager = testCaseManager;
        this.logService = logService;
        testResultMap = new HashMap<>();
    }

    @Override
    public void testStarted(Description description) throws Exception {
        TestResult testResult = getDefaultTestResult(description);
        if (testResult.getCaseID() != null && !testResult.getCaseID().equals("")) {
            testResultMap.put(description.getMethodName(), testResult);
        }
    }

    @Override
    public void testFinished(Description description) throws Exception {
        if (testResultMap.containsKey(description.getMethodName())) {
            TestResult testResult = testResultMap.remove(description.getMethodName());
            sendResult(testResult);
        } else {
            logService.log().warn("Test result is absent for method {}", description.getMethodName());
        }
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        String status = getFailStatus(failure.getException());
        String description = failure.getMessage();
        TestResult testResult = testResultMap.get(failure.getDescription().getMethodName());
        testResult.setStatus(status);
        testResult.setDescription(description);
    }

    private String getFailStatus(Throwable e) {
        if (e instanceof AssertionError) {
            return "fail";
        }
        return "broken";
    }

    private TestResult getDefaultTestResult(Description description) {
        String caseID = JunitHelper.getMethodCaseID(description);
        return new TestResult(caseID, "success", "");
    }

    private void sendResult(TestResult testResult) {
        logService.log().info("Send to testcaseManager: {}", testResult);
        logService.log().info("Response from testCaseManager: {}", testCaseManager.addResult(testResult));
    }
}
