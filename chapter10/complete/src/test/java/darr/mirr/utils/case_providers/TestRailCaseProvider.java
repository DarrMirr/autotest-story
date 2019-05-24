package darr.mirr.utils.case_providers;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.ResultField;
import com.codepine.api.testrail.model.Run;
import com.codepine.api.testrail.model.Test;
import darr.mirr.utils.HttpClientManager;
import darr.mirr.utils.LogService;
import darr.mirr.utils.testcase_manager.TestCaseManager;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static darr.mirr.data.SystemProperties.*;

public class TestRailCaseProvider implements CaseProvider, TestCaseManager<Result> {
    private static final Logger logger = LoggerFactory.getLogger(TestRailCaseProvider.class);
    private static final TestRail testRail = TestRail.builder(TEST_RAIL_URL, TEST_RAIL_USERNAME, TEST_RAIL_KEY).applicationName("meetup_demo").build();
    private static final HttpClientManager httpClientManager = new HttpClientManager();
    private int runId;

    @Override
    public List<String> getCaseID() {
        runId = createTestRun();
        logger.debug("run id : {}", runId);
        return testRail
                .tests()
                .list(runId)
                .execute()
                .stream()
                .map(Test::getCaseId)
                .map(caseID -> Integer.toString(caseID))
                .collect(Collectors.toList());
    }

    private int createTestRun() {
        return testRail
                .runs()
                .add(Integer.valueOf(PROJECT_ID),
                        new Run()
                                .setSuiteId(Integer.valueOf(SUITE_ID))
                                .setName(NEW_TESTRUN_NAME + " " + LocalDateTime.now()))
                .execute()
                .getId();
    }

    @Override
    public void addResult(Result result, String screenshotPath) {
        ResultField resultField = new ResultField();
        resultField.setName("step_results");
        Result createdResult = testRail.results()
                .addForCase(runId, result.getCaseId(), result, Collections.singletonList(resultField))
                .execute();
        Optional.ofNullable(screenshotPath)
                .ifPresent(path ->
                        sendAttachment(createdResult.getId(), path));
    }

    private void sendAttachment(int resultId, String screenshotPath) {
        String basicAuth = "Basic " + DatatypeConverter.printBase64Binary((TEST_RAIL_USERNAME + ":" + TEST_RAIL_KEY).getBytes(Charset.forName("UTF-8")));
        Header authHeader = new BasicHeader("Authorization", basicAuth);
        Optional<String> response = httpClientManager.sendFormData(TEST_RAIL_URL + "index.php?/api/v2/add_attachment_to_result/" + resultId, new File(screenshotPath), authHeader);
        response.ifPresent(LogService.get().log()::info);
    }
}
