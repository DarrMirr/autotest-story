package pol.mirr.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
public class OneStoryTest {
    private static final String YA_RU = "https://ya.ru";
    private SearchBlock searchBlock;
    private SearchSteps searchSteps;
    private String helloWorld = "Hello world";

    @Rule
    public WebdriverRule webdriverRule = new WebdriverRule();

    @Before
    public void setUp() {
        searchBlock = new SearchBlockImpl(webdriverRule.getDriver());
        searchSteps = new SearchStepsImpl(searchBlock);
    }

    @Test
    public void test() {
        webdriverRule.getUrl(YA_RU);
        searchSteps.inputSearchRequest(helloWorld);
        searchSteps.clickButtonSearch();
        assertThat(webdriverRule.getCurrentUrlDelay(1), containsString("yandex.ru/search"));
        assertThat(searchBlock.getInputSearchRequest(), value(containsString(helloWorld)));
    }
}
