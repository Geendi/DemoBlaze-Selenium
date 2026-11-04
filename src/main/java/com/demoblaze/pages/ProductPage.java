package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.components.NavBar;
import org.openqa.selenium.By;

public class ProductPage {
    private final GUIDriver driver;

    private final By productName = By.cssSelector(".name");
    private final By productPrice = By.cssSelector(".price-container");
    private final By addToCartButton = By.linkText("Add to cart");

    public ProductPage(GUIDriver driver) {
        this.driver = driver;
    }

    public NavBar navBar() {
        return new NavBar(driver);
    }

    public String addToCart() {
        driver.elementActions().click(addToCartButton);
        return driver.alertActions().getTextAndAccept();
    }

    public int getPrice() {
        String text = driver.elementActions().getNonEmptyText(productPrice);
        return Integer.parseInt(text.replaceFirst("^\\$(\\d+).*$", "$1"));
    }
}
