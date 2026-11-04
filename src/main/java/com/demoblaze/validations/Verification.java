package com.demoblaze.validations;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Verification extends Assertion {

    public Verification(WebDriver driver) {
        super(driver);
    }

    @Override
    public Verification assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
        return this;
    }

    @Override
    public Verification assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
        return this;
    }

    @Override
    public Verification assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
        return this;
    }

    @Override
    public Verification assertEquals(int actual, int expected, String message) {
        Assert.assertEquals(actual, expected, message);
        return this;
    }
}
