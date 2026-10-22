package com.example.framework.tests;

import com.example.framework.driver.DriverFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    @BeforeMethod(alwaysRun = true) public void setUp() { DriverFactory.createDriver(); }
    @AfterMethod(alwaysRun = true) public void tearDown(ITestResult result) {
        try {
            if (!result.isSuccess()) {
                Path folder = Path.of("target", "screenshots");
                Files.createDirectories(folder);
                byte[] png = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                Files.write(folder.resolve(result.getMethod().getMethodName() + "-" + System.nanoTime() + ".png"), png);
            }
        } catch (Exception screenshotError) { System.err.println("Screenshot failed: " + screenshotError.getMessage()); }
        finally { DriverFactory.quitDriver(); }
    }
}
