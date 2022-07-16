package org.example.factories;

import io.appium.java_client.MobileElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.constants.FrameworkConstants;
import org.example.driver.manager.DriverManager;
import org.example.enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WaitFactory {

    public static WebElement explicitlyWaitForElementLocatedBy(WaitStrategy waitStrategy, By by) {
        WebElement element = null;
        switch (waitStrategy) {
            case CLICKABLE:
                element = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT)
                        .until(ExpectedConditions.elementToBeClickable(by));
                break;
            case PRESENCE:
                element = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT)
                        .until(ExpectedConditions.presenceOfElementLocated(by));
                break;
            case VISIBLE:
                element = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(by));
                break;
            case NONE:
                element = DriverManager.getDriver().findElement(by);
                break;
        }
        return element;
    }

    public static WebElement explicitlyWaitForElement(WaitStrategy waitStrategy, MobileElement mobileElement) {
        WebElement element = null;
        switch (waitStrategy) {
            case CLICKABLE:
                element = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT)
                        .until(ExpectedConditions.elementToBeClickable(mobileElement));
                break;
            case VISIBLE:
                element = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT)
                        .until(ExpectedConditions.visibilityOf(mobileElement));
                break;
            case NONE:
                element = mobileElement;
                break;
        }
        return element;
    }
}
