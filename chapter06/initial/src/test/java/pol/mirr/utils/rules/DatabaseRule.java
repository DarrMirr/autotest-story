package pol.mirr.utils.rules;

import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pol.mirr.data.model.Dataset;
import pol.mirr.utils.DataParametersExtracter;

import java.util.Map;

/**
 * Created by Pol Mirr on 01.09.17.
 *
 * Obtain Dataset object from method argument.
 * Get database from Dataset and send to execute
 */
public class DatabaseRule extends TestWatcherRule {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseRule.class);

    @Override
    protected void starting(FrameworkMethod frameworkMethod) {
        Object[] parameters = DataParametersExtracter.extract(frameworkMethod);
        Dataset dataset = getDataset(parameters);
        handleDatabaseData(dataset);
    }

    private void handleDatabaseData(Dataset dataset) {
        if (dataset != null) {
            Map<String, Object> databaseData = dataset.getDatabase();
            mockExecuterQuery(databaseData);
        }
    }

    private Dataset getDataset(Object[] methodArgs) {
        if (methodArgs != null) {
            for (Object methodArg : methodArgs) {
                if (methodArg instanceof Dataset) {
                    return (Dataset) methodArg;
                }
            }
        }
        return null;
    }

    private void mockExecuterQuery(Map<String, Object> databaseData) {
        // execute SQL query stub
        logger.info("Database data : {}", databaseData.toString());
    }
}
