package org.example.pageObjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.example.enums.WaitStrategy;
import org.example.pageObjects.screen.ScreenActions;

public class LoginPage extends ScreenActions {

    @AndroidFindBy(accessibility = "test-Username")
    @iOSXCUITFindBy(accessibility = "test-Username")
    private static MobileElement txtFieldUsername;

    @AndroidFindBy(accessibility = "test-Password")
    @iOSXCUITFindBy(accessibility = "test-Password")
    private static MobileElement txtFieldPassword;

    @AndroidFindBy(accessibility = "test-LOGIN")
    @iOSXCUITFindBy(accessibility = "test-LOGIN")
    private static MobileElement btnLogin;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView")
    private static MobileElement errorMessage;

    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(txtFieldUsername);
    }

    public LoginPage setUsername(String username) {
        enter(txtFieldUsername, username, "Username");
        return this;
    }

    public LoginPage setPassword(String password) {
        enter(txtFieldPassword, password, "Password");
        return this;
    }

    public ProductPage tapOnLogin() {
        click(btnLogin, "Login");
        return new ProductPage();
    }

    public ProductPage login(String username, String password) {
        setUsername(username)
                .setPassword(password)
                .tapOnLogin();

        return new ProductPage();
    }

    public String getErrorText() {
        return getText(errorMessage, WaitStrategy.VISIBLE);
    }
}
