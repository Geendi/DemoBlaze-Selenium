package com.demoblaze.utils.reports;

import com.demoblaze.utils.constants.PathConstants;
import com.demoblaze.utils.files.FileHelper;
import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.system.OSUtils;
import com.demoblaze.utils.system.TerminalUtils;
import com.demoblaze.utils.time.TimeManager;
import org.apache.commons.io.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.demoblaze.utils.data.PropertyReader.getProperty;

public final class AllureReportGenerator {
    private AllureReportGenerator() {
    }

    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstant.REPORT_PATH : AllureConstant.FULL_REPORT_PATH;
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                PathConstants.ALLURE_RESULTS.toString(),
                "-o", outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }

    public static void openReport(String reportFileName) {
        if (!Boolean.parseBoolean(getProperty("openReportAfterExecution"))) return;
        if (!"local".equalsIgnoreCase(getProperty("executionType"))) return;

        String reportPath = AllureConstant.REPORT_PATH.resolve(reportFileName).toString();
        switch (OSUtils.getCurrentOS()) {
            case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", "", reportPath);
            case MAC -> TerminalUtils.executeTerminalCommand("open", reportPath);
            case LINUX -> TerminalUtils.executeTerminalCommand("xdg-open", reportPath);
            default -> LogsManager.warn("Opening allure report is not supported in this OS.");
        }
    }

    public static void copyHistory() {
        if (!Files.exists(AllureConstant.HISTORY_FOLDER)) return;
        try {
            FileUtils.copyDirectory(AllureConstant.HISTORY_FOLDER.toFile(), AllureConstant.RESULTS_HISTORY_FOLDER.toFile());
        } catch (Exception e) {
            LogsManager.error("Error copying history folder", e.getMessage());
        }
    }

    public static String renameReport() {
        String newFileName = AllureConstant.REPORT_PREFIX + TimeManager.getTimestamp() + AllureConstant.REPORT_EXTENSION;
        FileHelper.renameFile(AllureConstant.REPORT_PATH.resolve(AllureConstant.INDEX_HTML),
                AllureConstant.REPORT_PATH.resolve(newFileName));
        return newFileName;
    }
}
