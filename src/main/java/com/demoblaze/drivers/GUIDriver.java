package com.demoblaze.drivers;

import com.demoblaze.utils.actions.AlertActions;
import com.demoblaze.utils.actions.BrowserActions;
import com.demoblaze.utils.actions.ElementActions;
import com.demoblaze.utils.actions.FrameActions;
import com.demoblaze.validations.Validation;
import com.demoblaze.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;


public class GUIDriver {
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private final WebDriver driver;
    private final ElementActions elementActions;
    private final BrowserActions browserActions;
    private final AlertActions alertActions;
    private final FrameActions frameActions;
    private final Validation validation;
    private final Verification verification;

    public GUIDriver(DriverFactory driverFactory) {
        this.driver = ThreadGuard.protect(driverFactory.createDriver());
        DRIVER_THREAD_LOCAL.set(this.driver);

        this.elementActions = new ElementActions(this.driver);
        this.browserActions = new BrowserActions(this.driver);
        this.alertActions = new AlertActions(this.driver);
        this.frameActions = new FrameActions(this.driver);
        this.validation = new Validation(this.driver);
        this.verification = new Verification(this.driver);
    }

    public static WebDriver getDriver() {
        return DRIVER_THREAD_LOCAL.get();
    }

    public void quitDriver() {
        driver.quit();
        DRIVER_THREAD_LOCAL.remove();
    }

    public AlertActions alertActions() {
        return alertActions;
    }

    public BrowserActions browser() {
        return browserActions;
    }

    public ElementActions elementActions() {
        return elementActions;
    }

    public FrameActions frameActions() {
        return frameActions;
    }

    public Validation validation() {
        return validation;
    }

    public Verification verification() {
        return verification;
    }
}
