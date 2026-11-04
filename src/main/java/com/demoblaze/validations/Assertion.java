package com.demoblaze.validations;

public abstract class Assertion {

    public abstract Assertion assertTrue(boolean condition, String message);

    public abstract Assertion assertFalse(boolean condition, String message);

    public abstract Assertion assertEquals(String actual, String expected, String message);

    public abstract Assertion assertEquals(int actual, int expected, String message);
}
