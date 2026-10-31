package com.demoblaze.pages.components;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.HomePage;
import org.openqa.selenium.By;

public class AboutUsModal {
    private final GUIDriver driver;

    private final By modal = By.id("videoModal");
    private final By video = By.cssSelector("#videoModal video");
    private final By closeButton = By.cssSelector("#videoModal .btn-secondary");

    public AboutUsModal(GUIDriver driver) {
        this.driver = driver;
    }

    public boolean isVideoDisplayed() {
        return driver.elementActions().isDisplayed(video);
    }

    public HomePage close() {
        driver.elementActions().clickUntilInvisible(closeButton, modal);
        return new HomePage(driver);
    }
}
