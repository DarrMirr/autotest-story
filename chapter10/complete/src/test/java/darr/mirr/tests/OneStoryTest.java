package darr.mirr.tests;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import darr.mirr.utils.CaseID;
import darr.mirr.utils.rules.DatabaseRule;
import darr.mirr.utils.rules.LoggerRule;
import darr.mirr.utils.rules.WebScreenshotRule;
import darr.mirr.utils.rules.WebdriverRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import darr.mirr.data.model.Dataset;
import darr.mirr.data.providers.DatasetProvider;
import darr.mirr.pages.SearchBlock;
import darr.mirr.pages.selectors.YandexSearchBlockSelectors;
import darr.mirr.steps.SearchSteps;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.matchers.webdriver.AttributeMatcher.value;

/**
 * Created by Darr Mirr on 03.10.17.
 */
@RunWith(DataProviderRunner.class)
public class OneStoryTest {
    private static final String URL = "https://yandex.ru";
//    private static final String URL = "https://www.google.com";
    private SearchBlock searchBlock;
    private SearchSteps searchSteps;

    public WebdriverRule webdriverRule = new WebdriverRule();

    @Rule
    public RuleChain webRuleChain = RuleChain
                                        .outerRule(webdriverRule)
                                        .around(new WebScreenshotRule(webdriverRule.getDriver()));

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

        webdriverRule.getUrl(URL);
        searchSteps
                .inputSearchRequest(requestString)
                .clickButtonSearch();
        assertThat(webdriverRule.getCurrentUrlDelay(1), containsString(URL + "/search"));
        assertThat(searchBlock.getInputSearchRequest(), value(containsString(expectedRequestString)));
    }
}
