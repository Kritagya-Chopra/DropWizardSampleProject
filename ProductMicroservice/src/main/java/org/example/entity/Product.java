package org.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.*;


public class Product {

    private Integer productID;
    @NotEmpty
    private String name;
    @NotEmpty
    private Integer quantity;
    private Float rating = 0f;
    @NotEmpty
    private String code;
    @NotEmpty
    private Double price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;

    public Product() {
    }

    public Product(Integer productID, String name, Integer quantity, Float rating, String code, Double price, String description, LocalDateTime createdAt, LocalDateTime updatedAt, Integer createdBy, Integer updatedBy) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.rating = rating;
        this.code = code;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Product(String name, Integer quantity, String code, Double price, String description, Integer createdBy, Integer updatedBy) {
        this.name = name;
        this.quantity = quantity;
        this.code = code;
        this.price = price;
        this.description = description;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("{productID=%d, name=%s, quantity=%d, rating=%.1f, code=%s, price=%.1f, description=%s, createdAt=%s, updatedAt=%s, createdBy=%d, updatedBy=%d}",
                productID, name, quantity, rating, code, price, description, createdAt, updatedAt, createdBy, updatedBy);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
