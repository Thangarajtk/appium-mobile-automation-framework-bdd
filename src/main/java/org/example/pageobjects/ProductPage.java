package org.example.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.example.enums.WaitStrategy;
import org.example.pageobjects.screen.ScreenActions;

public class ProductPage extends ScreenActions {

	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
	private static MobileElement productPageTitle;

	public String getProductPageTitle() {
		return getText(productPageTitle, WaitStrategy.VISIBLE);
	}
}
