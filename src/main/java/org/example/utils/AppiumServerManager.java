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
    var isServerRunning = false;
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
      service = new AppiumServiceBuilder()
        .usingDriverExecutable(new File(FrameworkConstants.NODEJS_PATH))
        .withAppiumJS(new File(FrameworkConstants.APPIUM_JS_PATH))
        .withIPAddress(FrameworkConstants.APPIUM_SERVER_HOST)
        .usingPort(FrameworkConstants.APPIUM_SERVER_PORT)
        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
        .withArgument(GeneralServerFlag.ALLOW_INSECURE, "chromedriver_autodownload")
        .withLogFile(new File(FrameworkConstants.getAppiumServerLogsPath()))
        .build();
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
