package pol.mirr.steps;

import org.openqa.selenium.WebElement;
import pol.mirr.pages.SearchBlock;

/**
 * Created by Pol Mirr on 05.08.17.
 *
 * Implementation SearchSteps interface
 */
public class SearchStepsImpl implements SearchSteps {
    private SearchBlock searchBlock;

    public SearchStepsImpl(SearchBlock searchBlock) {
        this.searchBlock = searchBlock;
    }

    @Override
    public void inputSearchRequest(String request) {
        WebElement inputSearchRequest = searchBlock.getInputSearchRequest();
        inputSearchRequest.sendKeys(request);
    }

    @Override
    public void clickButtonSearch() {
        WebElement buttonSearch = searchBlock.getButtonSearch();
        buttonSearch.click();
    }
}
