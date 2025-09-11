package com.example.mnymng.model;

public class MenuItem {
    private String iconResId; // Changed from int to String
    private String name;
    private boolean isSelected = false; // Added this line

    public MenuItem(String iconResId, String name) { // Changed parameter type
        this.iconResId = iconResId;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public String getEmoji() {
        return this.iconResId;
    }

    // Added getter and setter for isSelected
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
