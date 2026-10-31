package com.demoblaze.pages.components;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.HomePage;
import org.openqa.selenium.By;

public class ContactModal {
    private final GUIDriver driver;

    private final By modal = By.id("exampleModal");
    private final By emailInput = By.id("recipient-email");
    private final By nameInput = By.id("recipient-name");
    private final By messageInput = By.id("message-text");
    private final By sendButton = By.cssSelector("button[onclick='send()']");
    private final By closeButton = By.cssSelector("#exampleModal .btn-secondary");

    public ContactModal(GUIDriver driver) {
        this.driver = driver;
    }

    public String sendMessage(String email, String name, String message){
        driver.elementActions()
                .clearAndType(emailInput, email)
                .clearAndType(nameInput, name)
                .clearAndType(messageInput, message)
                .click(sendButton);
        return driver.alertActions().getTextAndAccept();
    }

    public HomePage close(){
        driver.elementActions().clickUntilInvisible(closeButton, modal);
        return new HomePage(driver);
    }

}
