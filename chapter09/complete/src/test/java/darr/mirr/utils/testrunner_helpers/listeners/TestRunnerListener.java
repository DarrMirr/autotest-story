package darr.mirr.utils.testrunner_helpers.listeners;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import darr.mirr.utils.LogService;
import darr.mirr.utils.testrunner_helpers.JunitHelper;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Darr Mirr on 27.09.17.
 *
 * Listener which log test runner results
 */
public class TestRunnerListener extends RunListener {
    private LogService logService;
    private LocalTime startTime;
    private List<String> caseIDs;

    public TestRunnerListener(LogService logService, List<String> caseIDs) {
        this.logService = logService;
        this.caseIDs = caseIDs;
    }


    @Override
    public void testRunStarted(Description description) throws Exception {
        startTime = LocalTime.now();
    }

    @Override
    public void testFinished(Description description) throws Exception {
        String caseID = JunitHelper.getMethodCaseID(description);
        caseIDs.remove(caseID);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        summary(result);
        logMismatchCaseID();
    }

    private void summary(Result result) {
        logService.log().info("Summary :");
        logService.log().info("Test Run is complete.");
        logService.log().info("Test count : {}", result.getRunCount());
        logService.log().info("Test Run duration : {}", getTestRunDuration());
    }

    private void logMismatchCaseID() {
        if (!caseIDs.isEmpty()) {
            logService.log().warn("CaseID which doesn't run :");
            logService.log().warn(caseIDs.toString());
        }
    }

    private String getTestRunDuration() {
        Duration durationTestRun = Duration.between(startTime, LocalTime.now());
        return LocalTime.MIDNIGHT.plus(durationTestRun).format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
    }
}
