package com.example.framework.pages;

import com.example.framework.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) { super(driver); }
    public LoginPage open() { driver.get(Config.get("base.url")); return this; }
    public LoginPage enterCredentials(String user, String pass) {
        visible(username).sendKeys(user);
        visible(password).sendKeys(pass);
        return this;
    }
    public InventoryPage submitValidLogin() { click(loginButton); return new InventoryPage(driver); }
    public LoginPage submitInvalidLogin() { click(loginButton); return this; }
    public String errorText() { return visible(error).getText(); }
}
