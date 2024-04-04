package org.example.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class OrderEntry {
    @JsonProperty("id")
    @NotNull
    Integer id;
    @JsonProperty("quantity")
    @NotNull
    Integer quantity;

    public OrderEntry() {
    }

    public OrderEntry(Integer id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
