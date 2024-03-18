package org.example.Dto;

public class OrderDto {
    private Long userId;

    private Long cartId;

    public OrderDto() {}

    public OrderDto(Long userId, Long cartId) {
        this.userId = userId;
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
