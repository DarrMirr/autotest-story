package darr.mirr.data.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import darr.mirr.data.SystemProperties;
import darr.mirr.data.model.TestData;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class TestDataProvider {
    private ObjectMapper mapper = new ObjectMapper();

    public TestData getTestData(String caseID) {
        String path = Paths.get(SystemProperties.PATH_TEST_DATA, caseID + ".json").toString();
        try(InputStream io = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)){
            if (io != null) {
                return mapper.readValue(io, TestData.class);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Json mapping error for caseID = " + caseID  +" due to", e);
        }
        throw new IllegalStateException("There is no test data for caseID " + caseID);
    }
}
