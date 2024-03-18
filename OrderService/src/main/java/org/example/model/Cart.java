package org.example.model;

public class Cart {
    private Long cartId;
    private Long productId;
    private Long userId;
    private Integer quantity;
    private Long price;

    public Cart() {}

    public Cart(Long cartId, Long productId, Long userId, Integer quantity, Long price) {
        this.cartId = cartId;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
