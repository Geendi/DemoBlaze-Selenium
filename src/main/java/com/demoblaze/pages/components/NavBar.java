package com.demoblaze.pages.components;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.CartPage;
import com.demoblaze.pages.HomePage;
import org.openqa.selenium.By;

public class NavBar {
    private final GUIDriver driver;
    private final By homeLink = By.cssSelector("a.nav-link[href='index.html']");
    private final By contactLink = By.linkText("Contact");
    private final By aboutUsLink = By.linkText("About us");
    private final By cartLink = By.id("cartur");
    private final By loginLink = By.id("login2");
    private final By signUpLink = By.id("signin2");
    private final By logoutLink = By.id("logout2");
    private final By welcomeUser = By.id("nameofuser");

    public NavBar(GUIDriver driver) {
        this.driver = driver;
    }

    public HomePage openHome() {
        driver.elementActions().clickAndWaitForRefresh(homeLink, homeLink);
        return new HomePage(driver);
    }

    public CartPage openCart() {
        driver.elementActions().click(cartLink);
        return new CartPage(driver);
    }

    public LoginModal openLogin() {
        driver.elementActions().click(loginLink);
        return new LoginModal(driver);
    }

    public SignUpModal openSignUp() {
        driver.elementActions().click(signUpLink);
        return new SignUpModal(driver);
    }

    public ContactModal openContactUs() {
        driver.elementActions().click(contactLink);
        return new ContactModal(driver);
    }

    public AboutUsModal openAboutUs() {
        driver.elementActions().click(aboutUsLink);
        return new AboutUsModal(driver);
    }

    public HomePage logout() {
        driver.elementActions().click(logoutLink);
        return new HomePage(driver);
    }

    public String getWelcomeText() {
        return driver.elementActions().getText(welcomeUser);
    }

    public boolean isLoggedIn() {
        return driver.elementActions().isDisplayed(logoutLink);
    }

    public boolean isLoggedOut() {
        return driver.elementActions().isDisplayed(loginLink);
    }
}
