package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.components.NavBar;
import com.demoblaze.pages.components.PlaceOrderModal;
import org.openqa.selenium.By;

import java.util.List;

public class CartPage {
    private final GUIDriver driver;

    private final By productNames = By.cssSelector("#tbodyid tr td:nth-child(2)");
    private final By total = By.id("totalp");
    private final By placeOrderButton = By.cssSelector(".btn.btn-success");

    public CartPage(GUIDriver driver) {
        this.driver = driver;
        String url = driver.browser().waitForUrlContains("cart.html");
        if (!url.contains("cart.html"))
            throw new IllegalStateException("Expected the cart page, but the browser is on " + url);
    }

    public NavBar navBar() {
        return new NavBar(driver);
    }

    public List<String> getProductNames() {
        return driver.elementActions().getTexts(productNames);
    }

    public int getTotal() {
        String text = driver.elementActions().getNonEmptyText(total);
        return Integer.parseInt(text);
    }

    public PlaceOrderModal placeOrderModal() {
        driver.elementActions().click(placeOrderButton);
        return new PlaceOrderModal(driver);
    }

    public CartPage deleteProduct(String productName) {
        By deleteLink = By.xpath("//tr[td[normalize-space()='" + productName + "']]//a[text()='Delete']");
        driver.elementActions().clickAndWaitForRefresh(deleteLink, productNames);
        return this;
    }
}
