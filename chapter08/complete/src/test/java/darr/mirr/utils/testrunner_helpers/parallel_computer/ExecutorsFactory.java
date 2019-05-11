package darr.mirr.utils.testrunner_helpers.parallel_computer;

import darr.mirr.data.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Pol Mir on 30.09.17.
 *
 * Получение ThreadPool executor
 */
public class ExecutorsFactory {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorsFactory.class);

    private ExecutorsFactory() {
    }

    public static ExecutorService getFixedThreadPool(int threadCount) {
        return Executors.newFixedThreadPool(threadCount);
    }

    public static ExecutorService getFixedThreadPool() {
        int threadCount = getThreadCount();
        return getFixedThreadPool(threadCount);
    }

    private static int getThreadCount() {
        int thread_count = Runtime.getRuntime().availableProcessors() * 2;
        if (SystemProperties.THREAD_COUNT != null) {
            int thread_count_param = Integer.valueOf(SystemProperties.THREAD_COUNT);
            if (thread_count_param > 0) {
                thread_count = thread_count_param;
            }
        }
        logger.debug("Количество потоков : {}", thread_count);
        return thread_count;
    }
}
