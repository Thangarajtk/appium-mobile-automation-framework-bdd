package org.example.driver.factory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.customexceptions.DriverInitializationException;
import org.example.driver.manager.DriverManager;
import org.example.driver.Drivers;
import org.example.enums.MobilePlatformName;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverFactory {

    public static void initializeDriver(MobilePlatformName mobilePlatformName) {
        AppiumDriver<MobileElement> driver;
        switch (mobilePlatformName) {
            case ANDROID:
                driver = Drivers.createAndroidDriver();
                break;
            case IOS:
                driver = Drivers.createIOSDriver();
                break;
            default:
                throw new DriverInitializationException("Platform name " + mobilePlatformName + " is not found. Please check the platform name");
        }
        DriverManager.setAppiumDriver(driver);
    }

    public static void quitDriver() {
        if (Objects.nonNull(DriverManager.getDriver())) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }
}
