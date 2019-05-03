package pol.mirr.utils;

import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.remote.BrowserType.CHROME;
import static org.openqa.selenium.remote.BrowserType.FIREFOX;

/**
 * Created by Pol Mirr on 21.07.17.
 *
 * Class creates DesiredCapabilities for required browser
 */
public class DesiredCapabilityFactory {
    private static DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
    private static DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();

    private DesiredCapabilityFactory(){
    }

    public static DesiredCapabilities get(String browserName) {
        if (browserName == null) {
            throw new IllegalArgumentException("Browser name is null");
        }
        if (browserName.equalsIgnoreCase(CHROME)) {
            return chromeCapabilities;
        }
        if (browserName.equalsIgnoreCase(FIREFOX)) {
            return firefoxCapabilities;
        }
        throw new IllegalArgumentException("Can't find DesiredCapabilities for browser " + browserName);
    }
}
