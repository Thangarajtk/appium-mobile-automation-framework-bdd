package org.example.pageobjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.example.enums.WaitStrategy;
import org.example.pageobjects.screen.ScreenActions;
import org.openqa.selenium.WebElement;

public class ProductPage extends ScreenActions {

  @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
  private static WebElement productPageTitle;

  public String getProductPageTitle() {
    return getText(productPageTitle, WaitStrategy.VISIBLE);
  }
}
