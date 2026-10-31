package com.demoblaze.utils.reports;

import com.demoblaze.utils.constants.PathConstants;

import java.nio.file.Path;

public final class AllureConstant {
    public static final Path REPORT_PATH = PathConstants.OUTPUT.resolve("reports");
    public static final Path FULL_REPORT_PATH = PathConstants.OUTPUT.resolve("full-report");

    public static final Path HISTORY_FOLDER = FULL_REPORT_PATH.resolve("history");
    public static final Path RESULTS_HISTORY_FOLDER = PathConstants.ALLURE_RESULTS.resolve("history");

    public static final String INDEX_HTML = "index.html";
    public static final String REPORT_PREFIX = "AllureReport_";
    public static final String REPORT_EXTENSION = ".html";

    public static final String ALLURE_ZIP_BASE_URL = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/";

    public static final Path EXTRACTION_DIR = Path.of(System.getProperty("user.home"), ".m2", "repository", "allure");

    private AllureConstant() {
    }
}
