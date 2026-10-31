package com.demoblaze.utils.data;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

public class JsonReader {
    private final String fileName;
    private final DocumentContext json;

    public JsonReader(String jsonFileName) {
        this.fileName = jsonFileName + ".json";
        try (InputStream in = JsonReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in == null)
                throw new IllegalStateException("File not found on classpath: " + fileName);
            json = JsonPath.parse(in);
        } catch (IOException e) {
            throw new UncheckedIOException("Can't read " + fileName, e);
        }
    }

    public String getJsonData(String jsonPath) {
        try {
            return json.read(jsonPath, String.class);
        } catch (PathNotFoundException e) {
            throw new IllegalArgumentException("No value at '" + jsonPath + "' in " + fileName, e);
        }
    }

    public List<String> getKeys(String jsonPath) {
        try {
            Object value = json.read(jsonPath);
            if (!(value instanceof Map<?, ?> object))
                throw new IllegalArgumentException("'" + jsonPath + "' in " + fileName + " is not a JSON object");
            return object.keySet().stream().map(String::valueOf).toList();
        } catch (PathNotFoundException e) {
            throw new IllegalArgumentException("No value at '" + jsonPath + "' in " + fileName, e);
        }
    }
}
