package pol.mirr.utils.testrunner_helpers.parallel_computer;

import org.junit.runners.model.RunnerScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pol Mir on 27.09.17.
 *
 * Планировщик запуска тестов
 * Добавляет тесты в ThreadPoolExecutor
 */
public class TestRunnerScheduler implements RunnerScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunnerScheduler.class);
    private ExecutorService executor;
    private long mainThreadID;
    private AtomicInteger scheduleRunner = new AtomicInteger(0);

    public TestRunnerScheduler(ExecutorService executor, long mainThreadID) {
        this.executor = executor;
        this.mainThreadID = mainThreadID;
    }

    @Override
    public void finished() {
        scheduleRunner.decrementAndGet();
        if (Thread.currentThread().getId() == mainThreadID) {
            waitForTestEnd();
        }
    }

    @Override
    public void schedule(Runnable childStatement) {
        executor.execute(childStatement);
    }

    public void incScheduleRunner() {
        scheduleRunner.incrementAndGet();
    }

    private void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(9223372036854775807L, TimeUnit.NANOSECONDS);
    }

    private void waitForTestEnd() {
        try {
            waitScheduleTest();
            shutdown();
        } catch (InterruptedException e) {
            LOGGER.trace("Ошибка ожидания завершения тестов", e);
        }
    }

    private void waitScheduleTest() throws InterruptedException {
        while (scheduleRunner.get() != 0) {
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
