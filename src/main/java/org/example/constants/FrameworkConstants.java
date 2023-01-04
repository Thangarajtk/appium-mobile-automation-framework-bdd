package org.example.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.enums.ConfigProperties;
import org.example.utils.configloader.PropertyUtils;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FrameworkConstants {

    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String RESOURCES_PATH = "src/test/resources";
    public static final String APPIUM_SERVER_HOST = "127.0.0.1";
    public static final int APPIUM_SERVER_PORT = 4723;
    public static final String APPIUM_JS_PATH = System.getenv("APPIUM_HOME") + File.separator + "main.js";
    public static final String ANDROID_APK_PATH = PROJECT_PATH + File.separator + RESOURCES_PATH + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
    public static final String CONFIG_PROPERTIES_PATH = PROJECT_PATH + File.separator + RESOURCES_PATH + File.separator + "config" + File.separator + "config.properties";
    public static final String CONFIG_JSON_PATH = PROJECT_PATH + File.separator + RESOURCES_PATH + File.separator + "config" + File.separator + "config.json";
    public static final long EXPLICIT_WAIT = 15;
    public static final String IOS_APP_PATH = "";
    public static final String SCREENSHOT_PATH = PROJECT_PATH + File.separator + "screenshots";
    public static final String NODEJS_PATH = System.getenv("NODE_HOME") + File.separator + "node.exe";

    private static final String SERVER_LOGS_PATH = PROJECT_PATH + File.separator + "server-logs";

    public static String getAppiumServerLogsPath() {
        if (PropertyUtils.getPropertyValue(ConfigProperties.OVERRIDE_SERVER_LOG).equalsIgnoreCase("yes"))
            return SERVER_LOGS_PATH + File.separator + "server.log";
        else return SERVER_LOGS_PATH + File.separator + getCurrentDateTime() + File.separator + "server.log";
    }

    private static final String SCREEN_RECORDINGS_PATH = PROJECT_PATH + File.separator + "screen-recordings";

    public static String getScreenRecordingsPath() {
        var screenRecordingsDir = new File(SCREEN_RECORDINGS_PATH);
        if (!screenRecordingsDir.exists())
            screenRecordingsDir.mkdir();
        return SCREEN_RECORDINGS_PATH;
    }

    private static String getCurrentDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }
}
