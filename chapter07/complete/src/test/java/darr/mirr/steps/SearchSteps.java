package darr.mirr.steps;

import darr.mirr.pages.SearchBlock;
import org.openqa.selenium.WebElement;

/**
 * Created by Darr Mirr on 05.08.17.
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
