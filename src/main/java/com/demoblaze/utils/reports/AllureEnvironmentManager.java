package com.demoblaze.utils.reports;

import com.demoblaze.utils.constants.PathConstants;
import com.demoblaze.utils.logs.LogsManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Properties;

import static com.demoblaze.utils.data.PropertyReader.getProperty;

public final class AllureEnvironmentManager {
    private AllureEnvironmentManager() {
    }

    public static void setEnvironmentVariables() {
        Properties environment = new Properties();
        environment.setProperty("OS", valueOf("os.name"));
        environment.setProperty("Java version", valueOf("java.runtime.version"));
        environment.setProperty("Browser", valueOf("browserType"));
        environment.setProperty("Execution Type", valueOf("executionType"));
        environment.setProperty("URL", valueOf("baseUrlWeb"));
        try {
            Files.createDirectories(PathConstants.ALLURE_RESULTS);
            try (OutputStream out = Files.newOutputStream(PathConstants.ALLURE_RESULTS.resolve("environment.properties"))) {
                environment.store(out, null);
            }
            LogsManager.debug("Allure environment variables set.");
        } catch (IOException e) {
            LogsManager.error("Failed to write Allure environment:", e.toString());
        }
    }

    private static String valueOf(String key) {
        return Objects.requireNonNullElse(getProperty(key), "N/A");
    }
}
