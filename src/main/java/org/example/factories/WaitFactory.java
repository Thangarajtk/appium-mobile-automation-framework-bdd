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

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static org.example.enums.WaitStrategy.CLICKABLE;
import static org.example.enums.WaitStrategy.NONE;
import static org.example.enums.WaitStrategy.PRESENCE;
import static org.example.enums.WaitStrategy.VISIBLE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WaitFactory {

  private static final Map<WaitStrategy, Function<By, WebElement>> WAIT_STRATEGY_FUNCTION_MAP =
    new EnumMap<>(WaitStrategy.class);

  private static final Function<By, WebElement> CLICKABLE_BY_WEB_ELEMENT_FUNCTION =
    by -> new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT)
      .until(ExpectedConditions.elementToBeClickable(by));
  private static final Function<By, WebElement> PRESENCE_BY_WEB_ELEMENT_FUNCTION =
    by -> new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT)
      .until(ExpectedConditions.presenceOfElementLocated(by));
  private static final Function<By, WebElement> VISIBLE_BY_WEB_ELEMENT_FUNCTION =
    by -> new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT)
      .until(ExpectedConditions.visibilityOfElementLocated(by));
  private static final Function<By, WebElement> NONE_BY_WEB_ELEMENT_FUNCTION = by -> DriverManager.getDriver().findElement(by);

  static {
    WAIT_STRATEGY_FUNCTION_MAP.put(CLICKABLE, CLICKABLE_BY_WEB_ELEMENT_FUNCTION);
    WAIT_STRATEGY_FUNCTION_MAP.put(PRESENCE, PRESENCE_BY_WEB_ELEMENT_FUNCTION);
    WAIT_STRATEGY_FUNCTION_MAP.put(VISIBLE, VISIBLE_BY_WEB_ELEMENT_FUNCTION);
    WAIT_STRATEGY_FUNCTION_MAP.put(NONE, NONE_BY_WEB_ELEMENT_FUNCTION);
  }

  public static WebElement explicitlyWaitForElementLocatedBy(WaitStrategy waitStrategy, By by) {
    return WAIT_STRATEGY_FUNCTION_MAP.get(waitStrategy).apply(by);
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
