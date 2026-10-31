package com.demoblaze.apis;

import com.demoblaze.utils.api.ApiClient;
import io.restassured.response.Response;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class UserApi {
    private UserApi() {
    }

    // Creates an account the UI can log in with.
    // Throws if the API refuses, so a broken precondition fails the setup with a clear reason
    // instead of a confusing failure later in the UI steps
    public static void signUp(String username, String password) {
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + encode(password) + "\"}";
        Response response = ApiClient.post("/signup", body);
        // Success is an empty body (""); a refusal is {"errorMessage": "..."} with status 200, so the status alone isn't enough
        if (response.statusCode() != 200 || response.asString().contains("errorMessage"))
            throw new IllegalStateException("Sign up failed for " + username + ": " + response.asString().trim());
    }

    // The site's JavaScript sends Base64(UTF-8 password) to the server (b64EncodeUnicode), and the
    // login form does the same. A plain password here would make every UI login say "Wrong password."
    private static String encode(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }
}
