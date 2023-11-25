package com.monarca.backendmonarca.domain.product;

public record DataRegisterProduct(String name, String description, Integer stock, Double price, byte[] image, String warranty, String brand, String size, String color, String type) {
}
