package com.demoblaze.validations;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class Validation extends Assertion {
    private static final ThreadLocal<SoftAssert> SOFT_ASSERT = ThreadLocal.withInitial(SoftAssert::new);

    public Validation(WebDriver driver) {
        super(driver);
    }

    public static void assertAll() {
        try {
            SOFT_ASSERT.get().assertAll();
        } finally {
            SOFT_ASSERT.remove();
        }
    }

    @Override
    public Validation assertTrue(boolean condition, String message) {
        SOFT_ASSERT.get().assertTrue(condition, message);
        return this;
    }

    @Override
    public Validation assertFalse(boolean condition, String message) {
        SOFT_ASSERT.get().assertFalse(condition, message);
        return this;
    }

    @Override
    public Validation assertEquals(String actual, String expected, String message) {
        SOFT_ASSERT.get().assertEquals(actual, expected, message);
        return this;
    }

    @Override
    public Validation assertEquals(int actual, int expected, String message) {
        SOFT_ASSERT.get().assertEquals(actual, expected, message);
        return this;
    }
}
