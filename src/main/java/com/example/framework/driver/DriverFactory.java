package com.example.framework.driver;

import com.example.framework.config.Config;
import java.net.URI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private DriverFactory() {}
    public static void createDriver() {
        if (DRIVER.get() != null) throw new IllegalStateException("Driver already initialized");
        String browser = Config.get("browser").toLowerCase();
        boolean headless = Boolean.parseBoolean(Config.get("headless"));
        String gridUrl = Config.get("grid.url");
        try {
            WebDriver driver;
            switch (browser) {
                case "chrome" -> {
                    ChromeOptions options = new ChromeOptions();
                    if (headless) options.addArguments("--headless=new", "--window-size=1440,900", "--no-sandbox", "--disable-dev-shm-usage");
                    driver = gridUrl.isBlank() ? new ChromeDriver(options) : new RemoteWebDriver(URI.create(gridUrl).toURL(), options);
                }
                case "firefox" -> {
                    FirefoxOptions options = new FirefoxOptions();
                    if (headless) options.addArguments("-headless");
                    driver = gridUrl.isBlank() ? new FirefoxDriver(options) : new RemoteWebDriver(URI.create(gridUrl).toURL(), options);
                }
                default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            DRIVER.set(driver);
        } catch (Exception e) { throw new IllegalStateException("Unable to start browser", e); }
    }
    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) throw new IllegalStateException("Driver not started");
        return driver;
    }
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        try { if (driver != null) driver.quit(); } finally { DRIVER.remove(); }
    }
}
