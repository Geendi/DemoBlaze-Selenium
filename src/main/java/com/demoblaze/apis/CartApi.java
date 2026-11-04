package com.demoblaze.apis;

import com.demoblaze.utils.api.ApiClient;
import com.demoblaze.utils.waits.WaitManager;
import io.restassured.response.Response;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

public final class CartApi {

    private CartApi() {
    }

    public static void addToCart(String userCookie, int productId) {
        String rowId = UUID.randomUUID().toString();
        Response response = ApiClient.post("/addtocart", Map.of(
                "id", rowId,
                "cookie", "user=" + userCookie,
                "prod_id", productId,
                "flag", false));
        if (response.statusCode() != 200 || response.asString().contains("errorMessage"))
            throw new IllegalStateException("Add to cart failed for product " + productId + ": " + response.asString().trim());
        waitUntilInCart(userCookie, rowId);
    }
    
    public static void waitUntilEmpty(String userCookie) {
        new FluentWait<>(viewCartBody(userCookie))
                .withTimeout(WaitManager.timeout())
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("the cart to be empty in /viewcart")
                .until(b -> ApiClient.post("/viewcart", b).jsonPath().getList("Items").isEmpty());
    }

    private static void waitUntilInCart(String userCookie, String rowId) {
        new FluentWait<>(viewCartBody(userCookie))
                .withTimeout(WaitManager.timeout())
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("cart row " + rowId + " to show in /viewcart")
                .until(b -> ApiClient.post("/viewcart", b).asString().contains(rowId));
    }

    private static Map<String, Object> viewCartBody(String userCookie) {
        return Map.of("cookie", "user=" + userCookie, "flag", false);
    }
}
