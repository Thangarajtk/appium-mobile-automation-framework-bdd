package org.example.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.constants.FrameworkConstants;
import org.example.customexceptions.DriverInitializationException;
import org.example.enums.ConfigJson;
import org.example.enums.MobileBrowserName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static org.example.utils.configloader.JsonParser.getConfig;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Drivers {

    public static AppiumDriver<MobileElement> createAndroidDriver() {
        try {
            DesiredCapabilities capability = new DesiredCapabilities();
            capability.setCapability(PLATFORM_NAME, Platform.ANDROID);
            capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2); // Specific to Android
            capability.setCapability(MobileCapabilityType.DEVICE_NAME, (System.getProperty("deviceName") != null ? System.getProperty("deviceName") :
                    getConfig(ConfigJson.ANDROID_DEVICE_NAME)));
            capability.setCapability(MobileCapabilityType.UDID, (System.getProperty("udid") != null ? System.getProperty("udid") :
                    getConfig(ConfigJson.ANDROID_UDID))); // To uniquely identify device
            capability.setCapability(MobileCapabilityType.APP, FrameworkConstants.ANDROID_APK_PATH);
            capability.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getConfig(ConfigJson.ANDROID_APP_PACKAGE));
            capability.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getConfig(ConfigJson.ANDROID_APP_ACTIVITY));
//            capability.setCapability(CapabilityType.BROWSER_NAME, MobileBrowserName.CHROME);
            capability.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_PORT, (System.getProperty("chromedriverPort") != null ? System.getProperty("chromedriverPort") :
                    getConfig(ConfigJson.ANDROID_CHROMEDRIVER_PORT))); // For Web view/Chrome browser to launch the browser on different port
            capability.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, (System.getProperty("systemPort") != null ? System.getProperty("systemPort") :
                    getConfig(ConfigJson.ANDROID_SYSTEM_PORT))); // To set different port for each thread - This port is used to communicate with UiAutomator2
            if (getConfig(ConfigJson.ANDROID_EMULATOR).equalsIgnoreCase("yes")) {
                capability.setCapability(AndroidMobileCapabilityType.AVD, (System.getProperty("deviceName") != null ? System.getProperty("deviceName") :
                        getConfig(ConfigJson.ANDROID_DEVICE_NAME)));
                capability.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, Integer.parseInt(getConfig(ConfigJson.AVD_LAUNCH_TIMEOUT)));
            }
            return new AndroidDriver<>(new URL(getConfig(ConfigJson.APPIUM_URL)), capability);
        } catch (Exception e) {
            throw new DriverInitializationException("Failed to initialize driver. Please check the desired capabilities", e);
        }
    }

    public static AppiumDriver<MobileElement> createIOSDriver() {
        try {
            DesiredCapabilities capability = new DesiredCapabilities();
            capability.setCapability(PLATFORM_NAME, Platform.IOS);
            capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            capability.setCapability(MobileCapabilityType.DEVICE_NAME, (System.getProperty("deviceName") != null ? System.getProperty("deviceName") :
                    getConfig(ConfigJson.IOS_DEVICE_NAME)));
            capability.setCapability(MobileCapabilityType.UDID, (System.getProperty("udid") != null ? System.getProperty("udid") :
                    getConfig(ConfigJson.IOS_UDID)));
            capability.setCapability(MobileCapabilityType.APP, FrameworkConstants.IOS_APP_PATH);
            capability.setCapability(IOSMobileCapabilityType.BUNDLE_ID, getConfig(ConfigJson.IOS_BUNDLE_ID));
            capability.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, (System.getProperty("wdaLocalPort") != null ? System.getProperty("wdaLocalPort") :
                    getConfig(ConfigJson.IOS_WDA_LOCAL_PORT))); // To set different port for each thread - This port is used to communicate with WebDriverAgent driver
//            capability.setCapability(CapabilityType.BROWSER_NAME, MobileBrowserName.SAFARI);
            capability.setCapability("webkitDebugProxyPort", (System.getProperty("webkitDebugProxyPort") != null ? System.getProperty("webkitDebugProxyPort") :
                    getConfig(ConfigJson.IOS_WEBKIT_DEBUG_PROXY_PORT))); // For web view/Safari browser testing on real device

            return new IOSDriver<>(new URL(getConfig(ConfigJson.APPIUM_URL)), capability);
        } catch (Exception e) {
            throw new DriverInitializationException("Failed to initialize driver. Please check the desired capabilities", e);
        }
    }
}
