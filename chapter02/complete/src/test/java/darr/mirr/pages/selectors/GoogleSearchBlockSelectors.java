package darr.mirr.pages.selectors;

import org.openqa.selenium.By;

public class GoogleSearchBlockSelectors implements SearchBlockSelectors {

    @Override
    public By getInputSearchRequestSelector() {
        return By.cssSelector("form[action=\"/search\"] input[title=\"Поиск\"]");
    }

    @Override
    public By getButtonSearchSelector() {
        return By.cssSelector("form[action=\"/search\"] input[value^=\"Поиск\"]");
    }
}
