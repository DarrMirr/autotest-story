package pol.mirr;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.junit.Test;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.notification.RunListener;
import pol.mirr.utils.CaseID;
import pol.mirr.utils.LogService;
import pol.mirr.utils.case_providers.CaseProvider;
import pol.mirr.utils.case_providers.CaseProviders;
import pol.mirr.utils.testcase_manager.TestCaseManager;
import pol.mirr.utils.testrunner_helpers.filters.TestCaseFilter;
import pol.mirr.utils.testrunner_helpers.listeners.CollectResultListener;
import pol.mirr.utils.testrunner_helpers.listeners.TestRunnerListener;
import pol.mirr.utils.testrunner_helpers.parallel_computer.ExecutorsFactory;
import pol.mirr.utils.testrunner_helpers.parallel_computer.TestParallelComputer;
import pol.mirr.utils.testrunner_helpers.parallel_computer.TestRunnerScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Pol Mirr on 12.09.17.
 *
 * Runner which launch special test suite
 */
public class TestRunner {
    private CaseProvider caseProvider;
    private LogService logService;

    public TestRunner() {
        this.caseProvider = CaseProviders.get();
        this.logService = LogService.get();
        this.logService.generateUUID();
    }

    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        testRunner.run();
    }

    private void run() {
        List<String> caseIDs = caseProvider.getCaseID();
        if (caseIDs != null && !caseIDs.isEmpty()) {
            List<Class<?>> testClasses = getTestClasses(this.getClass().getPackageName());
            Computer parallelComputer = getParallelComputer();

            Request request =  Request
                    .classes(parallelComputer, testClasses.toArray(new Class[0]))
                    .filterWith(new TestCaseFilter(caseIDs));
            List<RunListener> runListenerList = getRunListener(caseIDs);

            runTests(request, runListenerList);
        }
    }

    private void runTests(Request request, List<RunListener> runListenerList) {
        JUnitCore jUnitCore = new JUnitCore();
        runListenerList.forEach(jUnitCore::addListener);
        jUnitCore.run(request);
    }

    private List<Class<?>> getTestClasses(String packageName) {
        try(ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .whitelistPackages(packageName)
                .scan()
        ){
            return scanResult
                    .getAllClasses()
                    .filter(classInfo ->
                                        classInfo.hasMethodAnnotation(Test.class.getName())
                                    &&  classInfo.hasMethodAnnotation(CaseID.class.getName()))
                    .loadClasses();
        }
    }

    private Computer getParallelComputer() {
        long currentThreadID = Thread.currentThread().getId();
        ExecutorService executorService = ExecutorsFactory.getFixedThreadPool();
        TestRunnerScheduler runnerScheduler = new TestRunnerScheduler(executorService, currentThreadID);
        return new TestParallelComputer(runnerScheduler);
    }

    private List<RunListener> getRunListener(List<String> caseIDs) {
        List<RunListener> runListenerList = new ArrayList<>();
        runListenerList.add(new TestRunnerListener(logService, new ArrayList<>(caseIDs)));
        if (caseProvider instanceof TestCaseManager) {
            TestCaseManager testCaseManager = (TestCaseManager) caseProvider;
            runListenerList.add(new CollectResultListener(testCaseManager, logService));
        }
        return runListenerList;
    }
}
