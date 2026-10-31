package com.demoblaze.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeFactory implements DriverFactory {
    static ChromeOptions options() {
        return ChromiumOptionsHelper.applyCommon(new ChromeOptions());
    }

    @Override
    public WebDriver createDriver() {
        return new ChromeDriver(options());
    }
}
