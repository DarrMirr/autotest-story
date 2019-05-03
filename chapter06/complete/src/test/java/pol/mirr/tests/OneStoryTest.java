package pol.mirr.tests;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import pol.mirr.data.model.Dataset;
import pol.mirr.data.providers.DatasetProvider;
import pol.mirr.pages.SearchBlock;
import pol.mirr.pages.selectors.YandexSearchBlockSelectors;
import pol.mirr.steps.SearchSteps;
import pol.mirr.utils.CaseID;
import pol.mirr.utils.rules.DatabaseRule;
import pol.mirr.utils.rules.LoggerRule;
import pol.mirr.utils.rules.WebdriverRule;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.matchers.webdriver.AttributeMatcher.value;

/**
 * Created by Pol Mirr on 03.10.17.
 */
@RunWith(DataProviderRunner.class)
public class OneStoryTest {
    private static final String YA_RU = "https://yandex.ru";
    private static final String GOOGLE_RU = "https://www.google.com";
    private SearchBlock searchBlock;
    private SearchSteps searchSteps;

    @Rule
    public WebdriverRule webdriverRule = new WebdriverRule();

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    @Rule
    public LoggerRule loggerRule = new LoggerRule();

    @Before
    public void setUp() {
        searchBlock = new SearchBlock(webdriverRule.getDriver(), new YandexSearchBlockSelectors());
        searchSteps = new SearchSteps(searchBlock);
    }

    @Test
    @CaseID("1")
    @UseDataProvider(location = DatasetProvider.class, value = "getDataset")
    public void test(String caseID, Dataset dataset) {
        String requestString = dataset.getInput("request");
        String expectedRequestString = dataset.getOutput("request");

        webdriverRule.getUrl(YA_RU);
        searchSteps
                .inputSearchRequest(requestString)
                .clickButtonSearch();
        assertThat(webdriverRule.getCurrentUrlDelay(1), containsString(YA_RU + "/search"));
        assertThat(searchBlock.getInputSearchRequest(), value(containsString(expectedRequestString)));
    }
}
