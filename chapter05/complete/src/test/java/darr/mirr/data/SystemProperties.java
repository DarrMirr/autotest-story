package darr.mirr.data;

import java.io.File;

import static org.openqa.selenium.remote.BrowserType.CHROME;

/**
 * Created by Darr Mirr on 21.07.17.
 *
 * System properties list uses in autotests
 */
public class SystemProperties {

    public static final String URL_SELENIUM_SERVER = System.getProperty("url_selenium_server", "http://127.0.0.1:4444/wd/hub");
    public static final String BROWSER = System.getProperty("browser", CHROME);

    public static final String PATH_TEST_DATA = System.getProperty("path_test_data", "test_data" + File.separator);

    public static final String PATH_SCREENSHOT_DIR = System.getProperty("path_screenshot_dir", "screenshots");

    private SystemProperties() {
    }
}
