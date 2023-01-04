package org.example.driver.factory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.driver.Drivers;
import org.example.driver.manager.DriverManager;
import org.example.enums.MobilePlatformName;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static org.example.enums.MobilePlatformName.ANDROID;
import static org.example.enums.MobilePlatformName.IOS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverFactory {

  private static final Map<MobilePlatformName, Supplier<AppiumDriver<MobileElement>>> DRIVER_TYPE_MAP =
    new EnumMap<>(MobilePlatformName.class);

  static {
    DRIVER_TYPE_MAP.put(ANDROID, Drivers::createAndroidDriver);
    DRIVER_TYPE_MAP.put(IOS, Drivers::createIOSDriver);
  }

  public static void initializeDriver(MobilePlatformName mobilePlatformName) {
    AppiumDriver<MobileElement> driver = DRIVER_TYPE_MAP.get(mobilePlatformName).get();
    DriverManager.setAppiumDriver(driver);
  }

  public static void quitDriver() {
    if (Objects.nonNull(DriverManager.getDriver())) {
      DriverManager.getDriver().quit();
      DriverManager.unload();
    }
  }
}
