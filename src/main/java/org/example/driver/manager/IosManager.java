package org.example.driver.manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.example.constants.FrameworkConstants;
import org.example.customexceptions.DriverInitializationException;
import org.example.enums.ConfigJson;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static org.example.utils.configloader.JsonParser.getConfig;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

public final class IosManager {

  private IosManager() {

  }

  public static AppiumDriver<MobileElement> createIOSDriver() {
    try {
      var capability = new DesiredCapabilities();
      capability.setCapability(PLATFORM_NAME, Platform.IOS);
      capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
      capability.setCapability(MobileCapabilityType.DEVICE_NAME,
                               (System.getProperty("deviceName") != null ? System.getProperty("deviceName") :
                                 getConfig(ConfigJson.IOS_DEVICE_NAME)));
      capability.setCapability(MobileCapabilityType.UDID, (System.getProperty("udid") != null ? System.getProperty("udid") :
        getConfig(ConfigJson.IOS_UDID)));
      capability.setCapability(MobileCapabilityType.APP, FrameworkConstants.IOS_APP_PATH);
      capability.setCapability(IOSMobileCapabilityType.BUNDLE_ID, getConfig(ConfigJson.IOS_BUNDLE_ID));
      capability.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT,
                               (System.getProperty("wdaLocalPort") != null ? System.getProperty("wdaLocalPort") :
                                 getConfig(
                                   ConfigJson.IOS_WDA_LOCAL_PORT))); // To set different port for each thread - This port is used to communicate with WebDriverAgent driver
//            capability.setCapability(CapabilityType.BROWSER_NAME, MobileBrowserName.SAFARI);
      capability.setCapability("webkitDebugProxyPort",
                               (System.getProperty("webkitDebugProxyPort") != null ? System.getProperty("webkitDebugProxyPort") :
                                 getConfig(
                                   ConfigJson.IOS_WEBKIT_DEBUG_PROXY_PORT))); // For web view/Safari browser testing on real device

      return new IOSDriver<>(new URL(getConfig(ConfigJson.APPIUM_URL)), capability);
    } catch (Exception e) {
      throw new DriverInitializationException("Failed to initialize driver. Please check the desired capabilities", e);
    }
  }
}