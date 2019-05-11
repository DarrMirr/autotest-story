package darr.mirr.tests;

import darr.mirr.pages.selectors.YandexSearchBlockSelectors;
import darr.mirr.steps.SearchSteps;
import darr.mirr.utils.rules.WebdriverRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import darr.mirr.pages.SearchBlock;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.matchers.webdriver.AttributeMatcher.value;

/**
 * Created by Darr Mirr on 03.10.17.
 */
public class OneStoryTest {
    private static final String URL = "https://yandex.ru";
//    private static final String URL = "https://www.google.com";
    private SearchBlock searchBlock;
    private SearchSteps searchSteps;
    private String helloWorld = "Hello world";

    @Rule
    public WebdriverRule webdriverRule = new WebdriverRule();

    @Before
    public void setUp() {
        searchBlock = new SearchBlock(webdriverRule.getDriver(), new YandexSearchBlockSelectors());
        searchSteps = new SearchSteps(searchBlock);
    }

    @Test
    public void test() {
        webdriverRule.getUrl(URL);
        searchSteps
                .inputSearchRequest(helloWorld)
                .clickButtonSearch();
        assertThat(webdriverRule.getCurrentUrlDelay(1), containsString(URL + "/search"));
        assertThat(searchBlock.getInputSearchRequest(), value(containsString(helloWorld)));
    }
}
