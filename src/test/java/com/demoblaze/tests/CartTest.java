package com.demoblaze.tests;

import com.demoblaze.flows.CheckoutFlow;
import com.demoblaze.pages.CartPage;
import com.demoblaze.tests.data.TestData;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "An added product shows in the cart")
    public void addedProductShowsInCart() {
        String samsung = TestData.productName("samsungGalaxyS6");

        List<String> names = new CheckoutFlow(driver())
                .addToCart(TestData.productId("samsungGalaxyS6"))
                .openCart()
                .getProductNames();

        driver().verification().assertTrue(names.contains(samsung), samsung + " missing from cart: " + names);
    }

    @Test(groups = "regression", description = "Cart total is the sum of the product prices")
    public void cartTotalIsSumOfPrices() {
        int expected = TestData.productPrice("samsungGalaxyS6") + TestData.productPrice("nokiaLumia1520");

        int total = new CheckoutFlow(driver())
                .addToCart(TestData.productId("samsungGalaxyS6"))
                .addToCart(TestData.productId("nokiaLumia1520"))
                .openCart()
                .getTotal();

        driver().verification().assertEquals(total, expected, "Cart total");
    }

    @Test(groups = "regression", description = "Delete removes only that product")
    public void deleteRemovesOnlyThatProduct() {
        String samsung = TestData.productName("samsungGalaxyS6");
        String nokia = TestData.productName("nokiaLumia1520");

        CartPage cart = new CheckoutFlow(driver())
                .addToCart(TestData.productId("samsungGalaxyS6"))
                .addToCart(TestData.productId("nokiaLumia1520"))
                .openCart()
                .deleteProduct(samsung);
        List<String> names = cart.getProductNames();

        driver().validation()
                .assertFalse(names.contains(samsung), samsung + " still in cart: " + names)
                .assertTrue(names.contains(nokia), nokia + " missing from cart: " + names);
    }
}
