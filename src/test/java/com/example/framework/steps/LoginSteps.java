package com.example.framework.steps;

import com.example.framework.driver.DriverFactory;
import com.example.framework.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {
    private LoginPage loginPage;
    @Given("the user is on the login page")
    public void openLogin() { loginPage = new LoginPage(DriverFactory.getDriver()).open(); }
    @When("the user signs in with username {string} and password {string}")
    public void login(String user, String pass) { loginPage.enterCredentials(user, pass).submitInvalidLogin(); }
    @Then("the login error should contain {string}")
    public void verifyError(String expected) { Assert.assertTrue(loginPage.errorText().contains(expected), "Unexpected login error"); }
}
