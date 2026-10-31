package com.demoblaze.tests.data;

import com.demoblaze.models.Category;
import com.demoblaze.models.OrderData;
import com.demoblaze.utils.data.JsonReader;
import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.time.TimeManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class TestData {
    private static final JsonReader PRODUCTS = new JsonReader("test-data/products");
    private static final JsonReader USERS = new JsonReader("test-data/users");
    private static final JsonReader ORDERS = new JsonReader("test-data/orders");
    private static final AtomicInteger USER_COUNTER = new AtomicInteger();

    private TestData() {
    }

    public static List<String> productKeys() {
        return PRODUCTS.getKeys("$");
    }

    public static int productId(String key) {
        return Integer.parseInt(PRODUCTS.getJsonData("$." + key + ".id"));
    }

    public static String productName(String key) {
        return PRODUCTS.getJsonData("$." + key + ".name");
    }

    public static int productPrice(String key) {
        return Integer.parseInt(PRODUCTS.getJsonData("$." + key + ".price"));
    }

    public static Category productCategory(String key) {
        return Category.valueOf(PRODUCTS.getJsonData("$." + key + ".category"));
    }

    public static String password() {
        return USERS.getJsonData("$.password");
    }

    public static String invalidPassword() {
        return USERS.getJsonData("$.invalidPassword");
    }

    public static OrderData order(String key) {
        String path = "$." + key + ".";
        return new OrderData(
                ORDERS.getJsonData(path + "name"),
                ORDERS.getJsonData(path + "country"),
                ORDERS.getJsonData(path + "city"),
                ORDERS.getJsonData(path + "card"),
                ORDERS.getJsonData(path + "month"),
                ORDERS.getJsonData(path + "year"));
    }

    public static String uniqueUsername() {
        String username = "ahmed_" + TimeManager.getTimestamp() + "_" + USER_COUNTER.incrementAndGet();
        LogsManager.info("Generated username:", username);
        return username;
    }
}