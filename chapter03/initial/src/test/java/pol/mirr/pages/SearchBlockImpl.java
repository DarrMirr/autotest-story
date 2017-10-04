package pol.mirr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Pol Mirr on 05.08.17.
 *
 * Implementation SearchBlock interface for Yandex web pages
 */
public class SearchBlockImpl implements SearchBlock {
    private WebDriver driver;
    private WebDriverWait driverWait;

    private By inputSearchRequest = By.cssSelector(".input__box>input");

    private By buttonSearch = By.cssSelector(".search2__button>button");

    public SearchBlockImpl(WebDriver driver) {
        this.driver = driver;
        driverWait = new WebDriverWait(driver, 5);
    }

    @Override
    public WebElement getInputSearchRequest() {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(inputSearchRequest));
        return driver.findElement(inputSearchRequest);
    }

    @Override
    public WebElement getButtonSearch() {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(buttonSearch));
        return driver.findElement(buttonSearch);
    }
}
