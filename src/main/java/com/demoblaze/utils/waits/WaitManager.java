package com.demoblaze.utils.waits;

import com.demoblaze.utils.data.PropertyReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;

public class WaitManager {
    private final WebDriver driver;

    public WaitManager(WebDriver driver) {
        this.driver = driver;
    }

    public FluentWait<WebDriver> fluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(timeout())
                .pollingEvery(Duration.ofMillis(100))
                .ignoreAll(getExceptions());
    }

    public static Duration timeout() {
        String value = PropertyReader.getProperty("fluentWaitSeconds");
        if (value == null || value.isBlank())
            throw new IllegalStateException("fluentWaitSeconds is not set in webApp.properties");
        try {
            return Duration.ofSeconds(Integer.parseInt(value.trim()));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("fluentWaitSeconds must be a number, but was: " + value, e);
        }
    }

    private ArrayList<Class<? extends Exception>> getExceptions() {
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<>();
        exceptions.add(NoSuchElementException.class);
        exceptions.add(StaleElementReferenceException.class);
        exceptions.add(ElementClickInterceptedException.class);
        exceptions.add(ElementNotInteractableException.class);
        return exceptions;
    }
}
