package com.demoblaze.tests;

import com.demoblaze.flows.CheckoutFlow;
import com.demoblaze.pages.components.PurchaseConfirmation;
import com.demoblaze.tests.data.TestData;
import org.testng.annotations.Test;

import java.util.List;

public class CheckoutTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "A valid order shows the thank-you box with the amount")
    public void trxDetailsIsDisplayedAfterPurchase() {
        int price = TestData.productPrice("samsungGalaxyS6");

        PurchaseConfirmation confirmation = new CheckoutFlow(driver())
                .buy(TestData.productId("samsungGalaxyS6"), TestData.order("validOrder"));

        driver().validation()
                .assertEquals(confirmation.getTitle(), "Thank you for your purchase!", "Confirmation title")
                .assertTrue(confirmation.getDetails().contains("Amount: " + price + " USD"),
                        "Amount in: " + confirmation.getDetails());
    }

    @Test(groups = "regression", description = "Purchase needs name and card")
    public void purchaseWithEmptyFormShowsError() {
        String alert = new CheckoutFlow(driver())
                .addToCart(TestData.productId("samsungGalaxyS6"))
                .openCart()
                .placeOrderModal()
                .purchaseWithEmptyForm();

        driver().verification().assertEquals(alert, "Please fill out Name and Creditcard.", "Empty order alert");
    }

    @Test(groups = "regression", description = "The cart is empty after a purchase")
    public void cartIsEmptyAfterPurchase() {
        List<String> names = new CheckoutFlow(driver())
                .buy(TestData.productId("samsungGalaxyS6"), TestData.order("validOrder"))
                .confirm()
                .navBar()
                .openCart()
                .getProductNames();

        driver().verification().assertTrue(names.isEmpty(), "Cart after purchase: " + names);
    }
}
