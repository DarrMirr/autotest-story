package pol.mirr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Created by Pol Mirr on 23.09.17.
 *
 * Service create UUID and add MDC clear operation to JVM shutdown hook
 * Also return logger instance
 */
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);
    private static final LogService logService = new LogService();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(MDC::clear));
    }

    private LogService() {
    }

    public static LogService get() {
        return logService;
    }

    public void generateUUID() {
        MDC.put("UUID", UUID.randomUUID().toString());
    }

    public Logger log() {
        return logger;
    }
}
