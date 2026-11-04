package com.demoblaze.drivers;

import com.demoblaze.utils.actions.AlertActions;
import com.demoblaze.utils.actions.BrowserActions;
import com.demoblaze.utils.actions.ElementActions;
import com.demoblaze.utils.actions.FrameActions;
import com.demoblaze.utils.media.ScreenshotManager;
import com.demoblaze.validations.Validation;
import com.demoblaze.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private static final ThreadLocal<GUIDriver> CURRENT = new ThreadLocal<>();

    private final WebDriver driver;
    private final ElementActions elementActions;
    private final BrowserActions browserActions;
    private final AlertActions alertActions;
    private final FrameActions frameActions;
    private final Validation validation;
    private final Verification verification;

    private GUIDriver(DriverFactory driverFactory) {
        this.driver = ThreadGuard.protect(driverFactory.createDriver());

        this.elementActions = new ElementActions(this.driver);
        this.browserActions = new BrowserActions(this.driver);
        this.alertActions = new AlertActions(this.driver);
        this.frameActions = new FrameActions(this.driver);
        this.validation = new Validation();
        this.verification = new Verification();
    }

    public static GUIDriver start(DriverFactory driverFactory) {
        GUIDriver guiDriver = new GUIDriver(driverFactory);
        CURRENT.set(guiDriver);
        return guiDriver;
    }

    public static GUIDriver current() {
        return CURRENT.get();
    }

    public void takeScreenshot(String name) {
        ScreenshotManager.takeScreenshot(driver, name);
    }

    public void quitDriver() {
        try {
            driver.quit();
        } finally {
            CURRENT.remove();
        }
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
