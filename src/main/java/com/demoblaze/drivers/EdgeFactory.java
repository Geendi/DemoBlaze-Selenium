package com.demoblaze.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeFactory implements DriverFactory {
    static EdgeOptions options() {
        return ChromiumOptionsHelper.applyCommon(new EdgeOptions());
    }

    @Override
    public WebDriver createDriver() {
        return new EdgeDriver(options());
    }
}
