package pol.mirr.utils.rules;

import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import pol.mirr.data.model.Dataset;
import pol.mirr.utils.DataParametersExtracter;
import pol.mirr.utils.LogService;

/**
 * Created by Pol Mirr on 03.09.17.
 *
 * Class log test events
 */
public class LoggerRule extends TestWatcherRule {
    private LogService logService;

    public LoggerRule() {
        logService = LogService.get();
        logService.generateUUID();
    }

    @Override
    protected void starting(FrameworkMethod frameworkMethod) {
        logService.log().info("Starting : {}", frameworkMethod.getName());
        Object[] parameters = DataParametersExtracter.extract(frameworkMethod);
        logDataset(parameters);
    }

    @Override
    protected void failed(Throwable e, FrameworkMethod frameworkMethod) {
        if (e instanceof AssertionError) {
            logFinishStatus(frameworkMethod.getName(), "fail");
            logService.log().info(e.getLocalizedMessage());
        } else {
            logFinishStatus(frameworkMethod.getName(), "error");
            logService.log().error("Exception : {}", e.getLocalizedMessage());
        }
        logService.log().debug(frameworkMethod.getName(), e);
    }

    @Override
    protected void succeeded(FrameworkMethod frameworkMethod) {
        logFinishStatus(frameworkMethod.getName(), "success");
    }

    public Logger log() {
        return logService.log();
    }

    private void logFinishStatus(String methodName, String status) {
        logService.log().info("Finish : {}", methodName);
        logService.log().info("Test result : {}", status);
    }

    private void logDataset(Object[] methodArgs) {
        if (methodArgs != null) {
            for (Object methodArg : methodArgs) {
                if (methodArg instanceof Dataset) {
                    logService.log().info(methodArg.toString());
                }
            }
        }
    }
}
