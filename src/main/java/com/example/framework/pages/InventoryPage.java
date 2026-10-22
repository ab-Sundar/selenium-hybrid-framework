package com.example.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {
    private final By pageTitle = By.cssSelector("[data-test='title']");
    public InventoryPage(WebDriver driver) { super(driver); }
    public String title() { return visible(pageTitle).getText(); }
}
