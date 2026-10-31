package com.demoblaze.tests;

import com.demoblaze.models.Category;
import com.demoblaze.pages.HomePage;
import com.demoblaze.tests.data.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {

    @DataProvider(name = "prices")
    public Object[][] prices() {
        return TestData.productKeys().stream()
                .map(key -> new Object[]{TestData.productName(key), TestData.productPrice(key), TestData.productCategory(key)})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "prices", groups = "regression", description = "Product page shows the right price")
    public void productPageShowsCorrectPrice(String productName, int price, Category category) {
        int actual = new HomePage(driver())
                .selectCategory(category)
                .openProduct(productName)
                .getPrice();

        driver().verification().assertEquals(String.valueOf(actual), String.valueOf(price), "Price of " + productName);
    }

    @Test(groups = {"smoke", "regression"}, description = "Add to cart shows the confirmation alert")
    public void addToCartShowsProductAdded() {
        String alert = new HomePage(driver())
                .openProduct(TestData.productName("samsungGalaxyS6"))
                .addToCart();

        driver().verification().assertEquals(alert, "Product added", "Add to cart alert");
    }
}
