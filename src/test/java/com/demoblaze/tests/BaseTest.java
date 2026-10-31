package com.demoblaze.tests;

import com.demoblaze.apis.UserApi;
import com.demoblaze.drivers.Browser;
import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.tests.data.TestData;
import com.demoblaze.utils.data.PropertyReader;
import com.demoblaze.utils.logs.LogsManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public abstract class BaseTest {
    private static final ThreadLocal<GUIDriver> DRIVER = new ThreadLocal<>();

    protected GUIDriver driver() {
        return DRIVER.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        LogsManager.startTest(method.getName());
        DRIVER.set(new GUIDriver(Browser.fromConfig()));
        driver().browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        GUIDriver guiDriver = DRIVER.get();
        if (guiDriver != null) guiDriver.quitDriver();
        DRIVER.remove();
        LogsManager.endTest();
    }

    // Precondition through the API (§12): faster than the sign up modal, and it never hits the site's
    // broken-modal bug. Returns the new username, so each test keeps its own in a local variable
    protected String createUser() {
        String username = TestData.uniqueUsername();
        UserApi.signUp(username, TestData.password());
        return username;
    }

}
