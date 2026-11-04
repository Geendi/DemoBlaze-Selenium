package com.demoblaze.utils.api;

import com.demoblaze.utils.data.PropertyReader;
import com.demoblaze.utils.logs.LogsManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public final class ApiClient {
    private static final ObjectMapper JSON = new ObjectMapper();

    private ApiClient() {
    }

    private static RequestSpecification request() {
        return RestAssured.given()
                .baseUri(PropertyReader.getProperty("baseUrlApi"))
                .contentType(ContentType.JSON);
    }

    public static Response post(String path, Map<String, ?> body) {
        String jsonBody = JSON.writeValueAsString(body);
        Response response = request().body(jsonBody).post(path);
        LogsManager.debug("POST", path, "->", String.valueOf(response.statusCode()), response.asString().trim());
        return response;
    }
}
