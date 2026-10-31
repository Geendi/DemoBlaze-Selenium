package com.demoblaze.utils.reports;

import com.demoblaze.utils.constants.PathConstants;
import com.demoblaze.utils.logs.LogsManager;
import io.qameta.allure.Allure;
import io.qameta.allure.AttachmentOptions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public final class AllureAttachmentManager {
    private AllureAttachmentManager() {
    }

    public static void attachScreenshot(String name, byte[] png) {
        Allure.attachment(name, "image/png", new ByteArrayInputStream(png), AttachmentOptions.withFileExtension(".png"));
    }

    public static void attachLogs() {
        try {
            if (Files.exists(PathConstants.LOG_FILE)) {
                Allure.attachment("logs", "text/plain", logsOfCurrentTest(),
                        AttachmentOptions.withFileExtension(".log"));
            }
        } catch (IOException e) {
            LogsManager.error("Error attaching logs", e.toString());
        }
    }

    private static String logsOfCurrentTest() throws IOException {
        String test = LogsManager.currentTest();
        if (test == null) return Files.readString(PathConstants.LOG_FILE);
        String tag = "[" + test + "]";
        return Files.readAllLines(PathConstants.LOG_FILE).stream()
                .filter(line -> line.contains(tag))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
