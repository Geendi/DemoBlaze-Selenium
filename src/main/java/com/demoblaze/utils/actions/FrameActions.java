package com.demoblaze.utils.actions;

import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.waits.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FrameActions {
    private final WebDriver driver;
    private final WaitManager waitManager;

    public FrameActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    public FrameActions switchToFrameByIndex(int index) {
        waitManager.fluentWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        LogsManager.debug("Switched to frame by index:", String.valueOf(index));
        return this;
    }

    public FrameActions switchToFrameByNameOrId(String nameOrId) {
        waitManager.fluentWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
        LogsManager.debug("Switched to frame by name or id:", nameOrId);
        return this;
    }

    public FrameActions switchToFrameByElement(By frameLocator) {
        waitManager.fluentWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        LogsManager.debug("Switched to frame by element:", frameLocator.toString());
        return this;
    }

    public FrameActions switchToDefaultContent() {
        driver.switchTo().defaultContent();
        LogsManager.debug("Switched to default content.");
        return this;
    }
}
