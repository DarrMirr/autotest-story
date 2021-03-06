package darr.mirr.data;

import static org.openqa.selenium.remote.BrowserType.CHROME;

/**
 * Created by Darr Mirr on 21.07.17.
 *
 * System properties list uses in autotests
 */
public class SystemProperties {

    public static final String URL_SELENIUM_SERVER = System.getProperty("url_selenium_server", "http://127.0.0.1:4444/wd/hub");
    public static final String BROWSER = System.getProperty("browser", CHROME);

    private SystemProperties() {
    }
}
