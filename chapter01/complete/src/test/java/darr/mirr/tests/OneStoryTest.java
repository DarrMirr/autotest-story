package darr.mirr.tests;

import darr.mirr.utils.rules.WebdriverRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Darr Mirr on 03.10.17.
 */
public class OneStoryTest {
    private static final String URL = "https://ya.ru";

    @Rule
    public WebdriverRule webdriverRule = new WebdriverRule();

    @Test
    public void test() {
        webdriverRule.getUrl(URL);
    }
}
