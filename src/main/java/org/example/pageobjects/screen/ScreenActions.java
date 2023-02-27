package org.example.pageobjects.screen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.PowerACState;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.example.driver.manager.DriverManager;
import org.example.enums.MobileFindBy;
import org.example.enums.WaitStrategy;
import org.example.factories.WaitFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.example.enums.MobileFindBy.ACCESSIBILITY_ID;
import static org.example.enums.MobileFindBy.CLASS;
import static org.example.enums.MobileFindBy.CSS;
import static org.example.enums.MobileFindBy.ID;
import static org.example.enums.MobileFindBy.NAME;
import static org.example.enums.MobileFindBy.XPATH;

public class ScreenActions {

  private final Map<MobileFindBy, Function<String, WebElement>> mobileFindByFunctionMap = new EnumMap(MobileFindBy.class);
  private final Function<String, WebElement> findByXpath =
    mobileElement -> DriverManager.getDriver().findElement(AppiumBy.xpath(mobileElement));
  private final Function<String, WebElement> findByCss =
    mobileElement -> DriverManager.getDriver().findElement(AppiumBy.cssSelector(mobileElement));
  private final Function<String, WebElement> findById =
    mobileElement -> DriverManager.getDriver().findElement(AppiumBy.id(mobileElement));
  private final Function<String, WebElement> findByName =
    mobileElement -> DriverManager.getDriver().findElement(AppiumBy.name(mobileElement));
  private final Function<String, WebElement> findByAccessibilityId =
    mobileElement -> DriverManager.getDriver().findElement(AppiumBy.accessibilityId(mobileElement));
  private final Function<String, WebElement> findByClassName =
    mobileElement -> DriverManager.getDriver().findElement(AppiumBy.className(mobileElement));

  protected ScreenActions() {
    PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
  }

  private WebElement getMobileElement(String mobileElement, MobileFindBy mobileFindBy) {
    if (mobileFindByFunctionMap.isEmpty()) {
      mobileFindByFunctionMap.put(XPATH, findByXpath);
      mobileFindByFunctionMap.put(CSS, findByCss);
      mobileFindByFunctionMap.put(ID, findById);
      mobileFindByFunctionMap.put(NAME, findByName);
      mobileFindByFunctionMap.put(ACCESSIBILITY_ID, findByAccessibilityId);
      mobileFindByFunctionMap.put(CLASS, findByClassName);
    }
    return mobileFindByFunctionMap.get(mobileFindBy).apply(mobileElement);
  }

  protected void waitForPageLoad(int waitTime) {
    DriverManager.getDriver().manage().timeouts().pageLoadTimeout(waitTime, TimeUnit.SECONDS);
  }

  protected String getTextFromAttribute(WaitStrategy waitStrategy, WebElement element) {
    return WaitFactory.explicitlyWaitForElement(waitStrategy, element).getAttribute("text");
  }

  protected String getText(WebElement element, WaitStrategy waitStrategy) {
    return WaitFactory.explicitlyWaitForElement(waitStrategy, element).getText();
  }

  protected boolean isElementDisplayed(WebElement element) {
    return element.isDisplayed();
  }

  protected void doClear(WebElement element) {
    element.clear();
  }

  protected void getServerStatus() {
    DriverManager.getDriver().getStatus();
  }

  protected String getElementAttribute(WebElement element, String attributeName) {
    return element.getAttribute(attributeName);
  }

  protected WebElement getActiveElement() {
    return DriverManager.getDriver().switchTo().activeElement();
  }

  protected void moveMouseToElement(WebElement element, int x_offset, int y_offset) {
    new Actions(DriverManager.getDriver())
      .moveToElement(element, x_offset, y_offset)
      .perform();
  }

  protected void doubleClickOnElement(WebElement element) {
    new Actions(DriverManager.getDriver())
      .moveToElement(element)
      .doubleClick()
      .perform();
  }

  protected void click(WebElement element) {
    element.click();
  }

  protected void click(String element, MobileFindBy elementType) {
    click(getMobileElement(element, elementType));
  }

  protected void enter(WebElement element, String value) {
    WaitFactory.explicitlyWaitForElement(WaitStrategy.VISIBLE, element);
    doClear(element);
    element.sendKeys(value);
  }

  protected void enterValueAndPressEnter(WebElement element, String value) {
    doClear(element);
    element.sendKeys(value, Keys.ENTER);
  }

  protected void enter(String element, MobileFindBy elementType, String value) {
    enter(getMobileElement(element, elementType), value);
  }

  protected boolean isTextPresent(String containsText) {
    return DriverManager.getDriver().getPageSource().contains(containsText);
  }

  protected void powerStateAndroid(PowerACState powerACState) {
    Consumer<PowerACState> powerStateConsumer = state ->
      ((AndroidDriver) DriverManager.getDriver()).setPowerAC(state);
    powerStateConsumer.accept(powerACState);
  }

  /**
   * Swipe Down
   */
  protected void swipeDown() {
    DriverManager.getDriver().executeScript("mobile:scroll", ImmutableMap.of("direction", "down"));
  }

  /**
   * Swipe Up
   */
  protected void swipeUP() {
    DriverManager.getDriver().executeScript("mobile:scroll", ImmutableMap.of("direction", "up"));
  }

  /**
   * Accept Alert
   */
  protected void acceptAlert() {
    DriverManager.getDriver().executeScript("mobile:acceptAlert");
  }

  /**
   * Dismiss Alert
   */
  protected void dismissAlert() {
    DriverManager.getDriver().executeScript("mobile:dismissAlert");
  }

  /**
   * Scroll to specific location
   *
   * @param element element
   * @param value   location
   */
  protected void scrollToLocation(WebElement element, int value) {
    HashMap<String, Double> scrollElement = new HashMap<>();
    scrollElement.put("startX", 0.50);
    scrollElement.put("startY", 0.95);
    scrollElement.put("endX", 0.50);
    scrollElement.put("endY", 0.01);
    scrollElement.put("duration", 3.0);
    DriverManager.getDriver().executeScript("mobile: swipe", scrollElement);
  }

  protected boolean checkListIsSorted(List<String> listToSort) {
    if (!listToSort.isEmpty()) {
      return Ordering.natural().isOrdered(listToSort);
    }
    return false;
  }
}
