package com.demoblaze.pages.components;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.HomePage;
import org.openqa.selenium.By;

public class LoginModal {
    private final GUIDriver driver;

    private final By modal = By.id("logInModal");
    private final By usernameInput = By.id("loginusername");
    private final By passwordInput = By.id("loginpassword");
    private final By loginButton = By.cssSelector("button[onclick='logIn()']");
    private final By closeButton = By.cssSelector("#logInModal .btn-secondary");

    public LoginModal(GUIDriver driver) {
        this.driver = driver;
    }

    public HomePage login(String username, String password) {
        fillAndSubmit(username, password);
        driver.elementActions().waitForInvisibility(modal);
        return new HomePage(driver);
    }

    public String loginExpectingError(String username, String password) {
        fillAndSubmit(username, password);
        return driver.alertActions().getTextAndAccept();
    }

    public HomePage close() {
        driver.elementActions().clickUntilInvisible(closeButton, modal);
        return new HomePage(driver);
    }

    private void fillAndSubmit(String username, String password) {
        driver.elementActions()
                .clearAndType(usernameInput, username)
                .clearAndType(passwordInput, password)
                .click(loginButton);
    }

}
