package com.demoblaze.utils.reports;

import com.demoblaze.utils.data.PropertyReader;
import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.system.OSUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class AllureBinaryManager {
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private AllureBinaryManager() {
    }

    public static void downloadAndExtract() {
        Path executable = getExecutable();
        if (Files.exists(executable)) {
            LogsManager.debug("Allure binaries already exist.");
            return;
        }
        try {
            Files.createDirectories(AllureConstant.EXTRACTION_DIR);
            Path zipPath = downloadZip(version());
            extractZip(zipPath);
            Files.deleteIfExists(zipPath);
            if (!executable.toFile().setExecutable(true))
                LogsManager.warn("Could not mark Allure as executable:", executable.toString());
            LogsManager.info("Allure", version(), "downloaded and extracted.");
        } catch (Exception e) {
            LogsManager.error("Error downloading or extracting Allure binaries:", e.toString());
        }
    }

    public static Path getExecutable() {
        Path binFolder = AllureConstant.EXTRACTION_DIR.resolve("allure-" + version()).resolve("bin");
        return OSUtils.getCurrentOS() == OSUtils.OS.WINDOWS
                ? binFolder.resolve("allure.bat")
                : binFolder.resolve("allure");
    }

    private static String version() {
        String version = PropertyReader.getProperty("allureVersion");
        if (version == null || version.isBlank())
            throw new IllegalStateException("allureVersion is not set in webApp.properties");
        return version.trim();
    }

    private static Path downloadZip(String version) throws Exception {
        String url = AllureConstant.ALLURE_ZIP_BASE_URL + version + "/allure-commandline-" + version + ".zip";
        Path zipFile = AllureConstant.EXTRACTION_DIR.resolve("allure-" + version + ".zip");
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofMinutes(2))
                .GET()
                .build();
        HttpResponse<Path> response = HTTP.send(request, HttpResponse.BodyHandlers.ofFile(zipFile));
        if (response.statusCode() != 200) {
            Files.deleteIfExists(zipFile);
            throw new IOException("Allure download failed with status " + response.statusCode() + ": " + url);
        }
        return zipFile;
    }

    private static void extractZip(Path zipPath) throws IOException {
        Path root = AllureConstant.EXTRACTION_DIR.normalize();
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path filePath = root.resolve(entry.getName()).normalize();
                if (!filePath.startsWith(root))
                    throw new IOException("Bad zip entry: " + entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zipInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
