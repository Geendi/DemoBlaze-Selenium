package com.demoblaze.pages.components;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.HomePage;
import org.openqa.selenium.By;

public class PurchaseConfirmation {
    private final GUIDriver driver;

    private final By title = By.xpath("//h2[normalize-space(.)='Thank you for your purchase!']");
    private final By details = By.cssSelector(".lead.text-muted");
    private final By okButton = By.cssSelector(".confirm.btn.btn-lg.btn-primary");
    private final By box = By.cssSelector(".sweet-alert");
    private final By openBox = By.cssSelector(".sweet-alert.visible");

    public PurchaseConfirmation(GUIDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.elementActions().getText(title);
    }

    public String getDetails() {
        return driver.elementActions().getText(details);
    }

    public HomePage confirm() {
        driver.elementActions()
                .waitForVisibility(openBox)
                .click(okButton)
                .waitForInvisibility(box);
        return new HomePage(driver);
    }
}
