package pol.mirr.tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import pol.mirr.pages.SearchBlock;
import pol.mirr.pages.SearchBlockImpl;
import pol.mirr.steps.SearchSteps;
import pol.mirr.steps.SearchStepsImpl;
import pol.mirr.utils.rules.WebdriverRule;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.matchers.webdriver.AttributeMatcher.value;

/**
 * Created by Pol Mirr on 03.10.17.
 */
@RunWith(DataProviderRunner.class)
public class OneStoryTest {
    private static final String YA_RU = "https://ya.ru";
    private SearchBlock searchBlock;
    private SearchSteps searchSteps;

    @Rule
    public WebdriverRule webdriverRule = new WebdriverRule();

    @Before
    public void setUp() {
        searchBlock = new SearchBlockImpl(webdriverRule.getDriver());
        searchSteps = new SearchStepsImpl(searchBlock);
    }

    @DataProvider
    public static Object[][] getDataset() {
        Object[][] parameters = {
                {"Hello world"},
                {"Байкал"},
                {"Исаакиевский собор"}
        };
        return parameters;
    }

    @Test
    @UseDataProvider("getDataset")
    public void test(String requestString) {
        webdriverRule.getUrl(YA_RU);
        searchSteps.inputSearchRequest(requestString);
        searchSteps.clickButtonSearch();
        assertThat(webdriverRule.getCurrentUrlDelay(1), containsString("yandex.ru/search"));
        assertThat(searchBlock.getInputSearchRequest(), value(containsString(requestString)));
    }
}
