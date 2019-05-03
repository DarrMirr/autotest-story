package pol.mirr.pages.selectors;

import org.openqa.selenium.By;

public class YandexSearchBlockSelectors implements SearchBlockSelectors {

    @Override
    public By getInputSearchRequestSelector() {
        return By.cssSelector(".input__box>input");
    }

    @Override
    public By getButtonSearchSelector() {
        return By.cssSelector(".search2__button>button");
    }
}
