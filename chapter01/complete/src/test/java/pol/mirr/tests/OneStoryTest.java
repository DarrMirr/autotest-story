package pol.mirr.tests;

import org.junit.Rule;
import org.junit.Test;
import pol.mirr.utils.rules.WebdriverRule;

/**
 * Created by Pol Mirr on 03.10.17.
 */
public class OneStoryTest {
    private static final String YA_RU = "https://ya.ru";

    @Rule
    public WebdriverRule webdriverRule = new WebdriverRule();

    @Test
    public void test() {
        webdriverRule.getUrl(YA_RU);
    }
}
