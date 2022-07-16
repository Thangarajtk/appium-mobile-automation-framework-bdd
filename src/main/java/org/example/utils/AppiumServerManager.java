package org.example.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.constants.FrameworkConstants;
import org.example.enums.ConfigProperties;
import org.example.utils.configloader.PropertyUtils;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppiumServerManager {

    private static AppiumDriverLocalService service;

    static boolean checkIfServerIsRunning() {
        boolean isServerRunning = false;
        try {
            ServerSocket serverSocket = new ServerSocket(FrameworkConstants.APPIUM_SERVER_PORT);
            serverSocket.close();
        } catch (IOException e) {
            isServerRunning = true;
        }
        return isServerRunning;
    }

    public static void startAppiumServer() {
        if (PropertyUtils.getPropertyValue(ConfigProperties.START_APPIUM_SERVER).equalsIgnoreCase("yes") &&
                !AppiumServerManager.checkIfServerIsRunning()) {
            //Build the Appium service
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder.usingDriverExecutable(new File(FrameworkConstants.NODEJS_PATH));
            builder.withAppiumJS(new File(FrameworkConstants.APPIUM_JS_PATH));
            builder.withIPAddress(FrameworkConstants.APPIUM_SERVER_HOST);
            builder.usingPort(FrameworkConstants.APPIUM_SERVER_PORT);
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            builder.withArgument(GeneralServerFlag.ALLOW_INSECURE, "chromedriver_autodownload");
            builder.withLogFile(new File(FrameworkConstants.getAppiumServerLogsPath()));
            //Start the server with the builder
            service = AppiumDriverLocalService.buildService(builder);
            service.start();
            service.clearOutPutStreams();
        }
    }

    public static void stopAppiumServer() {
        if (PropertyUtils.getPropertyValue(ConfigProperties.START_APPIUM_SERVER).equalsIgnoreCase("yes")) {
            service.stop();
        }
    }
}
