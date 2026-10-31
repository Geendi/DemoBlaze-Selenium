package com.demoblaze.tests;

import com.demoblaze.pages.HomePage;
import org.testng.annotations.Test;

public class ContactTest extends BaseTest {

    @Test(groups = "regression", description = "Sending a contact message shows a thank-you alert")
    public void sendMessageShowsThanks() {
        String alert = new HomePage(driver())
                .navBar()
                .openContactUs()
                .sendMessage("ahmed@example.com", "Ahmed", "Test message");

        driver().verification().assertEquals(alert, "Thanks for the message!!", "Contact alert");
    }
}
