package com.example.fabriscan;

public class CategoryItem {
    private String name;
    private String description;
    private int imageResource;

    public CategoryItem(String name, String description, int imageResource) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }
} 