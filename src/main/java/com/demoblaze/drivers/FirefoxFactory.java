package com.demoblaze.drivers;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxFactory implements DriverFactory {
    static FirefoxOptions options() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.disable_open_during_load", false);
        if (DriverConfig.isHeadless()) {
            options.addArguments("-headless",
                    "--width=" + DriverConfig.WINDOW_WIDTH, "--height=" + DriverConfig.WINDOW_HEIGHT);
        }
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    @Override
    public WebDriver createDriver() {
        WebDriver driver = new FirefoxDriver(options());
        if (!DriverConfig.isHeadless())
            driver.manage().window().setSize(new Dimension(
                    Integer.parseInt(DriverConfig.WINDOW_WIDTH), Integer.parseInt(DriverConfig.WINDOW_HEIGHT)));
        return driver;
    }
}
