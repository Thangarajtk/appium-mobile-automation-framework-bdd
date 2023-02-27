package org.example.driver.manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.constants.FrameworkConstants;
import org.example.customexceptions.DriverInitializationException;
import org.example.enums.ConfigJson;

import java.net.URL;
import java.time.Duration;
import java.util.Optional;

import static org.example.utils.configloader.JsonParser.getConfig;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AndroidManager {

  public static AppiumDriver createAndroidDriver() {
    try {
      UiAutomator2Options options = new UiAutomator2Options();
      options.setDeviceName(Optional.ofNullable(System.getProperty("deviceName"))
                              .orElse(getConfig(ConfigJson.ANDROID_DEVICE_NAME)))
        .setUdid(Optional.ofNullable(System.getProperty("udid"))
                   .orElse(getConfig(ConfigJson.ANDROID_UDID)))
        .setApp(FrameworkConstants.ANDROID_APK_PATH)
        .setAppPackage(getConfig(ConfigJson.ANDROID_APP_PACKAGE))
        .setAppActivity(getConfig(ConfigJson.ANDROID_APP_ACTIVITY))
        .setSystemPort(Integer.parseInt(Optional.ofNullable(System.getProperty("systemPort"))
                                          .orElse(getConfig(
                                            ConfigJson.ANDROID_SYSTEM_PORT))));
      if (getConfig(ConfigJson.ANDROID_EMULATOR).equalsIgnoreCase("yes")) {
        options.setAvd(Optional.ofNullable(System.getProperty("deviceName"))
                         .orElse(getConfig(ConfigJson.ANDROID_DEVICE_NAME)))
          .setAvdLaunchTimeout(Duration.ofDays(Integer.parseInt(getConfig(ConfigJson.AVD_LAUNCH_TIMEOUT))));
      }
      return new AndroidDriver(new URL(getConfig(ConfigJson.APPIUM_URL)), options);
    } catch (Exception e) {
      throw new DriverInitializationException("Failed to initialize driver. Please check the desired capabilities", e);
    }
  }
}
