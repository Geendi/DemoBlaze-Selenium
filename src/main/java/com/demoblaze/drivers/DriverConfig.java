package com.demoblaze.drivers;

import com.demoblaze.utils.data.PropertyReader;

final class DriverConfig {
    static final String WINDOW_WIDTH = "1920";
    static final String WINDOW_HEIGHT = "1080";

    private DriverConfig() {
    }

    static boolean isHeadless() {
        return "localHeadless".equalsIgnoreCase(PropertyReader.getProperty("executionType"));
    }
}
