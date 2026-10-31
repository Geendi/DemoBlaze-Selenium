package com.demoblaze.tests;

import com.demoblaze.pages.HomePage;
import com.demoblaze.tests.data.TestData;
import org.testng.annotations.Test;

public class SignUpTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "A new username can sign up")
    public void signUpWithNewUser() {
        String alert = new HomePage(driver())
                .navBar()
                .openSignUp()
                .signUp(TestData.uniqueUsername(), TestData.password());
        driver().verification().assertEquals(alert, "Sign up successful.", "Sign up alert");
    }

    @Test(groups = "regression", description = "The same username can't sign up twice")
    public void signUpWithExistingUser() {
        String username = createUser();
        String alert = new HomePage(driver())
                .navBar()
                .openSignUp()
                .signUp(username, TestData.password());
        driver().verification().assertEquals(alert, "This user already exist.", "Duplicate sign up alert");
    }

    @Test(groups = "regression", description = "Sign up needs a username and a password")
    public void signUpWithEmptyFields() {
        String alert = new HomePage(driver())
                .navBar()
                .openSignUp()
                .signUp("", "");
        driver().verification().assertEquals(alert, "Please fill out Username and Password.", "Empty sign up alert");
    }
}
