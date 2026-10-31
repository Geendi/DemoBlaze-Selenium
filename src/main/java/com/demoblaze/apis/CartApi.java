package com.demoblaze.apis;

import com.demoblaze.utils.api.ApiClient;
import com.demoblaze.utils.data.PropertyReader;
import io.restassured.response.Response;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.UUID;

public final class CartApi {

    private CartApi() {
    }

    public static void addToCart(String userCookie, int productId) {
        // "id" names this cart row; the site makes a random one with guid(), a UUID does the same
        String rowId = UUID.randomUUID().toString();
        String body = "{\"id\":\"" + rowId + "\",\"cookie\":\"user=" + userCookie
                + "\",\"prod_id\":" + productId + ",\"flag\":false}";
        Response response = ApiClient.post("/addtocart", body);
        if (response.statusCode() != 200 || response.asString().contains("errorMessage"))
            throw new IllegalStateException("Add to cart failed for product " + productId + ": " + response.asString().trim());
        waitUntilInCart(userCookie, rowId);
    }
    
    private static void waitUntilInCart(String userCookie, String rowId) {
        String body = "{\"cookie\":\"user=" + userCookie + "\",\"flag\":false}";
        new FluentWait<>(body)
                .withTimeout(Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("fluentWaitSeconds"))))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("cart row " + rowId + " to show in /viewcart")
                .until(b -> ApiClient.post("/viewcart", b).asString().contains(rowId));
    }
}
