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
    protected GUIDriver driver() {
        return GUIDriver.current();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        LogsManager.startTest(method.getName());
        GUIDriver.start(Browser.fromConfig())
                .browser()
                .navigateTo(PropertyReader.getProperty("baseUrlWeb"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        GUIDriver guiDriver = GUIDriver.current();
        if (guiDriver != null) guiDriver.quitDriver();
        LogsManager.endTest();
    }

    protected String createUser() {
        String username = TestData.uniqueUsername();
        UserApi.signUp(username, TestData.password());
        return username;
    }

}
