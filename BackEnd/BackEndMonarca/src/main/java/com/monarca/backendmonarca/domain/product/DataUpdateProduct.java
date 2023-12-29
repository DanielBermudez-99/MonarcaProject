package com.monarca.backendmonarca.domain.product;

public record DataUpdateProduct(String name, String description, Integer stock, Double price, String image_url, String warranty, String brand, String size, String color, String type) {
}
