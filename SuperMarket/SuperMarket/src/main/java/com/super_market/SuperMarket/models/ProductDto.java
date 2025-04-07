package com.super_market.SuperMarket.models;

import jakarta.validation.constraints.*;
public class ProductDto {
    @NotEmpty(message = "The Product Name is required")
    private String name;

    @NotEmpty(message = "The Category is required")
    private String category;

    @Positive(message = "The Price must be a positive value")
    private double price;

    @PositiveOrZero(message = "The Quantity in Stock cannot be negative")
    private int quantityInStock;

    @NotEmpty(message = "The Description is required")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
