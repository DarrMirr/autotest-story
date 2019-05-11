package darr.mirr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import darr.mirr.pages.selectors.SearchBlockSelectors;

/**
 * Created by Darr Mirr on 05.08.17.
 *
 *  SearchBlock in web pages
 */
public class SearchBlock {
    private WebDriver driver;
    private WebDriverWait driverWait;
    private SearchBlockSelectors selectors;

    public SearchBlock(WebDriver driver, SearchBlockSelectors selectors) {
        this.driver = driver;
        this.selectors = selectors;
        driverWait = new WebDriverWait(driver, 5);
    }

    public WebElement getInputSearchRequest() {
        By inputSearch = selectors.getInputSearchRequestSelector();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(inputSearch));
        return driver.findElement(inputSearch);
    }

    public WebElement getButtonSearch() {
        By buttonSearch = selectors.getButtonSearchSelector();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(buttonSearch));
        return driver.findElement(buttonSearch);
    }
}
