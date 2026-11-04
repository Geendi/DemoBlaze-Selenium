package com.demoblaze.drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chromium.ChromiumOptions;

final class ChromiumOptionsHelper {
    private ChromiumOptionsHelper() {
    }

    static <T extends ChromiumOptions<?>> T applyCommon(T options) {
        options.addArguments("--guest");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("--window-size=" + DriverConfig.WINDOW_WIDTH + "," + DriverConfig.WINDOW_HEIGHT);
        if (DriverConfig.isHeadless())
            options.addArguments("--headless=new");
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }
}
