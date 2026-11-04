package com.demoblaze.utils.actions;

import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.waits.WaitManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BrowserActions {
    private final WebDriver driver;
    private final WaitManager waitManager;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    public BrowserActions maximizeWindow() {
        driver.manage().window().maximize();
        return this;
    }

    public String waitForUrlContains(String expectedUrlPart) {
        try {
            waitManager.fluentWait().until(ExpectedConditions.urlContains(expectedUrlPart));
        } catch (TimeoutException e) {
            LogsManager.warn("URL does not contain", expectedUrlPart, "after waiting. Current URL:", driver.getCurrentUrl());
        }
        return driver.getCurrentUrl();
    }

    public BrowserActions navigateTo(String url) {
        driver.get(url);
        LogsManager.info("Navigated to:", url);
        return this;
    }

    public String getCookie(String name) {
        Cookie cookie = waitManager.fluentWait().until(d -> d.manage().getCookieNamed(name));
        LogsManager.debug("Cookie", name, "=", cookie.getValue());
        return cookie.getValue();
    }

    public BrowserActions closeCurrentWindow() {
        driver.close();
        return this;
    }

    public BrowserActions openNewWindow() {
        driver.switchTo().newWindow(WindowType.WINDOW);
        return this;
    }
}
