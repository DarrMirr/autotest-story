package darr.mirr;

import darr.mirr.utils.CaseID;
import darr.mirr.utils.LogService;
import darr.mirr.utils.case_providers.CaseProvider;
import darr.mirr.utils.case_providers.CaseProviders;
import darr.mirr.utils.testrunner_helpers.listeners.TestRunnerListener;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.notification.RunListener;
import darr.mirr.utils.testrunner_helpers.filters.TestCaseFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darr Mirr on 12.09.17.
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

            Request request =  Request
                    .classes(testClasses.toArray(new Class[0]))
                    .filterWith(new TestCaseFilter(caseIDs));
            RunListener runListener = new TestRunnerListener(logService, new ArrayList<>(caseIDs));

            runTests(request, runListener);
        }
    }

    private void runTests(Request request, RunListener runListener) {
        JUnitCore jUnitCore = new JUnitCore();
        jUnitCore.addListener(runListener);
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
}
