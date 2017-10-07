package pol.mirr.data.providers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pol.mirr.data.model.TestData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pol Mirr on 30.08.17.
 *
 * Keep test data which read from file
 */
public enum TestDataProvider {
    TEST_PRJ;

    private static final Logger logger = LoggerFactory.getLogger(TestDataProvider.class);
    private Map<String, String> testDataStore = new HashMap<>();
    private ObjectMapper mapper = new ObjectMapper();

    TestDataProvider() {
        mockReadFile();
    }

    public TestData getTestData(String caseID) {
        String jsonTestData = testDataStore.getOrDefault(caseID, null);
        if (jsonTestData == null) {
            throw new RuntimeException("There is no test data for caseID " + caseID);
        }
        return mapper(jsonTestData);
    }

    private TestData mapper(String json) {
        try {
            return mapper.readValue(json, TestData.class);
        } catch (JsonParseException e) {
            logger.trace(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.trace(e.getMessage(), e);
        } catch (IOException e) {
            logger.trace(e.getMessage(), e);
        }
        throw new RuntimeException("Json mapping error");
    }

    private void mockReadFile() {
        testDataStore.put("1", "{\"caseID\":\"1\",\"sets\":[{\"input\":{\"request\":\"hello world\"},\"output\":{\"request\":\"hello world\"},\"database\":{\"update\":[{\"table\":\"testtable\",\"set\":{\"field2\":\"value2\",\"field3\":\"value3\"},\"where\":{\"field1\":\"value1\"}}],\"delete\":[{\"from\":\"another_table\",\"where\":{\"field1\":\"value1\"}}]}},{\"input\":{\"request\":\"Байкал\"},\"output\":{\"request\":\"Байкал\"},\"database\":{}},{\"input\":{\"request\":\"Исаакиевский собор\"},\"output\":{\"request\":\"Исаакиевский собор\"},\"database\":{}},{\"input\":{\"request\":\"Гагарин\"},\"output\":{\"request\":\"Гагарин\"},\"database\":{}},{\"input\":{\"request\":\"Циолковский\"},\"output\":{\"request\":\"Циолковский\"},\"database\":{}},{\"input\":{\"request\":\"Кижи\"},\"output\":{\"request\":\"Кижи\"},\"database\":{}}]}");
        testDataStore.put("2", "{\"caseID\":\"2\",\"sets\":[{\"input\":{\"request\":\"Пушкин\"},\"output\":{\"request\":\"Пушкин\"},\"database\":{}},{\"input\":{\"request\":\"Достаевский\"},\"output\":{\"request\":\"Достаевскмй\"},\"database\":{}},{\"input\":{\"request\":\"Ефремов Иван\"},\"output\":{\"request\":\"Ефремов Иван\"},\"database\":{}}]}");
    }
}
