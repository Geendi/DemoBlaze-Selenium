package com.demoblaze.utils.api;

import com.demoblaze.utils.data.PropertyReader;
import com.demoblaze.utils.logs.LogsManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public final class ApiClient {
    private ApiClient() {
    }

    // A new spec for every request: RestAssured's static settings are shared by all threads,
    // a spec isn't, so parallel tests never change each other's request
    private static RequestSpecification request() {
        return RestAssured.given()
                .baseUri(PropertyReader.getProperty("baseUrlApi"))
                .contentType(ContentType.JSON);
    }

    // Sends the body as JSON and returns the response; the caller decides what counts as success.
    // DEBUG level, so the request details show only when investigating a failure (§12 logging)
    public static Response post(String path, String jsonBody) {
        Response response = request().body(jsonBody).post(path);
        LogsManager.debug("POST", path, "->", String.valueOf(response.statusCode()), response.asString().trim());
        return response;
    }
}
