package darr.mirr.utils.rules;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import darr.mirr.utils.CaseID;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

import static darr.mirr.data.SystemProperties.PATH_SCREENSHOT_DIR;

public class WebScreenshotRule extends TestWatcher {
    private Random random = new Random();
    private WebDriver webDriver;

    public WebScreenshotRule(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        String screenshotFileName = getTestName()
                                        .andThen(this::toFileName)
                                        .apply(description);
        Path target = Paths.get(PATH_SCREENSHOT_DIR, LocalDate.now().toString());
        takeScreenshot()
                .ifPresent(screenshot -> save(screenshot, target, screenshotFileName));
    }

    private void save(byte[] data, Path dir, String fileName) {
        try {
            Files.createDirectories(dir);
            Files.write(dir.resolve(fileName), data, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<byte[]> takeScreenshot() {
        if (webDriver instanceof TakesScreenshot) {
            return Optional.of(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES));
        }
        return Optional.empty();
    }

    private String toFileName(String testName) {
        return testName
                .concat("_T")
                .concat(LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss.SSS")))
                .concat("_")
                .concat(Integer.toString(random.nextInt(1000)))
                .concat(".png");
    }

    private Function<Description, String> getTestName() {
        return description ->
                Optional.of(description.getAnnotation(CaseID.class))
                        .map(CaseID::value)
                        .map(id -> "C" + id)
                        .orElseGet(description::getMethodName);
    }
}
