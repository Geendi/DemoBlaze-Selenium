package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.models.Category;
import com.demoblaze.pages.components.NavBar;
import org.openqa.selenium.By;

import java.util.List;

public class HomePage {
    private final GUIDriver driver;

    private final By productNames = By.cssSelector("#tbodyid .card-title a");
    private final By nextButton = By.id("next2");
    private final By previousButton = By.id("prev2");

    public HomePage(GUIDriver driver) {
        this.driver = driver;
    }

    public NavBar navBar() {
        return new NavBar(driver);
    }

    public HomePage selectCategory(Category category) {
        driver.elementActions().clickAndWaitForRefresh(By.linkText(category.linkText()), productNames);
        return this;
    }

    public HomePage nextPage() {
        driver.elementActions().clickAndWaitForRefresh(nextButton, productNames);
        return this;
    }

    public HomePage previousPage() {
        driver.elementActions().clickAndWaitForRefresh(previousButton, productNames);
        return this;
    }

    public ProductPage openProduct(String productName) {
        driver.elementActions().click(By.linkText(productName));
        return new ProductPage(driver);
    }

    public List<String> getProductNames() {
        return driver.elementActions().getTexts(productNames);
    }
}
