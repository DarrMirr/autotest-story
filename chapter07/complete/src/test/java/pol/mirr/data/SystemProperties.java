package pol.mirr.data;

import static org.openqa.selenium.remote.BrowserType.CHROME;

/**
 * Created by Pol Mirr on 21.07.17.
 *
 * System properties list uses in autotests
 */
public class SystemProperties {

    public static final String URL_SELENIUM_SERVER = System.getProperty("url_selenium_server", "http://127.0.0.1:4444/wd/hub");
    public static final String BROWSER = System.getProperty("browser", CHROME);

    public static final String TESTRUN_ID = System.getProperty("testrun_id", null);
    public static final String NEW_TESTRUN_NAME = System.getProperty("new_run_name", null);
    public static final String PROJECT_ID = System.getProperty("project_id", null);
    public static final String CASE_ID_PROPERTY = System.getProperty("case_id", null);

    private SystemProperties() {
    }
}
