package com.example.mnymng.model;

public class MenuItem {
    private int iconResId;
    private String name;

    public MenuItem(int iconResId, String name) {
        this.iconResId = iconResId;
        this.name = name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }
}
