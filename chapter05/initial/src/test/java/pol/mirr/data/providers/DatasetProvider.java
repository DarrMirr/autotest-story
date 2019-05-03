package pol.mirr.data.providers;

import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.runners.model.FrameworkMethod;
import pol.mirr.data.model.Dataset;
import pol.mirr.data.model.TestData;
import pol.mirr.utils.CaseID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pol Mirr on 30.08.17.
 *
 * Get TestData from TestDataProvider and return datasets list
 */
public class DatasetProvider {

    private DatasetProvider() {
    }

    @DataProvider(format = ("%m[CaseID:%p[0]](%i)"))
    public static List<List<Object>> getDataset(FrameworkMethod testMethod) {
        String caseIDs = getCaseID(testMethod);
        return getTestDataList(caseIDs);
    }

    private static List<List<Object>> getTestDataList(String caseID) {
        TestData testData = TestDataProvider.TEST_PRJ.getTestData(caseID);
        return packageDatasets(testData);
    }

    private static List<List<Object>> packageDatasets(TestData testData) {
        List<List<Object>> testDataList = new ArrayList<>();
        for (Dataset dataset : testData.getSets()) {
            List<Object> sets = new ArrayList<>();
            sets.add(testData.getCaseID());
            sets.add(dataset);
            testDataList.add(sets);
        }
        return testDataList;
    }

    private static String getCaseID(FrameworkMethod testMethod) {
        CaseID annotation = testMethod.getAnnotation(CaseID.class);
        if (annotation == null) {
            throw new RuntimeException("Annotation CaseID is not present in method " + testMethod);
        }
        return annotation.value();
    }
}
