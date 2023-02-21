package org.example.driver.manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.constants.FrameworkConstants;
import org.example.customexceptions.DriverInitializationException;
import org.example.enums.ConfigJson;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Optional;

import static org.example.utils.configloader.JsonParser.getConfig;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AndroidManager {

  public static AppiumDriver<MobileElement> createAndroidDriver() {
    try {
      var capability = new DesiredCapabilities();
      capability.setCapability(PLATFORM_NAME, Platform.ANDROID);
      capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2); // Specific to Android
      capability.setCapability(MobileCapabilityType.DEVICE_NAME, Optional.ofNullable(System.getProperty("deviceName"))
        .orElse(getConfig(ConfigJson.ANDROID_DEVICE_NAME)));
      capability.setCapability(MobileCapabilityType.UDID, Optional.ofNullable(System.getProperty("udid"))
        .orElse(getConfig(ConfigJson.ANDROID_UDID))); // To uniquely identify device
      capability.setCapability(MobileCapabilityType.APP, FrameworkConstants.ANDROID_APK_PATH);
      capability.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getConfig(ConfigJson.ANDROID_APP_PACKAGE));
      capability.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getConfig(ConfigJson.ANDROID_APP_ACTIVITY));
//            capability.setCapability(CapabilityType.BROWSER_NAME, MobileBrowserName.CHROME);
      capability.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_PORT,
                               Optional.ofNullable(System.getProperty("chromedriverPort"))
                                 .orElse(getConfig(
                                   ConfigJson.ANDROID_CHROMEDRIVER_PORT))); // For Web view/Chrome browser to launch the browser on different port
      capability.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Optional.ofNullable(System.getProperty("systemPort"))
        .orElse(getConfig(
          ConfigJson.ANDROID_SYSTEM_PORT))); // To set different port for each thread - This port is used to communicate with UiAutomator2
      if (getConfig(ConfigJson.ANDROID_EMULATOR).equalsIgnoreCase("yes")) {
        capability.setCapability(AndroidMobileCapabilityType.AVD, Optional.ofNullable(System.getProperty("deviceName"))
          .orElse(getConfig(ConfigJson.ANDROID_DEVICE_NAME)));
        capability.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT,
                                 Integer.parseInt(getConfig(ConfigJson.AVD_LAUNCH_TIMEOUT)));
      }
      return new AndroidDriver<>(new URL(getConfig(ConfigJson.APPIUM_URL)), capability);
    } catch (Exception e) {
      throw new DriverInitializationException("Failed to initialize driver. Please check the desired capabilities", e);
    }
  }
}
