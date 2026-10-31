package com.demoblaze.tests;

import com.demoblaze.models.Category;
import com.demoblaze.pages.HomePage;
import com.demoblaze.tests.data.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class HomePageTest extends BaseTest {

    @DataProvider(name = "products")
    public Object[][] products() {
        return TestData.productKeys().stream()
                .map(key -> new Object[]{TestData.productName(key), TestData.productCategory(key)})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "products", groups = "regression", description = "Each product shows in its category")
    public void productShowsInItsCategory(String productName, Category category) {
        List<String> names = new HomePage(driver())
                .selectCategory(category)
                .getProductNames();

        driver().verification().assertTrue(names.contains(productName),
                productName + " missing from " + category.linkText() + ": " + names);
    }

    @Test(groups = "regression", description = "Next shows a different set of products")
    public void nextPageShowsOtherProducts() {
        HomePage home = new HomePage(driver());
        List<String> firstPage = home.getProductNames();
        List<String> secondPage = home.nextPage().getProductNames();

        driver().verification().assertFalse(firstPage.equals(secondPage), "Next page has the same products");
    }
}
