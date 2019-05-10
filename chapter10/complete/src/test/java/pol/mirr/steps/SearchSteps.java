package pol.mirr.steps;

import org.openqa.selenium.WebElement;
import pol.mirr.pages.SearchBlock;

/**
 * Created by Pol Mirr on 05.08.17.
 *
 * implementation of SearchSteps on SearchBlock
 */
public class SearchSteps {
    private SearchBlock searchBlock;

    public SearchSteps(SearchBlock searchBlock) {
        this.searchBlock = searchBlock;
    }

    public SearchSteps inputSearchRequest(String request) {
        WebElement inputSearchRequest = searchBlock.getInputSearchRequest();
        inputSearchRequest.sendKeys(request);
        return this;
    }

    public SearchSteps clickButtonSearch() {
        WebElement buttonSearch = searchBlock.getButtonSearch();
        buttonSearch.click();
        return this;
    }
}
