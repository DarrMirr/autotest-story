package pol.mirr.data.providers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Json mapping error");
    }

    private void mockReadFile() {
        testDataStore.put("1", "{\"caseID\":\"1\",\"sets\":[{\"input\":{\"request\":\"hello world\"},\"output\":{\"request\":\"hello world\"}},{\"input\":{\"request\":\"Байкал\"},\"output\":{\"request\":\"Байкал\"}},{\"input\":{\"request\":\"Исаакиевский собор\"},\"output\":{\"request\":\"Исаакиевский собор\"}},{\"input\":{\"request\":\"Гагарин\"},\"output\":{\"request\":\"Гагарин\"}},{\"input\":{\"request\":\"Циолковский\"},\"output\":{\"request\":\"Циолковский\"}},{\"input\":{\"request\":\"Кижи\"},\"output\":{\"request\":\"Кижи\"}}]}");
        testDataStore.put("2", "{\"caseID\":\"2\",\"sets\":[{\"input\":{\"request\":\"Пушкин\"},\"output\":{\"request\":\"Пушкин\"}},{\"input\":{\"request\":\"Достаевский\"},\"output\":{\"request\":\"Достаевскмй\"}},{\"input\":{\"request\":\"Ефремов Иван\"},\"output\":{\"request\":\"Ефремов Иван\"}}]}");
    }
}
