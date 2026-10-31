package com.demoblaze.pages.components;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.HomePage;
import org.openqa.selenium.By;

public class SignUpModal {
    private final GUIDriver driver;

    private final By modal = By.id("signInModal");
    private final By usernameInput = By.id("sign-username");
    private final By passwordInput = By.id("sign-password");
    private final By signUpButton = By.cssSelector("button[onclick='register()']");
    private final By closeButton = By.cssSelector("#signInModal .btn-secondary");

    public SignUpModal(GUIDriver driver) {
        this.driver = driver;
    }

    public String signUp(String username, String password){
        driver.elementActions()
                .clearAndType(usernameInput, username)
                .clearAndType(passwordInput, password)
                .click(signUpButton);
        return driver.alertActions().getTextAndAccept();
    }

    public HomePage close(){
        driver.elementActions().clickUntilInvisible(closeButton, modal);
        return new HomePage(driver);
    }
}
