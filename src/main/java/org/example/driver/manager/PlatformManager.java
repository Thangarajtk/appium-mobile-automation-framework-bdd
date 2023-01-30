package org.example.driver.manager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlatformManager {

  private static final ThreadLocal<String> PLATFORM_NAME = new ThreadLocal<>();

  public static String getPlatformName() {
    return PLATFORM_NAME.get();
  }

  public static void setPlatformName(String platform) {
    PLATFORM_NAME.set(platform);
  }

  public static void removePlatformName() {
    PLATFORM_NAME.remove();
  }
}
