package com.demoblaze.pages.components;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.models.OrderData;
import com.demoblaze.pages.CartPage;
import org.openqa.selenium.By;

public class PlaceOrderModal {
    private final GUIDriver driver;

    private final By modal = By.id("orderModal");
    private final By nameInput = By.id("name");
    private final By countryInput = By.id("country");
    private final By cityInput = By.id("city");
    private final By cardInput = By.id("card");
    private final By monthInput = By.id("month");
    private final By yearInput = By.id("year");
    private final By purchaseButton = By.cssSelector("button[onclick='purchaseOrder()']");
    private final By closeButton = By.cssSelector("#orderModal .btn-secondary");

    public PlaceOrderModal(GUIDriver driver) {
        this.driver = driver;
    }

    public PurchaseConfirmation purchase(OrderData order) {
        driver.elementActions()
                .clearAndType(nameInput, order.name())
                .clearAndType(countryInput, order.country())
                .clearAndType(cityInput, order.city())
                .clearAndType(cardInput, order.card())
                .clearAndType(monthInput, order.month())
                .clearAndType(yearInput, order.year())
                .click(purchaseButton);
        return new PurchaseConfirmation(driver);
    }

    public String purchaseWithEmptyForm() {
        driver.elementActions().click(purchaseButton);
        return driver.alertActions().getTextAndAccept();
    }

    public CartPage close() {
        driver.elementActions().clickUntilInvisible(closeButton, modal);
        return new CartPage(driver);
    }
}
