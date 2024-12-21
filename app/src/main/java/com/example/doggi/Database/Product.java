package com.example.doggi.Database;

public class Product {
    private int id;
    private String name;
    private String brand;
    private String description;
    private String price;
    private String image;

    public Product(int id, String name, String brand, String description, String price, String image) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
