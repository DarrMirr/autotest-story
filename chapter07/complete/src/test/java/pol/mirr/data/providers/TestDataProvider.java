package pol.mirr.data.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import pol.mirr.data.model.TestData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TestDataProvider {
    private static final String TEST_DATA_PATH = "test_data" + File.separator;
    private ObjectMapper mapper = new ObjectMapper();

    public TestData getTestData(String caseID) {
        try(InputStream io = Thread.currentThread().getContextClassLoader().getResourceAsStream(TEST_DATA_PATH + caseID + ".json")){
            if (io != null) {
                return mapper.readValue(io, TestData.class);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Json mapping error for caseID = " + caseID  +" due to", e);
        }
        throw new IllegalStateException("There is no test data for caseID " + caseID);
    }
}
