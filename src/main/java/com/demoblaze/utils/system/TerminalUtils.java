package com.demoblaze.utils.system;

import com.demoblaze.utils.logs.LogsManager;

public final class TerminalUtils {
    private TerminalUtils() {
    }

    public static void executeTerminalCommand(String... commandParts) {
        String command = String.join(" ", commandParts);
        try {
            Process process = new ProcessBuilder(commandParts).inheritIO().start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LogsManager.error("Command failed with exit code", String.valueOf(exitCode) + ":", command);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogsManager.error("Terminal command interrupted:", command);
        } catch (Exception e) {
            LogsManager.error("Failed to execute terminal command:", command, e.getMessage());
        }
    }
}
