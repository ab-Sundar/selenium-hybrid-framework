package com.example.framework.hooks;

import com.example.framework.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class CucumberHooks {
    @Before public void before() { DriverFactory.createDriver(); }
    @After public void after(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] png = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", "failure-screenshot");
            }
        } finally { DriverFactory.quitDriver(); }
    }
}
