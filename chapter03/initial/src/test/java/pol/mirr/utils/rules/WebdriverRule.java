package pol.mirr.utils.rules;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pol.mirr.utils.DesiredCapabilityFactory;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static pol.mirr.data.SystemProperties.BROWSER;
import static pol.mirr.data.SystemProperties.URL_SELENIUM_SERVER;

/**
 * Created by Pol Mirr on 15.07.17.
 *
 * Rule creates Webdriver instance before test and close it after finishing test.
 * This class provides methods for work with Webdriver
 */
public class WebdriverRule extends ExternalResource {
    private WebDriver driver;
    private WebDriverWait driverWait;
    private DesiredCapabilities capabilities;

    public WebdriverRule() {
        this(BROWSER);
    }

    public WebdriverRule(String browserName) {
        capabilities = DesiredCapabilityFactory.get(browserName);
    }

    @Override
    protected void before() throws Throwable {
        driver = new RemoteWebDriver(new URL(URL_SELENIUM_SERVER), capabilities);
        driver.manage().window().maximize();
        driverWait = new WebDriverWait(driver, 5);
    }

    @Override
    protected void after() {
        driver.quit();
    }

    public void getUrlAndWait(String url, int timeout) {
        if (timeout <= 0) {
            throw new IllegalArgumentException("Argument timeout is less than 0");
        }
        getUrl(url);
        driverWait
                .withTimeout(timeout, TimeUnit.SECONDS)
                .until(ExpectedConditions.urlContains(url));
    }

    public void getUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Argument url is null");
        }
        driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public String getCurrentUrlDelay(int delay) {
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.getCurrentUrl();
    }

}