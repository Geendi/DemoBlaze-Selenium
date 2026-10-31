package com.demoblaze.utils.actions;

import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.waits.WaitManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AlertActions {
    private final WaitManager waitManager;

    public AlertActions(WebDriver driver) {
        this.waitManager = new WaitManager(driver);
    }

    public AlertActions acceptAlert() {
        waitForAlert().accept();
        LogsManager.debug("Alert accepted.");
        return this;
    }

    public AlertActions dismissAlert() {
        waitForAlert().dismiss();
        LogsManager.debug("Alert dismissed.");
        return this;
    }

    public String getAlertText() {
        String text = waitForAlert().getText();
        LogsManager.debug("Alert text:", text);
        return text;
    }

    public AlertActions setAlertText(String text) {
        waitForAlert().sendKeys(text);
        LogsManager.debug("Typed text into alert:", text);
        return this;
    }

    public String getTextAndAccept() {
        String text = getAlertText();
        acceptAlert();
        return text;
    }

    private Alert waitForAlert() {
        return waitManager.fluentWait().until(ExpectedConditions.alertIsPresent());
    }
}
