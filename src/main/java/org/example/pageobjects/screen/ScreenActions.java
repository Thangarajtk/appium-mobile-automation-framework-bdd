package org.example.pageobjects.screen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.PowerACState;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.example.driver.manager.DriverManager;
import org.example.enums.MobileFindBy;
import org.example.enums.WaitStrategy;
import org.example.factories.WaitFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScreenActions {

    protected ScreenActions() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    private MobileElement getMobileElement(String mobileElement, MobileFindBy mobileFindBy) {
        switch (mobileFindBy) {
            case XPATH:
                return DriverManager.getDriver().findElementByXPath(mobileElement);
            case CSS:
                return DriverManager.getDriver().findElementByCssSelector(mobileElement);
            case ID:
                return DriverManager.getDriver().findElementById(mobileElement);
            case NAME:
                return DriverManager.getDriver().findElementByName(mobileElement);
            case ACCESSIBILITY_ID:
                return DriverManager.getDriver().findElementByAccessibilityId(mobileElement);
            case CLASS:
                return DriverManager.getDriver().findElementByClassName(mobileElement);
        }
        return null;
    }

    protected void waitForPageLoad(int waitTime) {
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(waitTime, TimeUnit.SECONDS);
    }

    protected String getTextFromAttribute(WaitStrategy waitStrategy, MobileElement element) {
        return WaitFactory.explicitlyWaitForElement(waitStrategy, element).getAttribute("text");
    }

    protected String getText(MobileElement element, WaitStrategy waitStrategy) {
        return WaitFactory.explicitlyWaitForElement(waitStrategy, element).getText();
    }

    protected boolean isElementDisplayed(MobileElement element) {
        return element.isDisplayed();
    }

    protected void doClear(MobileElement element) {
        element.clear();
    }

    protected void getServerStatus() {
        DriverManager.getDriver().getStatus();
    }

    protected void setOrientation(ScreenOrientation screenOrientationType) {
        switch (screenOrientationType) {
            case LANDSCAPE:
                DriverManager.getDriver().rotate(ScreenOrientation.LANDSCAPE);
                break;
            case PORTRAIT:
                DriverManager.getDriver().rotate(ScreenOrientation.PORTRAIT);
                break;
            default:
                throw new IllegalStateException("Unexpected value in Screen Orientation: " + screenOrientationType);
        }
    }

    protected void backgroundApp() {
        DriverManager.getDriver().runAppInBackground(Duration.ofSeconds(10));
    }

    protected String getElementAttribute(MobileElement element, String attributeName) {
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

    protected void performSingleTap(WebElement element) {
        new TouchActions(DriverManager.getDriver())
                .singleTap(element)
                .perform();
    }

    protected void performDoubleTap(WebElement element) {
        new TouchActions(DriverManager.getDriver())
                .doubleTap(element)
                .perform();
    }

    protected void performLongTap(WebElement element) {
        new TouchActions(DriverManager.getDriver())
                .longPress(element)
                .perform();
    }

    protected void touchScreenScroll(WebElement element, int x, int y) {
        TouchActions touchActions = new TouchActions(DriverManager.getDriver());
        touchActions.scroll(element, x, y);
        touchActions.perform();
    }

    protected void hideKeyboard() {
        DriverManager.getDriver().hideKeyboard();
    }

    protected void scrollClickAndroid(String scrollableListId, String selectionText) {
        ((AndroidDriver<MobileElement>) DriverManager.getDriver()).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)."
                + "resourceId(\"" + scrollableListId + "\"))"
                + ".setAsHorizontalList().scrollIntoView(new UiSelector().text(\"" + selectionText + "\"))").click();
    }

    protected void click(MobileElement element) {
        element.click();
    }

    protected void click(String element, MobileFindBy elementType) {
        click(getMobileElement(element, elementType));
    }

    protected void enter(MobileElement element, String value) {
        WaitFactory.explicitlyWaitForElement(WaitStrategy.VISIBLE, element);
        doClear(element);
        element.sendKeys(value);
    }

    protected void enterValueAndPressEnter(MobileElement element, String value) {
        doClear(element);
        element.sendKeys(value, Keys.ENTER);
    }

    protected void enter(String element, MobileFindBy elementType, String value) {
        enter(getMobileElement(element, elementType), value);
    }

    protected boolean isTextPresent(String containsText) {
        return DriverManager.getDriver().getPageSource().contains(containsText);
    }

    protected void powerStateAndroid(String powerState) {
        switch (powerState) {
            case "ON":
                ((AndroidDriver<MobileElement>) DriverManager.getDriver()).setPowerAC(PowerACState.ON);
                break;
            case "OFF":
                ((AndroidDriver<MobileElement>) DriverManager.getDriver()).setPowerAC(PowerACState.OFF);
                break;
            default:
                break;
        }
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
     * Long press key
     * @param element element
     */
    protected void longPress(MobileElement element) {
        new TouchAction<>(DriverManager.getDriver())
                .longPress(ElementOption.element(element))
                .perform();
    }

    /**
     * Scroll to specific location
     * @param element element
     * @param value   location
     */
    protected void scrollToLocation(MobileElement element, int value) {
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

    /**
     * Touch Actions
     *
     * @param a1   axis 1
     * @param b1   axis 2
     * @param a2   axis 3
     * @param b2   axis 4
     * @param time time
     */
    private void touchActions(int a1, int b1, int a2, int b2, int time) {
        TouchAction<?> touchAction = new TouchAction<>(DriverManager.getDriver());
        touchAction.press(PointOption.point(a1, b1)).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(time))).
                moveTo(PointOption.point(a2, b2)).release();
        touchAction.perform();
    }

    /**
     * Swipe with axis
     *
     * @param x    x axis
     * @param y    y axis
     * @param x1   x1 axis
     * @param y1   y1 axis
     * @param time timeInMilli
     */
    protected void swipeAxis(int x, int y, int x1, int y1, int count, int time) {
        for (int i = 0; i < count; i++) {
            touchActions(x, y, x1, y1, time);
        }
    }

    /**
     * tap to element for 250sec
     *
     * @param androidElement element
     */
    protected void tapByElement(MobileElement androidElement) {
        new TouchAction<>(DriverManager.getDriver())
                .tap(TapOptions.tapOptions().withElement(ElementOption.element(androidElement)))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(250))).perform();
    }

    /**
     * Tap by coordinates
     *
     * @param x x
     * @param y y
     */
    protected void tapByCoordinates(int x, int y) {
        new TouchAction<>(DriverManager.getDriver())
                .tap(PointOption.point(x, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(250))).perform();
    }

    /**
     * Press by element
     *
     * @param element element
     * @param seconds time
     */
    protected void pressByElement(MobileElement element, long seconds) {
        new TouchAction<>(DriverManager.getDriver())
                .press(ElementOption.element(element))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(seconds)))
                .release()
                .perform();
    }

    /**
     * LongPress by element
     *
     * @param element element
     * @param seconds time
     */
    protected void longPressByElement(MobileElement element, long seconds) {
        new TouchAction<>(DriverManager.getDriver())
                .longPress(ElementOption.element(element))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(seconds)))
                .release()
                .perform();
    }

    /**
     * Press by co-ordinates
     *
     * @param x       x
     * @param y       y
     * @param seconds time
     */
    protected void pressByCoordinates(int x, int y, long seconds) {
        new TouchAction<>(DriverManager.getDriver())
                .press(PointOption.point(x, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(seconds)))
                .release()
                .perform();
    }

    /**
     * Horizontal swipe by percentage
     *
     * @param startPercentage  start
     * @param endPercentage    end
     * @param anchorPercentage anchor
     */
    protected void horizontalSwipeByPercentage(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = DriverManager.getDriver().manage().window().getSize();
        int anchor = (int) (size.height * anchorPercentage);
        int startPoint = (int) (size.width * startPercentage);
        int endPoint = (int) (size.width * endPercentage);
        new TouchAction<>(DriverManager.getDriver())
                .press(PointOption.point(startPoint, anchor))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endPoint, anchor))
                .release().perform();
    }

    /**
     * Vertical swipe by percentage
     *
     * @param startPercentage  start
     * @param endPercentage    end
     * @param anchorPercentage anchor
     */
    protected void verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = DriverManager.getDriver().manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);

        new TouchAction<>(DriverManager.getDriver())
                .press(PointOption.point(anchor, startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(anchor, endPoint))
                .release().perform();
    }

    /**
     * Swipe by elements
     *
     * @param startElement start
     * @param endElement   end
     */
    protected void swipeByElements(MobileElement startElement, MobileElement endElement) {
        int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
        int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);

        int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
        int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);

        new TouchAction<>(DriverManager.getDriver())
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endX, endY))
                .release().perform();
    }

    /**
     * Multitouch by element
     *
     * @param androidElement element
     */
    protected void multiTouchByElement(MobileElement androidElement) {
        TouchAction<?> press = new TouchAction<>(DriverManager.getDriver())
                .press(ElementOption.element(androidElement))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .release();

        new MultiTouchAction(DriverManager.getDriver())
                .add(press)
                .perform();
    }

    /**
     * Swipe touch (UP,DOWN,LEFT,RIGHT)
     *
     * @param direction direction
     * @param count     count
     */
    protected void swipe(String direction, int count, int time) {
        Dimension size = DriverManager.getDriver().manage().window().getSize();
        switch (direction) {
            case "left":
            case "LEFT":
                for (int i = 0; i < count; i++) {
                    int startx = (int) (size.width * 0.8);
                    int endx = (int) (size.width * 0.20);
                    int starty = size.height / 2;
                    touchActions(startx, starty, endx, starty, time);
                }
                break;
            case "right":
            case "RIGHT":
                for (int j = 0; j < count; j++) {
                    int endx = (int) (size.width * 0.8);
                    int startx = (int) (size.width * 0.20);
                    int starty = size.height / 2;
                    touchActions(startx, starty, endx, starty, time);
                }
                break;
            case "up":
            case "UP":
                for (int j = 0; j < count; j++) {
                    int starty = (int) (size.height * 0.80);
                    int endy = (int) (size.height * 0.20);
                    int startx = size.width / 2;
                    touchActions(startx, starty, startx, endy, time);
                }
                break;
            case "down":
            case "DOWN":
                for (int j = 0; j < count; j++) {
                    int starty = (int) (size.height * 0.80);
                    int endy = (int) (size.height * 0.20);
                    int startx = size.width / 2;
                    touchActions(startx, endy, startx, starty, time);
                }
                break;
            default:
                break;
        }
    }

    protected void closeApp() {
        DriverManager.getDriver().closeApp();
    }

    protected void launchApp() {
        DriverManager.getDriver().launchApp();
    }
}
