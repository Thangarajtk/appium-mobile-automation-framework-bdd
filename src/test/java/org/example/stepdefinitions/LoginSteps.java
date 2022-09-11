package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pageobjects.LoginPage;
import org.example.pageobjects.ProductPage;
import org.junit.Assert;

public class LoginSteps {

    @When("^User enter username as \"([^\"]*)\"$")
    public void userEnterUsernameAs(String username) {
        new LoginPage().setUsername(username);
    }

    @When("^User enter password as \"([^\"]*)\"$")
    public void userEnterPasswordAs(String password) {
        new LoginPage().setPassword(password);
    }

    @When("^clicks on login$")
    public void clickOnLogin() {
        new LoginPage().tapOnLogin();
    }

    @Then("^login should fail with an error \"([^\"]*)\"$")
    public void loginShouldFailWithAnError(String err) {
        Assert.assertEquals(new LoginPage().getErrorText(), err);
    }

    @Then("^User should see Products page with title \"([^\"]*)\"$")
    public void userShouldSeeProductsPageWithTitle(String title) {
        Assert.assertEquals(new ProductPage().getProductPageTitle(), title);
    }
}
