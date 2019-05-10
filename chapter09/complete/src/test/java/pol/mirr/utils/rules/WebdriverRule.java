package pol.mirr.utils.rules;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pol.mirr.utils.DesiredCapabilityFactory;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static pol.mirr.data.SystemProperties.BROWSER;
import static pol.mirr.data.SystemProperties.URL_SELENIUM_SERVER;

/**
 * Created by Pol Mirr on 15.07.17.
 * <p>
 * Rule creates Webdriver instance before test and close it after finishing test.
 * This class provides methods for work filterWith Webdriver
 */
public class WebdriverRule extends ExternalResource {
    private static final Logger logger = LoggerFactory.getLogger(WebdriverRule.class);
    private WebDriver driver;
    private WebDriverWait driverWait;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(WebDriverPool.DEFAULT::dismissAll));
    }

    public WebdriverRule() {
        this(BROWSER);
    }

    public WebdriverRule(String browserName) {
        try{
            driver = WebDriverPool.DEFAULT.getDriver(new URL(URL_SELENIUM_SERVER), DesiredCapabilityFactory.get(browserName));
            driverWait = new WebDriverWait(driver, 5);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void before() throws Throwable {
        driver.manage().window().maximize();
    }

    @Override
    protected void after() {
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
            logger.trace(e.getMessage(), e);
        }
        return driver.getCurrentUrl();
    }

}
