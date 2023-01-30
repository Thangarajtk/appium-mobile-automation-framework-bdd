package org.example.utils.screenrecording;

import io.appium.java_client.screenrecording.CanRecordScreen;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.example.constants.FrameworkConstants;
import org.example.driver.manager.DriverManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenRecordingUtils {

  public static void startScreenRecording() {
    ((CanRecordScreen) DriverManager.getDriver()).startRecordingScreen();
  }

  public static void stopScreenRecording(String methodName) {
    var recordedVideoFile = ((CanRecordScreen) DriverManager.getDriver()).stopRecordingScreen();
    var pathToWriteVideoFile = FrameworkConstants.getScreenRecordingsPath() + File.separator + methodName + ".mp4";
    writeToOutputStream(pathToWriteVideoFile, recordedVideoFile);
  }

  static void writeToOutputStream(String filePathToWrite, String recordedVideoFile) {
    try (var outputStream = new FileOutputStream(filePathToWrite)) {
      outputStream.write(Base64.decodeBase64(recordedVideoFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
