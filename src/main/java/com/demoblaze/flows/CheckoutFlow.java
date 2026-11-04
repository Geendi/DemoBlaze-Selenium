package com.demoblaze.flows;

import com.demoblaze.apis.CartApi;
import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.models.OrderData;
import com.demoblaze.pages.CartPage;
import com.demoblaze.pages.components.NavBar;
import com.demoblaze.pages.components.PurchaseConfirmation;

public class CheckoutFlow {
    private final GUIDriver driver;

    public CheckoutFlow(GUIDriver driver) {
        this.driver = driver;
    }

    public CheckoutFlow addToCart(int productId) {
        CartApi.addToCart(driver.browser().getCookie("user"), productId);
        return this;
    }

    public CartPage openCart() {
        return new NavBar(driver).openCart();
    }

    public PurchaseConfirmation buy(int productId, OrderData order) {
        return addToCart(productId).openCart().placeOrderModal().purchase(order);
    }

    public CartPage buyAndOpenCart(int productId, OrderData order) {
        buy(productId, order).confirm();
        CartApi.waitUntilEmpty(driver.browser().getCookie("user"));
        return openCart();
    }
}

