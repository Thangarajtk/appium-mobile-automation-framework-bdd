package org.example.driver.factory;

import io.appium.java_client.AppiumDriver;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.driver.manager.AndroidManager;
import org.example.driver.manager.IosManager;
import org.example.enums.MobilePlatformName;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.example.enums.MobilePlatformName.ANDROID;
import static org.example.enums.MobilePlatformName.IOS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverFactory {

  private static final Map<MobilePlatformName, Supplier<AppiumDriver>> DRIVER_TYPE_MAP =
    new EnumMap<>(MobilePlatformName.class);

  static {
    DRIVER_TYPE_MAP.put(ANDROID, AndroidManager::createAndroidDriver);
    DRIVER_TYPE_MAP.put(IOS, IosManager::createIOSDriver);
  }

  public static AppiumDriver getDriver(MobilePlatformName mobilePlatformName) {
    return DRIVER_TYPE_MAP.get(mobilePlatformName).get();
  }
}
