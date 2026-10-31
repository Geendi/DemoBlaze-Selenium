package com.demoblaze.validations;

import com.demoblaze.utils.waits.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class Assertion {
    protected final WebDriver driver;
    protected final WaitManager waitManager;

    protected Assertion(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    public abstract Assertion assertTrue(boolean condition, String message);

    public abstract Assertion assertFalse(boolean condition, String message);

    public abstract Assertion assertEquals(String actual, String expected, String message);

    public Assertion isElementVisible(By locator) {
        boolean visible;
        try {
            waitManager.fluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
            visible = true;
        } catch (TimeoutException e) {
            visible = false;
        }
        return assertTrue(visible, "Element not visible: " + locator);
    }

    public Assertion assertPageUrl(String expectedUrl) {
        try {
            waitManager.fluentWait().until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (TimeoutException e) {
        }
        return assertEquals(driver.getCurrentUrl(), expectedUrl, "Page URL mismatch");
    }

    public Assertion assertPageTitle(String expectedTitle) {
        try {
            waitManager.fluentWait().until(ExpectedConditions.titleIs(expectedTitle));
        } catch (TimeoutException e) {
        }
        return assertEquals(driver.getTitle(), expectedTitle, "Page title mismatch");
    }

    public Assertion assertElementText(By locator, String expectedText) {
        try {
            waitManager.fluentWait().until(ExpectedConditions.textToBe(locator, expectedText));
        } catch (TimeoutException e) {
        }
        return assertEquals(driver.findElement(locator).getText(), expectedText, "Text mismatch for " + locator);
    }

    public Assertion assertElementTextContains(By locator, String expectedPart) {
        try {
            waitManager.fluentWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedPart));
        } catch (TimeoutException e) {
        }
        String actual = driver.findElement(locator).getText();
        return assertTrue(actual.contains(expectedPart),
                "Text of " + locator + " is '" + actual + "', expected to contain '" + expectedPart + "'");
    }
}
