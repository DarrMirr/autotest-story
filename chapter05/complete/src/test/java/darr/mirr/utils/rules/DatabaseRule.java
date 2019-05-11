package darr.mirr.utils.rules;

import org.jooq.Field;
import org.junit.runners.model.FrameworkMethod;
import darr.mirr.data.model.Dataset;
import darr.mirr.utils.DataParametersExtracter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.*;

/**
 * Created by Darr Mirr on 01.09.17.
 *
 * Obtain Dataset object from method argument.
 * Get database from Dataset and send to execute
 */
public class DatabaseRule extends TestWatcherRule {

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

    private void mockExecuterQuery(Map<String, Object>  databaseData) {
        // execute SQL query stub
        System.out.println("Database data: " + databaseData.toString());

//        exampleUpdateStatement(databaseData);
    }

    /*
     * for demonstration purpose only
     */
    private void exampleUpdateStatement(Map<String, Object>  databaseData) {
        List<UpdateStatement> updateStatementList = (List<UpdateStatement>) databaseData.get("update");

        for(UpdateStatement updateStatement : updateStatementList) {

            update(table(name(updateStatement.table)))
                    .set(updateStatement.set)
                    .where(condition(updateStatement.getWhereAsField()))
                    .execute();
        }
    }

    /*
     * for demonstration purpose only
     */
    private static class UpdateStatement {
        private String table;
        private Map<String, String> set;
        private Map<String, String> where;

        public Map<Field<?>, ?> getWhereAsField() {
            // some implementation is needed here
            return Collections.emptyMap();
        }
    }
}
