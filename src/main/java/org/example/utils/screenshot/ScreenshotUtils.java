package org.example.utils.screenshot;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.example.constants.FrameworkConstants;
import org.example.driver.manager.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenshotUtils {

    // This class is to handle the change in third party library
    @SneakyThrows
    public static void captureScreenshotAsFile(String testName) {
        File source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        File destination = new File(FrameworkConstants.SCREENSHOT_PATH + File.separator + testName + ".png");
        FileUtils.copyFile(source, destination);
    }

    public static byte[] captureScreenshotAsBytes() {
        return DriverManager.getDriver().getScreenshotAs(OutputType.BYTES);
    }
}
