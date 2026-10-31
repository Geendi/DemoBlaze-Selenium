package com.demoblaze.tests;

import com.demoblaze.pages.HomePage;
import org.testng.annotations.Test;

public class AboutUsTest extends BaseTest {
    @Test(groups = "regression", description = "About us opens the video")
    public void aboutUsShowsVideo() {
        boolean videoShown = new HomePage(driver())
                .navBar()
                .openAboutUs()
                .isVideoDisplayed();

        driver().verification().assertTrue(videoShown, "About us video");
    }
}
