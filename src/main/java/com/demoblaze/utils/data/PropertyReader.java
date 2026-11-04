package com.demoblaze.utils.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class PropertyReader {
    private static final String[] FILES = {"webApp.properties", "environment.properties"};

    private PropertyReader() {
    }

    public static void loadProperties() {
        Properties properties = new Properties();
        for (String file : FILES) {
            try (InputStream in = PropertyReader.class.getClassLoader().getResourceAsStream(file)) {
                if (in == null)
                    throw new IllegalStateException("Properties file not found on classpath: " + file);
                properties.load(in);
            } catch (IOException e) {
                throw new UncheckedIOException("Could not read properties file: " + file, e);
            }
        }
        properties.putAll(System.getProperties());
        System.getProperties().putAll(properties);
    }

    public static String getProperty(String key) {
        return System.getProperty(key);
    }
}
