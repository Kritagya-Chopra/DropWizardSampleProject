package org.example.db;

import org.example.entity.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({UnitTestDBParameterResolver.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDaoTest {
    private ProductsDao productsDao;

    @BeforeAll
    public void setup(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    @BeforeEach
    public void preCleanDb(ProductsDao productsDao)
    {
        List<Product> all = productsDao.getProducts();

        if (!all.isEmpty()) {
            for(Product p : all)
            {
                productsDao.deleteProduct(p.getProductID());
            }
        }
    }

    @Test
    @DisplayName("Test CRUD operations for Product")
    public void testCRUDOperations() {
        // Create Product
        Product product = new Product();

        product.setName("Test Product");
        product.setQuantity(10);
        product.setRating(4.5F);
        product.setCode("TP008");
        product.setPrice(99.99);
        product.setDescription("Test Product Description");
        product.setCreatedBy(1);
        product.setUpdatedBy(1);
        productsDao.createProduct(product);


        // Retrieve Product
        List<Product> productList = productsDao.getProducts();
        int id = 0;
        for(Product p : productList)
        {
            if(p.getCode().equals(product.getCode()))
                 id = p.getProductID();
        }
        assertNotNull(productList);
        assertTrue(productList.size() > 0);

        Product retrievedProduct = productsDao.getProduct(id);
        assertNotNull(retrievedProduct);
        assertEquals(product.getCode(), retrievedProduct.getCode());


        // Update Product
        retrievedProduct.setName("Updated Test Product");
        productsDao.editProduct(retrievedProduct);

        Product updatedProduct = productsDao.getProduct(retrievedProduct.getProductID());
        assertNotNull(updatedProduct);
        assertEquals("Updated Test Product", updatedProduct.getName());

        // Delete Product
        int deletedRows = productsDao.deleteProduct(updatedProduct.getProductID());
        assertEquals(1, deletedRows);

        Product deletedProduct = productsDao.getProduct(updatedProduct.getProductID());
        assertNull(deletedProduct);
    }

    @Test
    @DisplayName("Test if product is added successfully")
    public void testProductCreation() {
        Product product = new Product();
        product.setName("New Product");
        product.setQuantity(20);
        product.setRating(3.8F);
        product.setCode("NP001");
        product.setPrice(49.99);
        product.setDescription("New Product Description");
        product.setCreatedBy(1);
        product.setUpdatedBy(1);
        productsDao.createProduct(product);

        List<Product> productList = productsDao.getProducts();
        assertNotNull(productList);
        assertTrue(productList.size() > 0);

        Product retrievedProduct = productsDao.getProduct(productList.get(productList.size() - 1).getProductID());
        assertNotNull(retrievedProduct);
        assertEquals(product.getName(), retrievedProduct.getName());
    }

    @Test
    @DisplayName("Test if product is updated successfully")
    public void testProductUpdate() {
        // Create a product first
        Product product = new Product();
        product.setName("Update Test Product");
        product.setQuantity(5);
        product.setRating(4.2F);
        product.setCode("UTP001");
        product.setPrice(79.99);
        product.setDescription("Update Test Product Description");
        product.setCreatedBy(1);
        product.setUpdatedBy(1);
        productsDao.createProduct(product);

        // Update Product
        List<Product> productList = productsDao.getProducts();
        assertNotNull(productList);
        assertTrue(productList.size() > 0);

        Product retrievedProduct = productList.get(productList.size() - 1);
        assertNotNull(retrievedProduct);

        retrievedProduct.setName("Updated Product Name");
        productsDao.editProduct(retrievedProduct);

        Product updatedProduct = productsDao.getProduct(retrievedProduct.getProductID());
        assertNotNull(updatedProduct);
        assertEquals("Updated Product Name", updatedProduct.getName());
    }

    @Test
    @DisplayName("Test if product is deleted successfully")
    public void testProductDeletion() {
        // Create a product first
        Product product = new Product();
        product.setName("Delete Test Product");
        product.setQuantity(15);
        product.setRating(4.0F);
        product.setCode("DTP001");
        product.setPrice(99.99);
        product.setDescription("Delete Test Product Description");
        product.setCreatedBy(1);
        product.setUpdatedBy(1);
        productsDao.createProduct(product);

        // Delete Product
        List<Product> productList = productsDao.getProducts();
        assertNotNull(productList);
        assertTrue(productList.size() > 0);

        Product retrievedProduct = productList.get(productList.size() - 1);
        assertNotNull(retrievedProduct);

        int deletedRows = productsDao.deleteProduct(retrievedProduct.getProductID());
        assertEquals(1, deletedRows);

        Product deletedProduct = productsDao.getProduct(retrievedProduct.getProductID());
        assertNull(deletedProduct);
    }
}
