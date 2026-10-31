package com.demoblaze.utils.media;

import com.demoblaze.utils.constants.PathConstants;
import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.reports.AllureAttachmentManager;
import com.demoblaze.utils.time.TimeManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ScreenshotManager {
    private ScreenshotManager() {
    }

    public static void takeScreenshot(WebDriver driver, String name) {
        try {
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Path file = PathConstants.SCREENSHOTS.resolve(name + "_" + TimeManager.getTimestamp() + ".png");
            Files.createDirectories(file.getParent());
            Files.write(file, png);
            AllureAttachmentManager.attachScreenshot(name, png);
            LogsManager.info("Screenshot saved:", file.toString());
        } catch (Exception e) {
            LogsManager.error("Failed to take screenshot for", name, e.toString());
        }
    }
}
