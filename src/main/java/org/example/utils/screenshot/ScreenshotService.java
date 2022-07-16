package org.example.utils.screenshot;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenshotService {

    // Abstract layer to handle the change in business requirement
    public static String getScreenshotAsBase64() {
        return ScreenshotUtils.captureScreenshotAsBase64();
    }

    public static byte[] getScreenshotAsBytes() {
        return ScreenshotUtils.captureScreenshotAsBytes();
    }
}
