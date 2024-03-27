package org.example.service;

import org.example.dao.CartsDao;
import org.example.model.Cart;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

public  class CartsService {

    private static final String CART_NOT_FOUND = "Cart id %s not found.";
    private static final String SUCCESS = "Success...";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred while deleting part.";

    private CartsDao cartsDao;

    public CartsService(CartsDao cartsDao) {
        this.cartsDao = cartsDao;
    }


    public Cart getCart(int id) {
        Cart part = cartsDao.getCart(id);
        if (Objects.isNull(part)) {
            throw new WebApplicationException(String.format(CART_NOT_FOUND, id), Response.Status.NOT_FOUND);
        }
        return part;
    }

    public Cart createCart(Cart cart) {
        cartsDao.createCart(cart);
        return cart;
    }


    public List<Cart> getProductIds(Long cartId) {
        return cartsDao.getProductIds(cartId);
    }



    public String deletePart(final int id) {
        int result = cartsDao.deleteCart(id);
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(CART_NOT_FOUND, id), Response.Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_ERROR, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Cart> getAll() {
        return cartsDao.getAll();
    }
}
