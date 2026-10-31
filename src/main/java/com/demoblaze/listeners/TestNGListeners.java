package com.demoblaze.listeners;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.utils.constants.PathConstants;
import com.demoblaze.utils.data.PropertyReader;
import com.demoblaze.utils.files.FileHelper;
import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.media.ScreenshotManager;
import com.demoblaze.utils.reports.AllureAttachmentManager;
import com.demoblaze.utils.reports.AllureBinaryManager;
import com.demoblaze.utils.reports.AllureEnvironmentManager;
import com.demoblaze.utils.reports.AllureReportGenerator;
import com.demoblaze.validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.nio.file.Files;


public class TestNGListeners implements IInvokedMethodListener, ITestListener, IExecutionListener {

    public void onExecutionStart() {
        PropertyReader.loadProperties();
        cleanTestOutputDirectories();
        createTestOutputDirectories();
        LogsManager.info("Test execution started.");
        AllureEnvironmentManager.setEnvironmentVariables();
        AllureBinaryManager.downloadAndExtract();
    }

    public void onExecutionFinish() {
        if (!Files.exists(AllureBinaryManager.getExecutable())) {
            LogsManager.warn("Allure binaries not available, skipping report generation.");
            return;
        }
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) return;

        try {
            Validation.assertAll();
        } catch (AssertionError e) {
            LogsManager.error("Soft assertion failures:", e.getMessage());
            if (testResult.isSuccess()) {
                testResult.setStatus(ITestResult.FAILURE);
                testResult.setThrowable(e);
            }
        }

        WebDriver driver = GUIDriver.getDriver();
        if (driver != null && !testResult.isSuccess()) {
            ScreenshotManager.takeScreenshot(driver, testResult.getName());
            AllureAttachmentManager.attachLogs();
        }
    }

    public void onTestStart(ITestResult result) {
        LogsManager.info("Test case " + result.getName() + " started");
    }

    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test case " + result.getName() + " passed");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.error("Test case " + result.getName() + " failed");
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.warn("Test case " + result.getName() + " skipped");
    }

    private void cleanTestOutputDirectories() {
        FileHelper.cleanDirectory(PathConstants.ALLURE_RESULTS.toFile());
        FileHelper.cleanDirectory(PathConstants.SCREENSHOTS.toFile());
    }

    private void createTestOutputDirectories() {
        FileHelper.createDirectory(PathConstants.SCREENSHOTS);
    }
}
