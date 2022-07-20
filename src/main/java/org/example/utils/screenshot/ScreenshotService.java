package org.example.utils.screenshot;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenshotService {

    public static byte[] getScreenshotAsBytes() {
        return ScreenshotUtils.captureScreenshotAsBytes();
    }
}
