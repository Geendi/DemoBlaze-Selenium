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
    
    private static void waitUntilInCart(String userCookie, String rowId) {
        Map<String, Object> body = Map.of("cookie", "user=" + userCookie, "flag", false);
        new FluentWait<>(body)
                .withTimeout(WaitManager.timeout())
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("cart row " + rowId + " to show in /viewcart")
                .until(b -> ApiClient.post("/viewcart", b).asString().contains(rowId));
    }
}
