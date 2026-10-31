package com.demoblaze.utils.constants;

import java.nio.file.Path;

public final class PathConstants {
    public static final Path USER_DIR = Path.of(System.getProperty("user.dir"));
    public static final Path OUTPUT = USER_DIR.resolve("test-output");
    public static final Path ALLURE_RESULTS = OUTPUT.resolve("allure-results");
    public static final Path SCREENSHOTS = OUTPUT.resolve("screenshots");
    public static final Path LOGS = OUTPUT.resolve("logs");
    public static final Path LOG_FILE = LOGS.resolve("logs.log");

    private PathConstants() {
    }
}
