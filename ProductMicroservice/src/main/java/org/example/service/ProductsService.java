package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.example.DTO.OrderEntry;
import org.example.db.ProductsDao;
import org.example.entity.Product;
import org.jdbi.v3.sqlobject.CreateSqlObject;

public  class ProductsService {

    private static final String PRODUCT_NOT_FOUND = "Product id %s not found.";
    private static final String PRODUCT_NOT_ENOUGH = "Product id %s is low on stock.";
    private static final String SUCCESS = "Success...";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred while deleting product.";


    private ProductsDao productsDao;

    public ProductsService(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    public List<Product> getProducts() {
        return productsDao.getProducts();
    }

    public List<Product> getProducts(List<Integer> ids) {
        List<Product> ls = new ArrayList<>();
        for(int id : ids) {
            Product product = productsDao.getProduct(id);
            if (Objects.isNull(product)) {
                throw new WebApplicationException(String.format(PRODUCT_NOT_FOUND, id), Status.NOT_FOUND);
            }
            ls.add(product);
        }
        return ls;
    }

    public Product createProduct(Product product) {
        productsDao.createProduct(product);
        return productsDao.getProduct(productsDao.lastInsertId());
    }

    public Product editProduct(Product product) {
        if (Objects.isNull(productsDao.getProduct(product.getProductID()))) {
            throw new WebApplicationException(String.format(PRODUCT_NOT_FOUND, product.getProductID()),
                    Status.NOT_FOUND);
        }
        productsDao.editProduct(product);
        return productsDao.getProduct(product.getProductID());
    }

    public String deleteProduct(final int id) {
        int result = productsDao.deleteProduct(id);
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(PRODUCT_NOT_FOUND, id), Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_ERROR, Status.INTERNAL_SERVER_ERROR);
        }
    }

    public String placeOrder(List<OrderEntry> entries) {
        for (OrderEntry entry : entries) {
            int id = entry.getId();
            int quantity = entry.getQuantity();
            try{
            productsDao.updateQuantity(id, quantity);
            }catch (Exception e)
            {
                if(e.getMessage().contains("Check constraint 'products_chk_1' is violated"))
                    throw new WebApplicationException(String.format(PRODUCT_NOT_ENOUGH, id), Status.NOT_FOUND);
                throw new WebApplicationException(String.format(PRODUCT_NOT_FOUND, id), Status.NOT_FOUND);
            }
        }
        return "Updated Successfully";
    }
}