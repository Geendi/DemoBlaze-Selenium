package com.demoblaze.utils.files;

import com.demoblaze.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class FileHelper {
    private FileHelper() {
    }

    public static void renameFile(Path source, Path target) {
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            LogsManager.debug("File renamed from", source.toString(), "to", target.toString());
        } catch (IOException e) {
            LogsManager.error("Failed to rename", source.toString(), "to", target.toString(), e.toString());
        }
    }

    public static void createDirectory(Path path) {
        try {
            Files.createDirectories(path);
            LogsManager.debug("Directory ready:", path.toString());
        } catch (IOException e) {
            LogsManager.error("Failed to create directory", path.toString(), e.toString());
        }
    }

    public static void cleanDirectory(File file) {
        try {
            FileUtils.deleteQuietly(file);
        } catch (Exception e) {
            LogsManager.error("Failed to clean directory", file.getAbsolutePath(), e.getMessage());
        }
    }
}
