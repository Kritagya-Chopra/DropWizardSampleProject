package org.example.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long orderId;
    private Long userId;
    private LocalDateTime createdAt;
    private Long totalCost;
    private List<Long> products;

    public Order() {}

    public Order(Long orderId, Long userId, LocalDateTime createdAt, Long totalCost, List<Long> products) {
        this.orderId = orderId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.totalCost = totalCost;
        this.products = products;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", createdAt=" + createdAt.toString() +
                ", totalCost=" + totalCost +
                ", products=" + products +
                '}';
    }
}
