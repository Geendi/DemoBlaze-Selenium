package com.demoblaze.apis;

import com.demoblaze.utils.api.ApiClient;
import io.restassured.response.Response;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public final class UserApi {
    private UserApi() {
    }

    public static void signUp(String username, String password) {
        Response response = ApiClient.post("/signup", Map.of("username", username, "password", encode(password)));
        if (response.statusCode() != 200 || response.asString().contains("errorMessage"))
            throw new IllegalStateException("Sign up failed for " + username + ": " + response.asString().trim());
    }

    private static String encode(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }
}
