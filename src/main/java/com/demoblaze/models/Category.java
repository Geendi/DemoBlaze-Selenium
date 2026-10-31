package com.demoblaze.models;

public enum Category {
    PHONES("Phones"),
    LAPTOPS("Laptops"),
    MONITORS("Monitors");

    private final String linkText;

    Category(String linkText) {
        this.linkText = linkText;
    }

    public String linkText() {
        return linkText;
    }
}
