package com.demoblaze.drivers;

import com.demoblaze.utils.data.PropertyReader;

import java.util.function.Supplier;

public enum Browser {
    CHROME(ChromeFactory::new),
    EDGE(EdgeFactory::new),
    FIREFOX(FirefoxFactory::new);

    private final Supplier<DriverFactory> factory;

    Browser(Supplier<DriverFactory> factory) {
        this.factory = factory;
    }

    public static DriverFactory fromConfig() {
        if ("remote".equalsIgnoreCase(PropertyReader.getProperty("executionType")))
            return new RemoteFactory();
        return fromConfigName().getDriverFactory();
    }

    static Browser fromConfigName() {
        String browser = PropertyReader.getProperty("browserType");
        if (browser == null || browser.isBlank())
            throw new IllegalStateException("browserType is not set in webApp.properties");
        return valueOf(browser.trim().toUpperCase());
    }

    public DriverFactory getDriverFactory() {
        return factory.get();
    }
}
