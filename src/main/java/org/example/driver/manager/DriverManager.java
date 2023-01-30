package org.example.driver.manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverManager {

  private static final ThreadLocal<AppiumDriver<MobileElement>> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

  public static AppiumDriver<MobileElement> getDriver() {
    return DRIVER_THREAD_LOCAL.get();
  }

  public static void setAppiumDriver(AppiumDriver<MobileElement> driver) {
    if (Objects.nonNull(driver))
      DRIVER_THREAD_LOCAL.set(driver);
  }

  public static void unload() {
    DRIVER_THREAD_LOCAL.remove();
  }
}
