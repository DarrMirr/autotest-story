package darr.mirr.utils.testrunner_helpers.parallel_computer;

import org.junit.runner.Computer;
import org.junit.runner.Runner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Created by Pol Mir on 27.09.17.
 *
 * Является клоном класса ParallelComputer из пакета org.junit.experimental
 * Отличается тем, что принимает произвольный RunnerScheduler
 * и тем, что использует threadPoolExecutor, переданный в TestRunnerScheduler
 */
public class TestParallelComputer extends Computer {
    private final boolean classes;
    private final boolean methods;
    private TestRunnerScheduler runnerScheduler;

    public TestParallelComputer(TestRunnerScheduler runnerScheduler) {
        this.runnerScheduler = runnerScheduler;
        this.classes = true;
        this.methods = true;
    }

    private Runner parallelize(Runner runner) {
        if (runner instanceof ParentRunner) {
            ((ParentRunner) runner).setScheduler(runnerScheduler);
            runnerScheduler.incScheduleRunner();
        }
        return runner;
    }

    public Runner getSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
        Runner suite = super.getSuite(builder, classes);
        return this.classes ? parallelize(suite) : suite;
    }

    protected Runner getRunner(RunnerBuilder builder, Class<?> testClass) throws Throwable {
        Runner runner = super.getRunner(builder, testClass);
        return this.methods ? parallelize(runner) : runner;
    }
}
