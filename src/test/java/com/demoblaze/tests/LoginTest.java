package com.demoblaze.tests;

import com.demoblaze.pages.HomePage;
import com.demoblaze.tests.data.TestData;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "Valid login shows the username in the navbar")
    public void validLogin() {
        String username = createUser();
        HomePage home = new HomePage(driver())
                .navBar()
                .openLogin()
                .login(username, TestData.password());
        driver().verification().assertEquals(home.navBar().getWelcomeText(), "Welcome " + username, "Welcome text");
    }

    @Test(groups = "regression", description = "Wrong password shows an alert")
    public void loginWithInvalidPassword() {
        String username = createUser();
        String alert = new HomePage(driver())
                .navBar()
                .openLogin()
                .loginExpectingError(username, TestData.invalidPassword());
        driver().verification().assertEquals(alert, "Wrong password.", "Wrong password alert");
    }

    @Test(groups = "regression", description = "A username that never signed up can't log in")
    public void loginWithInvalidUser() {
        String alert = new HomePage(driver())
                .navBar()
                .openLogin()
                .loginExpectingError(TestData.uniqueUsername(), TestData.password());

        driver().verification().assertEquals(alert, "User does not exist.", "Unknown user alert");
    }

    @Test(groups = "regression", description = "Logout shows the Log in link again")
    public void logout() {
        String username = createUser();
        HomePage home = new HomePage(driver())
                .navBar()
                .openLogin()
                .login(username, TestData.password())
                .navBar()
                .logout();

        driver().verification().assertTrue(home.navBar().isLoggedOut(), "Log in link after logout");
    }

}
