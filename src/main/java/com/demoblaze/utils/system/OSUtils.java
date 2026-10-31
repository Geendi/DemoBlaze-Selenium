package com.demoblaze.utils.system;

public final class OSUtils {
    private OSUtils() {
    }

    public static OS getCurrentOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return OS.WINDOWS;
        } else if (os.contains("mac")) {
            return OS.MAC;
        } else if (os.contains("nix") || os.contains("nux")) {
            return OS.LINUX;
        } else {
            return OS.OTHER;
        }
    }

    public enum OS {WINDOWS, MAC, LINUX, OTHER}
}
